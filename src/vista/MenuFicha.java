package vista;

import controlador.Controlador;
import modelo.IFicha;
import modelo.IJugador;

import javax.swing.*;
import java.awt.*;

public class MenuFicha extends JDialog implements IVista {


    public MenuFicha() {
        setTitle("Domino");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(450, 200);
        setResizable(false);
        JPanel panel = Lobby.getjPanel("img/tablero.png");
        panel.setLayout(null);
        JLabel label = new JLabel("Elige la posicion");
        label.setBounds(175, 20, 400, 20);
        label.setForeground(Color.white);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(label);
        // creo los botones
        JButton btn1 = new JButton("Izquieda");
        btn1.setBounds(140, 60, 100, 20);

        panel.add(btn1);

        JButton btn2 = new JButton("Derecha");
        btn2.setBounds(250, 60, 100, 20);
        panel.add(btn2);

        JButton btn3 = new JButton("Elegir otra ficha");
        btn3.setBounds(170, 120, 138, 20);
        panel.add(btn3);

        this.getContentPane().add(panel);
        mostrar();
    }

    @Override
    public void mostrarMensaje(String mensaje) {

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

    @Override
    public void setControlador(Controlador controlador) {

    }

    @Override
    public void ocultarBoton() {

    }

    @Override
    public void mostrarBoton() {

    }
}
