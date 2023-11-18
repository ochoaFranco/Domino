package modelo;

import modelo.Ficha;

import java.util.ArrayList;

public interface IJugador {
    String getNombre();
    IFicha getUltimaFicha();
    IFicha getFichaJugada();
    ArrayList<IFicha> getFichas();
    boolean tengoDobles();
    public int fichaSimpleMasAlta();
    IFicha fichaDobleMayor();
    void setMano(boolean mano);
    boolean getMano();
}
