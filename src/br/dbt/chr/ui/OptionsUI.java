package br.dbt.chr.ui;

import br.dbt.chr.resources.values.ColorValue;
import br.dbt.chr.resources.values.StringValue;
import br.dbt.chr.resources.values.StringValue.StringArrayValue;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

public class OptionsUI extends JDialog implements ActionListener, Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 6528188934016921309L;

    private transient ChrUI c;

    private JCheckBox chkTwoPlan, chkShowButtonClear;
    private transient JLabel jlOptions, jlTheme;
    private transient JButton btOk, btApply;
    private JComboBox<String> cbTheme;

    public OptionsUI(ChrUI c, OptionsUI o) {

        // Initialize Objects
        super(c, StringValue.ST_BT_SETTINGS.toString(), true);

        // Reference of object type ChrUI
        this.c = c;

        // Dialog Settings
        setType(Type.UTILITY);
        setSize(300, 300);
        setResizable(false);
        setLocationRelativeTo(c);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);

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
        jlTheme.setBounds(10, 100, 85, 20);


        if (o == null) {
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
        } else {
            cbTheme = o.cbTheme;
            chkShowButtonClear = o.chkShowButtonClear;
            chkTwoPlan = o.chkTwoPlan;
        }

        add(btOk);
        add(btApply);
        add(jlTheme);
        add(cbTheme);
        add(chkShowButtonClear);
        add(chkTwoPlan);


        // Initialize interface
        setVisible(true);
    }

    private void saveChanges() {

        // Show Button Clear
        c.btClear.setVisible(chkShowButtonClear.isSelected());

        // Theme Combobox
        if (cbTheme.getSelectedItem() == StringArrayValue.CB_ITEMS_THEME.getString(0)) {
            c.jp.setBackground(ColorValue.BLUE.build());
        } else if (cbTheme.getSelectedItem() == StringArrayValue.CB_ITEMS_THEME.getString(1)) {
            c.jp.setBackground(ColorValue.RED.build());
        } else if (cbTheme.getSelectedItem() == StringArrayValue.CB_ITEMS_THEME.getString(2)) {
            c.jp.setBackground(ColorValue.BLACK.build());
        } else if (cbTheme.getSelectedItem() == StringArrayValue.CB_ITEMS_THEME.getString(3)) {
            c.jp.setBackground(ColorValue.GREEN.build());
        } else if (cbTheme.getSelectedItem() == StringArrayValue.CB_ITEMS_THEME.getString(4)) {
            c.jp.setBackground(ColorValue.PINK.build());
        } else if (cbTheme.getSelectedItem() == StringArrayValue.CB_ITEMS_THEME.getString(5)) {
            c.jp.setBackground(ColorValue.ORANGE.build());
        } else if (cbTheme.getSelectedItem() == StringArrayValue.CB_ITEMS_THEME.getString(6)) {
            c.jp.setBackground(ColorValue.PURPLE.build());
        } else if (cbTheme.getSelectedItem() == StringArrayValue.CB_ITEMS_THEME.getString(7)) {
            c.jp.setBackground(ColorValue.YELLOW.build());
        }

        c.getConf = this;

        c.jtxtHistory.setBackground(c.jp.getBackground());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (btOk == e.getSource()) {
            this.saveChanges();
            this.dispose();
        } else if (btApply == e.getSource()) {
            this.saveChanges();
        }

    }
}
