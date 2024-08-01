package modelo;

import modelo.Ficha;
import modelo.exceptions.FichaIncorrecta;

import java.util.ArrayList;
import java.util.List;

public interface IJugador {
    int getId();
    String getNombre();
    List<IFicha> getFichas();
    boolean tengoDobles();
    IFicha fichaSimpleMasAlta();
    IFicha fichaDobleMayor();
    void setMano(boolean mano);
    void colocarFicha(IFicha ficha, String extremo, Tablero tablero) throws FichaIncorrecta;
    void recibirFicha(IFicha ficha);
    int contarPuntosFicha();
    void sumarPuntos(int puntos);
    int getPuntos();
}
