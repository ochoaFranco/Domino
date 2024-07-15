package vista;

import javax.swing.*;
import java.awt.*;

public class ComponenteTablero extends JPanel {
    private int cantFichasTableroHorizontales = 0;
    private final int CANTFICHASHORIZONTALESMAX = 5;
    private final JPanel segundoPanel;
    private int ultimoXAgregado = 0; // Initialize with the starting x-position
    private int ultimoYAgregado = 0; // Initialize with the starting y-position
    private final int ROTAR = 5;
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
        return cantFichasTableroHorizontales;
    }

    public boolean isRotar() {
        return cantFichasTableroHorizontales >= CANTFICHASHORIZONTALESMAX;
    }

    public void setCantFichasTablero(int cantFichasTableroHorizontales) {
        this.cantFichasTableroHorizontales = cantFichasTableroHorizontales;
    }

    public void agregarFicha(VistaFicha ficha) {
        if (cantFichasTableroHorizontales < CANTFICHASHORIZONTALESMAX) {
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
        gbc.gridx = CANTFICHASHORIZONTALESMAX - 1;
        gbc.gridy = y;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        if (ficha.getFicha().esFichaDoble())
            gbc.insets = new Insets(0, -5, 0, -5);
        segundoPanel.add(ficha, gbc);
        segundoPanel.revalidate();
        segundoPanel.repaint();

        // Update the position of the last added tile
        ultimoXAgregado = CANTFICHASHORIZONTALESMAX;
        ultimoYAgregado = y + 1;
    }
    private void agregarFichasVerticales(VistaFicha ficha) {

    }

    public void limpiarFicha() {
        segundoPanel.removeAll();
    }
}




