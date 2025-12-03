package model;
import java.io.*;

public class GerenciadorIO {
    private static final String ARQUIVO_DADOS = "dados.ser";

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

    public static Gerenciador desserializar() {
        Gerenciador gerenciador = new Gerenciador();
        try (FileInputStream fileIn = new FileInputStream(ARQUIVO_DADOS);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
             gerenciador = (Gerenciador) objectIn.readObject();
        } catch (FileNotFoundException e) {
            gerenciador = new Gerenciador();
        } catch (IOException | ClassNotFoundException e) {
        }
        return gerenciador;
        }
}
