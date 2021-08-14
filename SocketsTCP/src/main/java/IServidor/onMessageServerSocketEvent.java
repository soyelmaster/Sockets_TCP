
package IServidor;

import java.util.EventObject;

/*
 * @author Usuario
 */

public class onMessageServerSocketEvent extends EventObject {

    public onMessageServerSocketData data;

    public onMessageServerSocketEvent(Object source, onMessageServerSocketData data) {
        super(source); 
        this.data = data;
    }

    public onMessageServerSocketData getData() {
        return data;
    }

}
