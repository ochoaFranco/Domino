package vista;

import javax.swing.*;
import java.awt.*;


public class ComponenteJugadorMano extends JPanel {

    public ComponenteJugadorMano() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 30, 10));
        setSize(800,200);
        setOpaque(false);

    }

    public void agregarFichaJugador(VistaFicha ficha) {
        add(ficha);
    }

//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        try {
//            ImageIcon background = new ImageIcon(getClass().getResource("img/tablero.png"));
//            g.drawImage(background.getImage(), 0, 0, this);
//        } catch (NullPointerException e) {
//            throw new RuntimeException();
//        }
//    }
}




