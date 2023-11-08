package modelo;

public class Jugador {
    private String nombre;
    private Ficha[] fichas;
    private boolean mano;
    private int puntos;
    private Tablero tablero;
    //private Ficha fichaRecibida;
    
    public Jugador(String nombre) {
        this.nombre = nombre;
        fichas = new Ficha[28];
    }
    
    public String getNombre() {
        return nombre;
    }

    public int getPuntos() {
        return puntos;
    }

    public void recibirFicha(Ficha ficha) {
        fichas[fichas.length] = ficha;
    }

    public boolean esMano() {
        return mano;
    }
}
