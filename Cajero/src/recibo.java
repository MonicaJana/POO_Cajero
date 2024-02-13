import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class recibo extends JFrame{
    private JLabel numRecibo;
    private JLabel monto;
    private JLabel fecha;
    private JLabel tipoTransaccion;
    private JLabel nombreUser;
    private JButton menúButton;
    private JPanel panelRecibo;

    public recibo(){
        super("Recibo");
        setContentPane(panelRecibo);
        menúButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transaccion tras=new transaccion();
                tras.iniciar();
                tras.setLocationRelativeTo(null);
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

    public void mostrarDatosTransaccion(String numeroRe, String mont, String tipo, String fech, String nombre){
        numRecibo.setText(numeroRe);
        monto.setText("$ "+mont);
        tipoTransaccion.setText(tipo);
        fecha.setText(fech);
        nombreUser.setText(nombre);
    }
}
