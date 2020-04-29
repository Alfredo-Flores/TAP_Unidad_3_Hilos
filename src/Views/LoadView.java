package Views;

import javax.swing.JProgressBar;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.FontFormatException;
import java.io.IOException;

public class LoadView extends JFrame {
    JProgressBar progreso;
    JPanel Panel = new JPanel();

    int i = 0;

    LoadView() throws IOException {
        super("Cargando...");
        setSize(512, 128);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        Panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;

        progreso = new JProgressBar(0, 2000);
        progreso.setValue(0);
        progreso.setStringPainted(true);

        Panel.add(progreso, c);

        setContentPane(Panel);
    }

    public void iterate() throws IOException, FontFormatException {

        while (i <= 2000) {
            progreso.setValue(i);
            i = i + 20;
            try {
                Thread.sleep(20);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        this.dispose();
        new MainView();
    }

    public static void main(String[] args) throws IOException, FontFormatException {
        LoadView loadView = new LoadView();
        loadView.setVisible(true);
        loadView.iterate();
    }
}
