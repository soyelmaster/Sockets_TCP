package IHiloCliente;

import java.util.EventObject;

public class onDatosRecibidosEvent extends EventObject {

    public onDatosRecibidosData data;

    public onDatosRecibidosEvent(Object source, onDatosRecibidosData data) {
        super(source); 
        this.data = data;
    }

    public onDatosRecibidosData getData() {
        return data;
    }
}
