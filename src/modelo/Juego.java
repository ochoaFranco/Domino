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

    public static List<Jugador> getJugadores() {
        return jugadores;
    }
    public static Pozo getPozo() {
        return pozo;
    }

    public void repartir() {
        for (Jugador j : jugadores) {
            for (int i = 0; i < 7; i++) {
                Ficha ficha = pozo.sacarFicha();
                if (ficha != null) {
                    j.recibirFicha(ficha);
                }
            }
        }
    }


}