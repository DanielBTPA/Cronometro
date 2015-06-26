package br.dbt.chr.main;

import br.dbt.chr.resources.DrawableRes;
import br.dbt.chr.ui.ChrUI;
import br.dbt.chr.util.StateIO;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Launcher extends JDialog {

    private ChrUI get;

    private JLabel lbSplash;

    private Launcher() throws InterruptedException {
        final int width = 470, height = 300;

        Image getS = DrawableRes.SSCHRONOMETER.build();

        lbSplash = new JLabel(new ImageIcon(getS));

        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.setResizable(false);
        this.setLayout(null);
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));


        /* Pega o ponto do centro da tela do dispositivo (notebook ou televisão) e o tamanho da splash screen e
         * subitrai por largura / 2 com altura / 2, esse metodo de divisão retorna a metada da largura | altura
         * e depois é subitraido pelo ponto do centro da tela encaixando a spash screen completamente no centro.
         *
         */
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        int x = ge.getCenterPoint().x - (width / 2);
        int y = ge.getCenterPoint().y - (height / 2);

        // Define a localização da janela.
        this.setBounds(x, y, width, height);

        lbSplash.setSize(width, height);
        this.add(lbSplash);

        this.setVisible(true);

        Thread.sleep(2000);
        dispose();
    }

    private void initApplication() throws ClassNotFoundException {
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

    public static void main(String[] args) throws ClassNotFoundException, InterruptedException {

        Launcher launcher = new Launcher();
        launcher.initApplication();

    }

}
