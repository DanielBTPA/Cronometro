package br.dbt.chr.resources.values;

import java.util.stream.IntStream;

/**
 * Created by Daniel on 09/06/2015.
 */
public enum StringValue {

    ST_BT_OK ("OK"),
    ST_CONT_0("00:00:00"),
    ST_BT_START("Iniciar"),
    ST_BT_PAUSE("Pausar"),
    ST_BT_RESET("Resetar"),
    ST_BT_CLEAR("Limpar"),
    ST_BT_SETTINGS("Configurar"),
    ST_BT_ABOUT("Sobre"),
    ST_BT_ADD_HISTORY("Adicionar ao historico"),
    ST_TITLE("Cronometro"),
    ST_INFO_MSM("min | sec | mil"),
    ST_PAUSED_SPACES("    :    :   "),
    ST_LB_ADD_TWO_PLAN("Adicionar ao segundo plano"),
    ST_LB_SHOW_CLEAR("Adicionar botão limpar"),
    ST_LB_THEME("Cores de fundo e janela:");



    private String getValue;

    private StringValue(String value) {
        this.getValue = value;
    }

    @Override
    public String toString() {
        return getValue;
    }

    public static enum StringArrayValue {

        CB_ITEMS_THEME("Azul", "Vermelho", "Gray", "Verde", "Pink", "Laranja", "Purple", "Amarelo");

        private String[] getValue;

        private StringArrayValue(String... value) {
            this.getValue = value;
        }

        public String[] getArray() {
            return getValue;
        }

        public String getString(int index) {
            return getValue[index];
        }

    }

}
