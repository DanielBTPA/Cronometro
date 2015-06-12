import br.dbt.chr.ui.ChrUI;

import javax.swing.*;
import java.awt.*;


public class Launcher {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ChrUI c = new ChrUI();
                c.onInit(null);
                c.setVisible(true);
            }
        });

    }

}
