import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class transaccion extends JFrame{
    private JPanel panel2;
    private JRadioButton depositoRadioButton;
    private JRadioButton retiroRadioButton;
    private JRadioButton verSaldoRadioButton;
    private JButton salir;
    private JRadioButton historialRadioButton;


    public transaccion (){
        super("Transaccion a realizar");
        setContentPane(panel2);
        verSaldoRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                versaldo saldo=new versaldo();
                saldo.iniciar();
                saldo.setLocationRelativeTo(null);
                dispose();
            }
        });
        salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JOptionPane.showMessageDialog(null,"Muchas gracias!");
                dispose();
                System.exit(0);
            }
        });
        retiroRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                retiro ret=new retiro();
                ret.iniciar();
                ret.setLocationRelativeTo(null);
                dispose();

            }
        });
        depositoRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                deposito dep=new deposito();
                dep.iniciar();
                dep.setLocationRelativeTo(null);
                dispose();
            }
        });
        historialRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                historial his=new historial();
                his.iniciar();
                his.setLocationRelativeTo(null);
                dispose();
            }
        });
    }
    public void iniciar(){
        setUndecorated(true);
        setVisible(true);
        setSize(400,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(new Color(174, 214, 241));
    }
}
