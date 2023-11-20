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

    public void gestionarTurnos(int extremIzq, int extremDer, String extremo) {
        modelo.realizarJugada(extremIzq, extremDer, extremo);
    }

    public void robarFicha() {
        modelo.robarFichaPozo();
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
            vista.mostrarMensaje("Turno del jugador: " + modelo.getTurno().getNombre() + "\n elija la ficha a jugar: ");
            vista.mostrarFichasJugador(modelo.getTurno());
        }
    }

    @Override
    public void update(Evento e, Object o) {
        if (e != null) {
            switch (e) {
                case CAMBIO_FICHAS_JUGADOR :
                    vista.mostrarMensaje("Fichas jugador: " + ((IJugador)o).getNombre());
                    vista.mostrarFichasJugador((IJugador) o);
                    break;
                case JUGADOR_JUGO_FICHA:
                    vista.mostrarFicha((IFicha) o);
                    break;
                case ACTUALIZAR_TABLERO:
                    vista.mostrarTablero(o);
                    vista.mostrarMensaje("Turno del jugador: " + modelo.getTurno().getNombre() + "\n elija la ficha a jugar: ");
                    vista.mostrarFichasJugador(modelo.getTurno());
                    break;
                case PASAR_TURNO:
                    vista.mostrarMensaje("El pozo no tiene mas fichas.\n");
                    vista.mostrarMensaje("Turno del jugador: " + ((IJugador)o).getNombre());
                    vista.mostrarFichasJugador((IJugador) o);
                    break;
                case FIN_DEL_JUEGO:
                    vista.mostrarMensaje("El jugador: " + ((IJugador)o).getNombre() + " ha ganado el juego con " + ((IJugador)o).getPuntos() + " puntos gracias por jugar al domino!");
                    break;
            }
        }
    }

}
