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
	
	//
	
	// Icon github
	IC_ACTION_OPEN_GIT_NORMAL("br/dbt/chr/resources/drawable/ic_action_open_git_normal.png", 30, 30),
    IC_ACTION_OPEN_GIT_PRESSED("br/dbt/chr/resources/drawable/ic_action_open_git_pressed.png", 30, 30),
    IC_ACTION_OPEN_GIT_SELECTED("br/dbt/chr/resources/drawable/ic_action_open_git_selected.png", 30, 30),

    // Icons Logo
	LOGO("br/dbt/chr/resources/drawable/image/logo.png", 300, 230),
    LOGO_1("br/dbt/chr/resources/drawable/image/logo_1.png", 160, 160),
    LOGO_2("br/dbt/chr/resources/drawable/image/logo_2.png", 160, 160),

    // Icon Launcher
    IC_LAUNCHER("br/dbt/chr/resources/drawable/ic_launcher.png", null),

    // Icons action close and minimize
    IC_ACTION_BAR_CLOSE_NORMAL("br/dbt/chr/resources/drawable/ic_action_bar_close_normal.png", 20, 20),
    IC_ACTION_BAR_CLOSE_SELECTED("br/dbt/chr/resources/drawable/ic_action_bar_close_selected.png", 20, 20),
    IC_ACTION_BAR_CLOSE_PRESSED("br/dbt/chr/resources/drawable/ic_action_bar_close_pressed.png", 20, 20),
    IC_ACTION_BAR_MINIMIZE_NORMAL("br/dbt/chr/resources/drawable/ic_action_bar_minimize_normal.png", 20, 20),
    IC_ACTION_BAR_MINIMIZE_SELECTED("br/dbt/chr/resources/drawable/ic_action_bar_minimize_selected.png", 20, 20),
    IC_ACTION_BAR_MINIMIZE_PRESSED("br/dbt/chr/resources/drawable/ic_action_bar_minimize_pressed.png", 20, 20),

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


    // Constantes
    private static final int HINTS = 100;

    // Vareaveis para recuperação de valores!
    private URL getPath;
    private int getWidth, getHeight;

    private DrawableRes(String resDraw, int... size) {
        getPath = getClass().getClassLoader().getResource(resDraw);

        if (size != null) {
            getWidth = size[0];
            getHeight = size[1];
        }
    }

    /**
     * Cria o recurso na memoria e retorna a imagem criada de acordo com as constantes!
     */

    public Image build() {
        Image building = null;
        BufferedImage ic = null;
        try {
            ic = ImageIO.read(getPath);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if ((getWidth != 0) && (getHeight != 0)) {
                building = ic.getScaledInstance(getWidth,
                        getHeight, 100);
            } else {
                building = Toolkit.getDefaultToolkit().getImage(getPath);
            }
        }
        return building;
    }

    public Image getImageScaled(int getWidth, int getHeight) {
        Image img = null;
        try {
            BufferedImage buf = ImageIO.read(getPath);
            img = buf.getScaledInstance(getWidth, getHeight, HINTS);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return img;
    }


}
