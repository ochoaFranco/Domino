package vista;

import modelo.Ficha;
import modelo.IFicha;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ComponenteTablero extends JPanel {
    private final JPanel segundoPanel;

    public ComponenteTablero() {
        setLayout(new BorderLayout());
        setSize(750, 300);
        setOpaque(false);
        segundoPanel = new JPanel();
        segundoPanel.setOpaque(false);
        segundoPanel.setLayout(new GridLayout(1, 0));
        add(segundoPanel, BorderLayout.CENTER);
    }

    public void agregarFicha(VistaFicha ficha) {
        segundoPanel.add(ficha);
        segundoPanel.revalidate();
    }

    public void setCantFichasTablero(int cantFichasTablero) {

    }
    public void limpiarFicha() {
        segundoPanel.removeAll();
    }


}




