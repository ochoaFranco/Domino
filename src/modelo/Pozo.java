package modelo;

import java.util.List;

public class Pozo {
    private List<IFicha> fichas;
    
    public Pozo(List<IFicha> fichas) {
        this.fichas = fichas;
    }
    
    public IFicha sacarFicha() {
        if (fichas.isEmpty()) return null;
        return fichas.remove(0);
    }

}
