package modelo;

import java.util.ArrayList;

public class Tablero {
    private static ArrayList<IFicha> fichas = new ArrayList<>();
    private static IFicha extremoIzq;
    private static IFicha extremoDerec;

    public static IFicha getExtremoDerec() {
        return extremoDerec;
    }

    public static IFicha getExtremoIzq() {
        return extremoIzq;
    }

    // agrega una ficha en el extremo derecho.
    public static void setExtremoDerec(IFicha extremoDerec) {
        Tablero.fichas.add(extremoIzq);
        Tablero.extremoDerec = extremoDerec;
    }

    // agrega una ficha en el extremo izquierdo.
    public static void setExtremoIzq(IFicha extremoIzq) {
        Tablero.fichas.add(0, extremoIzq);
        Tablero.extremoIzq = extremoIzq;
    }

    // falta ser implementada.
    private static boolean validarPosicion(int extremo) {
        return true;
    }
}
