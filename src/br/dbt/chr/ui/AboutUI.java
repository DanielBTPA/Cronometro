package br.dbt.chr.ui;

import br.dbt.chr.resources.DrawableRes;
import br.dbt.chr.resources.values.StringValue;

import javax.swing.*;
import java.io.Serializable;

public class AboutUI extends JDialog implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 6528188934016921309L;


    public AboutUI(ChrUI c) {
        super(c, StringValue.ST_BT_ABOUT.toString(), true);
        JLabel l = new JLabel(new ImageIcon(DrawableRes.ABOUT_LOGO.build()));
        setLocationRelativeTo(c);
        setSize(310, 310);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setModal(false);
        setLayout(null);

        add(l);

        setVisible(true);

    }
}
