package SocketServidorTCP;

import IHiloConexiones.onConexionesRecibidasData;
import IHiloCliente.onDatosRecibidosEvent;
import Objeto.Persona;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import IHiloConexiones.IHiloConexiones;
import IHiloCliente.IHiloCliente;
import IHiloCliente.onDatosRecibidosData;
import IHiloConexiones.onConexionesRecibidasEvent;
import IServidor.IServidorSocket;
import IServidor.onMessageServerSocketData;
import IServidor.onMessageServerSocketEvent;
import Objeto.Ruta;
import javax.swing.event.EventListenerList;

public class Servidor implements IHiloCliente, IHiloConexiones {

    private ServerSocket ServidorSocket;
    private Socket clienteSocket;
    public HashMap<Integer, DataCliente> clientes;
    private HiloConexiones hiloConexiones = null;
    private HiloCliente hiloCliente = null;
    private DataCliente dataCliente = null;
    public EventListenerList ListaEscuchadoresIServidorSocket = null;
    int conexiones = 0;
    int puerto;
    Object mensaje;

    // HiloCliente    -> solo usa lista Mensajes
    // HiloConexiones -> solo usa lista de Conexiones 
    public Servidor(int puerto) {
        this.puerto = puerto;
        try {
            ServidorSocket = new ServerSocket(puerto);
            clientes = new HashMap<>();
            hiloConexiones = new HiloConexiones(ServidorSocket);
            hiloConexiones.addEscuchadorConexion(this);
            ListaEscuchadoresIServidorSocket = new EventListenerList();
        } catch (IOException e) {
            System.out.println("No se pudo abrir el socket en el puerto " + puerto);
            System.exit(-1);
        }
    }

    public void addConexionServidorListener(IHiloConexiones l) {
        hiloConexiones.addEscuchadorConexion(l);//registra escuchadores conexiones
    }

    public void removeConexionServidorListener(IHiloConexiones l) {
        hiloConexiones.removeEscuchadorConexion(l);
    }

    public void EnviarUno(Object msj, int id) {
        DataCliente hilo = clientes.get(id);
        try {
            hilo.Enviar(msj);
        } catch (Exception e) {
            System.out.println("Error al enviar el mensaje al cliente:" + id + " error:" + e);
        }

    }

    public void EnviarAll(Object msj) {
        Collection<DataCliente> hilos = clientes.values();
        for (DataCliente hilo : hilos) {
            try {
                hilo.Enviar(msj);
            } catch (Exception e) {
                System.out.println("Error al enviar el mensaje a todos los cliente, error:" + e);
            }
        }
    }

    public void EnviarAll1() {
        Persona a = new Persona(1, "Miguel Angel", "Mendoza");        
        Gson g = new Gson();
        String msj = g.toJson(a);        
        Collection<DataCliente> hilos = clientes.values();
        for (DataCliente hilo : hilos) {
            try {
                hilo.Enviar(msj);
            } catch (Exception e) {
                System.out.println("Error al enviar el mensaje a todos los cliente, error:" + e);
            }
        }
    }

    public void stopServer() {
        try {
            if (ServidorSocket != null) {
                ServidorSocket.close();
            }

        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void iniciarServer() {
        hiloConexiones.start();
    }

    public ServerSocket getServer() {
        return ServidorSocket;
    }

    public void setServer(ServerSocket server) {
        this.ServidorSocket = server;
    }

    // Receptores IHiloConexions
    @Override
    public void onNuevoCliente(onConexionesRecibidasEvent evt) {
        conexiones = conexiones + 1;
        //clienteSocket = evt.Dato; //primero obtener el socket que me lo estan pasando por el evento
        clienteSocket = evt.getData().getDato();
        hiloCliente = new HiloCliente(clienteSocket, conexiones); //2do. crear HiloCliente, eso lo hacemos en hiloCliente
        hiloCliente.addEscuchadorMensaje(this);
        hiloCliente.start();
        dataCliente = new DataCliente(clienteSocket, hiloCliente);//ahora creamos dataCliente
        clientes.put(conexiones, dataCliente);
//        hiloCliente.addMensajeListener(this);        
//        hiloCliente.start();
        String ip = (evt.getData().getIp().toString() + "").substring(1, (evt.getData().getIp().toString() + "").length());
        System.out.println("nuevo cliente conectado con puerto:" + evt.getData().getPuerto() + ", IP: " + ip);
        System.out.println("cantidad de clientes conectados:" + clientes.size());
    }
//**************************************************************************
    // Receptores IHiloCliente
    @Override
    public void onDatosRecibidos(onDatosRecibidosEvent evt) { // Receptor
        //int id = evt.data.id;
        int id = evt.getData().getId();
        //String msg = (String) evt.data.obj;
        String msg = (String) evt.getData().getObj();
        System.out.println("los datos recibidos del cliente " + id + " son :" + msg);
        onMessageServerSocket(id, msg);
    }

    @Override
    public void onConexionTerminada(onDatosRecibidosEvent evt) {//mensajes
        //int id = (int) evt.data.id;
        int id = (int) evt.getData().getId();
        clientes.remove(id);
        //System.out.println("se quito el cliente " + evt.data.id);
        System.out.println("se quito el cliente " + evt.getData().getId());
    }
//****************************************************************************
    //Manejadores de la lista  IServidor
    public void addListaIServidor(IServidorSocket l) {
        ListaEscuchadoresIServidorSocket.add(IServidorSocket.class,l);
    }

    public void removeListaIServidor(IServidorSocket l) {
        ListaEscuchadoresIServidorSocket.remove(IServidorSocket.class, l);
    }

    // Despachadores IServidor
    public void onMessageServerSocket(int id, String msg) {
        onMessageServerSocketData data = new onMessageServerSocketData(id, msg);
        onMessageServerSocketEvent evt = new onMessageServerSocketEvent(this, data);
        IServidorSocket[] ls = ListaEscuchadoresIServidorSocket.getListeners(IServidorSocket.class);//aqui estoy reLanzando el evento
        for (IServidorSocket listener : ls) {// es decir el msj que  me llega del cliente lo vuelvo a mandar a todos los escuchadores
            listener.OnMessageServerSocket(evt);// de eventos de mensajes del servidorSocket
        }
        System.out.println("origen:" + evt.getSource());
    }
}
