package modelo;

import modelo.exceptions.FichaIncorrecta;

import java.util.ArrayList;

public class Tablero {
    private static ArrayList<IFicha> fichas = new ArrayList<>();
    private static IFicha extremoIzq;
    private static IFicha extremoDerec;
    private static int cantFichasMaxTab = 5;

    public static IFicha getExtremoDerec() {
        return extremoDerec;
    }

    public static IFicha getExtremoIzq() {
        return extremoIzq;
    }


    // agrega una ficha en el extremo derecho.
    public static void setExtremoDerec(IFicha extremoDerec) throws FichaIncorrecta {
        if (Tablero.extremoIzq != null) { // seria nulo cuando no hay fichas en el tablero.
            int tableroDer = Tablero.extremoDerec.getDerecho();
            if (tableroDer == extremoDerec.getIzquierdo() || tableroDer == extremoDerec.getDerecho()) {
                if (extremoDerec.getIzquierdo() != tableroDer) {
                    int bkup = extremoDerec.getDerecho();
                    extremoDerec.setDerecho(extremoDerec.getIzquierdo());
                    extremoDerec.setIzquierdo(bkup);
                    extremoDerec.darVuelta(true); // marcamos la ficha para luego darla vuelta cuando repartamos nuevamente.
                }
            } else {
                throw new FichaIncorrecta();
            }
        }
        Tablero.extremosIgualesDer(); // checkeo si hay extremos iguales antes de agregar la ficha.
        Tablero.extremoDerec = extremoDerec;
        extremoDerec.setDerecho(true);
        Tablero.fichas.add(extremoDerec);
        Tablero.colocarVertical(extremoDerec); // chequeo si la ficha tiene que ser ubicada de manera vert.
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

    public static void resetearTablero() {
        extremoIzq = null;
        extremoDerec = null;
    }

    // agrega una ficha en el extremo izquierdo.
    public static void setExtremoIzq(IFicha extremoIzq) throws FichaIncorrecta {
        if (Tablero.extremoIzq != null) {
            int tableroIzq = Tablero.extremoIzq.getIzquierdo();
            if (tableroIzq == extremoIzq.getIzquierdo() || tableroIzq == extremoIzq.getDerecho()) {
                if (extremoIzq.getDerecho() != tableroIzq) {
                    int bkup = extremoIzq.getIzquierdo();
                    extremoIzq.setIzquierdo(extremoIzq.getDerecho());
                    extremoIzq.setDerecho(bkup);
                    extremoIzq.darVuelta(true); // marcamos la ficha para luego darla vuelta cuadno la printeamos.
                }
            } else {
                throw new FichaIncorrecta();
            }
        }
        Tablero.extremosIgualesIzq();
        Tablero.extremoIzq = extremoIzq;
        extremoIzq.setIzquierdo(true);
        Tablero.fichas.add(0, extremoIzq);
        Tablero.colocarVertical(extremoIzq); // chequeo si la ficha tiene que ser ubicada de manera vert.
    }

    // si se lleno el tablero, se coloca vertical.
    private static void colocarVertical(IFicha ficha) {
        if (fichas.size() > Tablero.cantFichasMaxTab) {
            ficha.setVertical(true);
        }
    }

    // falta ser implementada.
    private static boolean validarPosicion(int extremo) {
        return true;
    }

    public static ArrayList<IFicha> getFichas() {
        return fichas;
    }
}
