
package umgprogra1.crudproyecto;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author jjrec
 */
public class ClsConexion {
    
    Connection conectar = null;
    
    String usuario = "root";
    String contrasenia = "Umg2023*";
    String  bd= "bd_mascotas";
    String ip= "localhost";
    String puerto = "3306";
    
    String cadena = "jdbc:mysql://"+ip+":"+puerto+"/"+bd;
    
    public Connection estableceConexion (){
        try {
           Class.forName("com.mysql.cj.jdbc.Driver");
           conectar = DriverManager.getConnection(cadena, usuario,contrasenia);
           //JOptionPane.showMessageDialog(null, "la conexion se ha realizado con exito");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "error al conectarse a la base de datos"+e.toString());
        }
        return conectar;
    }
    
    
}
