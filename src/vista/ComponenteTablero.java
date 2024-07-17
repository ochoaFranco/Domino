package vista;

import modelo.IFicha;

import javax.swing.*;
import java.awt.*;


public class ComponenteTablero extends JPanel {
    private int cantFichasTableroHorizontales = 0;
    private int ultimoYAgregado = 0;
    private int YAnt = 200;

    public ComponenteTablero() {
        setLayout(new GridBagLayout());
        setSize(750, 300);
        setOpaque(false);
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
                agregarFichasVerticalesIzquierdas(ficha, YAnt);
            }
        }
    }

    private void agregarFichasCentrales(VistaFicha ficha) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = cantFichasTableroHorizontales;
        gbc.gridy = 0;
        add(ficha, gbc);
        revalidate();
        repaint();
        ultimoYAgregado++;
    }

    private void agregarFichasVertDerechas(VistaFicha ficha, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = cantFichasTableroHorizontales - 1;
        gbc.gridy = y;
        ultimoYAgregado = y + 1;
        add(ficha, gbc);
        revalidate();
        repaint();
    }

    private void agregarFichasVerticalesIzquierdas(VistaFicha ficha, int y) {
        ficha.setBounds(300, y, ficha.getPreferredSize().width, ficha.getPreferredSize().height);
        add(ficha, JLayeredPane.PALETTE_LAYER);
        YAnt = y + ficha.getHeight();
    }

    public void limpiarFicha() {
        removeAll();
    }
}




