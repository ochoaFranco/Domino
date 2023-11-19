package modelo;

import java.util.ArrayList;

public class Jugador implements IJugador {
    private String nombre;
    private ArrayList<IFicha> fichas;
    private boolean mano = false;
    private int puntos;
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

    public boolean esMano() {
        return mano;
    }

    public IFicha getUltimaFicha() {
        return fichas.get(fichas.size() - 1);
    }

    public ArrayList<IFicha> getFichas() {
        return fichas;
    }

    public void colocarFicha(int nroFicha, String extremo) {
//        if (extremo.equalsIgnoreCase("i")) {
//            Tablero.setExtremoIzq(fichas.get(nroFicha).getIzquierdo());
//        } else {
//            Tablero.setExtremoDerec(fichas.get(nroFicha).getDerecho());
//        }
//        fichaJugada = fichas.get(nroFicha);
//        fichas.remove(nroFicha);
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
