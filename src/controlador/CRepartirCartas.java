package controlador;

import interfaces.IObservador;
import modelo.Ficha;
import modelo.Jugador;
import modelo.Notificador;
import vista.VMostrasCartasJugador;

public class CRepartirCartas implements IObservador {
    private VMostrasCartasJugador vista;
    private Jugador jugador;
    private Notificador notificador;

    public CRepartirCartas(VMostrasCartasJugador vista, Jugador jugador) {
        this.vista = vista;
        this.jugador = jugador;
        notificador.agregarObservador(this);
    }

    @Override
    public void actualizar() {
        Ficha nuevaFicha = jugador.getFichaRecibida();
        vista.mostrarCarta(nuevaFicha);
    }
}
