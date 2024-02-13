import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class retiro extends JFrame {
    private JPasswordField pass;
    private JButton a1Button;
    private JButton a2Button;
    private JButton a3Button;
    private JButton a4Button;
    private JButton a5Button;
    private JButton a6Button;
    private JButton a7Button;
    private JButton a8Button;
    private JButton a9Button;
    private JButton a0Button;
    private JButton ENTERButton;
    private JPanel panelLogin;
    private JLabel ingreso;
    private JLabel l1;
    private JButton menu;
    private JButton CLEARButton;
    private JLabel dol;

    String contra="";

    public retiro(){
        super("Retiro");
        setContentPane(panelLogin);
        // Crear una nueva fuente con un tamaño más grande
        Font fuente = new Font("Arial", Font.PLAIN, 15);
        ingreso.setFont(fuente);
        dol.setFont(fuente);
        l1.setFont(fuente);
        a1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                contra+="1";
                ingreso.setText(contra);
            }
        });
        a2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                 contra+="2";
                 ingreso.setText(contra);
            }
        });
        a3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contra+="3";
                ingreso.setText(contra);

            }
        });
        a4Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contra+="4";
                ingreso.setText(contra);
            }
        });
        a5Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contra+="5";
                ingreso.setText(contra);
            }
        });
        a6Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contra+="6";
                ingreso.setText(contra);
            }
        });
        a7Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contra+="7";
                ingreso.setText(contra);
            }
        });
        a8Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contra+="8";
                ingreso.setText(contra);
            }
        });
        a9Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contra+="9";
                ingreso.setText(contra);
            }
        });
        a0Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contra+="0";
                ingreso.setText(contra);
            }
        });
        ENTERButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ingreso ingre=new ingreso();
                String textoIngreso = contra;
                int cantidadRetiro = Integer.parseInt(textoIngreso);
                if(cantidadRetiro <=ingre.getDinero()){
                    retirar();
                    String mensaje= "Su retiro es valido!";
                    l1.setText(mensaje);
                }else{
                    JOptionPane.showMessageDialog(null,"SALDO INSUFICIENTE!");
                    l1.setText("");
                    ingreso.setText("");
                    contra="";

                }

            }
        });
        menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transaccion tran=new transaccion();
                tran.iniciar();
                tran.setLocationRelativeTo(null);
                dispose();
            }
        });
        CLEARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contra="";
                ingreso.setText(contra);
            }
        });
    }

    public void iniciar(){

        setVisible(true);
        setSize(400,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(new Color(174, 214, 241));
    }

    public void retirar(){
        try{
            // Obtener el texto actual del JLabel
            String textoIngreso = contra;

            // Obtener la fecha y hora actual
            LocalDateTime fechaHoraActual = LocalDateTime.now();
            // Formatear la fecha y hora según tus necesidades
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String fechaHoraFormateada = fechaHoraActual.format(formato);

            // Convertir el texto a un entero y sumarlo a la variable dinero
            double cantidadRetiro = Double.parseDouble(textoIngreso);
            ingreso ingre=new ingreso();
            double total= ingre.getDinero()-cantidadRetiro;
            System.out.println(total);
            ingre.setDinero(total);
            registrarTransaccion("retiro",cantidadRetiro,fechaHoraFormateada,ingre.getId());
            actualizarSaldo(ingre.getId(),total);
            recibo rec=new recibo();
            rec.iniciar();
            rec.setLocationRelativeTo(null);
            rec.mostrarDatosTransaccion("101",textoIngreso,"retiro",fechaHoraFormateada,ingre.getNombre());
            dispose();
        } catch (Exception exception){
            JOptionPane.showMessageDialog(null,"No se pudo transformar a número válido");
        }

    }

    public void registrarTransaccion(String tipo, double monto, String fecha, int id){

        ConexionBD manejadorDB = new ConexionBD();
        Connection conexion = manejadorDB.conexionBase();

        if (conexion != null) {
            // La conexión se ha establecido correctamente
            try {
                // Preparar la consulta SQL para insertar los datos en la tabla
                String sql = "INSERT INTO transacciones(tipo,monto,fecha,FKusuario) VALUES (?, ?, ?, ?)";
                try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
                    // Establecer los valores de los parámetros en la consulta
                    pstmt.setString(1, tipo);
                    pstmt.setDouble(2, monto);
                    pstmt.setString(3,fecha);
                    pstmt.setInt(4, id); // Agregar el código como parámetro

                    // Ejecutar la consulta para insertar los datos
                    pstmt.executeUpdate();
                    //JOptionPane.showMessageDialog(null, "Datos insertados correctamente.");
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

    public void actualizarSaldo(int id, double saldoActual){
        ConexionBD manejadorDB = new ConexionBD();
        Connection conexion = manejadorDB.conexionBase();
        if (conexion != null) {
            // La conexión se ha establecido correctamente
            try {
                // Preparar la consulta SQL para insertar los datos en la tabla
                String sql = "UPDATE usuarios SET saldoActual = "+saldoActual+" WHERE id= "+ id +" ";
                try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
                    // Ejecutar la consulta para actualizar los datos
                    pstmt.executeUpdate(sql);
                    //JOptionPane.showMessageDialog(null, "Saldo actualizado correctamente.");
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
