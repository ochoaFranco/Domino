package vista;

import modelo.Ficha;

import javax.swing.*;
import java.awt.*;

public class ComponenteTablero extends JPanel {
    private int cantFichasTablero = 0;
    private final JPanel segundoPanel;

    public ComponenteTablero() {
        setLayout(new BorderLayout());
        setSize(750, 300);
        setOpaque(false);
        segundoPanel = new JPanel();
        segundoPanel.setOpaque(false);
        segundoPanel.setLayout(new GridBagLayout());
        add(segundoPanel, BorderLayout.CENTER);
    }

    public void agregarFicha(VistaFicha ficha) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = cantFichasTablero;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        segundoPanel.add(ficha, gbc);
        cantFichasTablero += 1;
        segundoPanel.revalidate();
        segundoPanel.repaint();
    }

    public void limpiarFicha() {
        segundoPanel.removeAll();
    }


}

