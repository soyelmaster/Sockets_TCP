
package IServidor;

/*
 * @author Usuario
 */

public class onMessageServerSocketData {
    public String msj;
    public int id;
    
    public onMessageServerSocketData( int id,String msj)
    {
        this.msj = msj;
        this.id = id;
    } 

    public String getMsj() {
        return msj;
    }

    public void setMsj(String msj) {
        this.msj = msj;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    
}