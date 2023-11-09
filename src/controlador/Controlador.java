package controlador;

import modelo.IJuego;
import modelo.IObserver;
import modelo.Juego;
import modelo.Jugador;
import vista.IVista;

public class Controlador implements IObserver {
    private final IVista vista;
    private IJuego modelo;

    public Controlador(IVista vista) {
        this.vista = vista;
        vista.setControlador(this); // seteo el controlador de la vista, es decir, el controlador actual.
    }

    public void setModelo(IJuego modelo) {
        this.modelo = modelo;
    }

    public void conectarJugador(String nombre) {
        modelo.conectarJugador(nombre);
    }


    @Override
    public void update() {

    }
}
