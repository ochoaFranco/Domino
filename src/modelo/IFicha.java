package modelo;

public interface IFicha {
    int getDerecho();
    int getIzquierdo();
    boolean esFichaDoble();
    boolean isDadaVuelta();
    void darVuelta(boolean darVuelta);
    void setIzquierdo(int izquierdo);
    void setDerecho(int derecho);
    void setDerePosVertArr(boolean derePosVertArr);
    void setDerePosVertAba(boolean DerePosVerAba);
    void setIzqPosArr(boolean izqPosArr);
    void setIzqPosAba(boolean izqPosAba);
    boolean getDerePosVerArr();
    boolean getDerePosVerAba();
    boolean getIzqPosVerArr();
    boolean getIzqPosVerAba();
}
