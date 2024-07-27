package modelo;

import modelo.exceptions.FichaIncorrecta;

import java.util.ArrayList;

public class Tablero {
    private static final int MAXIMO = 8;
    private static ArrayList<IFicha> fichas = new ArrayList<>();
    private static IFicha extremoIzq;
    private static IFicha extremoDerec;
    private static final int cantFichasMaxTab = 10;
    private final static int[] extremosJugados = new int[7];

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

    public static ArrayList<IFicha> getFichas() {
        return fichas;
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
        Tablero.fichas.addFirst(extremoIzq);
        Tablero.colocarVertical(extremoIzq); // chequeo si la ficha tiene que ser ubicada de manera vert.
    }

    // Incrementa la cantidad de veces que un extremo ha sido jugado.
    public static void incrementarExtremo(int extremo) {
        Tablero.extremosJugados[extremo] += 1;
    }
    
    // Detecta el cierre del juego, sucede cuando un extremo se juega el maximo cantidad de veces.
    public static boolean detectarCierre() {
        int tamanio = Tablero.extremosJugados.length;
        int i = 0;
        boolean cierre = false;
        while (i < tamanio && !cierre) {
            if (Tablero.extremosJugados[i] == MAXIMO)
                cierre = true;
            i++;
        }
        return cierre;
    }

    // si se lleno el tablero, se coloca vertical.
    private static void colocarVertical(IFicha ficha) {
        if (fichas.size() > Tablero.cantFichasMaxTab) {
            ficha.setVertical(true);
        }
    }
}
