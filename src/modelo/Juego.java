package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Juego implements IJuego, ISubject {
    private static List<Jugador> jugadores;
    private List<IFicha> fichas;
    private final int LIMITEPUNTOS = 100;
    private Jugador turno = null;
    private static Pozo pozo;
    private IFicha primeraFicha;
    private ArrayList<IObserver> observers;
    private Jugador jugadorMano = null;

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
        determinarJugadorTurno();
        notifyObserver(Evento.INICIAR_JUEGO, primeraFicha, jugadorMano);
    }

    private void repartir() {
        for (Jugador j : jugadores) {
            for (int i = 0; i < 7; i++) {
                IFicha ficha = pozo.sacarFicha();
                if (ficha != null) {
                    j.recibirFicha(ficha);
                    notifyObserver(Evento.CAMBIO_FICHAS_JUGADOR, j);
                }
            }
        }
    }

    private void determinarJugadorMano() {
        ArrayList<Jugador> jugadoresConFichasDobles = new ArrayList<>();
        int fichaSimpleAlta = -1;
        Jugador jugadorFichaSimpleMasAlta = null;
        Jugador jugMano = null;
        for (Jugador j: jugadores) {
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
            jugMano = jugadorfichaDobleMasAlta(jugadoresConFichasDobles);
            jugMano.setMano(true);
            jugadorMano = jugMano;
            primeraFicha = jugMano.fichaDobleMayor();
        } else {
            jugadorFichaSimpleMasAlta.setMano(true);
            primeraFicha = jugadorFichaSimpleMasAlta.fichaSimpleMasAlta();
            jugadorMano = jugadorFichaSimpleMasAlta;
        }
        ArrayList<IFicha> fichasJugador = jugadorMano.getFichas();
        fichasJugador.remove(primeraFicha);
    }

    private Jugador jugadorfichaDobleMasAlta(ArrayList<Jugador> jugadores) {
        Jugador jFichaDobleMasAlta = null;
        int fichaValor = -1;
        for (Jugador j : jugadores) {
            if (j.fichaDobleMayor().getIzquierdo() > fichaValor) {
                fichaValor = j.fichaDobleMayor().getIzquierdo();
                jFichaDobleMasAlta = j;
            }
        }
        return jFichaDobleMasAlta;
    }

    public void determinarJugadorTurno() {
        for (Jugador j : jugadores) {
            if (!j.esMano()) {
                turno = j;
            }
        }
    }

    public static List<Jugador> getJugadores() {
        return jugadores;
    }

    public static Pozo getPozo() {
        return pozo;
    }

    public Jugador getTurno() {
        return turno;
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
    public void notifyObserver(Evento e, Object o1) {
        for (IObserver ob: observers) {
            ob.update(e, o1);
        }
    }

    @Override
    public void notifyObserver(Evento e, Object o1, Object o2) {
        for (IObserver ob: observers) {
            ob.update(e, o1, o2);
        }
    }
}