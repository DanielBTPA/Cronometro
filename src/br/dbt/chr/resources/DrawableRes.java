package br.dbt.chr.resources;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * Enum dedicada para obter drawables existentes na pasta "br.dbt.chr.resources.drawable".
 *
 * @since 1.0
 */

public enum DrawableRes {

    // Icons Default
    ABOUT_LOGO("br/dbt/chr/resources/drawable/images/about_logo.jpg", 305, 295),
    IC_LAUNCHER("br/dbt/chr/resources/drawable/ic_launcher.png", null),
    IC_LAUNCHER_SMALL("br/dbt/chr/resources/drawable/ic_launcher.png", 15, 15),

    // Icons Normal
    IC_ACTION_PLAY_NORMAL("br/dbt/chr/resources/drawable/ic_action_play_normal.png", 75, 75),
    IC_ACTION_PAUSE_NORMAL("br/dbt/chr/resources/drawable/ic_action_pause_normal.png", 75, 75),
    IC_ACTION_CLEAR_NORMAL("br/dbt/chr/resources/drawable/ic_action_clear_normal.png", 30, 30),
    IC_ACTION_ADD_HISTORY_NORMAL("br/dbt/chr/resources/drawable/ic_action_add_history_normal.png", 30, 30),
    IC_ACTION_SETTINGS_NORMAL("br/dbt/chr/resources/drawable/ic_action_settings_normal.png", 30, 30),
    IC_ACTION_ABOUT_NORMAL("br/dbt/chr/resources/drawable/ic_action_about_normal.png", 30, 30),
    IC_ACTION_RESET_NORMAL("br/dbt/chr/resources/drawable/ic_action_reset_normal.png", 30, 30),

    // Icons Pressed
    IC_ACTION_PLAY_PRESSED("br/dbt/chr/resources/drawable/ic_action_play_pressed.png", 75, 75),
    IC_ACTION_PAUSE_PRESSED("br/dbt/chr/resources/drawable/ic_action_pause_pressed.png", 75, 75),
    IC_ACTION_CLEAR_PRESSED("br/dbt/chr/resources/drawable/ic_action_clear_pressed.png", 30, 30),
    IC_ACTION_ADD_HISTORY_PRESSED("br/dbt/chr/resources/drawable/ic_action_add_history_pressed.png", 30, 30),
    IC_ACTION_SETTINGS_PRESSED("br/dbt/chr/resources/drawable/ic_action_settings_pressed.png", 30, 30),
    IC_ACTION_ABOUT_PRESSED("br/dbt/chr/resources/drawable/ic_action_about_pressed.png", 30, 30),
    IC_ACTION_RESET_PRESSED("br/dbt/chr/resources/drawable/ic_action_reset_pressed.png", 30, 30);


    // Vareaveis para recuperação de valores!
    private URL getPath;
    private int getX, getY;

    private DrawableRes(String resDraw, int... size) {
        getPath = getClass().getClassLoader().getResource(resDraw);

        if (size != null) {
            getX = size[0];
            getY = size[1];
        }
    }

    /** Cria o recurso na memoria e retorna a imagem criada de acordo com as constantes!
     */

    public Image build() {
        Image building = null;
        BufferedImage ic = null;
        try {
            ic = ImageIO.read(getPath);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if ((getX != 0) && (getY != 0)) {
                building = ic.getScaledInstance(getX,
                        getX, 100);
            } else {
                building = Toolkit.getDefaultToolkit().getImage(getPath);
            }
        }
        return building;
    }


}
