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
 * Classe dedicada para criação de janelas customizadas com uma aparencia mais bonita, atualmente esta em beta.
 * Se for ultilizar esta classe, crie o mesmo processo de uma janela do JFrame, mas com umas diferenças:
 * <p>
 * <code>
 * class ClasseTeste extends MaterialLookView {
 * <p>
 *     ClasseTeste() {
 *         super("Janela Teste", 300, 300); // Titulo e tamanho da janela!
 *         this.initFrame(Type.NORMAL); // Tipo de Janela
 *         this.setVisible(true); // Visibilidade
 *     }
 * }
 * </code>
 * <p>
 * <p>
 * <p>
 * <p>
 * Created by Daniel on 10/06/2015.
 *
 * @since 1.0
 */
public class MaterialLookView extends JFrame {

    // Painel padrão da janela
    private JPanel jp;

    /* Botões da janela fechar e minimizar, como não ainda não existe o botão de maximizar, é somente ultilizado para montar caixa de dialogos
      ou Janelas menores (Por enquanto) a partir se essa classe for ultilizada com frequencia, a uma determinação que isso aparecera na proxima atualização.
     */
    private JButton btClose, btMin;

    /**
     * Construtor (Obrigatorio determinar o tamanho da janela)
     *
     * @param width  - Largura da janela
     * @param height - Altura da Janela
     */
    public MaterialLookView(int width, int height) {
        super.setSize(width, height);
    }

    /**
     * Construtor, pode atribuir um titulo e icone para janela (Obrigatorio determinar o tamanho da janela)
     *
     * @param title     - Titulo da janela
     * @param iconFrame - Icone da janela
     * @param width     - Largura da janela
     * @param height    - Altura da Janela
     */

    public MaterialLookView(String title, Image iconFrame, int width, int height) {
        super(title);
        super.setIconImage(iconFrame);
        super.setSize(width, height);
    }

    /**
     * Construtor, pode atribuir um titulo para janela (Obrigatorio determinar o tamanho da janela)
     *
     * @param title     - Titulo da janela
     * @param width     - Largura da janela
     * @param height    - Altura da Janela
     */

    public MaterialLookView(String title, int width, int height) {
        super(title);
        super.setSize(width, height);
    }

    // Inicialização da janela
    protected void initFrame(Type type) {

        /* Frame (Janela) */

        // Tipo da janela
        this.setType(type);


        // Preferencia da janela
        this.setUndecorated(true);
        this.setLocation(250, 250);


        // Icone da janela
        if (getIconImage() != null) {
            JLabel iconFrame = new JLabel(new ImageIcon(getIconImage().getScaledInstance(18, 18, 50)));
            iconFrame.setBounds(10, 8, 18, 18);
            rootPane.add(iconFrame);
        }

        // Titulo da janela
        if ((getTitle() != null) || (getTitle() != "")) {
            JLabel titleFrame = new JLabel(getTitle());
            titleFrame.setBounds(getIconImage() != null ? 38 : 10, 7, 140, 20);
            titleFrame.setFont(new Font("Consolas", Font.PLAIN, 13));
            titleFrame.setForeground(Color.white);

            rootPane.add(titleFrame);
        }

        // Inicialização do painel
        jp = new JPanel();
        jp.setLayout(null);
        this.add(jp);

        // Cores padrões da janela - Isso pode ser atribuido pelos metodos "setColorPrimary" e "setColorSecundary".
        this.setBackgroundPrimary(new Color(194, 194, 194));
        this.setBackgroundSecundary(new Color(139, 139, 139));

        // Ouvinte local do mouse (Arrastar a janela)
        MouseAdapter ma = new MouseAdapter() {
            int lastX, lastY;
            boolean located;

            @Override
            public void mousePressed(MouseEvent e) {

                /* Se "e.getPoint().y <= 34" então retornara a posição do ponteiro nessa aplicação (A janela que essa classe implemtará)
                ou seja, retornará true na varavél located;
                */
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

                /* Aqui é obtido o valor da janela e a posição da janela que é somado os dois valores e
                subtraido pelo valor antigo da posição do ponteiro na aplicação, tudo isso para que seja possivel
                arrastar a janela.
                */

                if (located) {
                    int x = e.getXOnScreen();
                    int y = e.getYOnScreen();

                    // Localização da janela com o valor da posição do mouse.
                    setLocation(getLocationOnScreen().x + x - lastX,
                            getLocationOnScreen().y + y - lastY);

                    lastX = x;
                    lastY = y;
                }
            }
        };

        // Eventos do mouse (Arrastar e clicar)
        this.addMouseListener(ma);
        this.addMouseMotionListener(ma);
    }

    /** Inicialização dos botões fechar e maximizar (Alpha)
     *
     *  @since 1.0
     */

    protected void initButtonsOfWindow() {
        ActionListener actionButton = (event) -> {
            if (event.getSource() == btClose) {
                if (getType() != Type.UTILITY) {
                    ChrUI.setStart(false);
                    System.exit(0);
                }
                dispose();
            } else if (event.getSource() == btMin) {
                this.setExtendedState(ICONIFIED);
            }
        };

        // Se o tipo for normal, será um frame normal (Janela padrão)
        if (getType() == Type.NORMAL) {

            // Buttton Min
            btMin = this.setCustomButton(btMin == null ? new JButton() : btMin, "Minimizar", DrawableRes.IC_ACTION_BAR_MINIMIZE_NORMAL.build(),
                    DrawableRes.IC_ACTION_BAR_MINIMIZE_PRESSED.build(), DrawableRes.IC_ACTION_BAR_MINIMIZE_SELECTED.build());
            btMin.setBounds((getWidth() - 47), 8, 19, 19);
            btMin.setFocusable(false);
            btMin.addActionListener(actionButton);
            rootPane.add(btMin);

            // Buttton Close
            btClose = this.setCustomButton(btClose == null ? new JButton() : btClose, "Fechar", DrawableRes.IC_ACTION_BAR_CLOSE_NORMAL.build(),
                    DrawableRes.IC_ACTION_BAR_CLOSE_PRESSED.build(), DrawableRes.IC_ACTION_BAR_CLOSE_SELECTED.build());
            btClose.setBounds((getWidth() - 25), 8, 19, 19);
            btClose.setFocusable(false);
            btClose.addActionListener(actionButton);
            rootPane.add(btClose);

            // Se o tipo for um dialogo ultilitario, será um frame somente com o botão fechar.
        } else if (getType() == Type.UTILITY) {
            // Buttton Close
            btClose = this.setCustomButton(btClose == null ? new JButton() : btClose, "Fechar", DrawableRes.IC_ACTION_BAR_CLOSE_NORMAL.build(),
                    DrawableRes.IC_ACTION_BAR_CLOSE_PRESSED.build(), DrawableRes.IC_ACTION_BAR_CLOSE_SELECTED.build());
            btClose.setBounds((getWidth() - 25), 8, 19, 19);
            btClose.setFocusable(false);
            btClose.addActionListener(actionButton);
            rootPane.add(btClose);
        }

    }

    /**
     *
     * @param type - O tipo da janela a ser inicializado:
     *       <code>
     *           Type.ULTILITY // tipo caixa de ultilidades (Dialogo)
     *           Type.NORMAL // tipo de janela normal (Padrão)
     *       </code>
     */
    @Override
    public void setType(Type type) {
        super.setType(type);
        initButtonsOfWindow();
    }

    /** Define as bordas da janela
     *
     *  @param c - A cor da borda.
     *  @param top - A largura da borda na parte de cima.
     *  @param left - A largura da borda no lado esquerdo.
     *  @param bottom - A largura da borda na parte de baixo.
     *  @param right - A largura da borda no lado direito.
     */

    public void setBorderInFrame(Color c, int top, int left, int bottom, int right) {
        rootPane.setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, c));
    }

    /** Adiciona os componentes direto no painel padrão desta classe.
     *
     * @param comp - O componente ou a classe que herda de "Component".
     *
     * @return - O Argumento do metodo.
     */
    public Component addComponentInPanel(Component comp) {
        return jp.add(comp);
    }

    /** Define a cor primaria da janela (Painel)
     *
     * @param bgColor - A cor do painel
     */
    public void setBackgroundPrimary(Color bgColor) {
        jp.setBackground(bgColor);
        jp.repaint();
    }

    /** Define a cor secundaria da janela (Bordas)
     *
     * @param bgColor - A cor das bordas
     *
     * Obs: por padrão a borda já é inicializada nesse metodo, pode ocorrer mudanças!
     */
    public void setBackgroundSecundary(Color bgColor) {
        setBorderInFrame(bgColor, 35, 6, 6, 6);
    }

    /** @return - A cor do painel definido pelo metodo "setColorPrimary"
     */
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