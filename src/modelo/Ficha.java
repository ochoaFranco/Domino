package modelo;

public class Ficha implements IFicha {
    private  int izquierdo;
    private  int derecho;
    private boolean dadaVuelta = false;
    private boolean izqPosVert = false;
    private boolean  derePosVert = false;


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

    @Override
    public void setDerePosVert(boolean derePosVert) {
        this.derePosVert = derePosVert;
    }

    @Override
    public void setIzqPosVert(boolean izqPosVert) {
        this.izqPosVert = izqPosVert;
    }

    @Override
    public boolean getIzqPosVert() {
        return izqPosVert;
    }

    @Override
    public boolean getDerPosVert() {
        return derePosVert;
    }


}
