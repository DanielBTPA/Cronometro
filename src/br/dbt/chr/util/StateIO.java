package br.dbt.chr.util;

/**
 * Created by Daniel on 19/06/2015.
 */

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Classe dedicada a guardar e recuperar objetos nos disco rigido.
 * <p> Para usar, use o metodo <code>putObjectForSerializable</code> para colocar objetos e depois salve
 * o estado usando o metodo <code>save</code>.</p>
 * <br>
 *
 * @since JDK 1.8
 * @see ObjectInputStream
 * @see ObjectOutputStream
 * @version 1.0
 */

public final class StateIO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -1530811968482460549L;

    // Caminho de pastas e arquivos
    private Path path;


    // Saida para os objetos
    private transient ObjectOutputStream objectOutput;

    // Entrada para os objetos
    private transient ObjectInputStream objectInput;

    /* Os objetos serão guardados nessa vareavel 'value'
       para que possa ser usado para re-colocar ou para recuperar
       valores.
     */
    private Object[] values;

    // Posição de arrays
    private int pos = 0;

    // posição antiga de arrays
    private int oldPosArray;

    /* Construtor padrão */
    public StateIO() {
        values = new Object[10];
        oldPosArray = (values.length - 1);
    }

    /* Outros construtores */

    /**
     * Construtor.
     *
     * @param path - Caminho do arquivo.
     *
     */
    public StateIO(String path) {
        this();
        this.setPath(path);
    }

    /** Construtor
     *
     * @param path - Caminho do arquivo.
     * @param value - O objeto para serialização.
     */
    public StateIO(String path, Object value) {
        this();
        this.setPath(path);
        this.putObjectForSerializable(value);
    }

    /** Construtor.
     *
     * @param path - Caminho do arquivo.
     * @param createPath - Se 'true' cria um diretorio com o caminho especificado.
     */
    public StateIO(String path, boolean createPath) {
        this();
        this.setPath(path, createPath);
    }

    /** Construtor.
     *
     * @param path Caminho do arquivo.
     * @param value O objeto para serialização.
     * @param createPath Se 'true' cria um diretorio com o caminho especificado.
     */
    public StateIO(String path, Object value, boolean createPath) {
        this();
        this.setPath(path, createPath);
        this.putObjectForSerializable(value);
    }

    @Deprecated
    /** Recupera dados serializado a partir de um caminho.
     *
     * @param path Caminho do diretorio e arquivo.
     * @return Uma instancia do tipo StateIO.
     */
    public static StateIO getStateObjectFromPath(String path) {
        StateIO s = new StateIO(path);
        try {
            s.getStateObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * @return Retorna o caminho do diretório.
     */
    public String getPath() {
        String s = null;

        if (path != null) {
            s = "" + path;
        } else {
            throw new NullPointerException("Coloque o caminho do diretorio ou arquivo!!");
        }
        return s;
    }

    /**
     * Define o caminho padrão.
     *
     * @param path Caminho do arquivo.
     */
    public void setPath(String path) {
        this.setPath(path, false);
    }

    /**
     * Define o caminho padrão.
     *
     * @param path Caminho do arquivo.
     * @param createFolders Se 'true' cria um diretorio com o caminho especificado.
     */
    public void setPath(String path, boolean createFolders) {
        this.path = Paths.get(path);
        try {
            this.createPath(createFolders);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Verifica se esse caminho existe.
     *
     * @return Se 'true' é porque o caminho existe!
     */
    public boolean isPathExists() {

        return Files.exists(path);
    }

    /** Verifica se o arquivo esta vazio!
     *
     * @return Se 'true' é porque esta vazio!
     */
    public boolean isFileEmpty() {
        try {
            BufferedReader in = new BufferedReader(new FileReader(getPath()));
            String r = in.readLine();
            if (r == null) {
                return true;
            }
            in.close();
        } catch (Exception e) {

        }
        return false;
    }

    /** Cria um diretorio, caso ele não exista.
     *
     * @throws IOException Caso de algum erro para criação do diretorio.
     */
    public void createPathIfNotExists() throws IOException {
        if (Files.notExists(path)) {
            this.createPath(true);
        }
    }

    /**
     * @return O nome do arquivo.
     */
    public String getFileName() {
        return "" + Paths.get(getPath()).getFileName();
    }

    /** Metodo especificamente para adicionar objetos a memoria
     *  para depois serem salvos no arquivo.
     *
     *  <b>Observação:</b><br>
     *  A cada chamada de metodo, serão definidos valores de posição do array, se
     *  esse metodo for chamada 10 vezes, então será criada mais 3 espaços de array.
     *
     * @param value O objeto a ser serializado.
     */
    public void putObjectForSerializable(Object value) {
        if (!(pos > oldPosArray)) {
            this.values[pos++] = value;
        } else {
            this.values = newArray(this.oldPosArray);
            this.values[pos++] = value;
        }
    }

    /** Salvar o estado dos objetos ou objeto unico colocado com o metodo <code>putObjectForSerializable</code>
     *
     */
    public void save() {
        this.writeObj();
    }

    /** Recupera os objetos ou objeto unico a partir do caminho setado pelo metodo <code>setPath</code>
     *  ou construtor.
     *  <br>
     *  <b>Observação:</b>
     *  <br> Veja que voce tera que converter os objetos a partir do tipo que voce definiu no metodo <code>putObjectForSerializable</code>.
     *  <br> Cada posição é um tipo de objeto, a cada chamada é uma nova posição no array de objetos.
     *
     * @return O objeto serializado.
     *
     * @throws ClassNotFoundException
     */
    public Object getStateObject() throws ClassNotFoundException {
        return getStateObject(0);
    }

    /** Recupera os objetos ou objeto unico a partir do caminho setado pelo metodo <code>setPath</code>
     *  ou construtor, a partir da posição.
     *  <br>
     *  <b>Observação:</b>
     *  <br> Veja que voce tera que converter os objetos a partir do tipo que voce definiu no metodo <code>putObjectForSerializable</code>.
     *  <br> Cada posição é um tipo de objeto, a cada chamada é uma nova posição no array de objetos.
     *
     * @return  O objeto serializado a partir da posição do array.
     *
     * @throws ClassNotFoundException
     */
    public Object getStateObject(int pos) throws ClassNotFoundException {
        try {
            FileInputStream fin = new FileInputStream(getPath());
            objectInput = new ObjectInputStream(fin);
            values = (Object[]) objectInput.readObject();
            objectInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int posArray = 0;
        for (Object get : values) {
            if (get != null) {
                values[posArray++] = get;

                if (this.pos == 0) {
                    this.pos = posArray;
                }
            }
        }

        if (pos >= posArray) {
            throw new ArrayIndexOutOfBoundsException("Posiçao maior que " + --posArray);
        }

        return values[pos];
    }

    /* Metodos privados */

    /**
     * @return - Retorna o array de objetos.
     */
    public Object[] getObjects() {
        try {
            getStateObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        return values;
    }

    private Object[] newArray(int oldPosArray) {
        Object[] newArray = new Object[oldPosArray + 3];
        int oldPos = 0;
        this.oldPosArray = (newArray.length - 1);
        for (Object v : values) {
            if (v != null) {
                newArray[oldPos++] = v;
            }
        }
        return newArray;
    }

    // Metodo para a criação de pastas e sub - pastas.
    private void createPath(boolean create) throws IOException {
        if (path != null) {
            if (create) {
                if (path.getParent() != null) {
                    Files.createDirectories(path.getParent());
                }

                if (path.getFileName() != null) {
                    Files.createFile(path);
                }
            }
        } else {
            throw new NullPointerException();
        }
    }

    // Grava dados da memoria no arquivo
    private void writeObj() {
        try {
            FileOutputStream fout = new FileOutputStream(getPath());
            objectOutput = new ObjectOutputStream(fout);
            if (pos != 0) {
                objectOutput.writeObject(values);
            } else {
                throw new NullPointerException("Adicione um objeto!!");
            }

            objectOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
