package vista;
import modelo.IFicha;
import javax.swing.*;
import java.awt.*;
import java.lang.foreign.AddressLayout;

public class ComponenteTablero extends JPanel {
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
        PCentral.setBackground(Color.pink);

        // Caracteristicas panel vertical derecho
        PVerticalDer = new JPanel();
        PVerticalDer.setSize(100, 300);
        PVerticalDer.setBounds(600, 166, 100, 150);
        PVerticalDer.setBackground(Color.BLACK);
        PVerticalDer.setLayout(new BoxLayout(PVerticalDer, BoxLayout.Y_AXIS));
        PVerticalDer.setOpaque(true);

        // Caracteristicas panel vertical izquierdo
        PVerticalIzq = new JPanel();

        PVerticalIzq.setSize(100, 300);
        PVerticalIzq.setBounds(100, 0, 100, 120);
        PVerticalIzq.setBackground(Color.GRAY);
        PVerticalIzq.setLayout(new BoxLayout(PVerticalIzq, BoxLayout.Y_AXIS));
        PVerticalIzq.setOpaque(true);
        PVerticalIzq.setBackground(Color.BLACK);

        PHorizontalArriba = new JPanel();
        PHorizontalAbajo = new JPanel();

        add(PCentral);
        add(PVerticalDer);
        add(PVerticalIzq);
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
        PVerticalDer.add(ficha);
        revalidate();
        repaint();
    }

    private void agregarFichasVerticalesIzquierdas(VistaFicha ficha) {
        PVerticalIzq.add(Box.createVerticalGlue()); // agrega componentes al final.
        PVerticalIzq.add(ficha);
        PVerticalIzq.add(Box.createVerticalStrut(4)); // agrego espacio entre fichas.
        revalidate();
        repaint();
    }

    public void limpiarFicha() {
        PCentral.removeAll();
        PVerticalDer.removeAll();
        PVerticalIzq.removeAll();
        revalidate();
        repaint();
    }
}




