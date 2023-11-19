package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Juego implements IJuego, ISubject {
    private static List<Jugador> jugadores;
    private List<Ficha> fichas;
    private final int LIMITEPUNTOS = 100;
    private Jugador turno;
    private static Pozo pozo;
    private IFicha primeraFicha;
    private ArrayList<IObserver> observers;

    public Juego() {
        jugadores = new ArrayList<>();
        fichas = new ArrayList<>();
        inicializarFichas();
        Collections.shuffle(fichas); // mezcla las fichas.
        pozo = new Pozo(fichas);
        observers = new ArrayList<>();
    }

    @Override
    public Jugador conectarJugador(String nombre) {
        Jugador jugador = new Jugador(nombre);
        jugadores.add(jugador);
        return jugador;
    }

    /** Inicializa un conjunto de fichas para el juego
     * desde (0, 0) hasta (6, 6)*/
    public void inicializarFichas() {
        for (int i = 0; i <= 6; i++) {
            for (int j = i; j <= 6; j++) {
                Ficha ficha = new Ficha(i, j);
                fichas.add(ficha);
            }
        }
    }

    public void iniciarJuego() {
        repartir();
        determinarJugadorMano();
        notifyObserver(Evento.INICIAR_JUEGO, getPrimeraFicha());
    }

    private void repartir() {
        for (Jugador j : jugadores) {
            for (int i = 0; i < 7; i++) {
                Ficha ficha = pozo.sacarFicha();
                if (ficha != null) {
                    j.recibirFicha(ficha);
                    notifyObserver(Evento.CAMBIO_FICHAS_JUGADOR, j);
                }
            }

        }
    }

    private void determinarJugadorMano() {
        ArrayList<IJugador> jugadoresConFichasDobles = new ArrayList<>();
        int fichaSimpleAlta = -1;
        IJugador jugadorFichaSimpleMasAlta = null;
        IJugador jugadorMano = null;
        for (IJugador j: jugadores) {
            if (j.tengoDobles()) {
                jugadoresConFichasDobles.add(j);
            }
            // determino ficha simple más alta.
            if (j.fichaSimpleMasAlta().getIzquierdo() > fichaSimpleAlta ) {
                fichaSimpleAlta = j.fichaSimpleMasAlta().getIzquierdo();
                jugadorFichaSimpleMasAlta = j;
            } else if (j.fichaSimpleMasAlta().getDerecho() > fichaSimpleAlta) {
                fichaSimpleAlta = j.fichaSimpleMasAlta().getIzquierdo();
                jugadorFichaSimpleMasAlta = j;
            }
        }
        // seteo el jugador mano y la primera ficha a poner en el tablero.
        if (!jugadoresConFichasDobles.isEmpty()) {
            jugadorMano = jugadorfichaDobleMasAlta(jugadoresConFichasDobles);
            jugadorMano.setMano(true);
            primeraFicha = jugadorMano.fichaDobleMayor();
        } else {
            jugadorFichaSimpleMasAlta.setMano(true);
            primeraFicha = jugadorFichaSimpleMasAlta.fichaSimpleMasAlta();
        }
    }

    private IJugador jugadorfichaDobleMasAlta(ArrayList<IJugador> jugadores) {
        IJugador jFichaDobleMasAlta = null;
        int fichaValor = -1;
        for (IJugador j : jugadores) {
            if (j.fichaDobleMayor().getIzquierdo() > fichaValor) {
                fichaValor = j.fichaDobleMayor().getIzquierdo();
                jFichaDobleMasAlta = j;
            }
        }
        return jFichaDobleMasAlta;
    }

    public static List<Jugador> getJugadores() {
        return jugadores;
    }

    public static Pozo getPozo() {
        return pozo;
    }

    public IFicha getPrimeraFicha() {
        return primeraFicha;
    }

    @Override
    public void attach(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detach(IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObserver(Evento e, Object o) {
        for (IObserver ob: observers) {
            ob.update(e, o);
        }
    }
}