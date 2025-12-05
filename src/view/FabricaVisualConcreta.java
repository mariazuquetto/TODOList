package view;

import view.grafica.ViewGraficaFactory;
import view.textual.ViewTextualFactory;

public class FabricaVisualConcreta {
    private static IFabricaVisual fabrica = new ViewGraficaFactory();

    public static void configurarInterface(String tipo) throws IllegalArgumentException {
        if (TipoInterface.GRAFICA.name().equalsIgnoreCase(tipo)) {
            fabrica = new ViewGraficaFactory();
        } else if (TipoInterface.TEXTUAL.name().equalsIgnoreCase(tipo)) {
            fabrica = new ViewTextualFactory();
        } else {
            throw new IllegalArgumentException("Tipo de interface inválida.");
        }
    }

    public static IFabricaVisual getFabrica() {
        return fabrica;
        }
    }

enum TipoInterface {GRAFICA, TEXTUAL}
