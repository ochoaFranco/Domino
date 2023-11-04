package modelo;

import java.util.ArrayList;
import java.util.List;

public class Jugador {
    private String nombre;
    private List<Ficha> fichas;
    private boolean mano;
    private int puntos;
    private Tablero tablero;

    public Jugador(String nombre) {
        this.nombre = nombre;
        fichas = new ArrayList<>();
    }
    public String getNombre() {
        return nombre;
    }

    public int getPuntos() {
        return puntos;
    }

    public void recibirFicha(Ficha ficha) {
        fichas.add(ficha);
    }

    public boolean esMano() {
        return mano;
    }
}
