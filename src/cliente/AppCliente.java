package cliente;

import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import ar.edu.unlu.rmimvc.RMIMVCException;
import ar.edu.unlu.rmimvc.Util;
import ar.edu.unlu.rmimvc.cliente.Cliente;
import controlador.Controlador;
import vista.IVista;
import vista.Lobby;

public class AppCliente {
    private static final String IP = "127.0.0.1";
    private static final int PORT = 8888;

    public static void main(String[] args) throws RemoteException {
//        ArrayList<String> ips = Util.getIpDisponibles();
//        String port = (String) JOptionPane.showInputDialog(
//                null,
//                "Seleccione el puerto en el que escuchará peticiones el cliente", "Puerto del cliente",
//                JOptionPane.QUESTION_MESSAGE,
//                null,
//                null,
//                9999
//        );
//        ArrayList<String> ips = Util.getIpDisponibles();
//        String ip = (String) JOptionPane.showInputDialog(
//                null,
//                "Seleccione la IP en la que escuchará peticiones el cliente", "IP del cliente",
//                JOptionPane.QUESTION_MESSAGE,
//                null,
//                ips.toArray(),
//                null
//        );
        String port = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione el puerto en el que escuchará peticiones el cliente", "Puerto del cliente",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                9999
        );
//        String ipServidor = (String) JOptionPane.showInputDialog(
//                null,
//                "Seleccione la IP en la corre el servidor", "IP del servidor",
//                JOptionPane.QUESTION_MESSAGE,
//                null,
//                null,
//                null
//        );
//        String portServidor = (String) JOptionPane.showInputDialog(
//                null,
//                "Seleccione el puerto en el que corre el servidor", "Puerto del servidor",
//                JOptionPane.QUESTION_MESSAGE,
//                null,
//                null,
//                8888
//        );
        Controlador controlador = new Controlador();
        IVista vista = new Lobby(controlador);
        Cliente c = new Cliente(AppCliente.IP, Integer.parseInt(port), AppCliente.IP, AppCliente.PORT);
        vista.iniciar();
        try {
            c.iniciar(controlador);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RMIMVCException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
