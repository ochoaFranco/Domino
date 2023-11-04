package modelo;

import java.util.ArrayList;
import java.util.List;

import interfaces.IObservado;
import interfaces.IObservador;

public class Notificador implements IObservado {
    private List<IObservador> observadores;
    private String estado;

    public Notificador() {
        observadores = new ArrayList<>();
    }

    @Override
    public void agregarObservador(IObservador observador) {
        observadores.add(observador);
    }

    @Override
    public void eliminarObservador(IObservador observador) {
        observadores.remove(observador);
    }

    @Override
    public void notificar() {
        for (IObservador o : observadores) {
            o.actualizar();
        }
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
        notificar();
    }

    public String getEstado() {
        return estado;
    }
}
