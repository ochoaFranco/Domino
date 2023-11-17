package modelo;

public class Ficha {
    private final int izquierdo;
    private final int derecho;

    public Ficha(int izquierdo, int derecho) {
        this.izquierdo = izquierdo;
        this.derecho = derecho;
    }

    public int getDerecho() {
        return derecho;
    }
    public int getIzquierdo() {
        return izquierdo;
    }

    public boolean esFichaDoble() {
        return izquierdo == derecho;
    }
}
