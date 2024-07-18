package vista;
import modelo.IFicha;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.lang.foreign.AddressLayout;

public class ComponenteTablero extends JPanel {
    private final int MAX_VERTICALES = 2;
    private int offset = 0;
    private boolean agregado = false;
    private JPanel PCentral = new JPanel();
    private JPanel PVerticalIzq = new JPanel();
    private JPanel PVerticalDer = new JPanel();
    private JPanel PHorizontalArriba;
    private JPanel PHorizontalAbajo = new JPanel();
    private JPanel  PVerticalIzq2 = new JPanel();

    public ComponenteTablero() {
        // caracteristicas del contenedor.
        setLayout(null);
        setSize(750,300);
        setOpaque(false);

        // Caracteristicas panel central.
        panelCentral();

        // Caracteristicas panel vertical derecho
        panelVerticalDer();

        // Caracteristicas panel vertical izquierdo
        panelVerticalIzq();

        // Caracteristicas panel vertical izquierdo
        panelVerticalIzq2();

        // Caracteristicas panel horizontal arriba.
        panelHorizontalArriba();


        //PHorizontalAbajo = new JPanel();
    }

    // // Se agrega el panel central.
    private void panelCentral() {
        PCentral.setSize(650, 100);
        PCentral.setBounds(100, 100, 650, 100);
        PCentral.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 15));
        PCentral.setOpaque(false);
        PCentral.setBackground(Color.pink);
        add(PCentral);
    }
    // Se agrega el panel vertical derecho.
    private void panelVerticalDer() {
        PVerticalDer.setSize(100, 300);
        PVerticalDer.setBounds(600, 166, 100, 150);
        PVerticalDer.setBackground(Color.BLACK);
        PVerticalDer.setLayout(new BoxLayout(PVerticalDer, BoxLayout.Y_AXIS));
        PVerticalDer.setOpaque(false);
        add(PVerticalDer);
    }
    // Se agrega el panel horizontal de arriba
    private void panelHorizontalArriba() {
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
        PHorizontalArriba.setOpaque(false);
        PHorizontalArriba.setBackground(Color.BLUE);
        add(PHorizontalArriba);
    }

    // Se agrega el panel vertical izq
    private void panelVerticalIzq() {
        PVerticalIzq = new JPanel();
        PVerticalIzq.setLayout(new BoxLayout(PVerticalIzq, BoxLayout.Y_AXIS));
        PVerticalIzq.setSize(50, 50);
        PVerticalIzq.setBounds(100, 65, 50, 50);
        PVerticalIzq.setOpaque(false);
        PVerticalIzq.setBackground(Color.BLACK);
        add(PVerticalIzq);
    }

    private void panelVerticalIzq2() {
        PVerticalIzq2 = new JPanel();
        PVerticalIzq2.setLayout(new BoxLayout(PVerticalIzq2, BoxLayout.Y_AXIS));
        PVerticalIzq2.setSize(50, 50);
        PVerticalIzq2.setBounds(100, 12, 50, 50);
        PVerticalIzq2.setOpaque(false);
        PVerticalIzq2.setBackground(Color.white);
        add(PVerticalIzq2);
    }

    public void resetOffset() {
        offset = 0;
    }

    public int getOffset() {
        return offset;
    }

    public void agregarFicha(VistaFicha ficha) {
        IFicha f = ficha.getFicha();
        if (!f.isVertical()) {
            agregarFichasCentrales(ficha);
        } else {
            if (f.isDerecho())
                agregarFichasVertDerechas(ficha);
            else {
                if (offset < 2)
                    agregarFichasVerticalesIzquierdas(ficha);
                else
                    agregarFichasHorizontalesArriba(ficha);
            }
        }
        System.out.printf("offset: " + offset + "\n");
    }

    // agrega las fichas horizontales arriba.
    private void agregarFichasHorizontalesArriba(VistaFicha ficha) {
        PHorizontalArriba.add(ficha);
        revalidate();
        repaint();
    }

    // agrega las fichas centrales.
    private void agregarFichasCentrales(VistaFicha ficha) {
        PCentral.add(ficha);
        revalidate();
        repaint();
    }

    // agrega las fichas verticales derechas.
    private void agregarFichasVertDerechas(VistaFicha ficha) {
        PVerticalDer.add(ficha);
        revalidate();
        repaint();
    }
    // agrega las fichas verticales izquierdas.
    private void agregarFichasVerticalesIzquierdas(VistaFicha ficha) {
        if (!agregado) {
            PVerticalIzq.add(ficha, 0);
            agregado = true;
        } else
            PVerticalIzq2.add(ficha, 0);
        revalidate();
        repaint();
        offset += 1;
    }

    public void limpiarFicha() {
        agregado = false;
        PCentral.removeAll();
        PHorizontalArriba.removeAll();
        PVerticalIzq.removeAll();
        PVerticalDer.removeAll();
        revalidate();
        repaint();
    }
}




