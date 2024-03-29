package vista;

import controlador.Controlador;
import modelo.IFicha;
import modelo.IJugador;
import modelo.Juego;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Login  extends JDialog implements IVista {
    private Controlador controlador;
    private JComboBox<String> interfazComboBox;
    private JTextField txtF1 = new JTextField();
    private JFrame parent;
    private Juego juego;
    private static boolean isJuegoIniciado = false;
    private static int cantVentanasAbiertas = 0;
    private final static int cantMaxVentanasAbiertas = 2;

    public Login(JFrame parent, Juego juego) {
        super(parent, "Login", false);
        this.juego = juego;
        // seteo la ventana anterior para poder cerrarla.
        this.parent = parent;
        // seteando atributos
        setTitle("Domino");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(450, 200);
        setResizable(false);

        // se crea un panel
        JPanel panel = Lobby.getjPanel("img/dominoes.jpg");
        panel.setLayout(null);

        // atributos de los labels.
        agregarLabels(panel);

        agregarLblConsola(panel);

        // agrego los text fields
        txtF1.setBounds(180, 20, 100, 20);
        panel.add(txtF1);

        // Agrego un menu de opciones.
        agregarMenuOpciones(panel);

        // agrego los botones.
        JButton okayBtn = new JButton("Ok");
        okayBtn.setBounds(180, 100, 100, 20);
        panel.add(okayBtn);

        // calculo tamanio pantalla.
        this.setLocationRelativeTo(null);


        this.getContentPane().add(panel);

        // Agrego la funcionalidad del boton.

        okayBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                okayBtnPresionado();
            }
        });

        // agrego un listener al textfield y combo box
        txtF1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    okayBtn.doClick();
            }
        });

        interfazComboBox.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    okayBtn.doClick();
                }
            }
        });
    }

    private void agregarMenuOpciones(JPanel panel) {
        String[] interfazOpciones = {"Grafica", "Consola"};
        interfazComboBox = new JComboBox<>(interfazOpciones);
        interfazComboBox.setBounds(180, 50, 100, 20);
        panel.add(interfazComboBox);
    }

    private void agregarLblConsola(JPanel panel) {
        JLabel lblConsola = new JLabel("Interfaz");
        lblConsola.setBounds(80, 50, 100, 20);
        lblConsola.setForeground(Color.white);
        lblConsola.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(lblConsola);
    }

    private void agregarLabels(JPanel panel) {
        JLabel lblNombre = new JLabel("Nombre");
        lblNombre.setBounds(80, 20, 100, 20);
        lblNombre.setForeground(Color.white);
        lblNombre.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(lblNombre);
    }


    // funcionalidad para el clicked btn
    private void okayBtnPresionado() {
        IVista vista;
        String usuario = txtF1.getText();
        String opSeleccionada = (String) interfazComboBox.getSelectedItem();
        // comprobamos la seleccion
        if (opSeleccionada.equalsIgnoreCase("Consola")) {
            vista = new VistaConsola(usuario);
        } else {
            vista = new VistaGrafica(usuario);
        }
        // seteamos el oontrolador e iniciamos el juego.
        controlador = new Controlador(vista);
        controlador.setModelo(juego);
        vista.setControlador(controlador);
        vista.mostrar();
        if (vista instanceof VistaConsola)
            vista.ocultarBoton();
        Login.cantVentanasAbiertas += 1;


        // si es gui ejecuto el juego.
        if (!isJuegoIniciado && Login.cantVentanasAbiertas == Login.cantMaxVentanasAbiertas) {
            if (vista instanceof VistaGrafica)
                ((VistaGrafica) vista).jugar();
            else
                ((VistaConsola)vista).jugar();
            Login.isJuegoIniciado = true;
        }


        dispose();
        MenuJuego.incrementarVentanasCerradas();

    }



    @Override
    public void mostrarMensaje(String mensaje) {

    }


    @Override
    public void mostrarFichasJugador(IJugador jugador) {

    }

    @Override
    public void mostrarFicha(IFicha ficha) {

    }

    @Override
    public void mostrar() {
        setVisible(true);
    }

    @Override
    public void mostrarTablero(Object o) {

    }

    @Override
    public void mostrarTablaPuntos(Object o) {

    }

    @Override
    public void setControlador(Controlador controlador) {

    }

    @Override
    public void ocultarBoton() {

    }

    @Override
    public void mostrarBoton() {

    }

}
