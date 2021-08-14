
package IHiloCliente;

/*
 * @author Usuario
 */
public class onDatosRecibidosData {
    public Object obj;
    public int id;
    
    public onDatosRecibidosData(Object source, int id)
    {
        this.obj = source;
        this.id = id;
    } 

    public Object getObj() {
        return obj;
    }

    public int getId() {
        return id;
    }
    
}
