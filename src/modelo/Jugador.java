package modelo;

import modelo.Interfaces.IJugador;
import modelo.Interfaces.IObserver;
import modelo.Interfaces.ISubject;

import java.util.ArrayList;

public class Jugador implements ISubject, IJugador {
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
    @Override
    public String getNombre() {
        return nombre;
    }

    public int getPuntos() {
        return puntos;
    }

    public void recibirFicha(Ficha ficha) {
        fichas.add(ficha);
        notifyObserver(Evento.CAMBIO_FICHAS_JUGADOR);
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
            o.update(e, this);
        }
    }
    public void colocarFicha(int nroFicha, String extremo) {
        if (extremo.equalsIgnoreCase("i")) {
            Tablero.setExtremoIzq(fichas.get(nroFicha).getIzquierdo());
        } else {
            Tablero.setExtremoIzq(fichas.get(nroFicha).getIzquierdo());
        }
        notifyObserver(Evento.JUGADOR_JUGO_FICHA);
    }

    private Ficha fichaDobleMayor() {
        Ficha dobleMayor = new Ficha(-1, -1);
        for (Ficha f : fichas) {
            if (f.esFichaDoble() && (f.getIzquierdo() > dobleMayor.getIzquierdo() && f.getDerecho() > dobleMayor.getDerecho())) {
                dobleMayor = f;
            }
        }
        return dobleMayor;
    }

    private Ficha fichaComunMasAlta() {
        Ficha comunMasAlta = new Ficha(-1, -1);
        for (Ficha f : fichas) {
            if (f.getIzquierdo() > comunMasAlta.getIzquierdo() || f.getDerecho() > comunMasAlta.getDerecho()) {
                comunMasAlta = f;
            }
        }
        return comunMasAlta;
    }

    public boolean tengoDobles() {
        boolean algunDoble = false;
        int i = 0;
        while (i < fichas.size() && !algunDoble) {
            if (fichas.get(i).esFichaDoble()) {
                algunDoble = true;
            }
            i++;
        }
        return algunDoble;
    }












}
