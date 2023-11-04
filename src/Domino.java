import controlador.CAltaJugador;
import modelo.Juego;
import vista.VAltaJugador;

public class Domino {
    public static void main(String[] args) {
        Juego mJuego = new Juego();
        VAltaJugador vista = new VAltaJugador();
        CAltaJugador controlador = new CAltaJugador(mJuego, vista);
        controlador.darDeAlta();
    }
}
