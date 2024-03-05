package vista;

import modelo.IFicha;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class VistaFicha extends JLabel {
    private IFicha ficha;
    private boolean elegida = false;
    private boolean isDoble;
    private boolean eventosMouseHabilitados;
    private static IFicha fichaEnMano = null;

    public VistaFicha(IFicha ficha, boolean cambiarTamanio, boolean eventosMouseHabilitados) {
        this.ficha = ficha;
        this.isDoble = ficha.esFichaDoble();
        this.eventosMouseHabilitados = eventosMouseHabilitados;
        if (eventosMouseHabilitados) {
            agregarListeners(cambiarTamanio);
        }
        cargarImagen();
    }

    public IFicha getFicha() {
        return ficha;
    }

    private void agregarListeners(boolean cambiarTamanio) {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (cambiarTamanio) {
                    setPreferredSize(new Dimension(60, 60)); // incrementa el tamanio
                    setLocation(getX() - 5, getY() - 5); // mueve la ficha.
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setPreferredSize(null); // resetea el tamanio cuando sale.
                setLocation(getX() + 5, getY() + 5); // resetea la posiion
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                VistaGrafica.incrementarClicks();
                // maneja cuando un jugador tiene la ficha en la mano.
                if (!(VistaGrafica.getCantClicks() > 1)) {
                    setVisible(false); // oculta la ficha.
                    VistaFicha.fichaEnMano = ficha;
                    MenuFicha menuFicha = new MenuFicha();
                    MenuFicha.setJugar(true);
                    menuFicha.agregarListeners(VistaFicha.this);
                    menuFicha.mostrar();
                }
                System.out.printf("Tile on your hand: " + VistaFicha.fichaEnMano.getIzquierdo() + "|" + VistaFicha.fichaEnMano.getDerecho());
            }
        });
    }

    private void cargarImagen() {
        String nombreArchivo = "";
        if (ficha.isDadaVuelta())
            nombreArchivo = "img/" + ficha.getDerecho() + "-" + ficha.getIzquierdo() + ".png";
        else
            nombreArchivo = "img/" + ficha.getIzquierdo() + "-" + ficha.getDerecho() + ".png";

        // cargar imagen.
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(nombreArchivo));
            setIcon(icon);
        } catch (NullPointerException n) {
            System.out.printf("The value is null");
        }

    }

}
























