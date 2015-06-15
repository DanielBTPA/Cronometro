package br.dbt.chr.ui.context;

import br.dbt.chr.resources.DrawableRes;
import br.dbt.chr.ui.ChrUI;

import javax.swing.*;
import java.awt.*;
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
    private JButton btClose, btMin;

    public MaterialLookView(int width, int height) {
        super.setSize(width, height);
    }

    /**
     * Constructor
     *
     * @param title     - Title of Window
     * @param iconFrame - Icon of Aplication
     */

    public MaterialLookView(String title, Image iconFrame, int width, int height) {
        super(title);
        super.setIconImage(iconFrame);
        super.setSize(width, height);
    }

    /**
     * Construtor
     *
     * @param title - Title of window
     */

    public MaterialLookView(String title, int width, int height) {
        super(title);
        super.setSize(width, height);
    }

    // initialize componntes and frame
    protected void initFrame(Type type) {

        /* Frame (Janela) */

        // Initialize Buttons Close and minimize
        this.setType(type);


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

        // Pane Default
        jp = new JPanel();
        jp.setLayout(null);
        this.add(jp);

        // Cores padrões da janela
        this.setBackgroundPrimary(new Color(194, 194, 194));
        this.setBackgroundSecundary(new Color(139, 139, 139));

        // Ouvinte local do mouse (Arrastar a janela)
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

    protected void initButtonsOfWindow() {

        if (getType() == Type.NORMAL) {
            // Buttton Min
            btMin = this.setCustomButton(btMin == null ? new JButton() : btMin, "Minimizar", DrawableRes.IC_ACTION_BAR_MINIMIZE_NORMAL.build(),
                    DrawableRes.IC_ACTION_BAR_MINIMIZE_PRESSED.build(), DrawableRes.IC_ACTION_BAR_MINIMIZE_SELECTED.build());
            btMin.setBounds((getWidth() - 47), 8, 19, 19);
            btMin.setFocusable(false);
            btMin.addActionListener((e) -> {
                setExtendedState(ICONIFIED);
            });
            rootPane.add(btMin);
            // Buttton Close
            btClose = this.setCustomButton(btClose == null ? new JButton() : btClose, "Fechar", DrawableRes.IC_ACTION_BAR_CLOSE_NORMAL.build(),
                    DrawableRes.IC_ACTION_BAR_CLOSE_PRESSED.build(), DrawableRes.IC_ACTION_BAR_CLOSE_SELECTED.build());
            btClose.setBounds((getWidth() - 25), 8, 19, 19);
            btClose.setFocusable(false);
            btClose.addActionListener((e) -> {
                if (getType() != Type.UTILITY) {
                    ChrUI.setStart(false);
                    System.exit(0);
                }
                dispose();
            });
            rootPane.add(btClose);
        } else if (getType() == Type.UTILITY) {
            // Buttton Close
            btClose = this.setCustomButton(btClose == null ? new JButton() : btClose, "Fechar", DrawableRes.IC_ACTION_BAR_CLOSE_NORMAL.build(),
                    DrawableRes.IC_ACTION_BAR_CLOSE_PRESSED.build(), DrawableRes.IC_ACTION_BAR_CLOSE_SELECTED.build());
            btClose.setBounds((getWidth() - 25), 8, 19, 19);
            btClose.setFocusable(false);
            btClose.addActionListener((e) -> {
                if (getType() != Type.UTILITY) {
                    ChrUI.setStart(false);
                    System.exit(0);
                }
                dispose();
            });
            rootPane.add(btClose);
        }

    }

    @Override
    public void setType(Type type) {
        super.setType(type);
        initButtonsOfWindow();
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

    // Define the colors of borders
    public void setBackgroundSecundary(Color bgColor) {
        setBorderInFrame(bgColor, 35, 6, 6, 6);
    }

    public Color getColorPainel() {
        return jp.getBackground();
    }

    // Custom button
    protected JButton setCustomButton(JButton source, String name, Image icNormal, Image icPressed) {

        return buildButton(source, name, icNormal, icPressed, null, null);
    }

    // Custom button
    protected JButton setCustomButton(JButton source, String name, Image icNormal, Image icPressed, Image icSelected) {

        return buildButton(source, name, icNormal, icPressed, icSelected, null);
    }

    // Custom button
    protected JButton setCustomButton(JButton source, String name, Image icNormal, Image icPressed, Rectangle bounds) {

        return buildButton(source, name, icNormal, icPressed, null, bounds);
    }

    // Custom button
    protected JButton setCustomButton(JButton source, String name, Image icNormal, Image icPressed, Image icSelected, Rectangle bounds) {

        return buildButton(source, name, icNormal, icPressed, icSelected, bounds);
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