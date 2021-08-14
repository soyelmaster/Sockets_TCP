package SocketServidorTCP;

import IHiloCliente.onDatosRecibidosData;
import IHiloCliente.onDatosRecibidosEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.EventListenerList;
import IHiloCliente.IHiloCliente;

public class HiloCliente extends Thread {

    private Socket clienteSocket;
    private int id;
    private boolean Conectado;
    private ObjectOutputStream streamOut = null;
    private ObjectInputStream streamIn = null;
    public EventListenerList ListaEscuchadoresMensajes = null;
    private onDatosRecibidosData data;

    public HiloCliente(Socket cliente, int id) {
        clienteSocket = cliente;
        this.id = id;
        Conectado = true;
        ListaEscuchadoresMensajes = new EventListenerList();
        try {
            streamOut = new ObjectOutputStream((cliente.getOutputStream()));
            streamIn = new ObjectInputStream((cliente.getInputStream()));

        } catch (IOException e) {
            System.out.println("Error: No se pudo crear in y out del hilo cliente :" + clienteSocket.getPort());
        }
    }

    public void addEscuchadorMensaje(IHiloCliente l) {
        ListaEscuchadoresMensajes.add(IHiloCliente.class, l);
        System.out.println("Nueva clase agregada");
    }

    public void removeEscuchadorMensaje(IHiloCliente l) {
        ListaEscuchadoresMensajes.remove(IHiloCliente.class, l);
    }

    @Override
    public void run() {

        while (Conectado) {
            try {
                onDatosRecibidos(streamIn.readObject());                
            } catch (IOException | ClassNotFoundException ioe) {
                System.out.println("Error al recibir mensaje o se desconecto cliente con Puerto: " + getPuerto() + "; IP: " + (getIp() + "").substring(1, (getIp() + "").length()));
                QuitarHilo();
                Desconectar();
            }
        }
    }

    public void onDatosRecibidos(Object readObject) {
        //onDatosRecibidosData data = new onDatosRecibidosData(readObject, this.id);
        data = new onDatosRecibidosData(readObject, this.id);
        onDatosRecibidosEvent evt = new onDatosRecibidosEvent(this, data);
        if (ListaEscuchadoresMensajes.getListenerCount() > 0) {
            IHiloCliente[] ls = ListaEscuchadoresMensajes.getListeners(IHiloCliente.class);
            for (IHiloCliente l : ls) {
                l.onDatosRecibidos(evt);
            }
        }
    }

    public void Desconectar() {
        Conectado = false;
        try {
            if (streamOut != null) {
                streamOut.close();
            }
            if (streamIn != null) {
                streamIn.close();
            }
            if (clienteSocket != null) {
                clienteSocket.close();
            }
            clienteSocket = null;
            streamOut = null;
            streamIn = null;
        } catch (IOException ex) {
            Logger.getLogger(HiloCliente.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    public void Enviar(Object obj) {
        try {
            streamOut.writeObject(obj);
            streamOut.flush();//limpia el buffer
        } catch (IOException ioe) {
            System.out.println(getPuerto() + "Error al enviar: " + ioe.getMessage());
            QuitarHilo();
            Desconectar();
        }
    }

    public synchronized void QuitarHilo() {
        onDatosRecibidosData data = new onDatosRecibidosData(this, this.id);
        onDatosRecibidosEvent evt = new onDatosRecibidosEvent(this, data);

        if (ListaEscuchadoresMensajes.getListenerCount() > 0) {
            IHiloCliente[] ls = ListaEscuchadoresMensajes.getListeners(IHiloCliente.class);
            for (IHiloCliente l : ls) {
                l.onConexionTerminada(evt);
            }
        }
    }

    public int getPuerto() {
        return clienteSocket.getPort();
    }

    public InetAddress getIp() {
        return clienteSocket.getInetAddress();
    }

    public String saberMiIp() {
        String ip = "";
        try {
            InetAddress miIp = InetAddress.getLocalHost();
            ip = miIp.getHostAddress();
        } catch (UnknownHostException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return ip;
    }

    public int getid() {
        return this.id;
    }
}
