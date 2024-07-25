package controlador;

import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import modelo.*;
import modelo.IJugador;
import modelo.IObserver;
import modelo.exceptions.FichaIncorrecta;
import modelo.exceptions.FichaInexistente;
import vista.IVista;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class Controlador implements IControladorRemoto {
    private IVista vista;
    private IJuego modelo;
    private int jugador;

    public Controlador() {
    }

    @Override
    public <T extends IObservableRemoto> void setModeloRemoto(T t) throws RemoteException {
        this.modelo = (IJuego) t;
    }

    public void setVista(IVista vista) {
        this.vista = vista;
    }

    public void conectarJugador(String nombre) {
        try {
            jugador = this.modelo.conectarJugador(nombre);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void desconectarJugador() {
        try {
            modelo.desconectarJugador(jugador);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void iniciarJuego() {
        try {
            modelo.iniciarJuego();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void gestionarTurnos(int extremIzq, int extremDer, String extremo) throws FichaInexistente, FichaIncorrecta {
        try {
            modelo.realizarJugada(extremIzq, extremDer, extremo);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void robarFicha() {
        try {
            modelo.robarFichaPozo();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public List<IFicha> getFichasJugador(IJugador jugador) {
        return jugador.getFichas();
    }

    @Override
    public void actualizar(IObservableRemoto iObservableRemoto, Object cambios) throws RemoteException {
        if (cambios instanceof EventoFichaJugador) {
            actualizarEventoFichaJugador((EventoFichaJugador) cambios);
        } else if (cambios instanceof EventoJugador) {
            actualizarEventoJugador((EventoJugador) cambios);
        } else if (cambios instanceof EventoTurnoJugadores) {
            actualizarEventoTurnoJugadores((EventoTurnoJugadores) cambios);
        } else if (cambios instanceof EventoFichasTablero) {
            actualizarEventoFichasTablero((EventoFichasTablero) cambios);
        }
    }

    // maneja el caso en el que se actualice un evento ficha jugador.
    private void actualizarEventoFichaJugador(EventoFichaJugador cambios) throws RemoteException {
        switch (cambios.getEvento()) {
            case INICIAR_JUEGO:
                vista.mostrarFicha(cambios.getFicha());
                if (modelo.getTurno() == jugador) {
                    vista.mostrarBoton();
                    vista.mostrarMensaje("Es tu turno, elige una ficha para jugar: \n");
                } else {
                    int jugadorTurno = modelo.getTurno();
                    vista.mostrarMensaje("Turno del jugador: " + modelo.getJugadorID(jugadorTurno).getNombre() + "\n");
                    vista.ocultarBoton();
                }
                IJugador jug = modelo.getJugadorID(jugador);
                vista.mostrarFichasJugador(jug);
                break;
        }
    }

    // Actualiza el tablero.
    private void actualizarEventoFichasTablero(EventoFichasTablero cambios) throws RemoteException {
        if (cambios.getEvento() == Evento.ACTUALIZAR_TABLERO) {
            List<IFicha> fichasTablero = cambios.getFichasTablero();
            vista.mostrarTablero(fichasTablero);
            if (modelo.getTurno() == jugador) {
                vista.mostrarBoton();
                vista.mostrarMensaje("Es tu turno, elige la ficha a jugar: \n");
                vista.mostrarFichasJugador(modelo.getJugadorID(jugador));
            } else {
                IJugador jugTurno = modelo.getJugadorID(modelo.getTurno());
                vista.mostrarMensaje("Turno del jugador: " + jugTurno.getNombre() + "\n");
                vista.ocultarBoton();
            }
        }
    }

    // Actualiza el cambio de ronda.
    private void actualizarEventoTurnoJugadores(EventoTurnoJugadores cambios) {
        switch (cambios.getEvento()) {
            case CAMBIO_RONDA:
                IJugador ganadorRonda = cambios.getTurno();
                List<IJugador> jugadores = cambios.getJugadores();
                vista.mostrarMensaje("Jugador que domino la ronda: " + ganadorRonda.getNombre() + "\n");
                vista.mostrarTablaPuntos(jugadores);
                vista.limpiarTablero();
                vista.mostrarMensaje("Comenzara una nueva ronda..\n");
                break;
        }
    }

    // Actualiza el caso del fin del juego.
    private void actualizarEventoJugador(EventoJugador cambios) throws RemoteException {
        switch (cambios.getEvento()) {
            case CAMBIO_FICHAS_JUGADOR:
                if (cambios.getJugador().getId() == jugador) {
                    IJugador j = modelo.getJugadorID(jugador);
                    vista.mostrarMensaje("Fichas jugador: " + j.getNombre());
                    vista.mostrarFichasJugador(j);
                }
                break;
            case PASAR_TURNO:
                vista.mostrarMensaje("El pozo no tiene mas fichas.\n");
                if (jugador == cambios.getJugador().getId()) {
                    vista.mostrarBoton();
                    vista.mostrarMensaje("Es tu turno, elige una ficha: \n");
                    vista.mostrarFichasJugador(modelo.getJugadorID(jugador));
                } else {
                    vista.mostrarMensaje("Turno del jugador: " + cambios.getJugador().getNombre());
                    vista.ocultarBoton();
                }
                break;
            case FIN_DEL_JUEGO:
                IJugador jug = modelo.getJugadorID(jugador);
                IJugador ganador = cambios.getJugador();
                vista.limpiarTablero();
                if (ganador.getId() == jugador) {
                    vista.finalizarJuego("Has ganado el juego con " + ganador.getPuntos() + " puntos gracias por jugar al domino!");
//                    modelo.desconectarJugador(ganador.getId());
                } else {
                    vista.finalizarJuego("El jugador: " + ganador.getNombre() + " ha ganado el juego con " + ganador.getPuntos() + " puntos gracias por jugar al domino!");
//                    modelo.desconectarJugador(jugador);
                }
                break;
        }
    }
}
