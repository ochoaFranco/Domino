package vista;

import controlador.Controlador;
import modelo.IFicha;
import modelo.IJugador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Login  extends JDialog implements IVista {
    private final Controlador controlador;
    private JComboBox<String> interfazComboBox;
    private final JTextField txtfieldNombre = new JTextField();
    private final JTextField txtfieldPuntos = new JTextField();
    private final JTextField txtfielCantJugadores = new JTextField();
    private final JFrame parent;
    private static boolean isJuegoIniciado;
    private static int cantVentanasAbiertas;
    private final static int cantMaxVentanasAbiertas = 1;

    public Login(JFrame parent, Controlador controlador) {
        super(parent, "Login", false);
        this.controlador = controlador;
        isJuegoIniciado = false;
        cantVentanasAbiertas = 0;
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

        // agrego los text fields
        txtfieldNombre.setBounds(180, 8, 100, 20);
        txtfieldPuntos.setBounds(180, 68, 100, 20);
        txtfielCantJugadores.setBounds(180, 94, 100, 20);
        panel.add(txtfieldNombre);
        panel.add(txtfieldPuntos);
        panel.add(txtfielCantJugadores);

        // Agrego un menu de opciones.
        agregarMenuOpciones(panel);
        // agrego los botones.
        JButton okayBtn = new JButton("Ok");
        okayBtn.setBounds(180, 130, 100, 20);
        panel.add(okayBtn);

        // calculo tamanio pantalla.
        this.setLocationRelativeTo(null);
        this.getContentPane().add(panel);

        // Agrego la funcionalidad del boton.
        okayBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtfieldNombre.getText().isEmpty() || txtfieldPuntos.getText().isEmpty() || txtfielCantJugadores.getText().isEmpty())
                    JOptionPane.showMessageDialog(null, "No puede haber campos vacios !!!", "Error", JOptionPane.ERROR_MESSAGE);
                else {
                    try {
                        int puntos = Integer.parseInt(txtfieldPuntos.getText());
                        int cantJugadors = Integer.parseInt(txtfielCantJugadores.getText());
                        if (cantJugadors < 2 || cantJugadors > 4)
                            JOptionPane.showMessageDialog(null, "Revise la cantidad minima y maxima de jugadores !!!", "Error", JOptionPane.ERROR_MESSAGE);
                        else
                            okayBtnPresionado();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Recuerde que los puntos y cantidad de jugadores son valores numericos" +
                                " !!!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // agrego un listener al textfield y combo box
        txtfieldNombre.addKeyListener(new KeyAdapter() {
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
        interfazComboBox.setBounds(180, 38, 100, 20);
        panel.add(interfazComboBox);
    }


    // agrega todos los labels en la pantalla
    private void agregarLabels(JPanel panel) {
        // Atributos para el label del nombre
        JLabel lblNombre = new JLabel("Nombre");
        lblNombre.setBounds(80, 8, 100, 20);
        lblNombre.setForeground(Color.white);
        lblNombre.setFont(new Font("Arial", Font.BOLD, 16));

        // Atributos para el label de la interfaz
        JLabel lblConsola = new JLabel("Interfaz");
        lblConsola.setBounds(80, 38, 100, 20);
        lblConsola.setForeground(Color.white);
        lblConsola.setFont(new Font("Arial", Font.BOLD, 16));

        // Atributos para el label de los puntos
        JLabel lblPuntos = new JLabel("Puntos");
        lblPuntos.setBounds(80, 68, 100, 20);
        lblPuntos.setForeground(Color.white);
        lblPuntos.setFont(new Font("Arial", Font.BOLD, 16));

        // Atributos para el label de los jugadores.
        JLabel lblJugadores = new JLabel("Jugadores");
        lblJugadores.setBounds(80, 94, 100, 20);
        lblJugadores.setForeground(Color.white);
        lblJugadores.setFont(new Font("Arial", Font.BOLD, 16));

        // Atributos para el label de los jugadores (constraints)
        JLabel lblJugadoresConstraints = new JLabel("(rango 2-4)");
        lblJugadoresConstraints.setBounds(290, 94, 100, 20);
        lblJugadoresConstraints.setForeground(Color.white);
        lblJugadoresConstraints.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(lblNombre);
        panel.add(lblConsola);
        panel.add(lblPuntos);
        panel.add(lblJugadores);
        panel.add(lblJugadoresConstraints);
    }

    // levanta la vista correspondiente y cierra las anteriores.
    private void okayBtnPresionado() {
        IVista vista;
        String usuario = txtfieldNombre.getText();
        String opSeleccionada = (String) interfazComboBox.getSelectedItem();
        // comprobamos la seleccion
        assert opSeleccionada != null;
        if (opSeleccionada.equalsIgnoreCase("Consola")) {
            vista = new VistaConsola(usuario, controlador);
            controlador.setVista(vista);
        } else {
            vista = new VistaGrafica(usuario, controlador);
            controlador.setVista(vista);
            vista.ocultarBoton();
        }
        // muestro la vista elegida
//        vista.iniciar();
        if (vista instanceof VistaConsola)
            vista.ocultarBoton();
        Login.cantVentanasAbiertas += 1;

        // EJecuto el juego y levanto las ventanas.
        if (!isJuegoIniciado  && Login.cantVentanasAbiertas == Login.cantMaxVentanasAbiertas) {
            int puntos = Integer.parseInt(txtfieldPuntos.getText()); // ya se encuentra validado.
            int cantJugadores = Integer.parseInt(txtfielCantJugadores.getText()); // ya se encuentra validado.
            if (vista instanceof VistaGrafica) {
                ((VistaGrafica) vista).jugar(puntos, cantJugadores);
            }
            else
                ((VistaConsola)vista).jugar(puntos, cantJugadores);

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
    public void iniciar() {
        setVisible(true);
    }

    @Override
    public void mostrarTablero(Object o) {

    }

    @Override
    public void mostrarTablaPuntos(Object o) {

    }

    @Override
    public void ocultarBoton() {

    }

    @Override
    public void mostrarBoton() {

    }

    @Override
    public void limpiarTablero() {

    }

    @Override
    public void finalizarJuego(String mensaje) {

    }
}
