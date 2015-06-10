package br.dbt.chr.ui.context;

import javax.swing.border.AbstractBorder;
import java.awt.*;

/**
 * Classe dedicada de uma janela modificada para o cronomtro, UI mais melhorada.
 * <p>
 * Created by Daniel on 10/06/2015.
 */

@Deprecated
public class ChrLookFrame extends AbstractBorder {

    public ChrLookFrame() {


    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        super.paintBorder(c, g, x, y, width, height);

        g.fillRect(10,10,10,10);

        g.draw3DRect(20,20,20,20,true);
    }
}
