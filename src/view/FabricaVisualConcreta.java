package view;

import view.textual.ViewTextualFactory;

public class FabricaVisualConcreta {
    private static IFabricaVisual fabrica = new ViewTextualFactory();

    public static void configurarInterface(TipoInterface tipo) throws IllegalArgumentException {
        if (tipo == TipoInterface.GRAFICA) {
            fabrica = new ViewTextualFactory();
        } else if (tipo == TipoInterface.TEXTUAL) {
            /// completar
        } else {
            throw new IllegalArgumentException("Tipo de interface inválida.");
        }
    }

    public static IFabricaVisual getFabrica() {
        return fabrica;
        }
    }

enum TipoInterface {GRAFICA, TEXTUAL}
