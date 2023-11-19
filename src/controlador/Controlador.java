package controlador;

import modelo.*;
import modelo.IJugador;
import modelo.IObserver;
import vista.IVista;

import java.util.ArrayList;

public class Controlador implements IObserver {
    private final IVista vista;
    private Juego modelo;

    public Controlador(IVista vista) {
        this.vista = vista;
        vista.setControlador(this); // seteo el controlador de la vista, es decir, el controlador actual.
    }

    public void setModelo(Juego modelo) {
        this.modelo = modelo;
        modelo.attach(this);
    }

    public void conectarJugador(String nombre) {
        modelo.conectarJugador(nombre);
    }


    public void iniciarJuego() {
        modelo.iniciarJuego();
    }

    public void colocarFicha(int nroFicha, String extremo, IJugador jugador) {
        jugador.colocarFicha(nroFicha, extremo);
    }

    public ArrayList<IFicha> getFichasJugador(IJugador jugador) {
        return jugador.getFichas();
    }

    @Override
    public void update(Evento e) {

    }

    @Override
    public void update(Evento e, Object o1, Object o2) {
        if (e.equals(Evento.INICIAR_JUEGO)) {
            vista.mostrarMensaje("Actualizando fichas jugador...\n");
            vista.mostrarFichasJugador((IJugador) o2);
            vista.mostrarFicha((IFicha) o1);
        }
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
            }
        }
    }

}
