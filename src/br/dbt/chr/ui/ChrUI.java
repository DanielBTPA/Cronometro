package br.dbt.chr.ui;

import br.dbt.chr.resources.DrawableRes;
import br.dbt.chr.resources.values.ColorValue;
import br.dbt.chr.resources.values.StringValue;
import br.dbt.chr.ui.context.MaterialLookView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ChrUI extends MaterialLookView implements Serializable, ActionListener,
        Runnable {

    // Controle de versão do aplicativo
    public static final double VERSION_APP = 1.3;
    /**
     * Java JDK 1.8/SE 1.8
     */


    public static final String PATH_DEFAULT = "Files/Data/ChrUI.data";


    // Default Serial
    private static final long serialVersionUID = 1338888196052293878L;

    // Controle da thread
    private static volatile boolean activeThread = false,
            pausedThread = false;

    public JButton btClear;

    public transient JTextArea jtxtHistory;

    public transient JLabel jlVisorChr, jlMsm;

    public OptionsUI getConf;
    public AboutUI about;
    private transient JButton btAction, btReset, btAddHistory, btSettings,
            btAbout;
    // millsecounds, secounds, minutes
    private transient int mseg = 0, seg = 0, min = 0;
    // string ms, sec, min
    private transient String stMs = "00", stS = "00",
            stMin = "00";
    private transient Thread threadChr;

    public ChrUI() {

        super(StringValue.ST_TITLE.toString(), DrawableRes.IC_LAUNCHER.build(), 275, 400);

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

    public static boolean setStart(boolean start) {
        if (start) {
            activeThread = true;
            pausedThread = false;
        } else {
            activeThread = false;
            pausedThread = true;
        }

        return start;
    }

    // Initialize Objects
    public void onInit(ChrUI saved) {


        // Conf of windows and colors default of Chronomter
        initFrame(Type.NORMAL);

        this.setBackgroundPrimary(ColorValue.BLUE_PRIMARY.build());
        this.setBackgroundSecundary(ColorValue.BLUE_SECUNDARY.build());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setAlwaysOnTop(false);

        // Display Chronometer
        jlVisorChr = new JLabel();
        jlVisorChr.setBounds(30, 20, 105, 40);
        jlVisorChr.setText(StringValue.ST_CONT_0.toString());
        jlVisorChr.setFont(new Font("Arial", Font.TYPE1_FONT, 25));
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
        scroll.setOpaque(false);
        scroll.setBounds(5, 180, 254, 120);

        // Button Action
        btAction = createCustomButton(new JButton(), StringValue.ST_BT_START.toString(), DrawableRes.IC_ACTION_PLAY_NORMAL.build(), DrawableRes.IC_ACTION_PLAY_PRESSED.build());
        btAction.setBounds(170, 15, 71, 71);
        btAction.setFocusable(false);
        btAction.addActionListener(this);

        /* Small Buttons */

        // Height of Buttons
        final int heightSmallButtons = 110;

        // Button Reset
        btReset = createCustomButton(new JButton(), StringValue.ST_BT_RESET.toString(), DrawableRes.IC_ACTION_RESET_NORMAL.build(), DrawableRes.IC_ACTION_RESET_PRESSED.build());
        btReset.setBounds(40, heightSmallButtons, 30, 30);
        btReset.setFocusable(false);
        btReset.addActionListener(this);
        btReset.setEnabled(false);

        // Button add
        btAddHistory = createCustomButton(new JButton(), StringValue.ST_BT_ADD_HISTORY.toString(), DrawableRes.IC_ACTION_ADD_HISTORY_NORMAL.build(), DrawableRes.IC_ACTION_ADD_HISTORY_PRESSED.build());
        btAddHistory.setBounds(90, heightSmallButtons, 30, 30);
        btAddHistory.setFocusable(false);
        btAddHistory.addActionListener(this);
        btAddHistory.setEnabled(false);

        // Button settings
        btSettings = createCustomButton(new JButton(), StringValue.ST_BT_SETTINGS.toString(), DrawableRes.IC_ACTION_SETTINGS_NORMAL.build(), DrawableRes.IC_ACTION_SETTINGS_PRESSED.build());
        btSettings.setBounds(140, heightSmallButtons, 30, 30);
        btSettings.setFocusable(false);
        btSettings.addActionListener(this);

        // Button about
        btAbout = createCustomButton(new JButton(), StringValue.ST_BT_ABOUT.toString(), DrawableRes.IC_ACTION_ABOUT_NORMAL.build(), DrawableRes.IC_ACTION_ABOUT_PRESSED.build());
        btAbout.setBounds(190, heightSmallButtons, 30, 30);
        btAbout.setFocusable(false);
        btAbout.addActionListener(this);

        // Button Clear
        btClear = createCustomButton(new JButton(), StringValue.ST_BT_CLEAR.toString(), DrawableRes.IC_ACTION_CLEAR_NORMAL.build(), DrawableRes.IC_ACTION_CLEAR_PRESSED.build());
        btClear.setBounds(120, 313, 30, 30);
        btClear.setFocusable(false);
        btClear.addActionListener(this);
        btClear.setVisible(false);
        btClear.setEnabled(jtxtHistory.getText().equals("") ? false : true);


        /* Recupera a cor da borda e painel
         *
         *
         */
        if (saved != null) {
            Color c = saved.getBackgroundPrimary();
            this.setBackgroundPrimary(c);
            jtxtHistory.setBackground(c);
            this.setBackgroundSecundary(saved.getBackgroundSecundary());
            btClear.setVisible(saved.btClear.isVisible());
            btClear.setEnabled(saved.btClear.isEnabled());
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
                btAction = createCustomButton(btAction, StringValue.ST_BT_PAUSE.toString(), DrawableRes.IC_ACTION_PAUSE_NORMAL.build(), DrawableRes.IC_ACTION_PAUSE_PRESSED.build());
                ChrUI.setStart(true);
                btReset.setEnabled(false);
                btAddHistory.setEnabled(true);
                threadChr = new Thread(this, "Chr");
                threadChr.start();
            } else {
                btAction = createCustomButton(btAction, StringValue.ST_BT_START.toString(), DrawableRes.IC_ACTION_PLAY_NORMAL.build(), DrawableRes.IC_ACTION_PLAY_PRESSED.build());
                ChrUI.setStart(false);
                threadChr.interrupt();
                threadChr = null;
                btReset.setEnabled(true);
                btAddHistory.setEnabled(false);
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
            if (!OptionsUI.isVisible) {
                if (getConf == null) {
                    getConf = new OptionsUI(this, null);
                } else {
                    getConf = new OptionsUI(this, getConf);
                }
            } else {
                getConf.requestFocus();
            }
        } else if (e.getSource() == btAbout) {
            if (about == null) {
                about = new AboutUI(this);
            } else {
                if (AboutUI.isVisible) {
                    about.requestFocus();
                } else {
                    about.setVisible(true);
                }
            }

        } else if (e.getSource() == btClear) {
            jtxtHistory.setText(null);
            btClear.setEnabled(false);
        }
    }

    // Get a hours of system.
    public String getHours() {
        Calendar c = GregorianCalendar.getInstance();
        String get = "(" + c.get(GregorianCalendar.HOUR_OF_DAY) + ":" + c.get(GregorianCalendar.MINUTE) + ":" + c.get(GregorianCalendar.SECOND) + ")";
        return get;
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

                    }
                }
                if (min >= 10) {
                    stMin = "" + min;
                } else {
                    stMin = "0" + min;
                }

                if (seg >= 10) {
                    stS = "" + seg;
                } else {
                    stS = "0" + seg;
                }

                if (mseg >= 10) {
                    stMs = "" + mseg;
                } else {
                    stMs = "0" + mseg;
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
