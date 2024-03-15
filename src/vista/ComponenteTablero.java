package vista;

import javax.swing.*;
import java.awt.*;

public class ComponenteTablero extends JPanel {
    public ComponenteTablero() {
//        setLayout(null);
        setLayout(new FlowLayout(FlowLayout.LEFT, 30, 10));
        setSize(800, 300);
        setOpaque(false);
    }

    public void agregarFicha(VistaFicha ficha) {
        add(ficha);
    }
}
