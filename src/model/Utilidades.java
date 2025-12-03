package model;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Utilidades {
    private static final DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static boolean stringValida(String string) {
        if (string == null || string.isBlank()) {
            return false;
        }
        return true;
    }

    public static boolean valorValido(int valor) {
        if (valor > 0) {
            return true;
        }
        return false;
    }

    public static boolean valorValido(double valor) {
        if (valor >= 0) {
            return true;
        }
        return false;
    }

    public static boolean dataValida(LocalDate data) {
        LocalDate dataHoje = LocalDate.now();

        if (data == null || data.isBefore(dataHoje)) {
            return false;
        }
        return true;
    }

    public static String formatarData(LocalDate data) {
        return data.format(formatadorData);
    }

    public static LocalDate formatarData(String data) {
        return LocalDate.parse(data, formatadorData);
    }
}
