package controlador;

import modelo.Juego;
import modelo.Jugador;
import vista.VAltaJugador;

public class CAltaJugador {
    private Juego mJuego;
    private VAltaJugador vista;

    public CAltaJugador(Juego mJuego, VAltaJugador vista) {
        this.mJuego = mJuego;
        this.vista = vista;
    }
/**
 * Registra un nuevo jugador, lo agrega al juego y muestra un mensaje de confirmación.
 */
    public void darDeAlta() {
        Jugador jugador = vista.altaJugador();
        mJuego.agregarJugador(jugador);
        vista.mostrarMensaje("Jugador " + jugador.getNombre() + " agregado correctamente.");
    }
}
