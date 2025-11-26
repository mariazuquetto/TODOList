import model.ItemPadrao;

void main() {
    ItemPadrao item = new ItemPadrao("Fazer compras (ver lista de compras)", LocalDate.of(2025, 12, 12));
    System.out.println(item.descrever());
}
