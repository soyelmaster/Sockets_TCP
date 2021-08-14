package SocketServidorTCP;

import java.net.Socket;

/*
 * @author Miguel Angel Mendoza Castro
 */
public class DataCliente {

    private Socket socketCliente;
    private HiloCliente hiloCliente;

    public Socket getSocketCliente() {
        return socketCliente;
    }

    public DataCliente(Socket socketCliente, HiloCliente hiloCliente) {
        this.socketCliente = socketCliente;
        this.hiloCliente = hiloCliente;
    }

    public DataCliente(HiloCliente hiloCliente) {
        this.hiloCliente = hiloCliente;
    }

    public DataCliente(Socket socketCliente) {
        this.socketCliente = socketCliente;
    }

    public void setSocketCliente(Socket socketCliente) {
        this.socketCliente = socketCliente;
    }

    public HiloCliente getHiloCliente() {
        return hiloCliente; 
    }

    public void setHiloCliente(HiloCliente hiloCliente) {
        this.hiloCliente = hiloCliente;
    }

    public void Enviar(Object obj) {
        this.hiloCliente.Enviar(obj);
    }
    
    public int getID() {
        return this.hiloCliente.getid();
    }
}
