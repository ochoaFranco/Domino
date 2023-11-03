package vista;

import java.util.Scanner;

import modelo.Jugador;
/**
     * Método para registrar un nuevo jugador.
     *
     * Este método solicita al usuario ingresar el nombre del jugador
     * a través de la entrada estándar y crea una instancia de la clase Jugador
     * con el nombre ingresado.
     *
     * @return Una instancia de la clase Jugador con el nombre del nuevo jugador.
     */
public class VAltaJugador extends Vista {
    public Jugador altaJugador() {
        Scanner sc = new Scanner(System.in);
        mostrarMensaje("Ingrese el nombre del jugador: ");
        String nombre = sc.nextLine();
        sc.close();
        return new Jugador(nombre);
    }
}
