package modelo;

import java.io.Serial;
import java.io.Serializable;

public class Partida implements Serializable {
    @Serial
    private static final long serialVersionUID = 3377792848344458649L;
    private Domino domino;
    private static int ID = 0;
    private int id;
    private final int[] jugadores = {-1, -1, -1, -1};

    public Partida(Domino domino) {
        this.domino = domino;
        id = Partida.ID++;
//        agregarJugador(idJugador);
    }

    /**
     * Permite agregar jugadores a la partida guardada.
     * @param id ID del jugador a agregar a la partida.
     */
    private void agregarJugador(int id) {
        int i = 0;
        boolean parar = false;
        while (i < jugadores.length && !parar) {
            if (jugadores[i] == -1) {
                jugadores[i] = id;
                parar = true;
            }
            i++;
        }
    }

    public Domino getDomino() {
        return domino;
    }

    public void setDomino(Domino domino) {
        this.domino = domino;
    }

    public static int getID() {
        return ID;
    }

    public static void setID(int ID) {
        Partida.ID = ID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
