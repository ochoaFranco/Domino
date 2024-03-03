package controlador;

import modelo.*;
import modelo.IJugador;
import modelo.IObserver;
import modelo.exceptions.FichaIncorrecta;
import modelo.exceptions.FichaInexistente;
import vista.IVista;

import java.util.ArrayList;

public class Controlador implements IObserver {
    private final IVista vista;
    private Juego modelo;
    private IJugador jugador;

    public Controlador(IVista vista) {
        this.vista = vista;
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


    public void gestionarTurnos(int extremIzq, int extremDer, String extremo) throws FichaInexistente, FichaIncorrecta {
        modelo.realizarJugada(extremIzq, extremDer, extremo);
    }

    public void robarFicha() {
        modelo.robarFichaPozo();
    }

    public ArrayList<IFicha> getFichasJugador(IJugador jugador) {
        return jugador.getFichas();
    }

    @Override
    public void update(Evento e, Object o1, Object o2) {
        switch (e) {
            case INICIAR_JUEGO:
                vista.mostrarFicha((IFicha) o1);
                if (modelo.getTurno() == jugador) {
                    vista.mostrarBoton();
                    vista.mostrarMensaje("Es tu turno, elige una ficha para jugar: \n");
                } else {
                    vista.mostrarMensaje("Turno del jugador: " + modelo.getTurno().getNombre() + "\n");
                    vista.ocultarBoton();
                }

                break;
            case CAMBIO_RONDA:
                vista.mostrarMensaje("Jugador que domino la ronda: " + ((IJugador)o1).getNombre() + "\n");
                vista.mostrarTablaPuntos(o2);
                vista.mostrarMensaje("Comenzara una nueva ronda..\n");
                break;
        }
    }

    @Override
    public void update(Evento e, Object o) {
        switch (e) {
            case CAMBIO_FICHAS_JUGADOR :
                if (o == jugador) {
                    vista.mostrarMensaje("Fichas jugador: " + ((IJugador)o).getNombre());
                    vista.mostrarFichasJugador((IJugador) o);
                }
                break;
            case JUGADOR_JUGO_FICHA:
                vista.mostrarFicha((IFicha) o);
                break;
            case ACTUALIZAR_TABLERO:
                vista.mostrarTablero(o);
                if (modelo.getTurno() == jugador) {
                    vista.mostrarBoton();
                    vista.mostrarMensaje("Es tu turno, elige la ficha a jugar: \n");
                    vista.mostrarFichasJugador(jugador);
                } else {
                    vista.mostrarMensaje("Turno del jugador: " + modelo.getTurno().getNombre() + "\n");
                    vista.ocultarBoton();
                }
                break;
            case PASAR_TURNO:
                vista.mostrarMensaje("El pozo no tiene mas fichas.\n");
                if (jugador == o) {
                    vista.mostrarMensaje("Es tu turno, elige una ficha: \n");
                    vista.mostrarFichasJugador(jugador);
                } else {
                    vista.mostrarMensaje("Turno del jugador: " + ((IJugador)o).getNombre());
                    vista.ocultarBoton();
                }
                break;
            case FIN_DEL_JUEGO:
                if (o == jugador) {
                    vista.mostrarMensaje("Has ganado el juego con " + jugador.getPuntos() + " puntos gracias por jugar al domino!");
                } else {
                    vista.mostrarMensaje("El jugador: " + ((IJugador)o).getNombre() + " ha ganado el juego con " + ((IJugador)o).getPuntos() + " puntos gracias por jugar al domino!");
                }
                break;
        }
    }
}
