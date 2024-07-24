package modelo;

import ar.edu.unlu.rmimvc.observer.ObservableRemoto;
import modelo.exceptions.FichaIncorrecta;
import modelo.exceptions.FichaInexistente;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;

public class Juego extends ObservableRemoto implements IJuego {
    private static List<Jugador> jugadores;
    private List<IFicha> fichas;
    private final int LIMITEPUNTOS = 15;
    private IJugador turno = null;
    private static Pozo pozo;
    private IFicha primeraFicha;
    private IJugador jugadorMano = null;
    private Queue<IJugador> colaTurnos = new LinkedList<>();
    private static IJuego instancia;

    public static IJuego getInstancia() throws RemoteException {
        if (instancia == null) {
            instancia = new Juego();
        }
        return instancia;
    }

    public Juego() throws RemoteException {
        jugadores = new ArrayList<>();
        fichas = new ArrayList<>();
        inicializarFichas();
        Collections.shuffle(fichas); // mezcla las fichas.
        pozo = new Pozo(fichas);
    }

    @Override
    public IJugador getTurno() throws RemoteException {
        return turno;
    }

    @Override
    public Jugador conectarJugador(String nombre) throws RemoteException {
        System.out.println("Saracatunga conectado");
        Jugador jugador = new Jugador(nombre);
        jugadores.add(jugador);
        colaTurnos.offer(jugador);
        return jugador;
    }

    /** Inicializa un conjunto de fichas para el juego
     * desde (0, 0) hasta (6, 6)*/
    @Override
    public void inicializarFichas() throws RemoteException {
        for (int i = 0; i <= 6; i++) {
            for (int j = i; j <= 6; j++) {
                Ficha ficha = new Ficha(i, j);
                fichas.add(ficha);
            }
        }
    }

    @Override
    public void iniciarJuego() throws RemoteException {
        repartir();
        determinarJugadorMano();
        determinarJugadorTurno();
        EventoFichaJugador eventoFichaJugador = new EventoFichaJugador(Evento.INICIAR_JUEGO, primeraFicha, jugadorMano);
        notificarObservadores(eventoFichaJugador);
    }

    // Logica principal del juego.
    @Override
    public void realizarJugada(int extremIzq, int extremDerec, String extremo) throws FichaInexistente, FichaIncorrecta, RemoteException {
        assert colaTurnos.peek() != null;
        IFicha ficha = buscarFicha(extremIzq, extremDerec, colaTurnos.peek());
        if (ficha == null) throw new FichaInexistente();
        IJugador jugador = colaTurnos.peek();
        jugador.colocarFicha(ficha, extremo);
        jugador = colaTurnos.poll(); // desencolo al jugador del primer turno.
        colaTurnos.offer(jugador); // lo vuelvo a encolar al final.
        ArrayList<IFicha> fichasTablero = Tablero.getFichas();
        // clase compuesta.
        EventoFichasTablero evFichasTablero = new EventoFichasTablero(Evento.ACTUALIZAR_TABLERO, fichasTablero);
        // dermino si el jugador jugo todas sus fichas.
        if (jugadorJugoTodasSusFichas(turno)) {
            contarPuntosJugadores();
            determinarSiJugadorGano();
        } else if (detectarCierre()) {
            casoCierre();
        } else {
            determinarJugadorTurno(); // paso el turno al siguiente jugador.
            notificarObservadores(evFichasTablero);
        }
    }

    @Override
    public void determinarJugadorTurno() throws RemoteException {
        turno = colaTurnos.peek();
    }

    // robo fichas del pozo y actualizo la mano.
    @Override
    public void robarFichaPozo() throws RemoteException {
        IJugador jugador = turno;
        IFicha ficha = pozo.sacarFicha();
        if (ficha == null) {
            pasarTurno();
        } else {
            jugador.recibirFicha(ficha);
//            notificarObservadores(Evento.CAMBIO_FICHAS_JUGADOR, jugador);
        }
    }

    private void repartir() {
        System.out.println("sracatunga IN");
        for (Jugador j : jugadores) {
            for (int i = 0; i < 7; i++) {
                IFicha ficha = pozo.sacarFicha();
                if (ficha != null) {
                    j.recibirFicha(ficha);
                }
            }
        }
    }

    private void determinarJugadorMano()  {
        List<IJugador> jugadoresConFichasDobles = new ArrayList<>();
        int fichaSimpleAlta = -1;
        IJugador jugadorFichaSimpleMasAlta = null;
        IJugador jugMano = null;
        for (IJugador j: jugadores) {
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
            try {
                jugadorFichaSimpleMasAlta.setMano(true);
                primeraFicha = jugadorFichaSimpleMasAlta.fichaSimpleMasAlta();
                jugadorMano = jugadorFichaSimpleMasAlta;
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        }
        System.out.println("fihcas");
        List<IFicha> fichasJugador = jugadorMano.getFichas();
        fichasJugador.remove(primeraFicha);
        // agrego al tablero las fichas.
        try {
            System.out.println(primeraFicha);
            setearTablero(primeraFicha);
        } catch (FichaIncorrecta f) {
            throw new RuntimeException("ficha incorrecta!!!");
        }
        moverJugFinalTurno();
    }

    private void setearTablero(IFicha ficha) throws FichaIncorrecta {
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

    private IJugador jugadorfichaDobleMasAlta(List<IJugador> jugadores) {
        IJugador jFichaDobleMasAlta = null;
        int fichaValor = -1;
        for (IJugador j : jugadores) {
            if (j.fichaDobleMayor().getIzquierdo() > fichaValor) {
                fichaValor = j.fichaDobleMayor().getIzquierdo();
                jFichaDobleMasAlta = j;
            }
        }
        return jFichaDobleMasAlta;
    }
    // cuento los puntos de las fichas de todos lo jugadores.
    private void contarPuntosJugadores() {
        int puntosTotal = 0;
        for (IJugador j : colaTurnos) {
            puntosTotal += j.contarPuntosFicha();
        }
        turno.sumarPuntos(puntosTotal);
    }

    private void detectarJugadorGanadorCierre() {
        IJugador ganador = null;
        int puntos = 10000;
        int temp = 0;
        for (IJugador j : jugadores) {
            for (IFicha f : j.getFichas()) {
                temp += f.getIzquierdo() + f.getDerecho();
            }
            if (temp < puntos) {
                puntos = temp;
                ganador = j;
            }
        }
        turno = ganador; // marcamos al ganador como el jugador del turno para dsp contar los puntos.
    }

    private void determinarSiJugadorGano() throws RemoteException {
        if (turno.getPuntos() >= LIMITEPUNTOS) {
//            notificarObservadores(Evento.FIN_DEL_JUEGO, turno);
        } else {
//            notificarObservadores(Evento.CAMBIO_RONDA, turno, jugadores); // jugador que domino la ronda mas todos los jugadores.
            reiniciarRonda();
        }
    }

    private void reiniciarRonda() throws RemoteException {
        juntarFichasTablero();
        juntarFichasJugadores();
        Collections.shuffle(pozo.getFichas());
        Tablero.resetearTablero(); // limpio las fichas del tablero.
        iniciarJuego();
    }

    private void juntarFichasTablero() {
        for (IFicha f : Tablero.getFichas()) {
            IFicha ficha;
            if (f.isDadaVuelta()) {
                ficha = new Ficha(f.getDerecho(), f.getIzquierdo());
            } else {
                ficha = new Ficha(f.getIzquierdo(), f.getDerecho());
            }
            ficha.darVuelta(false);
            pozo.agregarFicha(ficha);
        }
        Tablero.getFichas().clear(); // saco las fichas del tablero.
    }

    private void juntarFichasJugadores() {
        for (IJugador j : jugadores) {
            for (IFicha f : j.getFichas()) {
                IFicha ficha;
                if (f.isDadaVuelta()) {
                    ficha = new Ficha(f.getDerecho(), f.getIzquierdo());
                } else {
                    ficha = new Ficha(f.getIzquierdo(), f.getDerecho());
                }
                ficha.darVuelta(false);
                pozo.agregarFicha(ficha);
            }
            j.getFichas().clear(); // vacio la mano del jugador.
        }
    }

    private void casoCierre() throws RemoteException {
        detectarJugadorGanadorCierre();
        turno.getFichas().clear(); // limpio la mano del jugador ya que no jugo todas sus fichas pero gano.
        contarPuntosJugadores();
        determinarSiJugadorGano();
    }

    // determina si el jugador no tiene mas fichas.
    private boolean jugadorJugoTodasSusFichas(IJugador jugador) {
        return jugador.getFichas().isEmpty();
    }

    // Busca la ficha a tirar dentro del poll de fichas del jugador.
    private IFicha buscarFicha(int extremIzq, int extemDer, IJugador jug) {
        List<IFicha> fichas = jug.getFichas();
        IFicha ficha = null;
        for (IFicha f : fichas) {
            if (f.getIzquierdo() == extremIzq && f.getDerecho() == extemDer) {
                ficha = f;
                break;
            }
        }
        return ficha;
    }

    // detecta si ningun jugador puede jugar una ficha y no hay mas en el pozo.
    private boolean detectarCierre() {
        boolean cierre = false;
        int contador = 0;
        if (pozo.getFichas().isEmpty()) {
            for (IJugador j : jugadores) {
                if (!j.puedoJugar()) {
                    contador += 1;
                }
            }
        }
        return contador == jugadores.size();
    }

    // paso el turno, desencolandolo del frente y encolandolo en el final.
    private void pasarTurno() throws RemoteException {
        IJugador jugador = colaTurnos.poll();
        colaTurnos.offer(jugador);
        determinarJugadorTurno(); // actualizo el turno
//        notificarObservadores(Evento.PASAR_TURNO, turno);
    }
}