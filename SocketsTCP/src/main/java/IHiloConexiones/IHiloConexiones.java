package IHiloConexiones;

import IHiloConexiones.onConexionesRecibidasData;
import java.util.EventListener;

/*
 * @author Miguel Angel Mendoza Castro
 */
public interface IHiloConexiones extends EventListener {

    void onNuevoCliente(/*onConexionesRecibidasData*/onConexionesRecibidasEvent evt);
    
}
