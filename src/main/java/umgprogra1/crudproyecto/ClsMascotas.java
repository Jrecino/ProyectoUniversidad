package umgprogra1.crudproyecto;

import com.mysql.cj.jdbc.CallableStatement;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class ClsMascotas {

    int codigo;
    String tipoanimal;
    int edad;
    String nombre;
    String diagnostico;
    String procedimiento;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getTipoanimal() {
        return tipoanimal;
    }

    public void setTipoanimal(String tipoanimal) {
        this.tipoanimal = tipoanimal;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getProcedimiento() {
        return procedimiento;
    }

    public void setProcedimiento(String procedimiento) {
        this.procedimiento = procedimiento;
    }

    public void InsertarMascota(JTextField paramtipoanimal, JTextField paramedad, JTextField paramnombre, JTextField paramdiagnostico, JTextField paramprocedimiento) {
        setTipoanimal(paramtipoanimal.getText());
        setEdad(Integer.parseInt(paramedad.getText()));
        setNombre(paramnombre.getText());
        setDiagnostico(paramdiagnostico.getText());
        setProcedimiento(paramprocedimiento.getText());

        ClsConexion objetoConexion = new ClsConexion();

        String consulta = "INSERT INTO mascotas (tipo_animal, edad, nombre, diagnostico, procedimiento) VALUES (?, ?, ?, ?, ?)";

        try {
            Connection connection = objetoConexion.estableceConexion();
            PreparedStatement ps = connection.prepareStatement(consulta);

            ps.setString(1, getTipoanimal());
            ps.setInt(2, getEdad());
            ps.setString(3, getNombre());
            ps.setString(4, getDiagnostico());
            ps.setString(5, getProcedimiento());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Se insertó correctamente la mascota");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo insertar la mascota: " + e.toString());
        }
    }

    public void MostrarMascotas(JTable paramTablaTotalMascotas) {
        ClsConexion objetoConexion = new ClsConexion();

        DefaultTableModel modelo = new DefaultTableModel();

        TableRowSorter<TableModel> OrdenarTabla = new TableRowSorter<>(modelo);
        paramTablaTotalMascotas.setRowSorter(OrdenarTabla);

        String sql = "";

        modelo.addColumn("id");
        modelo.addColumn("tipoanimal");
        modelo.addColumn("edad");
        modelo.addColumn("nombre");
        modelo.addColumn("diagnostico");
        modelo.addColumn("procedimiento");

        paramTablaTotalMascotas.setModel(modelo);

        sql = "select * from mascotas;";

        String[] datos = new String[6];
        Statement st;
        try {
            Connection connection = objetoConexion.estableceConexion();
            st = (Statement) connection.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                datos[0] = rs.getString("id");
                datos[1] = rs.getString("tipo_animal");
                datos[2] = rs.getString("edad");
                datos[3] = rs.getString("nombre");
                datos[4] = rs.getString("diagnostico");
                datos[5] = rs.getString("procedimiento");
                modelo.addRow(datos);
            }
            paramTablaTotalMascotas.setModel(modelo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar las mascotas: " + e.toString());
        }
    }

    public void SeleccionarMascota(JTable paramTablaTotalMascotas, JTextField paramId, JTextField paramtipoanimal, JTextField paramedad, JTextField paramnombre, JTextField paramdiagnostico, JTextField paramprocedimiento, JTextField txtprocedimiento) {
        try {
            int fila = paramTablaTotalMascotas.getSelectedRow();

            if (fila >= 0) {
                paramId.setText(String.valueOf(paramTablaTotalMascotas.getValueAt(fila, 0).toString()));
                paramtipoanimal.setText(String.valueOf(paramTablaTotalMascotas.getValueAt(fila, 1).toString()));
                paramedad.setText(String.valueOf(paramTablaTotalMascotas.getValueAt(fila, 2).toString()));
                paramnombre.setText(String.valueOf(paramTablaTotalMascotas.getValueAt(fila, 3).toString()));
                paramdiagnostico.setText(String.valueOf(paramTablaTotalMascotas.getValueAt(fila, 4).toString()));
                paramprocedimiento.setText(String.valueOf(paramTablaTotalMascotas.getValueAt(fila, 5)));
            } else {
                JOptionPane.showMessageDialog(null, " Fila no selecionada");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al seleccionar la mascota: " + e.toString());
        }
    }
public void ModificarMascota(int id, String tipoanimal, int edad, String nombre, String diagnostico, String procedimiento) {
    ClsConexion objetoConexion = new ClsConexion();

String consulta = "UPDATE mascotas SET tipo_animal=?, edad=?, nombre=?, diagnostico=?, procedimiento=? WHERE id=?;";

try {
    Connection connection = objetoConexion.estableceConexion();
    PreparedStatement ps = connection.prepareStatement(consulta);
    ps.setString(1, tipoanimal);
    ps.setInt(2, edad);
    ps.setString(3, nombre);
    ps.setString(4, diagnostico);
    ps.setString(5, procedimiento);
    ps.setInt(6, id);

    ps.executeUpdate();

    JOptionPane.showMessageDialog(null, "Se modificó correctamente la mascota");
} catch (Exception e) {
    JOptionPane.showMessageDialog(null, "No se pudo modificar la mascota: " + e.toString());
}
}
    public void ElminarMascotas(JTextField paramCodigo){
        setCodigo(Integer.parseInt(paramCodigo.getText()));
        ClsConexion objetoConexion  = new ClsConexion();
        String consulta ="DELETE FROM mascotas WHERE Id = ?;";
        
        try {
            CallableStatement cs = (CallableStatement) objetoConexion.estableceConexion().prepareCall(consulta);
            cs.setInt(1, codigo);
            cs.execute();
            JOptionPane.showMessageDialog(null, "Se elimino correctamente la mascota");
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, "No se pudo elminar "+e.toString());
        }
    }

    
   
    }

    


