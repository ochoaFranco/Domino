package vista;

import controlador.Controlador;
import modelo.IFicha;
import modelo.IJugador;

import javax.swing.*;
import java.awt.*;

public class MenuJuego extends JFrame implements IVista {
    private Controlador controlador;




    public MenuJuego() {
        setTitle("Domino");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        // agrego un label
        JLabel label = new JLabel("Elija la modalidad con la que desea jugar.");
        label.setBounds(80, 20, 260, 20);
        panel.add(label);

        // creo los botones
        JButton soloBtn = new JButton("Solo");
        soloBtn.setBounds(100, 60, 80, 20);

        panel.add(soloBtn);

        JButton grupoBtn = new JButton("Grupo");
        grupoBtn.setBounds(200, 60, 80, 20);
        panel.add(grupoBtn);



        this.getContentPane().add(panel);
        this.mostrar();


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
