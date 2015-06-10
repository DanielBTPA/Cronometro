package br.dbt.chr.ui.context;

import br.dbt.chr.resources.DrawableRes;
import br.dbt.chr.resources.values.ColorValue;
import br.dbt.chr.resources.values.StringValue;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Daniel on 10/06/2015.
 */
public class Chronometer extends JFrame {


    private JPanel jp;


    public Chronometer(String title) {

        // Frame (Janela)
        super(title);
        this.setLocation(200, 200);
        this.setSize(275, 400);
        this.setIconImage(DrawableRes.IC_LAUNCHER.build());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setAlwaysOnTop(false);
        this.setUndecorated(true);

        // Pane
        jp = new JPanel();
        jp.setLayout(null);
        this.add(jp);

    }

    public Component addComponentInPanel(Component comp) {
        return jp.add(comp);
    }

    public void setColorPainel(Color bgColor) {
        jp.setBackground(bgColor);
    }

    public Color getColorPainel() {
        return jp.getBackground();
    }

    // Custom button
    protected void setCustomButton(JButton b, Image icNormal, Image icPressed, String name,
                                       Rectangle bounds) {
        b.setName(name);
        b.setIcon(new ImageIcon(icNormal));
        b.setPressedIcon(new ImageIcon(icPressed));
        b.setBounds(bounds);
        b.setOpaque(false);
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setToolTipText(name);
    }

}