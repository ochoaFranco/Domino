package modelo;

public class Ficha implements IFicha{
    private  int izquierdo;
    private  int derecho;
    private boolean dadaVuelta = false;

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

    public boolean isDadaVuelta() {
        return dadaVuelta;
    }
    public void darVuelta(boolean darVuelta) {
        dadaVuelta = darVuelta;
    }
    public void setIzquierdo(int izquierdo) {
        this.izquierdo = izquierdo;
    }

    public void setDerecho(int derecho) {
        this.derecho = derecho;
    }
}
