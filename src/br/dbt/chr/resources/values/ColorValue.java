package br.dbt.chr.resources.values;

import java.awt.*;

/**
 * Created by Daniel on 09/06/2015.
 */
public enum ColorValue {

    WHITE(Color.WHITE),
    GREEN(0, 139, 139),
    BLUE(21, 101, 192),
    BLACK(66, 66, 66),
    RED(165, 042, 042),
    PINK(233, 30, 99),
    ORANGE(251,140,0),
    PURPLE(103, 58, 183),
    YELLOW(255, 179, 0);


    private Color getValue;

    private ColorValue(Color value) {
        this.getValue = value;
    }

    private ColorValue(int r, int g, int b) {
        this.getValue = new Color(r, g, b);
    }

    public Color build() {
        return getValue;
    }
}
