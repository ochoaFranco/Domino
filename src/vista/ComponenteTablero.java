package vista;

import javax.swing.*;
import java.awt.*;

public class ComponenteTablero extends JPanel {
    private int cantFichasTablero = 0;
    private final JPanel segundoPanel;
    private int lastAddedX = 0; // Initialize with the starting x-position
    private int lastAddedY = 0; // Initialize with the starting y-position

    public ComponenteTablero() {
        setLayout(new BorderLayout());
        setSize(750, 300);
        setOpaque(false);
        segundoPanel = new JPanel();
        segundoPanel.setOpaque(false);
        segundoPanel.setLayout(new GridBagLayout());
        add(segundoPanel, BorderLayout.CENTER);
    }

    public int getCantFichasTablero() {
        return cantFichasTablero;
    }

    public void setCantFichasTablero(int cantFichasTablero) {
        this.cantFichasTablero = cantFichasTablero;
    }


    public void agregarFicha(VistaFicha ficha) {
        if (cantFichasTablero < 5) {
            agregarFichasCentrales(ficha);
        } else {
            lastAddedY--;
            agregarFichasCentrales(ficha, lastAddedX, lastAddedY);
        }
        cantFichasTablero += 1;
    }

    private void agregarFichasCentrales(VistaFicha ficha) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = cantFichasTablero;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        if (ficha.getFicha().esFichaDoble()) {
            System.out.printf("SARACATUNGAA IM INNNNN \n");
            gbc.insets = new Insets(0, -10, 0, -10);
        }
        segundoPanel.add(ficha, gbc);
        segundoPanel.revalidate();
        segundoPanel.repaint();
        lastAddedY++;
    }

    private void agregarFichasCentrales(VistaFicha ficha, int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        if (ficha.getFicha().esFichaDoble()) {
            System.out.printf("SARACATUNGAA IM INNNNN \n");
            gbc.insets = new Insets(0, -10, 0, -10);
        }
        segundoPanel.add(ficha, gbc);
        segundoPanel.revalidate();
        segundoPanel.repaint();

        // Update the position of the last added tile
        lastAddedX = x;
        lastAddedY = y + 1;
    }
    private void agregarFichasVerticales(VistaFicha ficha) {

    }

    public void limpiarFicha() {
        segundoPanel.removeAll();
    }
}




