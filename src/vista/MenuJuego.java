package vista;

import controlador.Controlador;
import modelo.IFicha;
import modelo.IJuego;
import modelo.IJugador;
import modelo.Juego;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuJuego extends JDialog implements IVista {
    private static final int MAXJUGSOLO = 2;
    private static int ventanasCerradas = 0;
    private static int jugadoresListos = 0;

    private static final int totalDeVentanasCerradasEsperadas = 2;
    private static JFrame parent;

    public MenuJuego(JFrame parent, IJuego juego) {
        super(parent, "Domino", true);
        MenuJuego.parent = parent; // Ventana anterior.
        setTitle("Domino");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(450, 200);
        setResizable(false);

        JPanel panel = Lobby.getjPanel("img/dominoes.jpg");
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

        // calculo la posicion de la pantalla.
        this.setLocationRelativeTo(null);

        this.getContentPane().add(panel);

        // Funcionalidad para un solo jugador.
        soloBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginUsuario(juego);
            }
        });
    }

    private void loginUsuario(IJuego juego) {
        Login usuario1 = new Login((JFrame) this.getParent(), juego);
        Login usuario2 = new Login((JFrame) this.getParent(), juego);

        usuario1.iniciar();
        usuario2.iniciar();
        dispose();
    }

    // cuenta la cantidad de ventanas cerradas para cerrar el resto.
    public static void incrementarVentanasCerradas() {
        MenuJuego.ventanasCerradas += 1;
        if (MenuJuego.ventanasCerradas == MenuJuego.totalDeVentanasCerradasEsperadas)
            MenuJuego.parent.dispose();

    }

    // comprueba si todos los jugadores estan listos.
    public  static Boolean jugadoresListos() {
        MenuJuego.jugadoresListos += 1;
        return  MenuJuego.jugadoresListos == MAXJUGSOLO;
    }

    @Override
    public void mostrarMensaje(String mensaje) {

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

    @Override
    public void limpiarTablero() {

    }

    @Override
    public void finalizarJuego(String mensaje) {

    }

    @Override
    public void mostrarFichasJugador(IJugador jugador) {

    }

    @Override
    public void mostrarFicha(IFicha ficha) {

    }

    @Override
    public void iniciar() {
        setVisible(true);
    }

    @Override
    public void mostrarTablero(Object o) {

    }

    @Override
    public void mostrarTablaPuntos(Object o) {

    }
}