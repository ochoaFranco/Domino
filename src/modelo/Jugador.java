package modelo;

import java.util.ArrayList;

public class Jugador implements ISubject, IJugador {
    private String nombre;
    private ArrayList<IFicha> fichas;
    private boolean mano = false;
    private int puntos;
    private Tablero tablero;
    private ArrayList<IObserver> observers;
    private IFicha fichaJugada;
    
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

    public void recibirFicha(IFicha ficha) {
        fichas.add(ficha);
        notifyObserver(Evento.CAMBIO_FICHAS_JUGADOR);
    }

    public boolean esMano() {
        return mano;
    }

    public IFicha getUltimaFicha() {
        return fichas.get(fichas.size() - 1);
    }

    public ArrayList<IFicha> getFichas() {
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
            Tablero.setExtremoDerec(fichas.get(nroFicha).getDerecho());
        }
        fichaJugada = fichas.get(nroFicha);
        fichas.remove(nroFicha);
        notifyObserver(Evento.JUGADOR_JUGO_FICHA);
    }

    public IFicha fichaDobleMayor() {
        IFicha dobleMayor = new Ficha(-1, -1);
        for (IFicha f : fichas) {
            if (f.esFichaDoble() && (f.getIzquierdo() > dobleMayor.getIzquierdo() && f.getDerecho() > dobleMayor.getDerecho())) {
                dobleMayor = f;
            }
        }
        return dobleMayor;
    }

    public int fichaSimpleMasAlta() {
        int fichaComunMasAlta = -1;
        for (IFicha f : fichas) {
            if (f.getIzquierdo() > fichaComunMasAlta ) {
                fichaComunMasAlta = f.getIzquierdo();
            if ( f.getDerecho() > fichaComunMasAlta) {
                fichaComunMasAlta = f.getDerecho();
            }
        }
    }
    return fichaComunMasAlta;
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


    public IFicha getFichaJugada() {
        return fichaJugada;
    }

    public void setMano(boolean mano) {
        this.mano = mano;
    }

    public boolean getMano() {
        return mano;
    }




}
