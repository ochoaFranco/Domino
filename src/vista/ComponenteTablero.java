package vista;

import modelo.Ficha;
import modelo.IFicha;

import javax.swing.*;
import java.awt.*;

public class ComponenteTablero extends JPanel {
    private BoxLayout layout;

    public ComponenteTablero() {
        setSize(750, 300);
        setOpaque(false);
        layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);


    }


    public void agregarFicha(VistaFicha ficha) {
        add(ficha);
    }


}

