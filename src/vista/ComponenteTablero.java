package vista;

import modelo.IFicha;

import javax.swing.*;
import java.awt.*;

public class ComponenteTablero extends JPanel {
    private int cantFichasTableroHorizontales = 0;
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

    public int getCantFichasTablero() {
        return cantFichasTableroHorizontales;
    }

    public void setCantFichasTablero(int cantFichasTableroHorizontales) {
        this.cantFichasTableroHorizontales = cantFichasTableroHorizontales;
    }

    public void agregarFicha(VistaFicha ficha) {
        IFicha f = ficha.getFicha();
        if (!f.isVertical()) {
            agregarFichasCentrales(ficha);
            cantFichasTableroHorizontales += 1;
        } else {
            if (f.isDerecho())
                agregarFichasVertDerechas(ficha, ultimoYAgregado);
            else {

            }
        }
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

    private void agregarFichasVertDerechas(VistaFicha ficha, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = cantFichasTableroHorizontales - 1;
        gbc.gridy = y;
        ultimoYAgregado = y + 1;
        segundoPanel.add(ficha, gbc);
        segundoPanel.revalidate();
        segundoPanel.repaint();
    }
    private void agregarFichasVerticales(VistaFicha ficha) {

    }

    public void limpiarFicha() {
        segundoPanel.removeAll();
    }
}




