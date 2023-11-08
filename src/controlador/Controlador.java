package controlador;

import modelo.IJuego;
import modelo.Juego;
import vista.IVista;

public class Controlador {
    private final IVista vista;
    private IJuego modelo;

    public Controlador(IVista vista) {
        this.vista = vista;
        vista.setControlador(this); // seteo el controlador de la vista, es decir, el controlador actual.
    }

    public void setModelo(IJuego modelo) {
        this.modelo = modelo;
    }

    public void conectarJugador() {
        vista.mostrarMensaje("Ingrese el nombre del jugador.");
//        modelo.conectarJugador(nombre);
//        vista.mostrarMensaje("Jugador " + nombre + " dado de alta correctamente.");
    }
}
