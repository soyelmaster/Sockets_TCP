package IHiloConexiones;

import java.util.EventObject;

/*
 * @author Miguel Angel Mendoza Castro
 */
public class onConexionesRecibidasEvent extends EventObject {

    //private Object mensaje;
    public onConexionesRecibidasData data;

    public onConexionesRecibidasEvent(Object source, onConexionesRecibidasData msj) {
        super(source);        
        this.data = msj;
    }

    public onConexionesRecibidasData getData() {
        return data;
    }
  
}
