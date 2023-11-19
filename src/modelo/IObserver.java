package modelo;

public interface IObserver {
    public void update(Evento e);
    public void update(Evento e, Object o1);
    public void update(Evento e, Object o1, Object o2);
}
