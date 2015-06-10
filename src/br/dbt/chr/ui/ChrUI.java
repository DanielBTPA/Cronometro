package br.dbt.chr.ui;

import br.dbt.chr.resources.DrawableRes;
import br.dbt.chr.resources.values.ColorValue;
import br.dbt.chr.resources.values.StringValue;
import br.dbt.chr.ui.context.Chronometer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChrUI extends Chronometer implements Serializable, ActionListener,
        Runnable {

    /**
     * Java JDK 1.7/SE 1.8
     */
    private static final long serialVersionUID = -6540072618399857687L;


    public transient JButton btAction, btReset, btAddHistory, btSettings,
            btAbout;

    public JButton btClear;

    public transient JTextArea jtxtHistory;

    public transient JLabel jlVisorChr, jlMsm;

    public OptionsUI getConf;




    // millsecounds, secounds, minutes
    private transient int mseg = 0, seg = 0, min = 0;
    // string ms, sec, min
    private transient String stMs = "00", stS = "00",
            stMin = "00";
    private transient Runtime getSystem = Runtime.getRuntime();
    // Size button action
    private Rectangle boundsPP;
    private transient Thread threadChr;
    // Thread on or off.
    private volatile transient boolean activeThread = false,
            pausedThread = false;

    public ChrUI() {

        super(StringValue.ST_TITLE.toString());

        // LookAndFeel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        } catch (UnsupportedLookAndFeelException e) {
            JOptionPane.showMessageDialog(this, "Sistema não compativel, por favor contate o desenvolvedor para mais informações!",
                    "Erro de incompatiblidade", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    // Initialize Objects
    public void onInit(ChrUI saved) {

        this.setColorPainel(ColorValue.BLUE.build());

        // Display Chronometer
        jlVisorChr = new JLabel();
        jlVisorChr.setBounds(30, 20, 105, 40);
        jlVisorChr.setText(StringValue.ST_CONT_0.toString());
        jlVisorChr.setFont(new Font("Font1", Font.TYPE1_FONT, 25));
        jlVisorChr.setForeground(Color.white);

        // Information (Mil, Sec, Min)
        jlMsm = new JLabel(StringValue.ST_INFO_MSM.toString());
        jlMsm.setBounds(43, 60, 100, 10);
        jlMsm.setForeground(ColorValue.WHITE.build());

        // JTextArea with Scrollpane
        jtxtHistory = new JTextArea();
        jtxtHistory.setEditable(false);
        jtxtHistory.setFont(new Font("Normal", Font.PLAIN, 13));
        jtxtHistory.setBackground(this.getColorPainel());
        jtxtHistory.setForeground(ColorValue.WHITE.build());

        JScrollPane scroll = new JScrollPane(jtxtHistory);
        scroll.setBounds(10, 210, 254, 120);

        // Button Action
        boundsPP = new Rectangle(170, 15, 70, 70);
        btAction = new JButton();
        this.setCustomButton(btAction, DrawableRes.IC_ACTION_PLAY_NORMAL.build(), DrawableRes.IC_ACTION_PLAY_PRESSED.build(),
                StringValue.ST_BT_START.toString(), boundsPP);
        btAction.setFocusable(false);
        btAction.addActionListener(this);

        /* Small Buttons */

        // Height of Buttons
        final int heightSmallButtons = 150;

        // Button Reset
        btReset = new JButton();
        this.setCustomButton(btReset, DrawableRes.IC_ACTION_RESET_NORMAL.build(), DrawableRes.IC_ACTION_RESET_PRESSED.build(),
                StringValue.ST_BT_RESET.toString(), new Rectangle(40, heightSmallButtons, 30, 30));
        btReset.setFocusable(false);
        btReset.addActionListener(this);
        btReset.setEnabled(false);

        // Button add
        btAddHistory = new JButton();
        this.setCustomButton(btAddHistory, DrawableRes.IC_ACTION_ADD_HISTORY_NORMAL.build(), DrawableRes.IC_ACTION_ADD_HISTORY_PRESSED.build(),
                StringValue.ST_BT_ADD_HISTORY.toString(), new Rectangle(88, heightSmallButtons, 30, 30));
        btAddHistory.setFocusable(false);
        btAddHistory.addActionListener(this);
        btAddHistory.setEnabled(false);

        // Button settings
        btSettings = new JButton();
        this.setCustomButton(btSettings, DrawableRes.IC_ACTION_SETTINGS_NORMAL.build(), DrawableRes.IC_ACTION_SETTINGS_PRESSED.build(),
                StringValue.ST_BT_SETTINGS.toString(), new Rectangle(140, heightSmallButtons, 30, 30));
        btSettings.setFocusable(false);
        btSettings.addActionListener(this);

        // Button about
        btAbout = new JButton();
        this.setCustomButton(btAbout, DrawableRes.IC_ACTION_ABOUT_NORMAL.build(), DrawableRes.IC_ACTION_ABOUT_PRESSED.build(),
                StringValue.ST_BT_ABOUT.toString(), new Rectangle(190, heightSmallButtons, 30, 30));
        btAbout.setFocusable(false);
        btAbout.addActionListener(this);

        // Button Clear
        btClear = new JButton();
        this.setCustomButton(btClear, DrawableRes.IC_ACTION_CLEAR_NORMAL.build(), DrawableRes.IC_ACTION_CLEAR_PRESSED.build(),
                StringValue.ST_BT_CLEAR.toString(), new Rectangle(120, 325, 30, 30));
        btClear.setFocusable(false);
        btClear.addActionListener(this);
        btClear.setVisible(false);
        btClear.setEnabled(jtxtHistory.getText().equals("") ? false : true);


        if (saved != null) {
            //...
            getConf = saved.getConf;
        }

        // Add all componnts!
        this.addComponentInPanel(btClear);
        this.addComponentInPanel(btAbout);
        this.addComponentInPanel(btSettings);
        this.addComponentInPanel(btAddHistory);
        this.addComponentInPanel(btReset);
        this.addComponentInPanel(btAction);
        this.addComponentInPanel(scroll);
        this.addComponentInPanel(jlMsm);
        this.addComponentInPanel(jlVisorChr);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btAction) {
            if (btAction.getName().equals(StringValue.ST_BT_START.toString())) {
                this.setCustomButton(btAction, DrawableRes.IC_ACTION_PAUSE_NORMAL.build(), DrawableRes.IC_ACTION_PAUSE_PRESSED.build(),
                        StringValue.ST_BT_PAUSE.toString(), boundsPP);
                activeThread = true;
                pausedThread = false;
                btReset.setEnabled(false);
                btAddHistory.setEnabled(true);
                threadChr = new Thread(this, "Chr");
                threadChr.start();
            } else {
                this.setCustomButton(btAction, DrawableRes.IC_ACTION_PLAY_NORMAL.build(), DrawableRes.IC_ACTION_PLAY_PRESSED.build(),
                        StringValue.ST_BT_START.toString(), boundsPP);
                activeThread = false;
                pausedThread = true;
                threadChr.interrupt();
                threadChr = null;
                btReset.setEnabled(true);
                btAddHistory.setEnabled(false);
                getSystem.gc();
            }
        } else if (e.getSource() == btReset) {
            mseg = 0;
            seg = 0;
            min = 0;
            stMs = "00";
            stS = "00";
            stMin = "00";
            pausedThread = false;
            jtxtHistory.setText(null);
            btReset.setEnabled(false);
            btClear.setEnabled(false);
            jlVisorChr.setText(StringValue.ST_CONT_0.toString());

        } else if (e.getSource() == btAddHistory) {
            if (activeThread) {
                btClear.setEnabled(true);
                jtxtHistory.setText("Tempo marcado: " + jlVisorChr.getText()
                        + " - " + getHours() + "\n" + jtxtHistory.getText());

            }
        } else if (e.getSource() == btSettings) {
            getConf = new OptionsUI(this, getConf != null ? getConf : null);
        } else if (e.getSource() == btAbout) {
            new AboutUI(this);
        } else if (e.getSource() == btClear) {
            jtxtHistory.setText(null);
            btClear.setEnabled(false);
        }
    }

    // Get a hours of system.
    private String getHours() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        return dateFormat.format(date);
    }

    @Override
    public void run() {
        while (activeThread) {
            try {
                Thread.sleep(18);
                mseg++;
                if (mseg == 60) {
                    mseg = 0;
                    seg++;
                    if (seg == 60) {
                        seg = 0;
                        min++;
                        ;
                    }
                }
                if (min >= 10) {
                    stMin = String.valueOf(min);
                } else {
                    stMin = "0" + String.valueOf(min);
                }

                if (seg >= 10) {
                    stS = String.valueOf(seg);
                } else {
                    stS = "0" + String.valueOf(seg);
                }

                if (mseg >= 10) {
                    stMs = String.valueOf(mseg);
                } else {
                    stMs = "0" + String.valueOf(mseg);
                }

            } catch (Exception e) {
                e.getStackTrace();
            } finally {
                jlVisorChr.setText(stMin + ":" + stS + ":" + stMs);
            }
        }

        while (pausedThread) {
            try {
                jlVisorChr.setText(StringValue.ST_PAUSED_SPACES.toString());
                Thread.sleep(800);
                jlVisorChr.setText(stMin + ":" + stS + ":" + stMs);
                Thread.sleep(800);
            } catch (Exception e) {

            }

        }
    }
}
