package model;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Utilidades {
    private static final DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static String formatarData(LocalDate data) {
        if (data == null) {
            return "";
            /// LIDAR
        }
        return data.format(formatadorData);
    }
}
