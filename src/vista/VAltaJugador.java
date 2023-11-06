package vista;

import java.util.Scanner;

import modelo.Jugador;
/**
     * Método para registrar un nuevo jugador.
     *
     * solicita al usuario ingresar el nombre del jugador
     * y crea una instancia de la clase Jugador
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
