package br.dbt.chr.ui;

import br.dbt.chr.resources.values.StringValue;
import br.dbt.chr.ui.context.MaterialLookView;

import java.io.Serializable;

public class AboutUI extends MaterialLookView implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 6528188934016921309L;


    public AboutUI(ChrUI c) {
        super(StringValue.ST_BT_ABOUT.toString(), 300,300);

        initFrame(Type.UTILITY);

        // ...

        setVisible(true);

    }
}
