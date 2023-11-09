import controlador.Controlador;
import modelo.Juego;
import vista.IVista;
import vista.VistaConsola;

public class Domino {
    public static void main(String[] args) {
        Juego modelo = new Juego();
        IVista vista = new VistaConsola();
        Controlador controlador = new Controlador(vista);
        controlador.setModelo(modelo);
        vista.mostrar();
 }
}
