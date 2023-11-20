import controlador.Controlador;
import modelo.Juego;
import vista.IVista;
import vista.VistaConsola;

public class Domino {
    public static void main(String[] args) {
        Juego modelo = new Juego();
        IVista vista1 = new VistaConsola();
        IVista vista2 = new VistaConsola();
        Controlador controlador1 = new Controlador(vista1);
        Controlador controlador2 = new Controlador(vista2);
        controlador1.setModelo(modelo);
        controlador2.setModelo(modelo);
        vista1.setControlador(controlador1);
        vista2.setControlador(controlador2);
        vista1.mostrar();
        vista2.mostrar();
 }
}
