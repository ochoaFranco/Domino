package controlador;

import modelo.*;
import modelo.IJugador;
import modelo.IObserver;
import vista.IVista;

import java.util.ArrayList;

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
        modelo.attach(this);
    }

    public void conectarJugador(String nombre) {
        jugador = modelo.conectarJugador(nombre);
    }


    public void iniciarJuego() {
        modelo.iniciarJuego();

    }

    public void colocarFicha(int nroFicha, String extremo) {
        jugador.colocarFicha(nroFicha, extremo);
    }

    public ArrayList<IFicha> getFichasJugador() {
        return jugador.getFichas();
    }

    @Override
    public void update(Evento e) {

    }

    @Override
    public void update(Evento e, Object o) {
        if (e != null) {
            switch (e) {
                case CAMBIO_FICHAS_JUGADOR :
                    vista.mostrarFichasRecibidas((IJugador) o);
                    break;
                case JUGADOR_JUGO_FICHA:
                    vista.mostrarFichasJugador((IJugador) o); // ?
                    break;
                case INICIAR_JUEGO:
                    vista.mostrarFicha((IFicha) o);
            }
        }
    }

}
