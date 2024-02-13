import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class deposito extends JFrame{
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
    private JButton OKButton;
    private JLabel ingreso;
    private JPanel panel4;
    private JLabel l1;
    private JButton menu;
    private JButton CLEARButton;
    private JLabel dol;

    String contra="";


    public deposito() {
        super("Deposito");
        setContentPane(panel4);

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
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                depositar();
                String mensaje= "Su deposito se realizó con éxito!";
                l1.setText(mensaje);

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

    public void depositar(){
        try{
           // Obtener el texto actual del JLabel
            String textoIngreso = contra;


            // Obtener la fecha y hora actual
            LocalDateTime fechaHoraActual = LocalDateTime.now();
            // Formatear la fecha y hora según tus necesidades
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String fechaHoraFormateada = fechaHoraActual.format(formato);


            // Convertir el texto a un entero y sumarlo a la variable dinero
            double cantidadDepositar = Double.parseDouble(textoIngreso);

            //Realizar suma
            ingreso ingre=new ingreso();
            double total= ingre.getDinero()+cantidadDepositar;
            System.out.println(total);
            ingre.setDinero(total);

            //registrar deposito en la BD
            retiro retir =new retiro();
            recibo rec=new recibo();
            retir.registrarTransaccion("deposito",cantidadDepositar,fechaHoraFormateada, ingre.getId());

            //Acualizar saldo en la BD
            retir.actualizarSaldo(ingre.getId(),total);
            rec.iniciar();
            rec.setLocationRelativeTo(null);
            rec.mostrarDatosTransaccion("101",textoIngreso,"deposito",fechaHoraFormateada,ingre.getNombre());
            dispose();

        } catch (Exception exception){
        JOptionPane.showMessageDialog(null,"No se pudo transformar a número válido");
         }
    }

}
