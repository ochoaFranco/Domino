package vista;

import controlador.Controlador;
import modelo.IFicha;
import modelo.IJugador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login  extends JDialog implements IVista {

    private Controlador controlador;
    JComboBox<String> interfazComboBox;
    JTextField txtF1 = new JTextField();
    JFrame parent;

    public Login(JFrame parent) {
        super(parent, "Login", false);
        // seteo la ventana anterior para poder cerrarla.
        this.parent = parent;
        // seteando atributos
        setTitle("Domino");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(450, 200);
        setResizable(false);

        // se crea un panel
        JPanel panel = Lobby.getjPanel();
        panel.setLayout(null);

        // atributos de los labels.
        JLabel lblNombre = new JLabel("Nombre");
        lblNombre.setBounds(80, 20, 100, 20);
        lblNombre.setForeground(Color.white);
        lblNombre.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(lblNombre);

        JLabel lblConsola = new JLabel("Interfaz");
        lblConsola.setBounds(80, 50, 100, 20);
        lblConsola.setForeground(Color.white);
        lblConsola.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(lblConsola);

        // agrego los text fields
        txtF1.setBounds(180, 20, 100, 20);
        panel.add(txtF1);

        // Agrego un menu de opciones.
        String[] interfazOpciones = {"Grafica", "Consola"};
        interfazComboBox = new JComboBox<>(interfazOpciones);
        interfazComboBox.setBounds(180, 50, 100, 20);
        panel.add(interfazComboBox);

        // agrego los botones.
        JButton okayBtn = new JButton("Ok");
        okayBtn.setBounds(180, 100, 100, 20);
        panel.add(okayBtn);

        this.getContentPane().add(panel);

        // Agrego la funcionalidad del boton.

        okayBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                okayBtnPresionado();
            }
        });
    }

    // funcionalidad para el clicked btn
    private void okayBtnPresionado() {
        String usuario = txtF1.getText();
        String opSeleccionada = (String) interfazComboBox.getSelectedItem();

        dispose();
        MenuJuego.incrementarVentanasCerradas();

    }

    @Override
    public void mostrarMensaje(String mensaje) {

    }

    @Override
    public void mostrarFichasRecibidas(IJugador jugador) {

    }

    @Override
    public void setControlador(Controlador controlador) {

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
}
