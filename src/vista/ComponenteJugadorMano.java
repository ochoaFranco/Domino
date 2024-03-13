package vista;

import javax.swing.*;
import java.awt.*;


public class ComponenteJugadorMano extends JPanel {

    public ComponenteJugadorMano() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 30, 100));
        setSize(800,800);
    }

    public void agregarFichaJugador(VistaFicha ficha) {
        add(ficha);
    }
}




