package br.dbt.chr.ui.components;

import br.dbt.chr.resources.DrawableRes;
import br.dbt.chr.util.StateIO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;

/**
 * Classe dedicada para criação de janelas customizadas com uma aparencia mais bonita, atualmente esta em versões.
 * Se for ultilizar esta classe, crie o mesmo processo de uma janela do JFrame, mas com algumas diferenças:
 * <p><code>super("Titulo", 300,300)</code> - Define o titulo e tamanho direto pelo construtor, opcionalmente o icone.</p>
 * <p><code>initFrame()</code> - Implemente no construtor para definir o tipo de janela.</p>
 * <p><code>setBackgroundPrimary()</code> - Define a cor do painel</p>
 * <p><code>setBackgroundSecundary()</code> - Define a cor da janela.</p>
 * <p><code>addComponentInPanel()</code> - Adicionar componentes ao painel padrão do frame.</p>
 * <br>
 * <head><b>Ultilitarios:</b></head>
 * <p><code>createCustomButton()</code> - Cria um botão customizado</p>
 * <p><code>createAnimationWithMouse()</code> - Cria uma animação basica para o painel que usa o mouse como acionamento.</p>
 * <br><br>Esta janela contem apenas 2 tipos de Dialogos (Janela).
 * <br>
 * <p>Created by Daniel on 10/06/2015.
 *
 * @version 1.2
 * @see java.awt.Window.Type
 * @see br.dbt.chr.resources.values.ColorValue
 * @since JDK 1.8
 */
public class MaterialLookView extends JFrame implements Serializable {


    private static final long serialVersionUID = -3741572581395532138L;

    // Painel padrão da janela
    public JPanel jp;
    /**
     * Na implemntação é usada a vareavel do tipo Runtime para manusear a aplicação (Memoria, etc).
     */
    protected transient Runtime runtime = Runtime.getRuntime();
    /* Botões da janela fechar e minimizar, como não ainda não existe o botão de maximizar, é somente ultilizado para montar caixa de dialogos
      ou Janelas menores (Por enquanto) a partir se essa classe for ultilizada com frequencia, a uma determinação que isso aparecera na proxima atualização.
     */
    private transient JButton btClose, btMin;

    // Define se deseja serializar esse objeto
    private transient boolean setSerializable;
    private transient String path;
    private transient Object getObject;

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
     * @param title  - Titulo da janela
     * @param width  - Largura da janela
     * @param height - Altura da Janela
     */

    public MaterialLookView(String title, int width, int height) {
        super(title);
        super.setSize(width, height);
    }

    /**
     * Metodo para a criação de animações basicas com sequencias de imagens.
     *
     * @param size        - Tamanho da imagem do JLabel.
     * @param repeat      - Numero de repetições (Se for 0, será repetido uma vez).
     * @param delay       - Delay da animação.
     * @param imgSequence - Coleções de imagens para que possa execultar uma atraz da outra.
     * @return - Jlabel para manusear e add no container da janela ou painel.
     */
    public static JLabel createAnimationWithMouse(int size, int repeat, int delay, Image[] imgSequence) {
        JLabel source = new JLabel(new ImageIcon(imgSequence[0].getScaledInstance(size, size, 50)));
        source.setSize(size, size);
        source.setDoubleBuffered(true);
        source.setOpaque(false);
        source.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);

                synchronized (e) {
                    new Thread(() -> {
                        final int posArray = imgSequence.length - 1;

                        for (int r = 1; r <= (repeat == 0 ? 1 : repeat); ++r) {
                            for (int pos = 0; pos <= posArray; pos++) {
                                try {
                                    source.setIcon(new ImageIcon(imgSequence[pos].getScaledInstance(size, size, 50)));
                                    Thread.sleep(delay);
                                } catch (InterruptedException e1) {
                                    e1.printStackTrace();
                                    // Dialog box
                                }
                            }
                            source.setIcon(new ImageIcon(imgSequence[0].getScaledInstance(size, size, 50)));
                        }
                    }, "Anim").start();
                }
            }
        });
        return source;
    }

    protected void initFrame(Type type, boolean setSerializable) {
        this.initFrame(type);
        this.setSerializable = setSerializable;
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
        super.add(jp);

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

    public void saveState(String path, Object getObject) {
        if ((path != null) || !(path.equals(""))) {
            this.path = path;
            this.getObject = getObject;
            this.setSerializable = true;
        } else {
            throw new NullPointerException("Define o caminho do arquivo!!");
        }
    }

    /**
     * Inicialização dos botões fechar e maximizar (Alpha)
     *
     * @since JDK 1.8
     */

    protected void initButtonsOfWindow() {
        ActionListener actionButton = (event) -> {
            if (event.getSource() == btClose) {
                if (getType() != Type.UTILITY) {
                    if (setSerializable) {
                        StateIO state = new StateIO(path);
                        state.putObjectForSerializable(getObject);
                        state.save();
                    }

                    switch (getDefaultCloseOperation()) {
                        case HIDE_ON_CLOSE:
                            setVisible(false);
                            break;
                        case EXIT_ON_CLOSE:
                            System.exit(0);
                            break;
                        case DISPOSE_ON_CLOSE:
                            dispose();
                            break;
                        case DO_NOTHING_ON_CLOSE:
                    }
                }
                dispose();
            } else if (event.getSource() == btMin) {
                this.setExtendedState(ICONIFIED);
            }
        };

        // Se o tipo for normal, será um frame normal (Janela padrão)
        if (getType() == Type.NORMAL) {

            // Buttton Min
            btMin = this.createCustomButton(btMin == null ? new JButton() : btMin, "Minimizar", DrawableRes.IC_ACTION_BAR_MINIMIZE_NORMAL.build(),
                    DrawableRes.IC_ACTION_BAR_MINIMIZE_PRESSED.build(), DrawableRes.IC_ACTION_BAR_MINIMIZE_SELECTED.build());
            btMin.setBounds((getWidth() - 47), 8, 19, 19);
            btMin.setFocusable(false);
            btMin.addActionListener(actionButton);
            rootPane.add(btMin);

            // Buttton Close
            btClose = this.createCustomButton(btClose == null ? new JButton() : btClose, "Fechar", DrawableRes.IC_ACTION_BAR_CLOSE_NORMAL.build(),
                    DrawableRes.IC_ACTION_BAR_CLOSE_PRESSED.build(), DrawableRes.IC_ACTION_BAR_CLOSE_SELECTED.build());
            btClose.setBounds((getWidth() - 25), 8, 19, 19);
            btClose.setFocusable(false);
            btClose.addActionListener(actionButton);
            rootPane.add(btClose);

            // Se o tipo for um dialogo ultilitario, será um frame somente com o botão fechar.
        } else if (getType() == Type.UTILITY) {
            // Buttton Close
            btClose = this.createCustomButton(btClose == null ? new JButton() : btClose, "Fechar", DrawableRes.IC_ACTION_BAR_CLOSE_NORMAL.build(),
                    DrawableRes.IC_ACTION_BAR_CLOSE_PRESSED.build(), DrawableRes.IC_ACTION_BAR_CLOSE_SELECTED.build());
            btClose.setBounds((getWidth() - 25), 8, 19, 19);
            btClose.setFocusable(false);
            btClose.addActionListener(actionButton);
            rootPane.add(btClose);
        }

    }

    /**
     * @param type - O tipo da janela a ser inicializado:
     *             <code>
     *             Type.ULTILITY // tipo caixa de ultilidades (Dialogo)
     *             Type.NORMAL // tipo de janela normal (Padrão)
     *             </code>
     */
    @Override
    public void setType(Type type) {
        super.setType(type);
        initButtonsOfWindow();
    }

    // Cria a borda da janela
    private void setBorderInFrame(Color c, int top, int left, int bottom, int right) {
        rootPane.setBackground(c);
        rootPane.setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, c));
    }

    /**
     * Adiciona os componentes direto no painel padrão desta classe.
     *
     * @param comp - O componente ou a classe que herda de "Component".
     * @return - O Argumento do metodo.
     */
    public Component addComponentInPanel(Component comp) {
        Component c = jp.add(comp);
        jp.validate();
        return c;
    }

    /**
     * @return
     * @see MaterialLookView
     * @deprecated - For the sake algorithm in class "MaterialLookView.java", use the method <code>addComponentInPanel</code>
     */

    @Deprecated
    @Override
    public Component add(Component comp) {
        return super.add(comp);
    }

    /**
     * @return - Retorna a cor primaria (Cor do painel)
     */
    public Color getBackgroundPrimary() {
        return jp.getBackground();
    }

    /**
     * Define a cor primaria da janela (Painel)
     *
     * @param bgColor - A cor do painel
     */
    public void setBackgroundPrimary(Color bgColor) {
        jp.setBackground(bgColor);
    }

    /**
     * @return - Retorna a cor secundaria (Cor da borda).
     */
    public Color getBackgroundSecundary() {
        return rootPane.getBackground();
    }

    /**
     * Define a cor secundaria da janela (Bordas)
     *
     * @param bgColor - A cor das bordas
     *                <p>
     *                Obs: por padrão a borda já é inicializada nesse metodo, pode ocorrer mudanças!
     */
    public void setBackgroundSecundary(Color bgColor) {
        setBorderInFrame(bgColor, 35, 6, 6, 6);
    }

    /**
     * @return - A cor do painel definido pelo metodo "setColorPrimary"
     */
    public Color getColorPainel() {
        return jp.getBackground();
    }

    // Custom button
    protected JButton createCustomButton(JButton source, String name, Image icNormal, Image icPressed) {

        return buildButton(source, name, icNormal, icPressed, null, null);
    }

    // Custom button
    protected JButton createCustomButton(JButton source, String name, Image icNormal, Image icPressed, Image icSelected) {

        return buildButton(source, name, icNormal, icPressed, icSelected, null);
    }

    // Custom button
    protected JButton createCustomButton(JButton source, String name, Image icNormal, Image icPressed, Rectangle bounds) {

        return buildButton(source, name, icNormal, icPressed, null, bounds);
    }

    // Custom button
    protected JButton createCustomButton(JButton source, String name, Image icNormal, Image icPressed, Image icSelected, Rectangle bounds) {

        return buildButton(source, name, icNormal, icPressed, icSelected, bounds);
    }

    private JButton buildButton(final JButton source, String name, Image icNormal, Image icPressed, Image icSelected, Rectangle bounds) {
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

    /**
     * Em breve....
     */
    @Deprecated
    public JButton createCustomButtonWithAnimation(JButton customButtonSource, int size, int delay) {
        return customButtonSource;
    }
}