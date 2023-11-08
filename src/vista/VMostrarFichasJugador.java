//package vista;
//
//import interfaces.IObservador;
//import modelo.Ficha;
//import modelo.Jugador;
//import modelo.Notificador;
//
//public class VMostrarFichasJugador extends IVista implements IObservador {
//    private Jugador jugador;
//    private Notificador notificador;
//    public VMostrarFichasJugador(Jugador jugador) {
//        this.jugador = jugador;
//        notificador.agregarObservador(this);
//    }
//    @Override
//    public void actualizar() {}
//
//    @Override
//    public void actualizar(Object ficha) {
//        mostrarMensaje("[" + ((Ficha) ficha).getIzquierdo() + "|" + ((Ficha) ficha).getDerecho()+"]");
//        try {
//            Thread.sleep(1000); // 1 seg.
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//    }
//
//}
