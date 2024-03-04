package vista;

import controlador.Controlador;
import modelo.IFicha;
import modelo.IJugador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuFicha extends JDialog implements IVista {
    private JButton btn1, btn2, btn3;

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
        btn1 = new JButton("Izquieda");
        btn1.setBounds(140, 60, 100, 20);

        panel.add(btn1);

        btn2 = new JButton("Derecha");
        btn2.setBounds(250, 60, 100, 20);
        panel.add(btn2);

        btn3 = new JButton("Elegir otra ficha");
        btn3.setBounds(170, 120, 138, 20);
        panel.add(btn3);

        this.getContentPane().add(panel);
    }

    public void agregarListeners(VistaFicha f) {
        btn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                f.setVisible(true);
                dispose();
                VistaGrafica.decrementarClicks();
            }
        });
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
