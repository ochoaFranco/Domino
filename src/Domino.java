import controlador.CAltaJugador;
import controlador.CRepartirFichas;
import modelo.Juego;
import modelo.Jugador;
import vista.VAltaJugador;
import vista.VMostrarFichasJugador;


public class Domino {
    public static void main(String[] args) {
        Juego mJuego = new Juego();
        VAltaJugador vista = new VAltaJugador();
        CAltaJugador controlador = new CAltaJugador(mJuego, vista);
        controlador.darDeAlta();
        CRepartirFichas controladorRepFichas = new CRepartirFichas();
        controladorRepFichas.repartir();
    }
}
