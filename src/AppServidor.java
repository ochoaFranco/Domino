import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.sound.sampled.Port;
import javax.swing.JOptionPane;

import ar.edu.unlu.rmimvc.RMIMVCException;
import ar.edu.unlu.rmimvc.Util;
import ar.edu.unlu.rmimvc.servidor.Servidor;
import modelo.IJuego;
import modelo.Juego;

public class AppServidor {
    private static final int PORT = 8888;
    private static final String IP = "127.0.0.1";
    public static void main(String[] args) throws RemoteException {
        IJuego juego = Juego.getInstancia();
        Servidor servidor = new Servidor("127.0.0.1", AppServidor.PORT);
        try {
            servidor.iniciar((Juego)juego);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RMIMVCException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}