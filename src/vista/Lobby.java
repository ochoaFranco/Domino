package vista;

import controlador.Controlador;
import modelo.IFicha;
import modelo.IJugador;
import modelo.Juego;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Lobby implements IVista {

    private Controlador controlador;
    private final JFrame frame;
    private Juego juego = new Juego();


    public Lobby()  {
        frame = new JFrame("Domino");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 768);
        frame.setResizable(false);

        JPanel panel = Lobby.getjPanel("img/dominoes.jpg");

        // set texto attributos.
        JLabel texto = new JLabel("Bienvenidos al juego del Domino.");
        texto.setForeground(Color.WHITE);
        texto.setFont(new Font("Arial", Font.BOLD, 24));
        texto.setAlignmentX(Component.CENTER_ALIGNMENT); // alinear el texto al centro.

        // set boton attributos.
        JButton jugarBtn = new JButton("JUGAR");
        jugarBtn.setBackground(Color.white);
        jugarBtn.setForeground(Color.black);
        jugarBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        // se agregan los componenetes al panel.
        agregarComponentes(panel, texto, jugarBtn);

        // Add the panel to the frame
        frame.getContentPane().add(panel, BorderLayout.CENTER);

        // calculo tamanio pantalla.

        frame.setLocationRelativeTo(null);

        // Funcionalidad del boton.

        jugarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ejecutarMenu();
            }
        });

    }

    // agrega componenetes al panel.
    private static void agregarComponentes(JPanel panel, JLabel texto, JButton jugarBtn) {
        // agregos los componentes al panel.
        panel.add(Box.createRigidArea(new Dimension(0, 100)));
        panel.add(texto);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(jugarBtn);
    }


    private void ejecutarMenu() {
        MenuJuego menu = new MenuJuego(frame, juego);
        menu.mostrar();
    }

    // configuro el BG para el panel.
    public static JPanel getjPanel(String path) {
        JPanel panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    ImageIcon background = new ImageIcon(getClass().getResource(path));
                    g.drawImage(background.getImage(), 0, 0, this);
                } catch (NullPointerException e ) {
                    throw new RuntimeException();
                }
            }

        };

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        return panel;
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
        frame.setVisible(true);
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
