import controlador.Controlador;
import modelo.Juego;
import vista.IVista;
import vista.VistaConsola;

public class Domino {
    public static void main(String[] args) {
        Juego modelo = new Juego();
        IVista vista = new VistaConsola();
        IVista vista2 = new VistaConsola();
        Controlador controlador = new Controlador(vista);
        //Controlador controlador2 = new Controlador(vista2);
        controlador.setModelo(modelo);
        //controlador2.setModelo(modelo);
        vista.mostrar();
        //vista2.mostrar();
 }
}
