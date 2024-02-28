package vista;

import controlador.Controlador;
import modelo.IFicha;
import modelo.IJugador;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Lobby implements IVista {

    private Controlador controlador;
    private final JFrame frame;
    private JLabel background = new JLabel();

    //resources/img/dominoes.jpg"
    public Lobby()  {
        frame = new JFrame("Domino");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 768);
        frame.setResizable(false);

        // establacer color del background.
        frame.getContentPane().setBackground(Color.PINK);
        JPanel panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                System.out.println(getClass().getResource("img/dominoes.jpg"));
                try {
                    ImageIcon background = new ImageIcon(getClass().getResource("img/dominoes.jpg"));// Assuming the image is in a "resources/img" folder
                    g.drawImage(background.getImage(), 0, 0, this);
                } catch (NullPointerException e ) {
                    throw new RuntimeException();
                }
            }

        };

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        JLabel texto = new JLabel("Bienvenidos al juego del Domino.");
        texto.setForeground(Color.WHITE);
        texto.setFont(new Font("Arial", Font.BOLD, 24));
        texto.setAlignmentX(Component.CENTER_ALIGNMENT); // alinear el texto al centro.

        JButton bJugar = new JButton("JUGAR");
        bJugar.setBackground(Color.white);
        bJugar.setForeground(Color.black);
        bJugar.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createRigidArea(new Dimension(0, 100)));
        panel.add(texto);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(bJugar);

        // Add the panel to the frame
        frame.getContentPane().add(panel, BorderLayout.CENTER);

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
        frame.setVisible(true);
    }

    @Override
    public void mostrarTablero(Object o) {

    }

    @Override
    public void mostrarTablaPuntos(Object o) {

    }
}
