package SocketServidorTCP;

import IHiloConexiones.onConexionesRecibidasData;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.event.EventListenerList;
import IHiloConexiones.IHiloConexiones;
import IHiloConexiones.onConexionesRecibidasEvent;
public class HiloConexiones extends Thread {

    public ServerSocket ServidorSocket;
    Socket clienteSocket = null;
    private boolean Continue;
    public EventListenerList ListaEscuchadoresConexiones = null;    
    
    public HiloConexiones(ServerSocket s) {
        this.Continue = true;
        ServidorSocket = s;
        ListaEscuchadoresConexiones = new EventListenerList();    
    }

    public void addEscuchadorConexion(IHiloConexiones l) {
        ListaEscuchadoresConexiones.add(IHiloConexiones.class, l);
    }

    public void removeEscuchadorConexion(IHiloConexiones l) {
        ListaEscuchadoresConexiones.remove(IHiloConexiones.class, l);
    }    

    @Override
    public void run() {
        while (Continue) {

            try {
                clienteSocket = ServidorSocket.accept();
                NuevoCliente(clienteSocket);
            } catch (IOException ex) {
                System.out.println("Error al crear nuevo cliente");
            }
        }
    }

    public void NuevoCliente(Socket socket) {
        
        onConexionesRecibidasData data = new onConexionesRecibidasData(this, socket);
        onConexionesRecibidasEvent evt = new onConexionesRecibidasEvent(this, data);
        
        if (ListaEscuchadoresConexiones.getListenerCount() > 0) {            
            IHiloConexiones[] ls = ListaEscuchadoresConexiones.getListeners(IHiloConexiones.class);
            for (IHiloConexiones l : ls) {
                l.onNuevoCliente(evt);
            }
        }
    }   

    public void stopControladorServer() {
        try {
            this.Continue = false;
            this.ServidorSocket.close();

        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public int obtenerCantidadDeListaEscuchadoresConexiones() {
        return (ListaEscuchadoresConexiones.getListenerCount());
    }   

}
