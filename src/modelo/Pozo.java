package modelo;

import java.util.List;

public class Pozo {
    private List<Ficha> fichas;
    
    public Pozo(List<Ficha> fichas) {
        this.fichas = fichas;
    }
    
    public Ficha sacarFicha() {
        if (fichas.isEmpty()) return null;
        return fichas.remove(0);
    }

}
