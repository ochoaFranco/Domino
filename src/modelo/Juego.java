package modelo;

import java.util.ArrayList;
import java.util.List;

public class Juego {
    private List<Jugador> jugadores;
    private List<Ficha> fichas;
    private int puntosAObtener;
    private Jugador turno;
    private Pozo pozo;

    public Juego() {
        jugadores = new ArrayList<>();
        fichas = new ArrayList<>();
    }

    public void agregarJugador(Jugador jugador) {
        jugadores.add(jugador);
    }
}