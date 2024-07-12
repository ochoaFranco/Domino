package vista;

import javax.swing.*;
import java.awt.*;

public class ComponenteTablero extends JPanel {
    private int rows = 10;
    private int cols = 10;
    public ComponenteTablero() {
        setLayout(new GridLayout(rows, cols));
        setSize(750, 300);
        setOpaque(false); // muestra el bg
    }

    public void agregarFicha(VistaFicha ficha) {
        add(ficha.setSize(100, 100));
    }

    public void limpiarFicha() {
    }

    public void setCantFichasTablero(int cantFichasTablero) {

    }
}




