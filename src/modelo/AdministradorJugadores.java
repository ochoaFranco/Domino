package modelo;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class AdministradorJugadores {
    List<IJugador> jugadores = new ArrayList<>();

    public AdministradorJugadores(List<IJugador> jugadores) {
        this.jugadores = jugadores;
    }


    /**
     * @param nombre nombre del jugador a buscar.
     * @return true si el jugador existe false caso contrario.
     * @throws RemoteException error de red.
     */
    public boolean existeJugador(String nombre) throws RemoteException {
        boolean result = false;
        for (IJugador j: jugadores) {
            if (j.getNombre().equalsIgnoreCase(nombre)) {
                result = true;
                break;
            }
        }
        return result;
    }

    // Junta las fichas de los jugadores y las agrega al  pozo.
    public void juntarFichasJugadores(Pozo pozo) {
        for (IJugador j : jugadores) {
            for (IFicha f : j.getFichas()) {
                IFicha ficha;
                if (f.isDadaVuelta()) {
                    ficha = new Ficha(f.getDerecho(), f.getIzquierdo());
                } else {
                    ficha = new Ficha(f.getIzquierdo(), f.getDerecho());
                }
                ficha.darVuelta(false);
                pozo.agregarFicha(ficha);
            }
            j.getFichas().clear(); // vacio la mano del jugador.
        }
    }
}
