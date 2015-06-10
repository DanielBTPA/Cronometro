package br.dbt.chr.ui;

import br.dbt.chr.resources.DrawableRes;
import br.dbt.chr.resources.values.ColorValue;
import br.dbt.chr.resources.values.StringValue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChrUI extends JFrame implements Serializable, ActionListener, WindowListener,
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
    public JPanel jp;
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

        // LookAndFeel
        try {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (UnsupportedLookAndFeelException e) {

        }
    }

    // Custom button
    public static void setCustomButton(JButton b, Image icNormal, Image icPressed, String name,
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

    // Initialize Objects
    public void onInit(ChrUI saved) {

        // Frame (Janela)
        this.setTitle(StringValue.ST_TITLE.toString());
        this.setLocation(200, 200);
        this.setSize(280, 400);
        this.setIconImage(DrawableRes.IC_LAUNCHER.build());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.addWindowListener(this);
        this.setAlwaysOnTop(true);

        // Pane
        jp = new JPanel();
        jp.setBackground(ColorValue.BLUE.build());
        jp.setLayout(null);



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
        jtxtHistory.setBackground(jp.getBackground());
        jtxtHistory.setForeground(ColorValue.WHITE.build());

        JScrollPane scroll = new JScrollPane(jtxtHistory);
        scroll.setBounds(10, 170, 254, 120);

        // Button Action
        boundsPP = new Rectangle(170, 15, 70, 70);
        btAction = new JButton();
        this.setCustomButton(btAction, DrawableRes.IC_ACTION_PLAY_NORMAL.build(), DrawableRes.IC_ACTION_PLAY_PRESSED.build(),
                StringValue.ST_BT_START.toString(), boundsPP);
        btAction.setFocusable(false);
        btAction.addActionListener(this);
        // ....

        // Button Reset
        btReset = new JButton();
        this.setCustomButton(btReset, DrawableRes.IC_ACTION_RESET_NORMAL.build(), DrawableRes.IC_ACTION_RESET_PRESSED.build(),
                StringValue.ST_BT_RESET.toString(), new Rectangle(40, 110, 30, 30));
        btReset.setFocusable(false);
        btReset.addActionListener(this);
        btReset.setEnabled(false);

        // Button Add
        btAddHistory = new JButton();
        this.setCustomButton(btAddHistory, DrawableRes.IC_ACTION_ADD_HISTORY_NORMAL.build(), DrawableRes.IC_ACTION_ADD_HISTORY_PRESSED.build(),
                StringValue.ST_BT_ADD_HISTORY.toString(), new Rectangle(88, 110, 30, 30));
        btAddHistory.setFocusable(false);
        btAddHistory.addActionListener(this);
        btAddHistory.setEnabled(false);

        // Button settings
        btSettings = new JButton();
        this.setCustomButton(btSettings, DrawableRes.IC_ACTION_SETTINGS_NORMAL.build(), DrawableRes.IC_ACTION_SETTINGS_PRESSED.build(),
                StringValue.ST_BT_SETTINGS.toString(), new Rectangle(140, 110, 30, 30));
        btSettings.setFocusable(false);
        btSettings.addActionListener(this);

        // Button about
        btAbout = new JButton();
        this.setCustomButton(btAbout, DrawableRes.IC_ACTION_ABOUT_NORMAL.build(), DrawableRes.IC_ACTION_ABOUT_PRESSED.build(),
                StringValue.ST_BT_ABOUT.toString(), new Rectangle(190, 110, 30, 30));
        btAbout.setFocusable(false);
        btAbout.addActionListener(this);

        // Button Clear
        btClear = new JButton();
        this.setCustomButton(btClear, DrawableRes.IC_ACTION_CLEAR_NORMAL.build(), DrawableRes.IC_ACTION_CLEAR_PRESSED.build(),
                StringValue.ST_BT_CLEAR.toString(), new Rectangle(120, 310, 30, 30));
        btClear.setFocusable(false);
        btClear.addActionListener(this);
        btClear.setVisible(false);
        btClear.setEnabled(jtxtHistory.getText().equals("") ? false : true);

        // JSeparator - Local only.
        JSeparator js = new JSeparator(SwingConstants.HORIZONTAL);
        jp.add(js);
        js.setBounds(10, 100, 250, 3);
        js = new JSeparator(SwingConstants.HORIZONTAL);
        js.setBounds(10, 147, 250, 3);

        if (saved != null) {
            //...
            getConf = saved.getConf;
        }

        // Add all componnts!
        jp.add(js);
        jp.add(btClear);
        jp.add(btAbout);
        jp.add(btSettings);
        jp.add(btAddHistory);
        jp.add(btReset);
        jp.add(btAction);
        jp.add(scroll);
        jp.add(jlMsm);
        jp.add(jlVisorChr);
        this.add(jp);

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

    @Override
    public void windowActivated(WindowEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowClosed(WindowEvent e) {
        // TODO Auto-generated method stub

    }

    public void windowClosing(WindowEvent e) {
        getSystem.gc();
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowIconified(WindowEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowOpened(WindowEvent e) {
        // TODO Auto-generated method stub

    }

}
