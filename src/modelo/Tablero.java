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
        if (Tablero.extremoIzq != null) { // seria nulo cuando no hay fichas en el tablero.
            int tableroDer = Tablero.extremoDerec.getDerecho();
            if (tableroDer == extremoDerec.getIzquierdo() || tableroDer == extremoDerec.getDerecho()) {
                if (extremoDerec.getIzquierdo() != tableroDer) {
                    extremoDerec.darVuelta(true); // marcamos la ficha para luego darla vuelta cuadno la printeamos.
                }
            }
        }
        Tablero.extremosIgualesDer(); // checkeo si hay extremos iguales antes de agregar la ficha.
        Tablero.extremoDerec = extremoDerec;
        Tablero.fichas.add(extremoDerec);
    }

    private static void extremosIgualesIzq() {
        if (Tablero.extremoIzq == Tablero.getExtremoDerec()) {
            Tablero.fichas.remove(Tablero.extremoIzq);
        }
    }
    private static void extremosIgualesDer() {
        if (Tablero.extremoIzq == Tablero.getExtremoDerec()) {
            Tablero.fichas.remove(Tablero.extremoDerec);
        }
    }

    // agrega una ficha en el extremo izquierdo.
    public static void setExtremoIzq(IFicha extremoIzq) {
        if (Tablero.extremoIzq != null) {
            int tableroIzq = Tablero.extremoIzq.getIzquierdo();
            if (tableroIzq == extremoIzq.getIzquierdo() || tableroIzq == extremoIzq.getDerecho()) {
                if (extremoIzq.getDerecho() != tableroIzq) {
                    extremoIzq.darVuelta(true); // marcamos la ficha para luego darla vuelta cuadno la printeamos.
                }
            }
        }
        Tablero.extremosIgualesIzq();
        Tablero.extremoIzq = extremoIzq;
        Tablero.fichas.add(0, extremoIzq);
    }

    // falta ser implementada.
    private static boolean validarPosicion(int extremo) {
        return true;
    }

    public static ArrayList<IFicha> getFichas() {
        return fichas;
    }
}
