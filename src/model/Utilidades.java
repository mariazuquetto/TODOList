package model;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Classe utilitária que fornece métodos auxiliares estáticos para validação e formatação de dados.
 * <p>
 * Centraliza a lógica de verificação de strings, valores numéricos e datas, bem como a conversão
 * entre objetos {@link LocalDate} e Strings no formato padrão do sistema ("dd/MM/yyyy").
 * </p>
 */
public class Utilidades {
    private static final DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Verifica se uma string é válida (não nula e não vazia/em branco).
     *
     * @param string A string a ser verificada.
     * @return {@code true} se a string contiver texto válido; {@code false} se for nula ou estiver em branco.
     */
    public static boolean stringValida(String string) {
        if (string == null || string.isBlank()) {
            return false;
        }
        return true;
    }

    /**
     * Verifica se um valor inteiro é válido (maior que zero).
     * Útil para validar quantidades.
     *
     * @param valor O número inteiro a ser verificado.
     * @return {@code true} se o valor for maior que 0; {@code false} caso contrário.
     */
    public static boolean valorValido(int valor) {
        if (valor > 0) {
            return true;
        }
        return false;
    }

    /**
     * Verifica se um valor decimal (double) é válido (maior ou igual a zero).
     * Útil para validar preços ou notas.
     *
     * @param valor O número decimal a ser verificado.
     * @return {@code true} se o valor for não-negativo; {@code false} caso contrário.
     */
    public static boolean valorValido(double valor) {
        if (valor >= 0) {
            return true;
        }
        return false;
    }

    /**
     * Verifica se uma data é válida (não nula e futura/presente).
     * <p>
     * Garante que prazos ou metas não sejam definidos para datas passadas em relação ao dia de hoje.
     * </p>
     *
     * @param data A data a ser verificada.
     * @return {@code true} se a data for válida (hoje ou futuro); {@code false} se for nula ou passada.
     */
    public static boolean dataValida(LocalDate data) {
        LocalDate dataHoje = LocalDate.now();

        if (data == null || data.isBefore(dataHoje)) {
            return false;
        }
        return true;
    }

    /**
     * Formata um objeto {@link LocalDate} para uma String no padrão "dd/MM/yyyy".
     *
     * @param data A data a ser formatada.
     * @return A representação textual da data.
     */
    public static String formatarData(LocalDate data) {
        return data.format(formatadorData);
    }

    /**
     * Converte uma String no padrão "dd/MM/yyyy" para um objeto {@link LocalDate}.
     *
     * @param data A string contendo a data.
     * @return O objeto data correspondente.
     * @throws java.time.format.DateTimeParseException Se a string não estiver no formato esperado.
     */
    public static LocalDate formatarData(String data) {
        return LocalDate.parse(data, formatadorData);
    }
}