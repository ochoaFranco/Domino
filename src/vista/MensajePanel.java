package vista;

import javax.swing.*;
import java.awt.*;

public class MensajePanel extends JPanel {
    private String mensaje;
    private Timer timer;

    public MensajePanel(String mensaje, int tiempoMensaje) {
        this.mensaje = mensaje;
        this.timer = new Timer(tiempoMensaje, e -> ocultarMensaje());
        this.timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString(mensaje, -20, 70);
    }
    private void ocultarMensaje() {
        setVisible(false);
    }
}
