package modelo;

import java.util.ArrayList;

public class Tablero {
    private ArrayList<IFicha> fichas;
    private static IFicha extremoIzq;
    private static IFicha extremoDerec;

    public static IFicha getExtremoDerec() {
        return extremoDerec;
    }

    public static IFicha getExtremoIzq() {
        return extremoIzq;
    }

    public static void setExtremoDerec(IFicha extremoDerec) {
        //Tablero.validarPosicion(extremoDerec);
        Tablero.extremoDerec = extremoDerec;
    }

    public static void setExtremoIzq(IFicha extremoIzq) {
        //Tablero.validarPosicion(extremoIzq);
        Tablero.extremoIzq = extremoIzq;
    }

    // falta ser implementada.
    private static boolean validarPosicion(int extremo) {
        return true;
    }
}
