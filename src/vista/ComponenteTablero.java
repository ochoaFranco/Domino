package vista;

import modelo.Ficha;
import modelo.IFicha;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ComponenteTablero extends JPanel {
    private final JPanel segundoPanel;
    private GridBagConstraints gbc;

    public ComponenteTablero() {
        setLayout(new BorderLayout());
        setSize(750, 300);
        setOpaque(false);
        segundoPanel = new JPanel();
        segundoPanel.setOpaque(false);
        segundoPanel.setLayout(new GridBagLayout());
        add(segundoPanel, BorderLayout.CENTER);
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(1, 1, 1, 1);
    }

    public void agregarFicha(VistaFicha ficha) {
        gbc.gridx = 3;
        gbc.gridy = 3;
        segundoPanel.add(ficha, gbc);
        segundoPanel.revalidate();
        segundoPanel.repaint();
    }

    public void setCantFichasTablero(int cantFichasTablero) {

    }
    public void limpiarFicha() {
        segundoPanel.removeAll();
    }


}




