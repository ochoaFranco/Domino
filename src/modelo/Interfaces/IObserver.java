package modelo.Interfaces;

import modelo.Evento;

public interface IObserver {
    public void update(Evento e);
    public void update(Evento e, IJugador jugador);
}
