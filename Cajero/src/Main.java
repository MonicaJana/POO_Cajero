import javax.swing.*;
import java.awt.*;
import java.lang.runtime.SwitchBootstraps;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(()->
        {
            ingreso ingres = new ingreso();
            ingres.iniciar();
            ingres.setLocationRelativeTo(null);
        });

    }
}