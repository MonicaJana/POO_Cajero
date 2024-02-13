import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    // Método para establecer la conexión a la base de datos
    public Connection conexionBase() {
        // Configuración de la conexión a la base de datos
        String url = "jdbc:mysql://localhost:3306/cajero";
        String usuarioDB = "root";
        String contrasenaDB = "moni123";

        Connection conexion = null;
        try {
            // Establecer la conexión
            conexion = DriverManager.getConnection(url, usuarioDB, contrasenaDB);
            //JOptionPane.showMessageDialog(null, "Conexión exitosa a la base de datos.");
        } catch (SQLException e) {
            // Manejar errores al establecer la conexión
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + e.getMessage());
        }
        return conexion;
    }
}
