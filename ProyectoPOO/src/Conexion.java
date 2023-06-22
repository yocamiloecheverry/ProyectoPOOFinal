
import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sound.midi.Soundbank;
import java.util.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kmili
 */
public class Conexion {

    private final String driver = "com.mysql.jdbc.Driver";
    private final String url = "jdbc:mysql://localhost:3306/proyecto_bd";
    private final String usuario = "root";
    private final String contrasenia = "";
    private Connection conexion;

    public boolean getConexion() {
        try {
            Class.forName(driver);
            conexion = (Connection) DriverManager.getConnection(url, usuario, contrasenia);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("No hay conexion " + ex.getMessage());
            return false;
        }
        return true;
    }

    public void cerrarConexion() {
        try {
            conexion.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int agregarPersona(Persona unaPersona) {
        int filasFilas = 0;
        List<String> myList = Arrays.asList("A1", "B2", "C3", "D4", "D5");
        Random r = new Random();
        int randomitem = r.nextInt(5);
        String codCliente = myList.get(randomitem);
        try {
            PreparedStatement sentenciaPersona = conexion.prepareStatement("INSERT INTO persona VALUES (?, ?, ?, ?, ?, ?, ?)");
            PreparedStatement sentenciaCliente = conexion.prepareStatement("INSERT cliente VALUES(?, ?)");
            sentenciaPersona.setString(1, unaPersona.getCedula());
            sentenciaPersona.setString(2, unaPersona.getNombre());
            sentenciaPersona.setString(3, unaPersona.getApellidos());
            sentenciaPersona.setString(4, unaPersona.getDireccion());
            sentenciaPersona.setString(5, unaPersona.getEmail());
            sentenciaPersona.setString(6, unaPersona.getCelular());
            sentenciaPersona.setString(7, unaPersona.getContrasenia());
            sentenciaCliente.setString(1, unaPersona.getCelular());
            sentenciaCliente.setString(2, codCliente);
            filasFilas = sentenciaPersona.executeUpdate();
            sentenciaCliente.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
        return filasFilas;
    }

    /*public int agregarCliente (Cliente unCliente){
        int filasCliente = 0;
        try {
            PreparedStatement sentencia = conexion.prepareStatement("INSERT INTO cliente VALUES (?, ?)");
            sentencia.setString(1, unCliente.getCelular());
            sentencia.setString(2, unCliente.getCodigoCliente());
            filasCliente = sentencia.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
        return filasCliente;
    }*/
    public String verificarUsuario(String celular, String contrasenia) {
        ResultSet rs = null;
        try {
            PreparedStatement sentencia = conexion.prepareStatement("SELECT * "
                    + "FROM persona p INNER JOIN administrador a ON P.celular = a.celular "
                    + "WHERE p.celular = ? AND  p.contrasenia = ?");
            sentencia.setString(1, celular);
            sentencia.setString(2, contrasenia);
            rs = sentencia.executeQuery();
            if (rs.next()) {
                return "ADMINISTRADOR";
            } else {
                sentencia = conexion.prepareStatement("SELECT * FROM persona p INNER JOIN cliente c ON p.celular = c.celular WHERE p.celular = ? AND p.contrasenia = ?");
                sentencia.setString(1, celular);
                sentencia.setString(2, contrasenia);
                rs = sentencia.executeQuery();
                if (rs.next()) {
                    return "CLIENTE";
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    public String[] listaClientes() {
        String[] arrayClientes = null;
        ResultSet rs = null;
        try {
            PreparedStatement sentencia = conexion.prepareCall("SELECT CONCAT(celular, '-', saldo) FROM cuenta");
            rs = sentencia.executeQuery();
            rs.last();
            int filas = rs.getRow();
            if (filas > 0) {
                arrayClientes = new String[filas];
                rs.first();
                int posicion = 0;
                do {
                    Cliente objCliente = new Cliente();
                    objCliente.setCelular(rs.getString(1));
                    arrayClientes[posicion] = objCliente.getCelular();
                    posicion++;
                } while (rs.next());
            } else {
                arrayClientes = null;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return arrayClientes;
    }

    public int actualizarSaldoCliente(String celular, int saldo) {
        int filasAfectadas = 0;
        PreparedStatement sentencia = null;
        try {
            sentencia = conexion.prepareStatement("UPDATE cuenta SET saldo = ? WHERE celular = ?");
            sentencia.setString(2, celular);
            sentencia.setInt(1, saldo);

            filasAfectadas = sentencia.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return filasAfectadas;
    }

    public String getSaldoCliente(String celularCliente) {
        ResultSet rs = null;
        String saldoCliente = "";
        try {
            PreparedStatement sentencia = conexion.prepareCall("SELECT * FROM cuenta WHERE celular = ?");
            sentencia.setString(1, celularCliente);
            rs = sentencia.executeQuery();
            if (rs.next()) {
                saldoCliente = rs.getString(2);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return saldoCliente;
    }

    public int consignarCliente(String celular, String nuevoSaldo) {
        int filasAfectadas = 0;
        try {
            PreparedStatement sentencia = conexion.prepareCall("UPDATE cuenta SET saldo = ? WHERE celular = ?");
            sentencia.setString(1, nuevoSaldo);
            sentencia.setString(2, celular);
            filasAfectadas = sentencia.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return filasAfectadas;
    }

    public int actualizarResta(String celular, int resta) {
        int filasAfectadas = 0;
        try {
            PreparedStatement senetencia = conexion.prepareCall("UPDATE cuenta SET saldo = ? WHERE celular = ?");
            senetencia.setInt(1, resta);
            senetencia.setString(2, celular);
            filasAfectadas = senetencia.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return filasAfectadas;
    }

    public Object[][] tablaPersonas() {
        ResultSet rs = null;
        Object[][] array = null;
        try {
            PreparedStatement sentencia = conexion.prepareStatement("SELECT * FROM persona");
            rs = sentencia.executeQuery();
            rs.last();
            int filas = rs.getRow();
            if (filas > 0) {
                array = new Object[filas][7];
                rs.first();
                int posicion = 0;
                do {
                    array[posicion][0] = rs.getString(1);
                    array[posicion][1] = rs.getString(2);
                    array[posicion][2] = rs.getString(3);
                    array[posicion][3] = rs.getString(4);
                    array[posicion][4] = rs.getString(5);
                    array[posicion][5] = rs.getString(6);
                    array[posicion][6] = rs.getString(7);
                    posicion++;
                } while (rs.next());
            } else {
                array = null;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return array;
    }

    public int actualizarPersona(Persona unPersona) {
        int filasAfectadas = 0;
        try {
            PreparedStatement sentencia = conexion.prepareCall("UPDATE persona SET cedula=?, nombre=?, apellidos=?, direccion=?,email=?,contrasenia=? WHERE celular=?");
            sentencia.setString(1, unPersona.getCedula());
            sentencia.setString(2, unPersona.getNombre());
            sentencia.setString(3, unPersona.getApellidos());
            sentencia.setString(4, unPersona.getDireccion());
            sentencia.setString(5, unPersona.getEmail());
            sentencia.setString(6, unPersona.getContrasenia());
            sentencia.setString(7, unPersona.getCelular());
            filasAfectadas = sentencia.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return filasAfectadas;
    }

    public void eliminarPersona(String celular) {

        try {
            PreparedStatement sentencia = conexion.prepareCall("DELETE FROM cliente WHERE celular =?");

            PreparedStatement sentencia2 = conexion.prepareStatement("DELETE FROM persona WHERE celular=?");

            sentencia.setString(1, celular);
            sentencia2.setString(1, celular);
            sentencia.executeUpdate();
            sentencia2.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int consignar(String celular, String saldo) {
        int filasAfectadas = 0;
        try {
            PreparedStatement sentencia = conexion.prepareCall("INSERT INTO cuenta(saldo=?)VALUES (?) WHERE celular=?");
            sentencia.setString(1, saldo);
            sentencia.setString(2, celular);
            filasAfectadas = sentencia.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return filasAfectadas;
    }
}
