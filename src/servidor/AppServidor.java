package servidor;

import java.rmi.RemoteException;
import java.util.ArrayList;

import ar.edu.unlu.rmimvc.RMIMVCException;
import ar.edu.unlu.rmimvc.servidor.Servidor;
import modelo.IJuego;
import modelo.Juego;
import ar.edu.unlu.rmimvc.Util;
import javax.swing.*;

public class AppServidor {
    private static final int PORT = 8888;
    private static final String IP = "127.0.0.1";
    public static void main(String[] args) throws RemoteException {
        ArrayList<String> ips = Util.getIpDisponibles();
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
        IJuego juego = Juego.getInstancia();
        Servidor servidor = new Servidor(AppServidor.IP, AppServidor.PORT);
        try {
            servidor.iniciar(juego);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RMIMVCException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
