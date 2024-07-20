package vista;
import modelo.IFicha;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.lang.foreign.AddressLayout;

public class ComponenteTablero extends JPanel {
    private final int MAX_VERTICALES = 2;
    private final int MAX_VERTICALES_DER = 3;
    private int offset = 0;
    private int offsetDerecha = 0;
    private int xOffset = 100;
    private int yPosicion = 250;
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
        // Caracteristicas panel horizontal abajo.
        panelHorizontalAbajo();


    }
// TODO create a gridbag layout and start adding tiles and then decreasing the X coordinate for every tile added.
    private void panelHorizontalAbajo() {
        PHorizontalAbajo.setSize(100, 300);
        PHorizontalAbajo.setBounds(300, 250, 500, 100);
        PHorizontalAbajo.setBackground(Color.yellow);
        PHorizontalAbajo.setLayout(new GridBagLayout());
        PHorizontalAbajo.setOpaque(false);
        add(PHorizontalAbajo);
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
        PHorizontalArriba = new JPanel();
        PHorizontalArriba.setSize(100, 300);
        PHorizontalArriba.setBounds(147, 0, 600, 120);
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

    public void agregarFicha(VistaFicha ficha) {
        IFicha f = ficha.getFicha();
        if (!f.isVertical()) {
            agregarFichasCentrales(ficha);
        } else {
            if (f.isDerecho())
                if (offsetDerecha < MAX_VERTICALES_DER)
                    agregarFichasVertDerechas(ficha);
                else
                    agregarFichasHorizontalesAbajo(ficha);
            else {
                if (offset < 2)
                    agregarFichasVerticalesIzquierdas(ficha);
                else
                    agregarFichasHorizontalesArriba(ficha);
            }
        }
    }

    // Permite rotar correctamente las fichas del panel superior horizontal.
    public boolean rotarHorizontalesArriba() {
        return PVerticalIzq2.getComponentCount() > 0 && offset >=2;
    }

    // agrega las fichas horizontales arriba.
    private void agregarFichasHorizontalesArriba(VistaFicha ficha) {
        PHorizontalArriba.add(ficha);
        revalidate();
        repaint();
    }

    // Agrega las fichas horizontales abajo.
    private void agregarFichasHorizontalesAbajo(VistaFicha ficha) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = xOffset;
        gbc.gridy = yPosicion;
        gbc.anchor = GridBagConstraints.WEST;
        PHorizontalAbajo.add(ficha, gbc);
        PHorizontalAbajo.revalidate();
        PHorizontalAbajo.repaint();
        this.xOffset -= 1;
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
        offsetDerecha +=1;
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
        offset = 0; // reseteo offset.
        agregado = false;
        offsetDerecha = 0;
        PCentral.removeAll();
        PHorizontalArriba.removeAll();
        PVerticalIzq.removeAll();
        PVerticalDer.removeAll();
        PHorizontalAbajo.removeAll();
        revalidate();
        repaint();
    }
}




