import br.dbt.chr.ui.ChrUI;
import br.dbt.chr.util.StateIO;

import javax.swing.*;
import java.io.IOException;

public class Launcher {

    private static ChrUI get;

    public static void main(String[] args) throws ClassNotFoundException {

        StateIO state = new StateIO(ChrUI.PATH_DEFAULT);
        try {
            state.createPathIfNotExists();
            if (!state.isFileEmpty()) {
                get = (ChrUI) state.getStateObject();
            }
        } catch (IOException e) {}

        SwingUtilities.invokeLater(() -> {
            ChrUI c = new ChrUI();

            c.onInit(get != null ? get : null);

            c.setVisible(true);
        });
    }

}
