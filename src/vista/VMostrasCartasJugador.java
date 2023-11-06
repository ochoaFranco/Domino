package vista;

import interfaces.IObservador;
import modelo.Ficha;

public class VMostrasCartasJugador extends Vista {
    

    public void mostrarCarta(Ficha ficha) {
        mostrarMensaje("[" + ficha.getIzquierdo() + "|" + ficha.getDerecho()+"]");
        try {
            Thread.sleep(1000); // 1 seg.
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
