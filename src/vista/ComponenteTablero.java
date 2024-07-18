package vista;
import modelo.IFicha;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.lang.foreign.AddressLayout;

public class ComponenteTablero extends JPanel {
    private final int MAX_VERTICALES = 2;
    private int offset = 0;
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
        PVerticalIzq.setBounds(100, 0, 70, 120);
        PVerticalIzq.setBackground(Color.GRAY);
        PVerticalIzq.setLayout(new BoxLayout(PVerticalIzq, BoxLayout.Y_AXIS));
        PVerticalIzq.setOpaque(true);
        PVerticalIzq.setBackground(Color.BLACK);

        // Caracteristicas panel horizontal arriba.
        PHorizontalArriba = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                AffineTransform original = g2.getTransform();
                AffineTransform alreves = new AffineTransform();
                alreves.scale(-1, 1);
                alreves.translate(-getWidth(),0);
                g2.setTransform(alreves);
                super.paintComponent(g2);
                g2.setTransform(original);
            }
        };
        PHorizontalArriba.setSize(100, 300);
        PHorizontalArriba.setBounds(200, 0, 300, 120);
        PHorizontalArriba.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 15));
        PHorizontalArriba.setOpaque(true);
        PHorizontalArriba.setBackground(Color.BLUE);

        //PHorizontalAbajo = new JPanel();

        add(PCentral);
        add(PVerticalDer);
        add(PVerticalIzq);
        add(PHorizontalArriba);
    }

    public void resetOffset() {
        offset = 0;
    }

    public void agregarFicha(VistaFicha ficha) {
        IFicha f = ficha.getFicha();
        if (!f.isVertical()) {
            agregarFichasCentrales(ficha);
        } else {
            if (f.isDerecho())
                agregarFichasVertDerechas(ficha);
            else {
//                if (offset < 2)
//                    agregarFichasVerticalesIzquierdas(ficha);
//                else
                    agregarFichasHorizontalesArriba(ficha);
            }
        }
        System.out.printf("offset: " + offset + "\n");
    }

    // agrega las fichas horizontales arriba.
    private void agregarFichasHorizontalesArriba(VistaFicha ficha) {
        PHorizontalArriba.add(ficha);
        System.out.println("Adding to PHorizontalArriba\n");
        revalidate();
        repaint();
    }

    // agrega las fichas centrales.
    private void agregarFichasCentrales(VistaFicha ficha) {
        System.out.println("Adding to PCentral\n");
        PCentral.add(ficha);
        revalidate();
        repaint();
    }

    // agrega las fichas verticales derechas.
    private void agregarFichasVertDerechas(VistaFicha ficha) {
        System.out.println("Adding to PVerticalDer\n");
        PVerticalDer.add(ficha);
        revalidate();
        repaint();
    }
    // agrega las fichas verticales izquierdas.
    private void agregarFichasVerticalesIzquierdas(VistaFicha ficha) {
        System.out.println("Adding to PVerticalIzq\n");
        PVerticalIzq.add(ficha);
        offset += 1;
        revalidate();
        repaint();
    }

    public void limpiarFicha() {
        PCentral.removeAll();
        PHorizontalArriba.removeAll();
        PVerticalIzq.removeAll();
        PVerticalDer.removeAll();
        revalidate();
        repaint();
    }
}




