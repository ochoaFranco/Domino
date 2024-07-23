package modelo;

import java.util.List;

public class EventoFichasTablero {
    private Evento evento;
    private List<IFicha> fichasTablero;
    public EventoFichasTablero() {

    }
    public EventoFichasTablero(Evento evento, List<IFicha> fichasTablero) {
        this.evento = evento;
        this.fichasTablero = fichasTablero;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public List<IFicha> getFichasTablero() {
        return fichasTablero;
    }

    public void setFichasTablero(List<IFicha> fichasTablero) {
        this.fichasTablero = fichasTablero;
    }
}
