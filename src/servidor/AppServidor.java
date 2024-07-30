package servidor;

import java.rmi.RemoteException;

import ar.edu.unlu.rmimvc.RMIMVCException;
import ar.edu.unlu.rmimvc.servidor.Servidor;
import modelo.AdministradorPartidas;
import modelo.IDomino;
import modelo.Domino;

import javax.swing.*;

public class AppServidor {
    private static final int PORT = 8888;
    private static final String IP = "127.0.0.1";
    public static void main(String[] args) throws RemoteException {
//        ArrayList<String> ips = Util.getIpDisponibles();
//        String ip = (String) JOptionPane.showInputDialog(
//                null,
//                "Seleccione la IP en la que escuchará peticiones el servidor", "IP del servidor",
//                JOptionPane.QUESTION_MESSAGE,
//                null,
//                ips.toArray(),
//                null
//        );
//        String port = (String) JOptionPane.showInputDialog(
//                null,
//                "Seleccione el puerto en el que escuchará peticiones el servidor", "Puerto del servidor",
//                JOptionPane.QUESTION_MESSAGE,
//                null,
//                null,
//                8888
//        );

        IDomino juego = AdministradorPartidas.getUltimaPartida();
        Servidor servidor = new Servidor(AppServidor.IP, AppServidor.PORT);

        try {
            servidor.iniciar(juego);
        } catch (RemoteException e) {
            SwingUtilities.invokeLater(()->JOptionPane.showMessageDialog(null, "Ha ocurrido un error de red !!!",
                    "Error Red", JOptionPane.ERROR_MESSAGE));
            new javax.swing.Timer(7000, evt -> System.exit(1)).start();
            e.printStackTrace();
        } catch (RMIMVCException e) {
            SwingUtilities.invokeLater(()->JOptionPane.showMessageDialog(null, "Ha ocurrido un error !!!",
                    "Error", JOptionPane.ERROR_MESSAGE));
            new javax.swing.Timer(7000, evt -> System.exit(1)).start();
            e.printStackTrace();
        }
    }
}
