import controlador.CAltaJugador;
import controlador.CRepartirFichas;
import modelo.Juego;




public class Domino {
    public static void main(String[] args) {
        Juego mJuego = new Juego();
        CAltaJugador controlador = new CAltaJugador(mJuego);
        CAltaJugador controladorJ2 = new CAltaJugador(mJuego);
        controlador.darDeAlta();
        controladorJ2.darDeAlta();
        CRepartirFichas controladorRepFichas = new CRepartirFichas();
        controladorRepFichas.repartir();
    }
}
