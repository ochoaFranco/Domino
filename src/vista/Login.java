package vista;

import controlador.Controlador;
import modelo.IFicha;
import modelo.IJugador;

import javax.swing.*;
import java.awt.*;

public class Login  extends JDialog implements IVista {

    private Controlador controlador;

    public Login(JFrame parent) {
        super(parent, "Login", true);
        // seteando atributos
        setTitle("Domino");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(450, 200);
        setResizable(false);

        // se crea un panel
        JPanel panel = Lobby.getjPanel();
        panel.setLayout(null);

        // atributos de los labels.
        JLabel lblNombre = new JLabel("Nombre");
        lblNombre.setBounds(80, 20, 100, 20);
        lblNombre.setForeground(Color.white);
        lblNombre.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(lblNombre);

        JLabel lblConsola = new JLabel("Interfaz");
        lblConsola.setBounds(180, 20, 100, 20);
        lblConsola.setForeground(Color.white);
        lblConsola.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(lblConsola);

        this.getContentPane().add(panel);
    }

    @Override
    public void mostrarMensaje(String mensaje) {

    }

    @Override
    public void mostrarFichasRecibidas(IJugador jugador) {

    }

    @Override
    public void setControlador(Controlador controlador) {

    }

    @Override
    public void mostrarFichasJugador(IJugador jugador) {

    }

    @Override
    public void mostrarFicha(IFicha ficha) {

    }

    @Override
    public void mostrar() {
        setVisible(true);
    }

    @Override
    public void mostrarTablero(Object o) {

    }

    @Override
    public void mostrarTablaPuntos(Object o) {

    }
}
