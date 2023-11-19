package modelo;

public interface ISubject {
    public void attach(IObserver observer);
    public void detach(IObserver observer);
    public void notifyObserver(Evento e, Object jugador);
}
