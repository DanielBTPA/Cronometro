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

        // ...

        setVisible(true);

    }
}
