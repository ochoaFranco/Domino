package modelo;

public class Jugador {
    private String nombre;
    private Ficha fichas;
    private boolean mano;
    private int puntos;
    private Tablero tablero;

    public Jugador(String nombre) {
        this.nombre = nombre;
    }
    public String getNombre() {
        return nombre;
    }
    public Ficha getFichas() {
        return fichas;
    }
    public int getPuntos() {
        return puntos;
    }
}
