package modelo;

import java.util.ArrayList;
import java.util.List;


public class Jugador {
    private String nombre;
    private List<Ficha> fichas;
    private boolean mano;
    private int puntos;
    private Tablero tablero;
    private Notificador notificador;
    private Ficha fichaRecibida;
    
    public Jugador(String nombre) {
        this.nombre = nombre;
        fichas = new ArrayList<>();
        notificador = new Notificador();
    }
    public String getNombre() {
        return nombre;
    }

    public int getPuntos() {
        return puntos;
    }

    public void recibirFicha(Ficha ficha) {
        fichas.add(ficha);
        fichaRecibida = ficha;
        notificador.notificar();
    }

    public boolean esMano() {
        return mano;
    }

    public Notificador getNotificador() {
        return notificador;
    }

    public Ficha getFichaRecibida() {
        return fichaRecibida;
    }
}
