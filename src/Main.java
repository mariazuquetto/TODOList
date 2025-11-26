import model.ItemPadrao;
import java.time.LocalDate;

public class Main {
    public static void main(String args[]) {
        ItemPadrao item = new ItemPadrao("Fazer compras (ver lista de compras)", LocalDate.of(2025, 12, 12));
        System.out.println(item.descrever());
} }
