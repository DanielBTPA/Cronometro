package br.dbt.chr.ui;

import br.dbt.chr.resources.values.ColorValue;
import br.dbt.chr.resources.values.StringValue;
import br.dbt.chr.resources.values.StringValue.StringArrayValue;
import br.dbt.chr.ui.context.MaterialLookView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;

public class OptionsUI extends MaterialLookView implements ActionListener, Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 6528188934016921309L;

    public static boolean isVisible = false;
    private transient ChrUI c;
    private JCheckBox chkTwoPlan, chkShowButtonClear;
    private transient JLabel jlOptions, jlTheme;
    private transient JButton btOk, btApply;
    private JComboBox<String> cbTheme;

    public OptionsUI(ChrUI c, OptionsUI o) {

        // Initialize Objects
        super(StringValue.ST_BT_SETTINGS.toString(), 300, 300);

        isVisible = true;

        // Reference of object type ChrUI
        this.c = c;

        initFrame(Type.UTILITY);

        // Dialog Settings
        setSize(300, 300);
        setResizable(false);
        setLocationRelativeTo(c);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                isVisible = false;
            }
        });

        this.setBackgroundPrimary(Color.WHITE);
        this.setBackgroundSecundary(c.getBackgroundSecundary());


        // Button - Ok
        btOk = new JButton(StringValue.ST_BT_OK.toString());
        btOk.setBounds(210, 20, 70, 30);
        btOk.addActionListener(this);
        btOk.setFocusable(false);

        // Button - Apply
        btApply = new JButton("Aplicar");
        btApply.setBounds(210, 65, 70, 30);
        btApply.addActionListener(this);
        btApply.setFocusable(false);

        // Themes
        jlTheme = new JLabel(StringValue.ST_LB_THEME.toString());
        jlTheme.setBounds(10, 100, 120, 20);
        // CheckBox - ShowButtonClear
        chkShowButtonClear = new JCheckBox(StringValue.ST_LB_SHOW_CLEAR.toString());
        chkShowButtonClear.setBounds(10, 60, 160, 20);
        chkShowButtonClear.setFocusable(false);

        // ComboBox Theme
        cbTheme = new JComboBox<String>(StringArrayValue.CB_ITEMS_THEME.getArray());
        cbTheme.setBounds(10, 125, 70, 20);
        cbTheme.setFocusable(false);

        // CheckBox - Two Plain
        chkTwoPlan = new JCheckBox(StringValue.ST_LB_ADD_TWO_PLAN.toString());
        chkTwoPlan.setBounds(10, 30, 165, 20);
        chkTwoPlan.setFocusable(false);
        chkTwoPlan.setEnabled(false); // Wait for elaboration of code.

        // Recupera dados selecionados
        if (o != null) {
            chkTwoPlan.setSelected(o.chkShowButtonClear.isSelected());
            chkShowButtonClear.setSelected(o.chkShowButtonClear.isSelected());
            cbTheme.setSelectedIndex(o.cbTheme.getSelectedIndex());
        }

        // Adiciona componente ao painel
        addComponentInPanel(btOk);
        addComponentInPanel(btApply);
        addComponentInPanel(jlTheme);
        addComponentInPanel(cbTheme);
        addComponentInPanel(chkShowButtonClear);
        addComponentInPanel(chkTwoPlan);


        // Initialize interface
        setVisible(true);


    }

    private void saveChanges() {

        // Show Button Clear
        c.btClear.setVisible(chkShowButtonClear.isSelected());

        // Theme Combobox
        if (cbTheme.getSelectedItem() == StringArrayValue.CB_ITEMS_THEME.getString(0)) {
            c.setBackgroundPrimary(ColorValue.BLUE_PRIMARY.build());
            c.setBackgroundSecundary(ColorValue.BLUE_SECUNDARY.build());
        } else if (cbTheme.getSelectedItem() == StringArrayValue.CB_ITEMS_THEME.getString(1)) {
            c.setBackgroundPrimary(ColorValue.RED_PRIMARY.build());
            c.setBackgroundSecundary(ColorValue.RED_SECUNDARY.build());
        } else if (cbTheme.getSelectedItem() == StringArrayValue.CB_ITEMS_THEME.getString(2)) {
            c.setBackgroundPrimary(ColorValue.GRAY_PRIMARY.build());
            c.setBackgroundSecundary(ColorValue.GRAY_SECUNDARY.build());
        } else if (cbTheme.getSelectedItem() == StringArrayValue.CB_ITEMS_THEME.getString(3)) {
            c.setBackgroundPrimary(ColorValue.GREEN_PRIMARY.build());
            c.setBackgroundSecundary(ColorValue.GREEN_SECUNDARY.build());
        } else if (cbTheme.getSelectedItem() == StringArrayValue.CB_ITEMS_THEME.getString(4)) {
            c.setBackgroundPrimary(ColorValue.PINK_PRIMARY.build());
            c.setBackgroundSecundary(ColorValue.PINK_SECUNDARY.build());
        } else if (cbTheme.getSelectedItem() == StringArrayValue.CB_ITEMS_THEME.getString(5)) {
            c.setBackgroundPrimary(ColorValue.ORANGE_PRIMARY.build());
            c.setBackgroundSecundary(ColorValue.ORANGE_SECUNDARY.build());
        } else if (cbTheme.getSelectedItem() == StringArrayValue.CB_ITEMS_THEME.getString(6)) {
            c.setBackgroundPrimary(ColorValue.PURPLE_PRIMARY.build());
            c.setBackgroundSecundary(ColorValue.PURPLE_SECUNDARY.build());
        } else if (cbTheme.getSelectedItem() == StringArrayValue.CB_ITEMS_THEME.getString(7)) {
            c.setBackgroundPrimary(ColorValue.YELLOW_PRIMARY.build());
            c.setBackgroundSecundary(ColorValue.YELLOW_SECUNDARY.build());
        }

        c.getConf = this;

        c.jtxtHistory.setBackground(c.getColorPainel());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (btOk == e.getSource()) {
            this.saveChanges();
            this.dispose();
        } else if (btApply == e.getSource()) {
            this.saveChanges();
        }
        c.saveState(ChrUI.PATH_DEFAULT, this.c);
    }
}
