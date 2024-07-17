package vista;

import modelo.IFicha;

import javax.swing.*;
import java.awt.*;


public class ComponenteTablero extends JPanel {
    private int xCentral = 300;
    private int yCentral = 50;
    private int derechaVertX = 50;
    private int derechaVertY = 50;
    private int izquierdaVertX = 0;
    private int izquierdaVertY = 100;
    private int widthFicha = 50;
    private int heightFicha = 100;
    private JPanel PCentral;
    private JPanel PVerticalIzq;
    private JPanel PVerticalDer;
    private JPanel PHorizontalArriba;
    private JPanel PHorizontalAbajo;



    public ComponenteTablero() {
        // caracteristicas del contenedor.
        setLayout(null);
        setSize(750,300);
        setOpaque(false);
        // Caracteristicas panel central.
        PCentral = new JPanel();
        PCentral.setSize(650, 100);
        PCentral.setBounds(100, 100, 650, 100);
        PCentral.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 15));
        PCentral.setOpaque(true);
        PCentral.setBackground(Color.black);

        PVerticalIzq = new JPanel();
        PVerticalDer = new JPanel();
        PHorizontalArriba = new JPanel();
        PHorizontalAbajo = new JPanel();

        add(PCentral);

    }

    public void agregarFicha(VistaFicha ficha) {
        IFicha f = ficha.getFicha();
        if (!f.isVertical()) {
            agregarFichasCentrales(ficha);
        } else {
            if (f.isDerecho())
                agregarFichasVertDerechas(ficha);
            else {
                agregarFichasVerticalesIzquierdas(ficha);
            }
        }
    }

    private void agregarFichasCentrales(VistaFicha ficha) {
        PCentral.add(ficha);
        revalidate();
        repaint();
    }

    private void agregarFichasVertDerechas(VistaFicha ficha) {
        derechaVertX = xCentral - widthFicha; // Align with the last central tile
        System.out.println("Right Vertical Tile Position: (" + derechaVertX + ", " + derechaVertY + ")");
        ficha.setBounds(derechaVertX, derechaVertY, widthFicha, heightFicha);
        derechaVertY += heightFicha; // Move the next tile downthe next tile down

    }

    private void agregarFichasVerticalesIzquierdas(VistaFicha ficha) {
        izquierdaVertX = xCentral; // Align with the last central tile
        izquierdaVertY -= heightFicha;
        ficha.setBounds(izquierdaVertX, izquierdaVertY, widthFicha, heightFicha);
    }

    public void limpiarFicha() {
        PCentral.removeAll();
        revalidate();
        repaint();
    }
}




