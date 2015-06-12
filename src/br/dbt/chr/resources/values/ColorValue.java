package br.dbt.chr.resources.values;

import java.awt.*;

/**
 * Created by Daniel on 09/06/2015.
 */
public enum ColorValue {

    WHITE(Color.WHITE),
    GREEN_PRIMARY(0, 139, 139),
    GREEN_SECUNDARY(0, 121, 107),
    BLUE_PRIMARY(25, 118, 210),
    BLUE_SECUNDARY(21, 101, 192),
    GRAY_PRIMARY(97, 97, 97),
    GRAY_SECUNDARY(80, 80, 80),
    RED_PRIMARY(211, 47, 47),
    RED_SECUNDARY(190, 40, 40),
    PINK_PRIMARY(233, 30, 99),
    PINK_SECUNDARY(194, 24, 91),
    ORANGE_PRIMARY(251,140,0),
    ORANGE_SECUNDARY(239, 108, 0),
    PURPLE_PRIMARY(103, 58, 183),
    PURPLE_SECUNDARY(81, 45, 168),
    YELLOW_PRIMARY(255, 179, 0),
    YELLOW_SECUNDARY(255, 143, 0);


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
