package vista;

import controlador.Controlador;

public interface IVista {
    void mostrarMensaje(String mensaje);
    void mostrarFichas(Object jugador);
    void setControlador(Controlador controlador);
    void mostrar();
}
