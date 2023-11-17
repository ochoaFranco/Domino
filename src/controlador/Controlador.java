package controlador;

import modelo.*;
import vista.IVista;

public class Controlador implements IObserver {
    private final IVista vista;
    private Juego modelo;
    private Jugador jugador;

    public Controlador(IVista vista) {
        this.vista = vista;
        vista.setControlador(this); // seteo el controlador de la vista, es decir, el controlador actual.
    }

    public void setModelo(Juego modelo) {
        this.modelo = modelo;
    }

    public void conectarJugador(String nombre) {
        jugador = modelo.conectarJugador(nombre);
        jugador.attach(this);
    }

    @Override
    public void update(Evento e) {

    }

    @Override
    public void update(Evento e, IJugador jugador) {
        if (e instanceof Evento) {
            switch (e) {
                case CAMBIO_FICHAS_JUGADOR :
                    vista.mostrarFichas(jugador, true);
            }
        }
    }

    public void repartirFichas() {
        modelo.repartir();
    }
}
