package vista;

import controlador.Controlador;
import modelo.IFicha;
import modelo.IJugador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuJuego extends JDialog implements IVista {
    private Controlador controlador;

    public MenuJuego(JFrame parent) {
        super(parent, "Domino", true);
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
                login();
            }
        });


    }

    private void login() {
        Login login = new Login();
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
