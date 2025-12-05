package view.grafica;

import model.Gerenciador;
import model.Lista;
import view.IFabricaVisual;
import view.IListaView;
import view.IMenuView;

public class ViewGraficaFactory implements IFabricaVisual {
    public static ViewGraficaFactory instance = new ViewGraficaFactory();

    public ViewGraficaFactory() {}

    @Override
    public IMenuView mostrarMenu(Gerenciador gerenciador) {
        return new MenuGraficoView();
    }

    @Override
    public <T extends Lista> IListaView mostrarLista(Gerenciador gerenciador, T lista) {
        return new ListaGraficaView(gerenciador, lista);
    }
}