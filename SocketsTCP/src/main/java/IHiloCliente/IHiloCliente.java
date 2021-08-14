package IHiloCliente;

import java.util.EventListener;

public interface IHiloCliente extends EventListener {

    void onDatosRecibidos(onDatosRecibidosEvent evt);

    void onConexionTerminada(onDatosRecibidosEvent evt);
}
