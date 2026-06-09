package daos;
import clases.DetalleTrabajador;
import clases.TrabajadorDescuento;
import config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class TrabajadorDao {
    
    public ArrayList<TrabajadorDescuento> descuentosLegales(String run1,String run2,String run3){
        ArrayList<TrabajadorDescuento> datos = new ArrayList();
        ResultSet res;
        try {
            Conexion db = new Conexion();
            Connection cnn = db.conectar();
            String consulta = "SELECT t.nombre,t.apellido1,t.apellido2,t.run,s.sueldo,"
                    + "ROUND(s.sueldo * 0.1049,0) AS afp,ROUND(s.sueldo * 0.07,0) AS fonasa "
                    + "FROM trabajadores t "
                    + "INNER JOIN sueldos s ON t.trabajador_id = s.trabajador_id "
                    + "WHERE run IN(?,?,?)"
                    + "ORDER BY t.nombre ASC";
            PreparedStatement ps = cnn.prepareStatement(consulta);
            ps.setString(1, run1);
            ps.setString(2, run2);
            ps.setString(3, run3);
            res = ps.executeQuery();
            while(res.next()){
                TrabajadorDescuento td = new TrabajadorDescuento(
                    res.getString("nombre"),
                    res.getString("apellido1"),
                    res.getString("apellido2"),
                    res.getString("run"),
                    res.getInt("sueldo"),
                    res.getInt("afp"),
                    res.getInt("fonasa")
                );
                datos.add(td);
            }
        } catch (SQLException e) {
            System.out.println(e.getCause());
        }
        return datos;
    }
    
    public ArrayList<DetalleTrabajador> obtenerDetalle(){
        ArrayList<DetalleTrabajador> datos = new ArrayList();
        ResultSet res;
        try {
            Conexion db = new Conexion();
            Connection cnn = db.conectar();
            String consulta = "SELECT trabajador_id,nombre,run"
                    + " FROM trabajadores"
                    + " ORDER BY nombre ASC";
            PreparedStatement ps = cnn.prepareStatement(consulta);
            res = ps.executeQuery();
            while(res.next()){
                DetalleTrabajador dt = new DetalleTrabajador(
                        res.getInt("trabajador_id"),
                        res.getString("nombre"),
                        res.getString("run")
                );
                datos.add(dt);
            }
        } catch (SQLException e) {
            System.out.println(e.getCause());
        }
        return datos;
    }
    
    public ArrayList<TrabajadorDescuento> itemSueldosDAO(int id){
        ArrayList<TrabajadorDescuento> datos = new ArrayList();
        ResultSet res;
        try {
            Conexion db = new Conexion();
            Connection cnn = db.conectar();
            String consulta = "SELECT t.nombre,"
                    + "t.apellido1,t.apellido2,t.run,s.sueldo,"
                    + "ROUND(s.sueldo * 0.105,0) AS afp,"
                    + "ROUND(s.sueldo * 0.07,0) AS fonasa "
                    + "FROM trabajadores t INNER JOIN sueldos s ON "
                    + "t.trabajador_id = s.trabajador_id AND "
                    + "t.trabajador_id = ? "
                    + "ORDER BY t.apellido1 ASC";
            PreparedStatement ps = cnn.prepareStatement(consulta);
            ps.setInt(1, id);
            res = ps.executeQuery();
            while(res.next()){
                TrabajadorDescuento td = new TrabajadorDescuento(
                    res.getString("nombre"),
                    res.getString("apellido1"),
                    res.getString("apellido2"),
                    res.getString("run"),
                    res.getInt("sueldo"),
                    res.getInt("afp"),
                    res.getInt("fonasa")
                );
                datos.add(td);
            }
        } catch (SQLException e) {
            System.out.println(e.getCause());
        }
        return datos;
    }
    
    
}
