package IHiloConexiones;

import java.net.InetAddress;
import java.net.Socket;
import java.util.EventObject;

/*
 * @author Miguel Angel Mendoza Castro
 */
public class onConexionesRecibidasData /*extends EventObject*/ {

    public Object obj;
    public Socket Dato;

    public onConexionesRecibidasData(Object source, Socket dato) {
        //super(source);
        this.obj = source;
        this.Dato = dato;
    }

    public Socket getDato() {
        return Dato;
    }

    public void setDato(Socket Dato) {
        this.Dato = Dato;
    }

    public InetAddress getIp() {
        return this.Dato.getInetAddress();
    }

    public int getPuerto() {
        return this.Dato.getPort();
    }

}
