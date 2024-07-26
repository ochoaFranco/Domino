package modelo;

import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import ar.edu.unlu.rmimvc.observer.ObservableRemoto;
import modelo.exceptions.FichaIncorrecta;
import modelo.exceptions.FichaInexistente;
import ar.edu.unlu.rmimvc.observer.IObservadorRemoto;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;

public class Juego extends ObservableRemoto implements IJuego {
    private List<IJugador> jugadores;
    private List<IFicha> fichas;
    private int LIMITEPUNTOS = 2;
    private int turno;
    private static Pozo pozo;
    private IFicha primeraFicha;
    private IJugador jugadorMano = null;
    private Queue<Integer> colaTurnos = new LinkedList<>();
    private static IJuego instancia;

    public static IJuego getInstancia() throws RemoteException {
        if (instancia == null) {
            instancia = new Juego();
        }
        return instancia;
    }

    private Juego() throws RemoteException {
        jugadores = new ArrayList<>();
        fichas = new ArrayList<>();
        inicializarFichas();
        Collections.shuffle(fichas); // mezcla las fichas.
        pozo = new Pozo(fichas);
    }

    @Override
    public int getTurno() throws RemoteException {
        return turno;
    }

    @Override
    public void desconectarJugador(int idJugador) throws RemoteException {
        System.out.println("Current players: ");
        for (IJugador j : jugadores) {
            System.out.println(j.getNombre() + " " + j.getId());
        }
        System.out.println("ID sent: " + idJugador);
        IJugador jugador = getJugadorID(idJugador);
        System.out.println("JUgador: to be erased" + jugador.getId());
        jugadores.remove(jugador);
        colaTurnos.remove(idJugador);
        System.out.println("Current players: ");
        for (IJugador j : jugadores) {
            System.out.println(j.getNombre() + "\n");
        }
    }

    @Override
    public int conectarJugador(String nombre) throws RemoteException {
        IJugador jugador = new Jugador(nombre);
        jugadores.add(jugador);
        colaTurnos.offer(jugador.getId());
        return jugador.getId();
    }

    @Override
    public List<IJugador> getJugadores() throws RemoteException {
        return jugadores;
    }

    @Override
    public void cerrar(IObservadorRemoto controlador, int usuarioId) throws RemoteException {
        this.removerObservador(controlador);
        this.desconectarJugador(usuarioId);
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
    public void iniciarJuego(int puntos) throws RemoteException {
        LIMITEPUNTOS = puntos; // seteo los puntos del juego.
        System.out.println("Printing players from game\n");
        for (IJugador j : jugadores) {
            System.out.println(j.getNombre() + "\n");
        }
        repartir();
        determinarJugadorMano();
        determinarJugadorTurno();
        EventoFichaJugador eventoFichaJugador = new EventoFichaJugador(Evento.INICIAR_JUEGO, primeraFicha, jugadorMano);
        System.out.println("Jugador mano: " + jugadorMano + "\n" + "First Tile: " + primeraFicha + "\n");
        notificarObservadores(eventoFichaJugador);
    }

    // Reinicia el juego cuando todos los jugadores se desconectaron.
    @Override
    public void reniciarJuego() throws RemoteException {
        System.out.println("REBOOTING GAME\n");
        jugadores = new ArrayList<>();
        fichas = new ArrayList<>();
        inicializarFichas();
        Collections.shuffle(fichas); // mezcla las fichas.
        pozo = new Pozo(fichas);
        Tablero.resetearTablero(); // limpio las fichas del tablero.
        Tablero.getFichas().clear();
        System.out.println("TILES: " + fichas.size() + "\n");
        System.out.println("POZO: " + pozo.getFichas().size() + "\n");
    }

    @Override
    public IJugador getJugadorID(int id) throws RemoteException {
        return buscarJugadorPorID(id);
    }

    // Logica principal del juego.
    @Override
    public void realizarJugada(int extremIzq, int extremDerec, String extremo) throws FichaInexistente, FichaIncorrecta, RemoteException {
        assert colaTurnos.peek() != null;
        IFicha ficha = buscarFicha(extremIzq, extremDerec, getJugadorID(colaTurnos.peek()));
        if (ficha == null) throw new FichaInexistente();
        IJugador jugador = getJugadorID(colaTurnos.peek());
        jugador.colocarFicha(ficha, extremo);
        jugador = getJugadorID(colaTurnos.poll()); // desencolo al jugador del primer turno.
        colaTurnos.offer(jugador.getId()); // lo vuelvo a encolar al final.
        ArrayList<IFicha> fichasTablero = Tablero.getFichas();
        // clase compuesta.
        EventoFichasTablero evFichasTablero = new EventoFichasTablero(Evento.ACTUALIZAR_TABLERO, fichasTablero);
        // dermino si el jugador jugo todas sus fichas.
        if (jugadorJugoTodasSusFichas(buscarJugadorPorID(turno))) {
            contarPuntosJugadores();
            determinarSiJugadorGano();
        } else if (detectarCierre()) {
            // I might need to trigger an update to the controller here.
            casoCierre();
        } else {
            determinarJugadorTurno(); // paso el turno al siguiente jugador.
            notificarObservadores(evFichasTablero);
        }
    }

    // SARACATUNGA BUG!!!!
    @Override
    public void determinarJugadorTurno() throws RemoteException {
        turno = colaTurnos.peek();
        System.out.println("TURN: " + turno + "\n");
    }

    // dada una ID busca el jugador y lo retorna.
    private IJugador buscarJugadorPorID(int id) {
        IJugador jugador;
        for (IJugador j : jugadores) {
            if (j.getId() == id)
                return j;
        }
        return null;
    }

    // robo fichas del pozo y actualizo la mano.
    @Override
    public void robarFichaPozo() throws RemoteException {
        IJugador jugador = buscarJugadorPorID(turno);
        IFicha ficha = pozo.sacarFicha();
        if (ficha == null) {
            pasarTurno();
        } else {
            EventoJugador eventoJugador = new EventoJugador(Evento.CAMBIO_FICHAS_JUGADOR, jugador);
            jugador.recibirFicha(ficha);
            notificarObservadores(eventoJugador);
        }
    }

    // reparte las fichas a todos los jugadores.
    private void repartir() {
        for (IJugador j : jugadores) {
            for (int i = 0; i < 7; i++) {
                IFicha ficha = pozo.sacarFicha();
                if (ficha != null) {
                    j.recibirFicha(ficha);
                }
            }
        }
    }

    // it needs to be refactored.
    private void determinarJugadorMano() throws RemoteException {
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
            jugMano = setJugadorMano(jugadoresConFichasDobles);
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

    private IJugador setJugadorMano(List<IJugador> jugadoresConFichasDobles) {
        IJugador jugMano;
        jugMano = jugadorfichaDobleMasAlta(jugadoresConFichasDobles);
        jugMano.setMano(true);
        jugadorMano = jugMano;
        return jugMano;
    }

    private void setearTablero(IFicha ficha) throws FichaIncorrecta {
        Tablero.setExtremoDerec(ficha);
        Tablero.setExtremoIzq(ficha);
    }

    // mueve el jugdor al final del turno en el caso de que ya haya tirado.
    private void moverJugFinalTurno() throws RemoteException {
        if (getJugadorID(colaTurnos.peek()) == jugadorMano) {
            IJugador jugador = getJugadorID(colaTurnos.poll());
            colaTurnos.offer(jugador.getId());
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
        for (IJugador j : jugadores) {
            puntosTotal += j.contarPuntosFicha();
        }
        buscarJugadorPorID(turno).sumarPuntos(puntosTotal);
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
        turno = ganador.getId(); // marcamos al ganador como el jugador del turno para dsp contar los puntos.
    }

    private void determinarSiJugadorGano() throws RemoteException {
        if (buscarJugadorPorID(turno).getPuntos() >= LIMITEPUNTOS) {
            EventoJugador eventoJugador = new EventoJugador(Evento.FIN_DEL_JUEGO, getJugadorID(turno));
            notificarObservadores(eventoJugador);
        } else {
            EventoTurnoJugadores eventoTurnoJugadores = new EventoTurnoJugadores(Evento.CAMBIO_RONDA, buscarJugadorPorID(turno), jugadores);
            notificarObservadores(eventoTurnoJugadores); // jugador que domino la ronda mas todos los jugadores.
            reiniciarRonda();
        }
    }

    private void reiniciarRonda() throws RemoteException {
        juntarFichasTablero();
        juntarFichasJugadores();
        Collections.shuffle(pozo.getFichas());
        Tablero.resetearTablero(); // limpio las fichas del tablero.
        System.out.println("TILES: " + fichas.size() + "\n");
        System.out.println("POZO: " + pozo.getFichas().size() + "\n");
        iniciarJuego(LIMITEPUNTOS);
    }

    // Junta las fichas del tablero y las agrega al pozo.
    private void juntarFichasTablero() {
        List<IFicha> tableroFichas = Tablero.getFichas();
        for (IFicha f : tableroFichas) {
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

    // Junta las fichas de los jugadores y las agrega al  pozo.
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
        System.out.println("Closing case\n");
        detectarJugadorGanadorCierre();
        buscarJugadorPorID(turno).getFichas().clear(); // limpio la mano del jugador ya que no jugo todas sus fichas pero gano.
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
        IJugador jugador = getJugadorID(colaTurnos.poll());
        colaTurnos.offer(jugador.getId());
        determinarJugadorTurno(); // actualizo el turno
        EventoJugador eventoJugador = new EventoJugador(Evento.PASAR_TURNO, getJugadorID(turno));
        notificarObservadores(eventoJugador);
    }
}
