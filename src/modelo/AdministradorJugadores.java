package modelo;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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

    // agrega el ganador al ranking si es que tiene los puntos para agregarse.
    public void agregarjugadorRanking(IJugador[] rankCincoMejores, int turno) {
        IJugador jugador = buscarJugadorPorID(turno);
        if (jugador == null)
            return;
        boolean agregado = false;
        int i = rankCincoMejores.length - 1;
        int tamanio = 0;
        int posicion = posicionLibre(rankCincoMejores);
        if (posicion != -1)
            rankCincoMejores[posicion] = jugador;
        else {
            while (i > tamanio && !agregado) {
                if (jugador.getPuntos() > rankCincoMejores[i].getPuntos()) {
                    rankCincoMejores[i] = jugador;
                    agregado = true;
                }
                i--;
            }
        }
        // ordena el array.
        Arrays.sort(rankCincoMejores, Comparator.nullsLast(Comparator.comparingInt(IJugador::getPuntos).reversed()));
    }

    // Busca una posicion libre en el array.
    private int posicionLibre(IJugador[] jugadores) {
        for (int i = 0; i < jugadores.length; i++) {
            if (jugadores[i] == null)
                return i;
        }
        return -1;
    }

    // dada una ID busca el jugador y lo retorna.
    public IJugador buscarJugadorPorID(int id) {
        for (IJugador j : jugadores) {
            if (j.getId() == id)
                return j;
        }
        return null;
    }
}
