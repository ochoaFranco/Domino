package vista;

import controlador.Controlador;
import modelo.Interfaces.IJugador;

public interface IVista {
    void mostrarMensaje(String mensaje);
    void mostrarFichas(IJugador jugador, boolean mostrarNombre);
    void setControlador(Controlador controlador);
    void mostrar();
}
