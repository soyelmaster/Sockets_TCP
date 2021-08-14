
package IServidor;

import IHiloCliente.onDatosRecibidosEvent;
import IHiloConexiones.onConexionesRecibidasEvent;
import java.util.EventListener;

/*
 * @author Miguel Angel Mendoza Castro
 */
public interface IServidorSocket extends EventListener{
    
    void OnMessageServerSocket(onMessageServerSocketEvent evt);
    
}
