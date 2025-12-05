package model;

import java.io.*;

/**
 * Classe responsável pelas operações de Entrada e Saída (I/O) de dados do sistema.
 * <p>
 * Esta classe lida com a persistência dos dados da aplicação, permitindo salvar (serializar)
 * e carregar (desserializar) o estado completo do {@link Gerenciador} em ficheiro.
 * Utiliza o mecanismo de serialização nativo do Java.
 * </p>
 */
public class GerenciadorIO {

    /**
     * Nome do ficheiro padrão onde os dados são salvos automaticamente.
     */
    private static final String ARQUIVO_DADOS = "dados.ser";

    /**
     * Salva (serializa) o estado atual do gerenciador no ficheiro padrão ("dados.ser").
     *
     * @param gerenciador A instância do {@link Gerenciador} contendo os dados a serem salvos.
     * @throws IOException Se ocorrer um erro durante a escrita do ficheiro (ex: falha de disco, permissão negada).
     */
    public static void serializar(Gerenciador gerenciador) throws IOException {
        try (
                FileOutputStream fileOut = new FileOutputStream(ARQUIVO_DADOS);
                ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)
        ) {
            objectOut.writeObject(gerenciador);
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    /**
     * Carrega (desserializa) o estado do gerenciador a partir do ficheiro padrão ("dados.ser").
     * <p>
     * Se o ficheiro não existir, estiver corrompido ou ocorrer qualquer outro erro de leitura,
     * um novo {@link Gerenciador} vazio será retornado, garantindo que a aplicação possa iniciar
     * mesmo sem dados prévios.
     * </p>
     *
     * @return Uma instância de {@link Gerenciador} recuperada do ficheiro ou uma nova instância vazia em caso de falha.
     */
    public static Gerenciador desserializar() {
        Gerenciador gerenciador = new Gerenciador();
        try (FileInputStream fileIn = new FileInputStream(ARQUIVO_DADOS);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
            gerenciador = (Gerenciador) objectIn.readObject();
        } catch (FileNotFoundException e) {
            gerenciador = new Gerenciador();
        } catch (IOException | ClassNotFoundException e) {
            // Em caso de erro (ex: classe alterada, ficheiro corrompido), ignora e retorna um novo.
        }
        return gerenciador;
    }

    /**
     * Carrega o estado do gerenciador a partir de um ficheiro específico especificado pelo caminho.
     * <p>
     * Diferente do método {@link #desserializar()}, este método lança exceções explicitamente,
     * permitindo que a interface gráfica ou textual trate o erro e informe o utilizador.
     * </p>
     *
     * @param caminho O caminho (path) do ficheiro `.ser` a ser carregado.
     * @return A instância de {@link Gerenciador} carregada do ficheiro.
     * @throws IOException Se o ficheiro não for encontrado, não puder ser lido ou não contiver um Gerenciador válido.
     * @throws ClassNotFoundException Se a classe serializada não for encontrada no classpath atual.
     */
    public static Gerenciador carregarArquivo(String caminho) throws IOException, ClassNotFoundException {
        try (FileInputStream fileIn = new FileInputStream(caminho);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {

            Object obj = objectIn.readObject();
            if (obj instanceof Gerenciador) {
                return (Gerenciador) obj;
            } else {
                throw new IOException("Arquivo incompatível: não contém um Gerenciador válido.");
            }
        }
    }
}