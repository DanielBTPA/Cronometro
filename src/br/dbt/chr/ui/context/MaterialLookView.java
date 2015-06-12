package br.dbt.chr.ui.context;

import br.dbt.chr.resources.DrawableRes;
import br.dbt.chr.ui.ChrUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created class for manager components and create a new frame.
 * <p>
 * <p>
 * Created by Daniel on 10/06/2015.
 */
public class MaterialLookView extends JFrame {

    // Painel Defaul
    private JPanel jp;

    // Buttons Minimize and CLose
    private JButton btMinimize, btClose;


    public MaterialLookView() {
        super();
        initFrame();
    }

    /**
     * Constructor
     *
     * @param title     - Title of Window
     * @param iconFrame - Icon of Aplication
     */

    public MaterialLookView(String title, Image iconFrame) {
        super(title);
        super.setIconImage(iconFrame);
        initFrame();
    }

    /**
     * Construtor
     *
     * @param title - Title of window
     */

    public MaterialLookView(String title) {
        super(title);
        initFrame();
    }

    // initialize componntes and frame
    private void initFrame() {

        /* Frame (Janela) */

        // Icon and Title

        // Conf Frame
        this.setUndecorated(true);
        this.setLocation(250, 250);


        // Icon of frame
        if (getIconImage() != null) {
            JLabel iconFrame = new JLabel(new ImageIcon(getIconImage().getScaledInstance(18, 18, 50)));
            iconFrame.setBounds(10, 8, 18, 18);
            rootPane.add(iconFrame);
        }

        if ((getTitle() != null) || (getTitle() != "")) {
            JLabel titleFrame = new JLabel(getTitle());
            titleFrame.setBounds(getIconImage() != null ? 38 : 10, 7, 140, 20);
            titleFrame.setFont(new Font("Consolas", Font.PLAIN, 13));
            titleFrame.setForeground(Color.white);

            rootPane.add(titleFrame);
        }

        // Buttton Close
        btClose = this.setCustomButton(new JButton(), "Fechar", DrawableRes.IC_ACTION_BAR_CLOSE_NORMAL.build(),
                DrawableRes.IC_ACTION_BAR_CLOSE_PRESSED.build(), DrawableRes.IC_ACTION_BAR_CLOSE_SELECTED.build());
        btClose.setBounds((getBounds().x - 4), 8, 19, 19);
        btClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChrUI.activeThread = false;
                ChrUI.pausedThread = false;
                dispose();
            }
        });
        rootPane.add(btClose);

        // Buttton Min
        btMinimize = this.setCustomButton(new JButton(), "Minimizar", DrawableRes.IC_ACTION_BAR_MINIMIZE_NORMAL.build(),
                DrawableRes.IC_ACTION_BAR_MINIMIZE_PRESSED.build(), DrawableRes.IC_ACTION_BAR_MINIMIZE_SELECTED.build());
        btMinimize.setBounds((getBounds().x - 27), 8, 19, 19);
        btMinimize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setExtendedState(ICONIFIED);
            }
        });
        rootPane.add(btMinimize);

        // Pane Default
        jp = new JPanel();
        jp.setLayout(null);
        this.add(jp);

        // Colors Default
        this.setBackgroundPrimary(new Color(194, 194, 194));
        this.setBackgroundSecundary(new Color(139, 139, 139));

        // Listenr in Window
        MouseAdapter ma = new MouseAdapter() {
            int lastX, lastY;
            boolean located;

            @Override
            public void mousePressed(MouseEvent e) {

                if (e.getPoint().y <= 34) {
                    lastX = e.getXOnScreen();
                    lastY = e.getYOnScreen();
                    located = true;
                } else {
                    located = false;
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {

                if (located) {
                    int x = e.getXOnScreen();
                    int y = e.getYOnScreen();

                    setLocation(getLocationOnScreen().x + x - lastX,
                            getLocationOnScreen().y + y - lastY);

                    lastX = x;
                    lastY = y;
                }
            }
        };

        this.addMouseListener(ma);
        this.addMouseMotionListener(ma);
    }

    // Define the colors of borders
    public void setBackgroundSecundary(Color bgColor) {
        setBorderInFrame(bgColor, 35, 6, 6, 6);
    }

    // Define the borders and colors of window
    public void setBorderInFrame(Color c, int top, int left, int bottom, int right) {
        rootPane.setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, c));
    }

    // Add component in JPanel
    public Component addComponentInPanel(Component comp) {
        return jp.add(comp);
    }

    // Color of painel
    public void setBackgroundPrimary(Color bgColor) {
        jp.setBackground(bgColor);
    }

    public Color getColorPainel() {
        return jp.getBackground();
    }

    // Custom button
    protected JButton setCustomButton(JButton source, String name, Image icNormal, Image icPressed) {

        return buildButton(source, name, icNormal, icPressed, null , null);
    }

    // Custom button
    protected JButton setCustomButton(JButton source, String name, Image icNormal, Image icPressed, Image icSelected) {

        return buildButton(source, name, icNormal, icPressed, icSelected , null);
    }

    // Custom button
    protected JButton setCustomButton(JButton source, String name, Image icNormal, Image icPressed, Rectangle bounds) {

        return buildButton(source, name, icNormal, icPressed, null , bounds);
    }

    // Custom button
    protected JButton setCustomButton(JButton source, String name, Image icNormal, Image icPressed, Image icSelected, Rectangle bounds) {

        return buildButton(source, name, icNormal, icPressed, icSelected , bounds);
    }

    private JButton buildButton(JButton source, String name, Image icNormal, Image icPressed, Image icSelected, Rectangle bounds) {
        source.setName(name);
        source.setOpaque(false);

        if (bounds != null) {
            source.setBounds(bounds);
        }
        if (icNormal != null) {
            source.setIcon(new ImageIcon(icNormal));
        }

        if (icPressed != null) {
            source.setPressedIcon(new ImageIcon(icPressed));
        }

        if (icSelected != null) {
            source.setRolloverIcon(new ImageIcon(icSelected));
        }

        source.setBorderPainted(false);
        source.setContentAreaFilled(false);
        source.setToolTipText(name);
        return source;
    }
}