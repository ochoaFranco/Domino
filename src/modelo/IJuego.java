package modelo;

import modelo.exceptions.FichaIncorrecta;
import modelo.exceptions.FichaInexistente;
import ar.edu.unlu.rmimvc.observer.IObservadorRemoto;
import java.rmi.RemoteException;
import java.util.List;

import ar.edu.unlu.rmimvc.observer.IObservableRemoto;

public interface IJuego extends  IObservableRemoto {
    int getTurno() throws RemoteException;

    void desconectarJugador(int idJugador) throws RemoteException;

    int conectarJugador(String nombre) throws RemoteException;

    void cerrar(IObservadorRemoto controlador, int usuarioId) throws RemoteException;

    void inicializarFichas() throws RemoteException;

    void iniciarJuego(int puntos) throws RemoteException;

    void TotalJugadores(int cantidadJugadores) throws RemoteException;

    void iniciarJuego() throws RemoteException;

    void reniciarJuego() throws RemoteException;

    // Logica principal del juego.
    void realizarJugada(int extremIzq, int extremDerec, String extremo) throws FichaInexistente, FichaIncorrecta, RemoteException;

    void determinarJugadorTurno() throws RemoteException;

    // robo fichas del pozo y actualizo la mano.
    void robarFichaPozo() throws RemoteException;

    IJugador getJugadorID(int id) throws RemoteException;

    List<IJugador> getJugadores() throws RemoteException;

    int getCantidadJugadores() throws RemoteException;
}
