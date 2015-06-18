package br.dbt.chr.ui;

import br.dbt.chr.resources.DrawableRes;
import br.dbt.chr.resources.values.ColorValue;
import br.dbt.chr.resources.values.StringValue;
import br.dbt.chr.ui.context.MaterialLookView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.URL;

public class AboutUI extends MaterialLookView implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 6528188934016921309L;

    private static final String SOURCE_CODE = "https://github.com/DanielBTPA/Cronometro/";

    public static boolean isVisible = true;

    private static final String stInfo = "Versão: " + ChrUI.VERSION_APP
    + "\n\nCriado por: DanielBT\n" + "\nVersão JRE: 1.8";

    public AboutUI(ChrUI c) {
        super(StringValue.ST_BT_ABOUT.toString(), 425, 400);

        this.initFrame(Type.UTILITY);
        this.setLocationRelativeTo(c.jlVisorChr);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                isVisible = false;
                runtime.gc();
                super.windowClosed(e);
            }
        });

        this.setBackgroundPrimary(ColorValue.GRAY_PRIMARY.build());
        this.setBackgroundSecundary(ColorValue.GRAY_SECUNDARY.build());

        JLabel label = createAnimationWithMouse(110, 2, 100, new Image[]{DrawableRes.LOGO_1.build(), DrawableRes.LOGO_2.build()});
        label.setLocation(15, 25);

        this.addComponentInPanel(label);

        JLabel lbLogo = new JLabel(new ImageIcon(DrawableRes.LOGO.build()));
        lbLogo.setBounds(140, 0, 280, 200);
        this.addComponentInPanel(lbLogo);

        JLabel lbIconOfApplication = new JLabel(new ImageIcon(DrawableRes.IC_LAUNCHER.getImageScaled(35, 35)));
        lbIconOfApplication.setBounds(140, 160, 30, 30);
        this.addComponentInPanel(lbIconOfApplication);

        JLabel lbNameOfApp = new JLabel(c.getTitle());
        lbNameOfApp.setBounds(180, 162, 140, 30);
        lbNameOfApp.setForeground(ColorValue.WHITE.build());
        lbNameOfApp.setFont(new Font("Candara", Font.PLAIN, 15));
        this.addComponentInPanel(lbNameOfApp);

        JTextArea txtInfo = new JTextArea(stInfo);
        txtInfo.setBounds(120, 210, 170, 100);
        txtInfo.setOpaque(false);
        txtInfo.setBorder(BorderFactory.createLineBorder(ColorValue.GRAY_SECUNDARY.build(), 1));
        txtInfo.setForeground(Color.white);
        txtInfo.setEditable(false);
        txtInfo.setSelectionColor(ColorValue.GRAY_PRIMARY.build());
        txtInfo.setSelectedTextColor(Color.WHITE);
        this.addComponentInPanel(txtInfo);

        JLabel lbInfo = new JLabel("Para ver o codigo fonte, acesse: ");
        lbInfo.setBounds(34, 325, 200, 20);
        lbInfo.setForeground(Color.white);
        this.addComponentInPanel(lbInfo);

        JButton btOpenGit = this.createCustomButton(new JButton(), "Github", DrawableRes.IC_ACTION_OPEN_GIT_NORMAL.build(),
                DrawableRes.IC_ACTION_OPEN_GIT_PRESSED.build(), DrawableRes.IC_ACTION_OPEN_GIT_SELECTED.build());
        btOpenGit.setBounds(212, 320, 30,30);
        btOpenGit.setFocusable(false);
        btOpenGit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btOpenGit.addActionListener((e) -> {
            Desktop d = Desktop.getDesktop();
            try {
                d.browse(new URL(SOURCE_CODE).toURI());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        this.addComponentInPanel(btOpenGit);

        this.setVisible(true);

    }
}
