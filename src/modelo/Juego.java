package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;

public class Juego implements IJuego, ISubject {
    private static List<Jugador> jugadores;
    private List<IFicha> fichas;
    private final int LIMITEPUNTOS = 50;
    private IJugador turno = null;
    private static Pozo pozo;
    private IFicha primeraFicha;
    private ArrayList<IObserver> observers;
    private Jugador jugadorMano = null;
    Queue<IJugador> colaTurnos = new LinkedList<>();

    public Juego() {
        jugadores = new ArrayList<>();
        fichas = new ArrayList<>();
        inicializarFichas();
        Collections.shuffle(fichas); // mezcla las fichas.
        pozo = new Pozo(fichas);
        observers = new ArrayList<>();
    }

    @Override
    public Jugador conectarJugador(String nombre) {
        Jugador jugador = new Jugador(nombre);
        jugadores.add(jugador);
        colaTurnos.offer(jugador);
        return jugador;
    }

    /** Inicializa un conjunto de fichas para el juego
     * desde (0, 0) hasta (6, 6)*/
    public void inicializarFichas() {
        for (int i = 0; i <= 6; i++) {
            for (int j = i; j <= 6; j++) {
                Ficha ficha = new Ficha(i, j);
                fichas.add(ficha);
            }
        }
    }

    public void iniciarJuego() {
        repartir();
        determinarJugadorMano();
        determinarJugadorTurno();
        notifyObserver(Evento.INICIAR_JUEGO, primeraFicha, jugadorMano);
    }

    private void repartir() {
        for (Jugador j : jugadores) {
            for (int i = 0; i < 7; i++) {
                IFicha ficha = pozo.sacarFicha();
                if (ficha != null) {
                    j.recibirFicha(ficha);
                }
            }
            notifyObserver(Evento.CAMBIO_FICHAS_JUGADOR, j);
        }
    }

    private void determinarJugadorMano() {
        ArrayList<Jugador> jugadoresConFichasDobles = new ArrayList<>();
        int fichaSimpleAlta = -1;
        Jugador jugadorFichaSimpleMasAlta = null;
        Jugador jugMano = null;
        for (Jugador j: jugadores) {
            if (j.tengoDobles()) {
                jugadoresConFichasDobles.add(j);
            }
            // determino ficha simple más alta.
            if (j.fichaSimpleMasAlta().getIzquierdo() > fichaSimpleAlta ) {
                fichaSimpleAlta = j.fichaSimpleMasAlta().getIzquierdo();
                jugadorFichaSimpleMasAlta = j;
            } else if (j.fichaSimpleMasAlta().getDerecho() > fichaSimpleAlta) {
                fichaSimpleAlta = j.fichaSimpleMasAlta().getIzquierdo();
                jugadorFichaSimpleMasAlta = j;
            }
        }
        // seteo el jugador mano y la primera ficha a poner en el tablero.
        if (!jugadoresConFichasDobles.isEmpty()) {
            jugMano = jugadorfichaDobleMasAlta(jugadoresConFichasDobles);
            jugMano.setMano(true);
            jugadorMano = jugMano;
            primeraFicha = jugMano.fichaDobleMayor();
        } else {
            jugadorFichaSimpleMasAlta.setMano(true);
            primeraFicha = jugadorFichaSimpleMasAlta.fichaSimpleMasAlta();
            jugadorMano = jugadorFichaSimpleMasAlta;
        }
        ArrayList<IFicha> fichasJugador = jugadorMano.getFichas();
        fichasJugador.remove(primeraFicha);
        // agrego al tablero las fichas.
        setearTablero(primeraFicha);
        moverJugFinalTurno();
    }

    private void setearTablero(IFicha ficha) {
        Tablero.setExtremoDerec(ficha);
        Tablero.setExtremoIzq(ficha);
    }

    // mueve el jugdor al final del turno en el caso de que ya haya tirado.
    private void moverJugFinalTurno() {
        if (colaTurnos.peek() == jugadorMano) {
            IJugador jugador = colaTurnos.poll();
            colaTurnos.offer(jugador);
        }
    }

    private Jugador jugadorfichaDobleMasAlta(ArrayList<Jugador> jugadores) {
        Jugador jFichaDobleMasAlta = null;
        int fichaValor = -1;
        for (Jugador j : jugadores) {
            if (j.fichaDobleMayor().getIzquierdo() > fichaValor) {
                fichaValor = j.fichaDobleMayor().getIzquierdo();
                jFichaDobleMasAlta = j;
            }
        }
        return jFichaDobleMasAlta;
    }

    public void determinarJugadorTurno() {
        turno = colaTurnos.peek();
    }


    // cuento los puntos de las fichas de todos lo jugadores.
    private void contarPuntosJugadores() {
        int puntosTotal = 0;
        for (IJugador j : colaTurnos) {
            puntosTotal += j.contarPuntosFicha();
        }
        turno.sumarPuntos(puntosTotal);
    }

    private void determinarSiJugadorGano() {
        if (turno.getPuntos() >= LIMITEPUNTOS) {
            notifyObserver(Evento.FIN_DEL_JUEGO, turno);
        } else {
            notifyObserver(Evento.CAMBIO_RONDA, turno, jugadores); // jugador que domino la ronda mas todos los jugadores.
            reiniciarRonda();
        }
    }

    private void reiniciarRonda() {
        juntarFichasTablero();
        juntarFichasJugadores();
        Collections.shuffle(pozo.getFichas());
        iniciarJuego();
    }


    private void juntarFichasTablero() {
        for (IFicha f : Tablero.getFichas()) {
            IFicha ficha = new Ficha(f.getIzquierdo(), f.getDerecho());
            pozo.agregarFicha(ficha);
        }
        Tablero.getFichas().clear(); // saco las fichas del tablero.
    }

    private void juntarFichasJugadores() {
        for (IJugador j : jugadores) {
            for (IFicha f : j.getFichas()) {
                IFicha ficha = new Ficha(f.getIzquierdo(), f.getDerecho());
                pozo.agregarFicha(ficha);
            }
            j.getFichas().clear(); // vacio la mano del jugador.
        }
    }

    // Logica principal del juego.
    public void realizarJugada(int extremIzq, int extremDerec, String extremo) {
        IJugador jugador = colaTurnos.poll(); // desencolo al jugador del primer turno.
        IFicha ficha = buscarFicha(extremIzq, extremDerec, jugador);
        jugador.colocarFicha(ficha, extremo);
        colaTurnos.offer(jugador); // lo vuelvo a encolar al final.
        ArrayList<IFicha> fichasTablero = Tablero.getFichas();
        // dermino si el jugador jugo todas sus fichas.
        if (jugadorJugoTodasSusFichas(turno)) {
            contarPuntosJugadores();
            determinarSiJugadorGano();
        } else {
            determinarJugadorTurno(); // paso el turno al siguiente jugador.
            notifyObserver(Evento.ACTUALIZAR_TABLERO, fichasTablero);
        }
    }

    // determina si el jugador no tiene mas fichas.
    private boolean jugadorJugoTodasSusFichas(IJugador jugador) {
        return jugador.getFichas().isEmpty();
    }

    // Busca la ficha a tirar dentro del poll de fichas del jugador.
    private IFicha buscarFicha(int extremIzq, int extemDer, IJugador jug) {
        ArrayList<IFicha> fichas = jug.getFichas();
        IFicha ficha = null;
        for (IFicha f : fichas) {
            if (f.getIzquierdo() == extremIzq && f.getDerecho() == extemDer) {
                ficha = f;
                break;
            }
        }
        return ficha;
    }

    // robo fichas del pozo y actualizo la mano.
    public void robarFichaPozo() {
        IJugador jugador = turno;
        IFicha ficha = pozo.sacarFicha();
        if (ficha == null) {
            pasarTurno();
        } else {
            jugador.recibirFicha(pozo.sacarFicha());
            notifyObserver(Evento.CAMBIO_FICHAS_JUGADOR, jugador);
        }
    }

    // paso el turno, desencolandolo del frente y encolandolo en el final.
    private void pasarTurno() {
        IJugador jugador = colaTurnos.poll();
        colaTurnos.offer(jugador);
        determinarJugadorTurno(); // actualizo el turno
        notifyObserver(Evento.PASAR_TURNO, turno);
    }

    public static List<Jugador> getJugadores() {
        return jugadores;
    }

    public static Pozo getPozo() {
        return pozo;
    }

    public IJugador getTurno() {
        return turno;
    }

    @Override
    public void attach(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detach(IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObserver(Evento e, Object o1) {
        for (IObserver ob: observers) {
            ob.update(e, o1);
        }
    }

    @Override
    public void notifyObserver(Evento e, Object o1, Object o2) {
        for (IObserver ob: observers) {
            ob.update(e, o1, o2);
        }
    }
}