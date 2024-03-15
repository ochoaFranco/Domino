package vista;

import javax.swing.*;
import java.awt.*;


public class ComponenteJugadorMano extends JPanel {

    public ComponenteJugadorMano() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 30, 10));
        setSize(800,200);
        setOpaque(false);

    }

    public void agregarFichaJugador(VistaFicha ficha) {
        add(ficha);
    }
}




