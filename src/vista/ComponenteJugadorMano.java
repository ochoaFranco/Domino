package vista;

import javax.swing.*;
import java.awt.*;


public class ComponenteJugadorMano extends JPanel {

    public ComponenteJugadorMano() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 10));
        setSize(800,200);
        setOpaque(false);

    }

    public void agregarFichaJugador(VistaFicha ficha) {
        add(ficha);
    }
}




