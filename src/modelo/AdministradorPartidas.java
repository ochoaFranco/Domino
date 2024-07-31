package modelo;
import utils.Serializador;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdministradorPartidas implements Serializable {
    @Serial
    private static final long serialVersionUID = -9171934268332987443L;
    private static List<Partida> partidas = new ArrayList<>();
    private static final Serializador serializadorPartida = new Serializador("partida.dat");

    public AdministradorPartidas() {
    }

    public static void agregarPartida(Partida partida) {
        partidas.add(partida);
        Partida p = partidas.getFirst();
        serializadorPartida.writeOneObject(p);
        serializadorPartida.addOneObject(p);
    }

    public List<Partida> getPartidas() {
        return partidas;
    }

    public static IDomino getUltimaPartida() throws RemoteException {
        Object[] partidas = serializadorPartida.readObjects();
        System.out.println(Arrays.toString(partidas));
        IDomino partida;
        if (partidas != null) {
            partida = ((Partida)partidas[0]).getDomino();
            System.out.println("LOADING SAVED GAME!!!\n");
            List<IFicha> fichas = partida.getTablero().getFichas();
            for (IFicha f: fichas) {
                System.out.println(f + "\n");
            }
            List<IJugador> jugadores = partida.getJugadores();
            for (IJugador j: jugadores) {
                System.out.println(j + "\n");
            }

        } else {
            System.out.println("COULDN'T LOAD GAME!!\n");
            partida = Domino.getInstancia();
        }
        return partida;
    }
}
