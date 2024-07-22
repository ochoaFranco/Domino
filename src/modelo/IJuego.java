package modelo;

import modelo.exceptions.FichaIncorrecta;
import modelo.exceptions.FichaInexistente;

import java.rmi.RemoteException;
import java.util.List;

public interface IJuego {
    IJugador getTurno() throws RemoteException;

    Jugador conectarJugador(String nombre) throws RemoteException;

    void inicializarFichas() throws RemoteException;

    void iniciarJuego() throws RemoteException;

    // Logica principal del juego.
    void realizarJugada(int extremIzq, int extremDerec, String extremo) throws FichaInexistente, FichaIncorrecta, RemoteException;

    void determinarJugadorTurno() throws RemoteException;

    // robo fichas del pozo y actualizo la mano.
    void robarFichaPozo() throws RemoteException;
}
