package vista;

import controlador.Controlador;
import modelo.IFicha;
import modelo.IJugador;

public interface IVista {
    void mostrarMensaje(String mensaje);
    void mostrarFichasJugador(IJugador jugador);
    void mostrarFicha(IFicha ficha);
    void mostrar();
    void mostrarTablero(Object o);
    void mostrarTablaPuntos(Object o);
    void setControlador(Controlador controlador);
    void ocultarBoton();
    void mostrarBoton();
    void limpiarTablero();
}
