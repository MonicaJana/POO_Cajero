import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ingreso extends JFrame{
    private JPasswordField clave;
    private JButton ENTERButton;
    private JPanel panel1;
    private JLabel img;
    private JTextField userr;
    private JButton REGISTRARSEButton;
    private JButton CAMBIARCONTRASEÑAButton;

    private static int id;
    private static double dinero;

    private static  String nombreU;

    public ingreso(){
        super("Bienvenido al Banco del Búho");
        setContentPane(panel1);

        // Carga la imagen desde un archivo
        ImageIcon icono = new ImageIcon("BUHO_EPN_big.png"); // Reemplaza con la ruta de tu imagen
        // Establece la imagen en el JLabel
        img.setIcon(icono);
        icono.setImage(icono.getImage().getScaledInstance(100, 150, Image.SCALE_DEFAULT));
        ENTERButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validaciones();
            }
        });
        CAMBIARCONTRASEÑAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambclave cambio=new cambclave();
                cambio.iniciar();
                cambio.setLocationRelativeTo(null);
                dispose();
            }
        });
        REGISTRARSEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrar();
            }
        });
    }

    public void registrar(){
        //Obtener los datos del formulario
        String nombre=userr.getText();
        String pass = new String(clave.getPassword());

        //Obtener la conexion a la Base de Datos
        ConexionBD manejadorDB = new ConexionBD();
        Connection conexion = manejadorDB.conexionBase();

        if (autenticarParaRegistro(nombre)) {
            // Si la autenticación es exitosa, permitir el acceso
            JOptionPane.showMessageDialog(null, "Ya existe un usuario con ese nombre!");
            userr.setText("");
            clave.setText("");
        } else {
            if (conexion != null) {
                // La conexión se ha establecido correctamente
                try {
                    // Preparar la consulta SQL para insertar los datos en la tabla
                    String sql = "INSERT INTO usuarios (nombreUsuario, contraseña, saldoActual) VALUES (?, sha2(?,256), ?)";
                    try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
                        // Establecer los valores de los parámetros en la consulta
                        pstmt.setString(1, nombre);
                        pstmt.setString(2, pass);
                        pstmt.setDouble(3, 0);

                        // Ejecutar la consulta para insertar los datos
                        pstmt.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Usuario registrado exitosamente!");
                        userr.setText("");
                        clave.setText("");
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

    public boolean autenticarParaRegistro(String nombre) {

        //Obtener la conexion a la Base de Datos
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
    public void validaciones() {
        // Obtener los datos del formulario
        String usuario = userr.getText();
        String contraseña = clave.getText();

        // Verificar la autenticación
        if (autenticarUsuario(usuario, contraseña)) {
            // Si la autenticación es exitosa, permitir el acceso
            //JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso.");
            consultarDatos(usuario);
            transaccion tran = new transaccion();
            tran.iniciar();
            tran.setLocationRelativeTo(null);
            dispose();

        } else {
            // Si la autenticación falla, mostrar un mensaje de error
            JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos. Inténtalo de nuevo.");
            userr.setText("");
            clave.setText("");
        }
    }
    public boolean autenticarUsuario(String nombre, String contraseña) {

        //Obtener la conexion a la Base de Datos
        ConexionBD manejadorDB = new ConexionBD();
        Connection conexion = manejadorDB.conexionBase();

        if (conexion != null) {
            try {
                // Preparar la consulta SQL para verificar la autenticación
                String sql = "SELECT * FROM usuarios WHERE nombreUsuario = ? AND contraseña = sha2(?,256)";
                try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
                    // Establecer los valores de los parámetros en la consulta
                    pstmt.setString(1, nombre);
                    pstmt.setString(2, contraseña);

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

    public void consultarDatos(String nombre){

        //Obtener la conexion a la Base de Datos
        ConexionBD manejadorDB = new ConexionBD();
        Connection conexion = manejadorDB.conexionBase();

        if (conexion != null) {
            // La conexión se ha establecido correctamente
            try {
                // Preparar la consulta SQL para extraer los datos en la tabla
                String sql = "SELECT id, saldoActual FROM usuarios WHERE nombreUsuario=? ";
                try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {

                    pstmt.setString(1, nombre);
                    ResultSet rs=pstmt.executeQuery();

                    while(rs.next())
                    {
                        id = rs.getInt("id");
                        dinero= rs.getDouble("saldoActual");
                        nombreU=nombre;
                    }

                    System.out.println(id);
                    System.out.println(dinero);
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
    public void iniciar(){
        setVisible(true);
        setSize(400,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(new Color(174, 214, 241));
    }
    public double getDinero() {
        return dinero;
    }

    public void setDinero(double dinero) {
        this.dinero = dinero;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombreU;
    }
}
