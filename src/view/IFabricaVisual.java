package view;
import model.Gerenciador;
import model.Lista;

public interface IFabricaVisual {

    public IMenuView mostrarMenu(Gerenciador gerenciador);

    public <T extends Lista> IListaView mostrarLista(Gerenciador gerenciador, T lista);
}
