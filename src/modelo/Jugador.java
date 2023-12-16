package modelo;

import java.util.ArrayList;

public class Jugador implements IJugador {
    private String nombre;
    private ArrayList<IFicha> fichas;
    private boolean mano = false;
    private int puntos = 0;
    private Tablero tablero;
    private IFicha fichaJugada;

    public Jugador(String nombre) {
        this.nombre = nombre;
        fichas = new ArrayList<>();
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
    }

    @Override
    public int contarPuntosFicha() {
        int puntosFicha = 0;
        for (IFicha f: fichas) {
            puntosFicha += f.getIzquierdo() + f.getDerecho();
        }
        return puntosFicha;
    }

    public void sumarPuntos(int puntos) {
        this.puntos += puntos;
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

    public void colocarFicha(IFicha ficha, String extremo) {
        extremo = extremo.toLowerCase();
        if (extremo.equals("i"))
            Tablero.setExtremoIzq(ficha, false, false);
        else if (extremo.equals("d"))
            Tablero.setExtremoDerec(ficha, false, false);
        else if (extremo.equals("iarr"))
            Tablero.setExtremoIzq(ficha, true, false);
        else if (extremo.equals("iab"))
            Tablero.setExtremoIzq(ficha, false, true);
        else if (extremo.equals("darr"))
            Tablero.setExtremoDerec(ficha, true, false);
        else if (extremo.equals("daba"))
            Tablero.setExtremoDerec(ficha, false, true);

        fichas.remove(ficha);
    }


    // determina si un jugador puede jugar una ficha.
    public boolean puedoJugar() {
        int extremoIzquierdo = Tablero.getExtremoIzq().getIzquierdo();
        int extremoDerecho = Tablero.getExtremoDerec().getDerecho();
        for (IFicha f: fichas) {
            if (f.getIzquierdo() == extremoIzquierdo || f.getDerecho() == extremoIzquierdo) {
                return true;
            }
            if (f.getIzquierdo() == extremoDerecho || f.getDerecho() == extremoDerecho) {
                return true;
            }
        }
        return false;
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

    public IFicha fichaSimpleMasAlta() {
        IFicha fichaComunMasAlta = new Ficha(-1, -1);
        for (IFicha f : fichas) {
            if (f.getIzquierdo() > fichaComunMasAlta.getIzquierdo() ||  f.getDerecho() > fichaComunMasAlta.getDerecho()) {
                fichaComunMasAlta = f;
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

    public void setMano(boolean mano) {
        this.mano = mano;
    }

    public boolean getMano() {
        return mano;
    }




}
