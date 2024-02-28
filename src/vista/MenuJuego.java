package vista;

import controlador.Controlador;
import modelo.IFicha;
import modelo.IJugador;

import javax.sound.midi.VoiceStatus;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuJuego extends JDialog implements IVista {
    private static int ventanasCerradas = 0;
    private static final int totalDeVentanasCerradasEsperadas = 2;
    private static JFrame parent;

    public MenuJuego(JFrame parent) {
        super(parent, "Domino", true);
        MenuJuego.parent = parent; // Ventana anterior.
        setTitle("Domino");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(450, 200);
        setResizable(false);

        JPanel panel = Lobby.getjPanel();
        panel.setLayout(null);
        // agrego un label
        JLabel label = new JLabel("Elija la modalidad con la que desea jugar.");
        label.setBounds(80, 20, 400, 20);
        label.setForeground(Color.white);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(label);

        // creo los botones
        JButton soloBtn = new JButton("Solo");
        soloBtn.setBounds(120, 60, 80, 20);

        panel.add(soloBtn);

        JButton grupoBtn = new JButton("Grupo");
        grupoBtn.setBounds(250, 60, 80, 20);
        panel.add(grupoBtn);

        this.getContentPane().add(panel);

        // Funcionalidad para un solo jugador.
        soloBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginUsuario();
            }
        });


    }

    private void loginUsuario() {
        Login usuario1 = new Login((JFrame) this.getParent());
        Login usuario2 = new Login((JFrame) this.getParent());

        usuario1.mostrar();
        usuario2.mostrar();
        dispose();
    }

    public static void incrementarVentanasCerradas() {
        MenuJuego.ventanasCerradas += 1;
        if (MenuJuego.ventanasCerradas == MenuJuego.totalDeVentanasCerradasEsperadas) {
            IVista vistaConsola1 = new VistaConsola();
            IVista vistaConsola2 = new VistaConsola();
            vistaConsola1.mostrar();
            vistaConsola2.mostrar();
            MenuJuego.parent.dispose();
        }
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
