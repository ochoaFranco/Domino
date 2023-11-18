package controlador;

import modelo.*;
import modelo.Interfaces.IJugador;
import modelo.Interfaces.IObserver;
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


    public void repartirFichas() {
        modelo.repartir();
    }

    public void colocarFicha(int nroFicha, String extremo) {
        jugador.colocarFicha(nroFicha, extremo);
    }


    @Override
    public void update(Evento e) {

    }

    @Override
    public void update(Evento e, IJugador jugador) {
        if (e != null) {
            switch (e) {
                case CAMBIO_FICHAS_JUGADOR :
                    vista.mostrarFichas(jugador, true);
                case JUGADOR_JUGO_FICHA:

            }
        }
    }

}
