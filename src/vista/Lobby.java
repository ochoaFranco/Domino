package vista;

import controlador.Controlador;
import modelo.IFicha;
import modelo.IJuego;
import modelo.IJugador;
import modelo.Juego;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

public class Lobby {
    private final JFrame frame;
    private Controlador controlador;

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
        jugarBtn.setBackground(Color.white);
        jugarBtn.setForeground(Color.black);
        jugarBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton rankingBtn = new JButton("Ranking");
        rankingBtn.setBackground(Color.white);
        rankingBtn.setForeground(Color.black);
        rankingBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        // se agregan los componenetes al panel.
        agregarComponentes(panel, texto, jugarBtn, rankingBtn);

        // Add the panel to the frame
        frame.getContentPane().add(panel, BorderLayout.CENTER);

        // calculo tamanio pantalla.

        frame.setLocationRelativeTo(null);

        // Funcionalidad del jugarBtn.
        jugarBtn.addActionListener(e -> ejecutarMenu());

        // funcionalidad del rankingBtn
        rankingBtn.addActionListener(e -> mostrarRanking());
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
    private static void agregarComponentes(JPanel panel, JLabel texto, JButton jugarBtn, JButton rankingBtn) {
        // agregos los componentes al panel.
        panel.add(Box.createRigidArea(new Dimension(0, 100)));
        panel.add(texto);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(jugarBtn);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(rankingBtn);
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
