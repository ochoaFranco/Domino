package cliente;

import java.rmi.RemoteException;
import javax.swing.*;

import ar.edu.unlu.rmimvc.RMIMVCException;
import ar.edu.unlu.rmimvc.cliente.Cliente;
import controlador.Controlador;
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
        Lobby vista = new Lobby(controlador);
        Cliente c = null;
        try {
            c = new Cliente(AppCliente.IP, Integer.parseInt(port), AppCliente.IP, AppCliente.PORT);
            vista.iniciar();
        } catch (NumberFormatException ex) {
            SwingUtilities.invokeLater(()->JOptionPane.showMessageDialog(null, "Ha ocurrido un error de red, revise la configuracion.!!!",
                    "Error Red", JOptionPane.ERROR_MESSAGE));
            System.exit(1);
            ex.printStackTrace();
        }
        try {
            c.iniciar(controlador);
        } catch (RemoteException e) {
            SwingUtilities.invokeLater(()->JOptionPane.showMessageDialog(null, "Ha ocurrido un error de red, revise la configuracion.!!!",
                    "Error Red", JOptionPane.ERROR_MESSAGE));
            e.printStackTrace();
            System.exit(1);

        } catch (RMIMVCException e) {
            SwingUtilities.invokeLater(()->JOptionPane.showMessageDialog(null, "Ha ocurrido un error !!!",
                    "Error", JOptionPane.ERROR_MESSAGE));
            e.printStackTrace();
        }
    }
}

