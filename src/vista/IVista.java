package vista;

import controlador.Controlador;
import modelo.IJugador;

public interface IVista {
    void mostrarMensaje(String mensaje);
    void mostrarFichasRecibidas(IJugador jugador);
    void setControlador(Controlador controlador);
    void mostrarFichasJugador(IJugador jugador);
    void mostrar();
}
