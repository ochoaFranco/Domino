package vista;

import javax.swing.*;
import java.awt.*;

public class ComponenteTablero extends JPanel {
    private int cantFichasTableroHorizontales = 0;
    private static final int CANT_HORIZONTALES_MAX = 5;
    private boolean esTableroVertical = false;
    private final JPanel segundoPanel;
    private int ultimoXAgregado = 0; // Initialize with the starting x-position
    private int ultimoYAgregado = 0; // Initialize with the starting y-position

    public ComponenteTablero() {
        setLayout(new BorderLayout());
        setSize(750, 300);
        setOpaque(false);
        segundoPanel = new JPanel();
        segundoPanel.setOpaque(false);
        segundoPanel.setLayout(new GridBagLayout());
        add(segundoPanel, BorderLayout.CENTER);
    }
    public static int getCant_horizontales_max() {
        return getCant_horizontales_max();
    }
    public boolean esTableroVertical() {
        esTableroVertical = cantFichasTableroHorizontales >= CANT_HORIZONTALES_MAX;
        return esTableroVertical;
    }

    public int getCantFichasTablero() {
        return cantFichasTableroHorizontales;
    }

    public void setCantFichasTablero(int cantFichasTableroHorizontales) {
        this.cantFichasTableroHorizontales = cantFichasTableroHorizontales;
    }

    public void agregarFicha(VistaFicha ficha) {
        if (cantFichasTableroHorizontales < CANT_HORIZONTALES_MAX) {
            agregarFichasCentrales(ficha);
        } else {
            agregarFichasCentrales(ficha, ultimoXAgregado, ultimoYAgregado);
        }
        cantFichasTableroHorizontales += 1;
    }

    private void agregarFichasCentrales(VistaFicha ficha) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = cantFichasTableroHorizontales;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        if (ficha.getFicha().esFichaDoble())
            gbc.insets = new Insets(0, -10, 0, -10);
        segundoPanel.add(ficha, gbc);
        segundoPanel.revalidate();
        segundoPanel.repaint();
        ultimoYAgregado++;
    }

    private void agregarFichasCentrales(VistaFicha ficha, int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = CANT_HORIZONTALES_MAX - 1;
        gbc.gridy = y;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        if (ficha.getFicha().esFichaDoble())
            gbc.insets = new Insets(0, -5, 0, -5);
        segundoPanel.add(ficha, gbc);
        segundoPanel.revalidate();
        segundoPanel.repaint();

        // Update the position of the last added tile
        ultimoXAgregado = CANT_HORIZONTALES_MAX;
        ultimoYAgregado = y + 1;
    }
    private void agregarFichasVerticales(VistaFicha ficha) {

    }

    public void limpiarFicha() {
        segundoPanel.removeAll();
    }
}




