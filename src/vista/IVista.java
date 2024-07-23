package vista;

import controlador.Controlador;
import modelo.IFicha;
import modelo.IJugador;

public interface IVista {
    void mostrarMensaje(String mensaje);
    void mostrarFichasJugador(IJugador jugador);
    void mostrarFicha(IFicha ficha);
    void iniciar();
    void mostrarTablero(Object o);
    void mostrarTablaPuntos(Object o);
    void ocultarBoton();
    void mostrarBoton();
    void limpiarTablero();
    void finalizarJuego(String mensaje);
}
