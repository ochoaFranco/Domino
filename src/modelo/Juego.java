package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Juego implements IJuego {
    private static List<Jugador> jugadores;
    private List<Ficha> fichas;
    private final int LIMITEPUNTOS = 100;
    private Jugador turno;
    private static Pozo pozo;

    public Juego() {
        jugadores = new ArrayList<>();
        fichas = new ArrayList<>();
        inicializarFichas();
        Collections.shuffle(fichas); // mezcla las fichas.
        pozo = new Pozo(fichas);
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
    }

    private void repartir() {
        for (Jugador j : jugadores) {
            for (int i = 0; i < 7; i++) {
                Ficha ficha = pozo.sacarFicha();
                if (ficha != null) {
                    j.recibirFicha(ficha);
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
            if (j.fichaSimpleMasAlta() > fichaSimpleAlta) {
                fichaSimpleAlta = j.fichaSimpleMasAlta();
                jugadorFichaSimpleMasAlta = j;
            }
        }
        if (!jugadoresConFichasDobles.isEmpty()) {
            jugadorMano = jugadorfichaDobleMasAlta(jugadoresConFichasDobles);
            jugadorMano.setMano(true);
        } else {
            jugadorFichaSimpleMasAlta.setMano(true);
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

}