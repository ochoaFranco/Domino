package modelo;

import java.util.ArrayList;
import java.util.List;

public class Jugador implements ISubject {
    private String nombre;
    private ArrayList<Ficha> fichas;
    private boolean mano;
    private int puntos;
    private Tablero tablero;
    private ArrayList<IObserver> observers;
    //private Ficha fichaRecibida;
    
    public Jugador(String nombre) {
        this.nombre = nombre;
        fichas = new ArrayList<>();
        observers = new ArrayList<>();
    }
    
    public String getNombre() {
        return nombre;
    }

    public int getPuntos() {
        return puntos;
    }

    public void recibirFicha(Ficha ficha) {
        fichas.add(ficha);
        notifyObserver(Evento.CAMBIO_CARTAS_JUGADOR);
    }

    public boolean esMano() {
        return mano;
    }

    public Ficha getUltimaFicha() {
        return fichas.get(fichas.size() - 1);
    }

    public ArrayList<Ficha> getFichas() {
        return fichas;
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
    public void notifyObserver(Evento e) {
        for (IObserver o: observers) {
            o.update(e);
        }
    }
}
