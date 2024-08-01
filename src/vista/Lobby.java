package vista;

import controlador.Controlador;
import modelo.IJugador;

import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;

public class Lobby {
    private final JFrame frame;
    private final Controlador controlador;

    public Lobby(Controlador controlador) throws RemoteException {
        this.controlador = controlador;
        frame = new JFrame("Domino");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 768);
        frame.setResizable(false);

        JPanel panel = Lobby.getjPanel("img/dominoes.jpg");

        // set texto attributos.
        JLabel texto = new JLabel("Bienvenidos al juego del Domino.");
        texto.setForeground(Color.WHITE);
        texto.setFont(new Font("Arial", Font.BOLD, 24));
        texto.setAlignmentX(Component.CENTER_ALIGNMENT); // alinear el texto al centro.

        // Atributos de los botones.
        JButton jugarBtn = new JButton("JUGAR");
        atributosBotones(jugarBtn, Color.white, Color.black);
        JButton rankingBtn = new JButton("Ranking");
        atributosBotones(rankingBtn, Color.white, Color.black);
        JButton comoJugarBtn = new JButton("¿Como jugar?");
        atributosBotones(comoJugarBtn, Color.white, Color.black);
        // se agregan los componenetes al panel.
        agregarComponentes(panel, texto, jugarBtn, rankingBtn, comoJugarBtn);

        // Add the panel to the frame
        frame.getContentPane().add(panel, BorderLayout.CENTER);

        // calculo tamanio pantalla.

        frame.setLocationRelativeTo(null);

        // Funcionalidad del jugarBtn.
        jugarBtn.addActionListener(e -> ejecutarMenu());

        // funcionalidad del rankingBtn
        rankingBtn.addActionListener(e -> mostrarRanking());
        
        // funcionalidad del comoJugarBtn
        comoJugarBtn.addActionListener(e -> mostrarInstrucciones());
    }

    private void mostrarInstrucciones() {
        String instrucciones = "<html><body style='font-size:12px;'>" +
                "<h2 style='color: blue;'>Vista gráfica</h2>" +
                "<p>Para jugar a la izquierda simplemente haga click en el botón <i style='color: green;'>izquierda</i>, caso contrario para la derecha.</p>" +
                "<p>El botón <i style='color: green;'>robar</i> le permite sacar una ficha del pozo, basta con hacer un click.</p>" +
                "<h2 style='color: red;'>Vista Consola</h2>" +
                "<p>Esta utiliza comandos para poder jugar, los mismos son detallados a continuación.</p>" +
                "<p> <i style='color: green;'>ficha: valorIzquierdo Valor derecho + posición (i o d)</i>, por ejemplo, para jugar la ficha 3 5 en la izquierda usted debe hacer ficha: 3 5 i. En el caso de la derecha es lo mismo pero debe reemplazar la i por la d.</p>" +
                "<p><i style='color: green;'>robar</i>: permite sacar una ficha del pozo.</p>" +
                "<p><i style='color: green;'>desconectar</i>: permite desconectarse del juego para jugar más tarde.</p>" +
                "</body></html>";

        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, instrucciones, "¿Cómo jugar?", JOptionPane.INFORMATION_MESSAGE));
    }


    /**
     * Recibe un boton y lo customiza dependiendo de los parametros pasados.
     * @param btn el boton al cual se le quiere aplicar los atributos.
     * @param colorBck color para el background.
     * @param colorFrg color para el foreground.
     */
    private void atributosBotones(JButton btn, Color colorBck, Color colorFrg) {
        btn.setBackground(colorBck);
        btn.setForeground(colorFrg);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    // Muestra el ranking de los jugdores.
    private void mostrarRanking() {
        IJugador[] jugadoresRanking = controlador.getRanking();
        String finalRanking;
        if (jugadoresRanking[0] != null) {
            finalRanking = jugadoresRankingAtributos(jugadoresRanking);
            SwingUtilities.invokeLater(()-> JOptionPane.showMessageDialog(null, finalRanking, "Ranking", JOptionPane.INFORMATION_MESSAGE));
        } else {
            SwingUtilities.invokeLater(()-> JOptionPane.showMessageDialog(null, "No hay jugadores en el ranking aun", "Ranking", JOptionPane.INFORMATION_MESSAGE));
        }
    }

    // Retonra los atributos de los jugadores del ranking.
    private String jugadoresRankingAtributos(IJugador[] jugadoresRanking) {
        StringBuilder ranking = new StringBuilder(" ");
        for (IJugador j : jugadoresRanking) {
            if (j != null)
                ranking.append(j.getNombre()).append(" ").append(j.getPuntos()).append(" puntos\n");
        }

        return ranking.toString();
    }

    // agrega componentes al panel.
    private static void agregarComponentes(JPanel panel, JLabel texto, JButton jugarBtn, JButton rankingBtn, JButton comoJugarBtn) {
        // agregos los componentes al panel.
        panel.add(Box.createRigidArea(new Dimension(0, 100)));
        panel.add(texto);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(jugarBtn);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(rankingBtn);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(comoJugarBtn);
    }

    private void ejecutarMenu() {
        MenuJuego menu = new MenuJuego(frame, controlador);
        menu.iniciar();
    }

    // configuro el BG para el panel.
    public static JPanel getjPanel(String path) {
        JPanel panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    ImageIcon background = new ImageIcon(getClass().getResource(path));
                    g.drawImage(background.getImage(), 0, 0, this);
                } catch (NullPointerException e ) {
                    throw new RuntimeException();
                }
            }

        };

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        return panel;
    }

    public void iniciar() {
        frame.setVisible(true);
    }

}
