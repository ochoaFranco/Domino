package vista;
import modelo.IFicha;
import javax.swing.*;
import java.awt.*;

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
        PVerticalDer.setSize(100, 100);
        PVerticalDer.setBounds(650, 200, 100, 300);
        PVerticalDer.setBackground(Color.BLACK);
        PVerticalDer.setLayout(new BoxLayout(PVerticalDer, BoxLayout.Y_AXIS));
        PCentral.setOpaque(true);

        PVerticalIzq = new JPanel();
        PHorizontalArriba = new JPanel();
        PHorizontalAbajo = new JPanel();

        add(PCentral);
        add(PVerticalDer);

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

    }

    public void limpiarFicha() {
        PCentral.removeAll();
        PVerticalDer.removeAll();
        revalidate();
        repaint();
    }
}




