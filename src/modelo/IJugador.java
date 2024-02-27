package modelo;

import modelo.Ficha;
import modelo.exceptions.FichaIncorrecta;

import java.util.ArrayList;

public interface IJugador {
    String getNombre();
    IFicha getUltimaFicha();
    ArrayList<IFicha> getFichas();
    boolean tengoDobles();
    public IFicha fichaSimpleMasAlta();
    IFicha fichaDobleMayor();
    void setMano(boolean mano);
    boolean getMano();
    void colocarFicha(IFicha ficha, String extremo) throws FichaIncorrecta;
    boolean puedoJugar();
    void recibirFicha(IFicha ficha);
    int contarPuntosFicha();
    void sumarPuntos(int puntos);
    int getPuntos();
}
