import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
public class versaldo extends JFrame {
    private JButton menu;
    private JPanel panel3;
    private JLabel saldo;
    private JLabel dol;


    public versaldo(){
        super("Saldo");
        setContentPane(panel3);

        ingreso ingre= new ingreso();

        Font fuente = new Font("Arial", Font.PLAIN, 15);
        saldo.setFont(fuente);
        dol.setFont(fuente);

        saldo.setText(Double.toString(ingre.getDinero()));
        menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transaccion tran=new transaccion();
                tran.iniciar();
                tran.setLocationRelativeTo(null);
                dispose();
            }
        });
    }


    public void iniciar(){

        setVisible(true);
        setSize(400,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(new Color(174, 214, 241));
    }
}
