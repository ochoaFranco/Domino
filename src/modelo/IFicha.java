package modelo;

public interface IFicha {
    int getDerecho();
    int getIzquierdo();
    boolean esFichaDoble();
    boolean isDadaVuelta();
    void darVuelta(boolean darVuelta);
}
