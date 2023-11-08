// package controlador;

// import java.util.List;

// import modelo.Ficha;
// import modelo.Juego;
// import modelo.Jugador;
// import modelo.Pozo;
// import vista.VMostrarFichasJugador;


// public class CRepartirFichas {
//     private List<Jugador> jugadores;
//     private static Pozo pozo;

//     public CRepartirFichas() {
//         jugadores = Juego.getJugadores();
//         pozo = Juego.getPozo();
//     }

//     // Se reparten 7 fichas a cada jugador.
//     public void repartir() {
//         for (Jugador j : jugadores) {
//             VMostrarFichasJugador vista = new VMostrarFichasJugador(j);
//             vista.mostrarMensaje("Repartiendo al jugador: " + j.getNombre());
//             for (int i = 0; i < 7; i++) {
//                 Ficha ficha = pozo.sacarFicha();
//                 if (ficha != null) {
//                     j.recibirFicha(ficha);
//                 }
//             }
//         }
//     }
// }
