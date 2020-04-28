package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Login extends JFrame {

    JFrame dashboard;
    JProgressBar progreso;

    JPanel LoginPanel = new JPanel();
    ImageIcon image = new ImageIcon(new ImageIcon("src/Views/assets/login_welcome.jpg").getImage().getScaledInstance(575, 480, Image.SCALE_SMOOTH));
    ImageIcon imagebrand = new ImageIcon(new ImageIcon("src/Views/assets/brand.png").getImage().getScaledInstance(220, 75, Image.SCALE_SMOOTH));
    Image imagetooth = new ImageIcon("src/Views/assets/logo.png").getImage();

    int i = 0, num = 0;


    Login() throws IOException, FontFormatException {
        super("Iniciar sesión");
        setSize(512, 128);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setIconImage(imagetooth);
        setLayout(null);

        LoginPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        Insets inset = new Insets(50, 50, 50, 50);
        c.gridx = 0;
        c.gridy = 0;
        c.insets = inset;
        progreso = new JProgressBar(0, 2000);
        progreso.setBounds(40, 40, 160, 30);
        progreso.setValue(0);
        progreso.setStringPainted(true);


        LoginPanel.add(progreso, c);


        setContentPane(LoginPanel);
    }

    private void login() throws IOException, FontFormatException {

        this.dispose();
        dashboard = new RecepcionistaView();

    }

    public void iterate() {
        while (i <= 2000) {
            progreso.setValue(i);
            i = i + 20;
            try {
                Thread.sleep(150);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws IOException, FontFormatException {
        Login login = new Login();
        login.setVisible(true);
        login.iterate();
    }
}
