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
            case FIN_DEL_JUEGO:
                IJugador jug = modelo.getJugadorID(jugador);
                IJugador ganador = cambios.getJugador();
                vista.limpiarTablero();
                if (ganador.getId() == jugador) {
                    vista.finalizarJuego("Has ganado el juego con " + ganador.getPuntos() + " puntos gracias por jugar al domino!");
                } else {
                    vista.finalizarJuego("El jugador: " + ganador + " ha ganado el juego con " + ganador.getPuntos() + " puntos gracias por jugar al domino!");
                }
                break;
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
                System.out.println("Ive printed all the tiles of player: " + jug.getNombre() + "\n");
                System.out.println("and its tiles are " + jug.getFichas() + "\n");
                break;
        }
    }

//
//    @Override
//    public void actualizar(IObservableRemoto iObservableRemoto, Object cambios) throws RemoteException {
//        switch (e) {
//            case CAMBIO_FICHAS_JUGADOR :
//                if (o == jugador) {
//                    vista.mostrarMensaje("Fichas jugador: " + ((IJugador)o).getNombre());
//                    vista.mostrarFichasJugador((IJugador) o);
//                }
//                break;
//            case JUGADOR_JUGO_FICHA:
//                vista.mostrarFicha((IFicha) o);
//                break;
//            case ACTUALIZAR_TABLERO:
//                vista.mostrarTablero(o);
//                if (modelo.getTurno() == jugador) {
//                    vista.mostrarBoton();
//                    vista.mostrarMensaje("Es tu turno, elige la ficha a jugar: \n");
//                    vista.mostrarFichasJugador(jugador);
//                } else {
//                    vista.mostrarMensaje("Turno del jugador: " + modelo.getTurno().getNombre() + "\n");
//                    vista.ocultarBoton();
//                }
//                break;
//            case PASAR_TURNO:
//                vista.mostrarMensaje("El pozo no tiene mas fichas.\n");
//                if (jugador == o) {
//                    vista.mostrarBoton();
//                    vista.mostrarMensaje("Es tu turno, elige una ficha: \n");
//                    vista.mostrarFichasJugador(jugador);
//                } else {
//                    vista.mostrarMensaje("Turno del jugador: " + ((IJugador)o).getNombre());
//                    vista.ocultarBoton();
//                }
//                break;
//            case FIN_DEL_JUEGO:
//                vista.limpiarTablero();
//                if (o == jugador) {
//                    vista.finalizarJuego("Has ganado el juego con " + jugador.getPuntos() + " puntos gracias por jugar al domino!");
//                } else {
//                    vista.finalizarJuego("El jugador: " + ((IJugador)o).getNombre() + " ha ganado el juego con " + ((IJugador)o).getPuntos() + " puntos gracias por jugar al domino!");
//                }
//                break;
//        }
//    }
}
