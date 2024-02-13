import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.FileWriter;

public class historial extends JFrame {
    private JTable tableHistorial;
    private JPanel panelHistorial;
    private JButton menúButton;
    private JButton descargarButton;

    DefaultTableModel model = new DefaultTableModel();
    public historial(){
        super("Historial transacciones");
        setContentPane(panelHistorial);
        ingreso ingre=new ingreso();

        model.addColumn("Fecha");
        model.addColumn("Tipo_Transaccion");
        model.addColumn("Monto");
        tableHistorial.setModel(model);
        mostrar(ingre.getId());
        menúButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transaccion transa=new transaccion();
                transa.iniciar();
                transa.setLocationRelativeTo(null);
                dispose();

            }
        });
        descargarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    ingreso ingr=new ingreso();
                    descargarArchivo(ingr.getId());
            }
        });
    }

    public void descargarArchivo(int id){
        ConexionBD manejadorDB = new ConexionBD();
        Connection conexion = manejadorDB.conexionBase();
        // Datos que deseas guardar en el archivo
        String datosAGuardar = "Hola, este es un ejemplo de crear y guardar datos en un archivo de texto.";

        // Ruta del archivo de texto
        String rutaArchivo = "historial.txt";

        try {
            // Crear un objeto File que represente el archivo
            File archivo = new File(rutaArchivo);

            // Crear el archivo si no existe
            if (!archivo.exists()) {
                archivo.createNewFile();
                System.out.println("Archivo creado: " + rutaArchivo);
            }

            // Utilizar FileWriter y BufferedWriter para escribir en el archivo
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {

                if (conexion != null) {
                    // La conexión se ha establecido correctamente
                    try {
                        // Preparar la consulta SQL para insertar los datos en la tabla
                        String sql = "SELECT * FROM transacciones WHERE FKusuario="+id+" ";
                        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {

                            ResultSet rs=pstmt.executeQuery(sql);

                            while(rs.next())
                            {
                                String row = rs.getString(4)+","+rs.getString(2)+","+rs.getString(3);
                                writer.append("\n");
                                writer.append(row);
                            }
                            writer.flush();
                            writer.close();
                            JOptionPane.showMessageDialog(null,"Histtorial descargado!");
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

        } catch (IOException e) {
            // Manejar posibles excepciones de entrada/salida (IOException)
            e.printStackTrace();
        }
    }
    public void mostrar(int id){

        ConexionBD manejadorDB = new ConexionBD();
        Connection conexion = manejadorDB.conexionBase();

        if (conexion != null) {
            // La conexión se ha establecido correctamente
            try {
                // Preparar la consulta SQL para insertar los datos en la tabla
                String sql = "SELECT * FROM transacciones WHERE FKusuario="+id+" ";
                try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {


                    String [] datos = new String[3];

                    ResultSet rs=pstmt.executeQuery(sql);

                    while(rs.next())
                    {
                        datos[0] = rs.getString(4);
                        datos[1] = rs.getString(2);
                        datos[2] = rs.getString(3);
                        model.addRow(datos);
                    }

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
}
