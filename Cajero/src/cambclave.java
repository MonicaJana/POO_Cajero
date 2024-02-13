import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class cambclave extends JFrame {
    private JPasswordField clv1;
    private JPasswordField clv2;
    private JButton aceptar;
    private JPanel panel6;
    private JTextField userr;
    private JButton menuButton;

    public cambclave() {
        super("Cambio de clave");
        setContentPane(panel6);

        aceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validaciones();

            }
        });
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ingreso ingre=new ingreso();
                ingre.iniciar();
                ingre.setLocationRelativeTo(null);
                dispose();
            }
        });
    }
    public void iniciar() {

        setVisible(true);
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(new Color(174, 214, 241));
    }
    public void validaciones() {
        // Obtener los datos del formulario
        String usuario = userr.getText();

        // Verificar la autenticación
        if (autenticarUsuario(usuario)) {
            // Si la autenticación es exitosa, permitir el acceso
            //JOptionPane.showMessageDialog(null, "Usuario encontrado");
            String pass1 = new String(clv1.getPassword());
            String pass2 = new String(clv2.getPassword());

            //Comparammos que la clave1 y clave2  ingresadas sean las mismas
            if (pass1.compareTo(pass2) == 0) {

                actualizarPassword(usuario,pass1);

                ingreso ing = new ingreso();
                ing.iniciar();
                ing.setLocationRelativeTo(null);
                dispose();
            } else {

                JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden");
                clv1.setText("");
                clv2.setText("");
            }
        } else {
            // Si la autenticación falla, mostrar un mensaje de error
            JOptionPane.showMessageDialog(null, "El usuario ingresado no existe!");
            userr.setText("");
            clv1.setText("");
            clv2.setText("");
        }
    }
    public boolean autenticarUsuario(String nombre) {

        ConexionBD manejadorDB = new ConexionBD();
        Connection conexion = manejadorDB.conexionBase();

        if (conexion != null) {
            try {
                // Preparar la consulta SQL para verificar la autenticación
                String sql = "SELECT * FROM usuarios WHERE nombreUsuario = ? ";
                try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {

                    // Establecer los valores de los parámetros en la consulta
                    pstmt.setString(1, nombre);

                    // Ejecutar la consulta para obtener el resultado
                    System.out.println("Consulta SQL: " + pstmt.toString()); // Imprimir la consulta SQL
                    ResultSet resultSet = pstmt.executeQuery();
                    return resultSet.next();
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Imprimir el seguimiento de la pila para diagnóstico
                JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta: " + e.getMessage());
            } finally {
                try {
                    conexion.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    public void actualizarPassword(String nombre,String pass){
        ConexionBD manejadorDB = new ConexionBD();
        Connection conexion = manejadorDB.conexionBase();

        if (conexion != null) {
            // La conexión se ha establecido correctamente
            try {
                // Preparar la consulta SQL para insertar los datos en la tabla
                String sql = "UPDATE usuarios SET contraseña = sha2(?,256) WHERE nombreUsuario= ? ";

                try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
                    pstmt.setString(1, pass);
                    pstmt.setString(2, nombre);

                    // Ejecutar la consulta para actualizar los datos
                    pstmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Contraseña actualizada correctamente.");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al insertar datos en la base de datos: " + e.getMessage());
            } finally {
                try {
                    // Cerrar la conexión
                    conexion.close();
                } catch (SQLException e) {
                    // Manejar errores al cerrar la conexión
                    e.printStackTrace();
                }
            }
        }

    }
}
