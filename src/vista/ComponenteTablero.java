package vista;

import modelo.Ficha;

import javax.swing.*;
import java.awt.*;

public class ComponenteTablero extends JPanel {
//    private final BoxLayout layout;

    private int cantFichasTablero = 0;
    private final JPanel segundoPanel;


    public ComponenteTablero() {
        setLayout(new BorderLayout());
        setSize(750, 300);
        setOpaque(false);
        segundoPanel = new JPanel();
        segundoPanel.setOpaque(false);
        segundoPanel.setLayout(new BoxLayout(segundoPanel, BoxLayout.X_AXIS));
        add(segundoPanel, BorderLayout.CENTER);
    }

    public void agregarFicha(VistaFicha ficha) {
        segundoPanel.add(ficha);
        cantFichasTablero += 1;
        segundoPanel.revalidate();
        segundoPanel.repaint();
    }


}

