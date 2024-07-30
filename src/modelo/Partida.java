package modelo;

import java.io.Serializable;

public class Partida implements Serializable {
    private Domino domino;
    private static final long serialVersionUID = 1L;
    private static int ID = 0;
    private int id;

    public Partida(Domino domino) {
        this.domino = domino;
        id = Partida.ID++;
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
