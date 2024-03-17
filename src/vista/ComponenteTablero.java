package vista;

import javax.swing.*;
import java.awt.*;

public class ComponenteTablero extends JPanel {
    public ComponenteTablero() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));

        setSize(800, 300);
        setOpaque(false);
    }

    public void agregarFicha(VistaFicha ficha) {
        add(ficha);
    }
}
