package model;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Utilidades {
    private static final DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static boolean podeFormatarData(LocalDate data) {
        LocalDate dataHoje = LocalDate.now();

        if (data == null || data.isBefore(dataHoje)) {
            return false;
        }
        return true;
    }

    public static String formatarData(LocalDate data) {
        return data.format(formatadorData);
    }
}
