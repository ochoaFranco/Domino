package modelo;

import utils.Serializador;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdministradorPartidas implements Serializable {
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
            System.out.println("GAME POINTS: " + partida.getLIMITEPUNTOS() + "\n");
            List<IJugador> jugadores = partida.getJugadores();
            for (IJugador j: jugadores) {
                System.out.println(j + "\n");
            }
            System.out.println("Domino class + " + "\n" + partida);
        } else {
            System.out.println("COULDN'T LOAD GAME!!\n");
            partida = Domino.getInstancia();
        }
        return partida;
    }
}
