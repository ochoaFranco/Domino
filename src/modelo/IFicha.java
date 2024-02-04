package modelo;

public interface IFicha {
    int getDerecho();
    int getIzquierdo();
    boolean esFichaDoble();
    boolean isDadaVuelta();
    void darVuelta(boolean darVuelta);
    void setIzquierdo(int izquierdo);
    void setDerecho(int derecho);
    boolean isVertical();
    void setVertical(boolean vertical);
}
