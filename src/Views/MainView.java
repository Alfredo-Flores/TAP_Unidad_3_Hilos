package Views;

import Handler.Alumno;
import Handler.Cronometro;
import Handler.Saludo;
import Handler.Sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class MainView extends JFrame implements ActionListener {
    JSplitPane splitPane = new JSplitPane();
    Panel sidebar = new Panel();
    public static Panel mainpane = new Panel();
    JPanel innerpane = new JPanel();
    File montserratregular = new File("src/Views/assets/Montserrat-Regular.ttf");
    File montserratbold = new File("src/Views/assets/Montserrat-Bold.ttf");
    Font fontregular = Font.createFont(Font.TRUETYPE_FONT, montserratregular);
    Font fontbold = Font.createFont(Font.TRUETYPE_FONT, montserratbold);
    Font sizedFontBold = fontbold.deriveFont(14f);
    Font sizedFontRegular = fontregular.deriveFont(12f);
    Image imagetoothicon = new ImageIcon("src/Views/assets/logo.png").getImage();
    ImageIcon imagetooth = new ImageIcon(new ImageIcon("src/Views/assets/logo.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
    ImageIcon imagebrand = new ImageIcon(new ImageIcon("src/Views/assets/brand.png").getImage().getScaledInstance(120, 40, Image.SCALE_SMOOTH));
    ImageIcon imageclientes = new ImageIcon(new ImageIcon("src/Views/assets/client.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
    ImageIcon imagesalon = new ImageIcon(new ImageIcon("src/Views/assets/documents.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
    ImageIcon imagecorredor = new ImageIcon(new ImageIcon("src/Views/assets/employees.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
    ImageIcon imageshrink = new ImageIcon(new ImageIcon("src/Views/assets/arrow-left-solid.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
    ImageIcon imageexpand = new ImageIcon(new ImageIcon("src/Views/assets/arrow-right-solid.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
    ImageIcon imageuser = new ImageIcon(new ImageIcon("src/Views/assets/user-solid.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
    public static ImageIcon imagesprite = new ImageIcon(new ImageIcon("src/Views/assets/sprite/0.gif").getImage());
    JButton tabronometro = new JButton("Cronometro", imageclientes), tabsalon = new JButton("Clases", imagesalon);
    JButton tabcorredor = new JButton("Corredor", imagecorredor), buttonshrink = new JButton(imageshrink);
    JMenuBar menu;
    public static JLabel reloj = new JLabel("00:00:00");
    JButton reiniciar = new JButton("Reiniciar");
    JButton ejecutar = new JButton("Ejecutar/Pausar");
    JButton registrar = new JButton("Registrar tiempo");
    public static DefaultListModel<String> modeloreloj = new DefaultListModel<>();
    public static JList<String> listareloj = new JList<>(modeloreloj);
    JScrollPane scrollreloj = new JScrollPane(listareloj);
    Cronometro cronometro;
    Thread cronometrothread;
    JCheckBoxMenuItem alarma;
    JLabel stringalarma;
    Saludo saludo = new Saludo();
    JLabel labeluser_1 = new JLabel(imageuser);
    JLabel labeluser_2 = new JLabel(imageuser);
    JLabel labeluser_3 = new JLabel(imageuser);
    JLabel labeluser_4 = new JLabel(imageuser);
    JLabel labeluser_5 = new JLabel(imageuser);
    JLabel labelmaestro = new JLabel();
    JButton buttonmaestro = new JButton("Empezar clase");
    JLabel labelalumno_1 = new JLabel("Alumno 1");
    JButton buttonalumno_1 = new JButton("Prestar atencion");
    JLabel labelalumno_2 = new JLabel("Alumno 2");
    JButton buttonalumno_2 = new JButton("Prestar atencion");
    JLabel labelalumno_3 = new JLabel("Alumno 3");
    JButton buttonalumno_3 = new JButton("Prestar atencion");
    JLabel labelalumno_4 = new JLabel("Alumno 4");
    JButton buttonalumno_4 = new JButton("Prestar atencion");
    public static JLabel sprite = new JLabel(imagesprite);
    JButton ejecutarsprite = new JButton("Ejecutar/Pausar");
    Sprite animacion;
    Thread animacionthread;

    int pestanaactual = 1;
    boolean banderasidebar = false;

    private volatile boolean status = false;

    public void setAlarma() {
        new Thread(() -> {
            try {
                while (true) {
                    if (status) {
                        menu.remove(stringalarma);
                        stringalarma = new JLabel("Fuego");
                        menu.add(stringalarma);
                        menu.revalidate();
                        Thread.sleep(250);

                        menu.remove(stringalarma);
                        stringalarma = new JLabel("Fuego");
                        stringalarma.setForeground(Color.red);
                        menu.add(stringalarma);
                        menu.revalidate();
                        Thread.sleep(250);
                    } else {
                        menu.remove(stringalarma);
                        stringalarma = new JLabel();
                        menu.add(stringalarma);
                        menu.revalidate();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private synchronized void setStatus(Boolean status) {
        this.status = status;
    }

    MainView() throws IOException, FontFormatException {
        super("OdontoClinic - Recepción");
        setSize(1152, 640);
        setMinimumSize(new Dimension(850, 480));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setIconImage(imagetoothicon);
        this.setSideBar(1);
        this.setCronometroPane();
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sidebar, mainpane);
        splitPane.setEnabled(false);
        getContentPane().add(splitPane);
        menu = new JMenuBar();
        alarma = new JCheckBoxMenuItem("Alarma");
        stringalarma = new JLabel();

        alarma.addActionListener(this);

        menu.add(alarma);
        menu.add(stringalarma);

        setJMenuBar(menu);
        setStatus(false);
        setAlarma();
        setVisible(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(null,
                        "¿Estas seguro que quieres salir?", "Cerrar ventana",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == 0) {
                    System.exit(0);
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object Objecto = actionEvent.getSource();

        try {
            if (Objecto == tabronometro && pestanaactual != 1) {
                this.removeSidebar();
                this.removeCurrentPane();
                this.setSideBar(1);
                this.setCronometroPane();
            }
            if (Objecto == tabsalon && pestanaactual != 2) {
                this.removeSidebar();
                this.removeCurrentPane();
                this.setSideBar(2);
                this.setSalonPane();
                innerpane.repaint();
                innerpane.revalidate();
                mainpane.repaint();
                mainpane.revalidate();
            }
            if (Objecto == tabcorredor && pestanaactual != 3) {
                this.removeSidebar();
                this.removeCurrentPane();
                this.setSideBar(3);
                this.setCorredorPane();
            }
            if (Objecto == buttonshrink) {
                this.removeSidebar();
                this.removeCurrentPane();
                this.changeSideBar();
                this.setSideBar(this.pestanaactual);

                if (this.pestanaactual == 1) {
                    this.setCronometroPane();
                } else if (this.pestanaactual == 2) {
                    this.setSalonPane();
                } else if (this.pestanaactual == 3) {
                    this.setCorredorPane();
                }
            }
            if (Objecto == alarma) {
                assert alarma != null;
                this.setStatus(alarma.getState());
            }
            if (Objecto == reiniciar) {
                if (cronometro != null) cronometro.stop();
                cronometro = new Cronometro();
                cronometrothread = new Thread(cronometro);
                cronometrothread.start();
            }
            if (Objecto == ejecutar) {
                if (cronometro == null) {
                    cronometro = new Cronometro();
                    cronometrothread = new Thread(cronometro);
                    cronometrothread.start();
                } else {
                    if (cronometro.isPaused()) cronometro.resume();
                    else cronometro.pause();
                }
            }
            if (Objecto == registrar) {
                if (cronometro == null) {
                    return;
                } else {
                    cronometro.registerClock();
                }
            }
            if (Objecto == buttonalumno_1) {
                Alumno alumno = new Alumno("Alumno 1", false, saludo);
                alumno.start();
            }if (Objecto == buttonalumno_2) {
                Alumno alumno = new Alumno("Alumno 2", false, saludo);
                alumno.start();
            }if (Objecto == buttonalumno_3) {
                Alumno alumno = new Alumno("Alumno 3", false, saludo);
                alumno.start();
            }if (Objecto == buttonalumno_4) {
                Alumno alumno = new Alumno("Alumno 4", false, saludo);
                alumno.start();
            }if (Objecto == buttonmaestro) {
                Alumno alumno = new Alumno("El Maestro", true, saludo);
                alumno.start();
            }if (Objecto == ejecutarsprite) {
                if (animacion == null) {
                    animacion = new Sprite();
                    animacionthread = new Thread(animacion);
                    animacionthread.start();
                } else {
                    if (animacion.isPaused()) animacion.resume();
                    else animacion.pause();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        repaint();
    }


    public void setCronometroPane() {
        Font sizedFontBold = fontbold.deriveFont(78f);
        innerpane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        Insets inset = new Insets(5, 5, 5, 5);
        c.gridy = 0;
        c.insets = inset;
        c.ipady = 0;
        c.ipadx = 0;
        c.weightx = 0.1;
        c.weighty = 0.1;
        c.gridwidth = 3;
        c.anchor = GridBagConstraints.CENTER;
        reloj.setFont(sizedFontBold);
        innerpane.add(reloj, c);
        c.gridwidth = 1;
        c.gridy = 1;
        c.gridx = 0;
        ejecutar.removeActionListener(this);
        ejecutar.addActionListener(this);
        innerpane.add(ejecutar, c);
        c.gridx = 1;
        reiniciar.removeActionListener(this);
        reiniciar.addActionListener(this);
        innerpane.add(reiniciar, c);
        c.gridx = 2;
        registrar.removeActionListener(this);
        registrar.addActionListener(this);
        innerpane.add(registrar, c);
        c.gridy = 2;
        c.gridx = 0;
        c.gridwidth = 3;
        c.fill = GridBagConstraints.HORIZONTAL;
        scrollreloj.setFont(sizedFontRegular);
        innerpane.add(scrollreloj, c);
        inset = new Insets(5, 10, 50, 10);
        c.gridx = 0;
        c.gridy = 1;
        c.insets = inset;
        c.anchor = GridBagConstraints.CENTER;
        c.gridwidth = 2;
        mainpane.add(innerpane, c);
    }

    public void setSalonPane() {
        Font sizedFontBold = fontbold.deriveFont(78f);
        innerpane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        Insets inset = new Insets(5, 5, 5, 5);
        c.gridy = 0;
        c.insets = inset;
        c.ipady = 0;
        c.ipadx = 0;
        c.weightx = 0.1;
        c.weighty = 0.1;
        c.gridwidth = 4;
        c.anchor = GridBagConstraints.CENTER;
        reloj.setFont(sizedFontBold);
        innerpane.add(labeluser_1, c);
        c.gridy = 1;
        innerpane.add(labelmaestro, c);
        c.gridy = 2;
        buttonmaestro.removeActionListener(this);
        buttonmaestro.addActionListener(this);
        innerpane.add(buttonmaestro, c);
        c.gridwidth = 1;
        c.gridy = 3;
        c.gridx = 0;
        innerpane.add(labeluser_2, c);
        c.gridy = 4;
        innerpane.add(labelalumno_1, c);
        c.gridy = 5;
        buttonalumno_1.removeActionListener(this);
        buttonalumno_1.addActionListener(this);
        innerpane.add(buttonalumno_1, c);
        c.gridy = 3;
        c.gridx = 1;
        innerpane.add(labeluser_3, c);
        c.gridy = 4;
        innerpane.add(labelalumno_2, c);
        c.gridy = 5;
        buttonalumno_2.removeActionListener(this);
        buttonalumno_2.addActionListener(this);
        innerpane.add(buttonalumno_2, c);
        c.gridy = 3;
        c.gridx = 2;
        innerpane.add(labeluser_4, c);
        c.gridy = 4;
        innerpane.add(labelalumno_3, c);
        c.gridy = 5;
        buttonalumno_3.removeActionListener(this);
        buttonalumno_3.addActionListener(this);
        innerpane.add(buttonalumno_3, c);
        c.gridy = 3;
        c.gridx = 3;
        innerpane.add(labeluser_5, c);
        c.gridy = 4;
        innerpane.add(labelalumno_4, c);
        c.gridy = 5;
        buttonalumno_4.removeActionListener(this);
        buttonalumno_4.addActionListener(this);
        innerpane.add(buttonalumno_4, c);

        inset = new Insets(5, 10, 50, 10);
        c.gridx = 0;
        c.gridy = 1;
        c.insets = inset;
        c.anchor = GridBagConstraints.CENTER;
        c.gridwidth = 2;
        mainpane.add(innerpane, c);
    }

    public void setCorredorPane() {
        innerpane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        Insets inset = new Insets(5, 5, 5, 5);
        c.gridy = 0;
        c.insets = inset;
        c.ipady = 0;
        c.ipadx = 0;
        c.weightx = 0.1;
        c.weighty = 0.1;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        innerpane.add(sprite, c);
        c.gridy = 1;
        c.gridx = 0;
        ejecutarsprite.removeActionListener(this);
        ejecutarsprite.addActionListener(this);
        innerpane.add(ejecutarsprite, c);
        inset = new Insets(5, 10, 50, 10);
        c.gridx = 0;
        c.gridy = 1;
        c.insets = inset;
        c.anchor = GridBagConstraints.CENTER;
        c.gridwidth = 2;
        mainpane.add(innerpane, c);
    }


    public void setSideBar(int tab) {
        this.pestanaactual = tab;
        GridLayout gridLayout = new GridLayout(10, 1, 5, 10);
        sidebar.setLayout(gridLayout);
        JLabel LabelTitulo = new JLabel("OdontoClinic");
        String fontname = LabelTitulo.getFont().getName();
        LabelTitulo.setFont(new Font(fontname, Font.BOLD, 18));
        Font sizedFont = fontbold.deriveFont(14f);
        Font sizedFontRegular = fontregular.deriveFont(12f);
        if (banderasidebar) {
            splitPane.setDividerLocation(50);
            LabelTitulo = new JLabel(imagetooth);
            LabelTitulo.setHorizontalAlignment(JLabel.CENTER);
            sidebar.add(LabelTitulo);
            tabronometro = new JButton(imageclientes);
            tabsalon = new JButton(imagesalon);
            tabcorredor = new JButton(imagecorredor);
        } else {
            splitPane.setDividerLocation(195);
            LabelTitulo = new JLabel(imagebrand);
            LabelTitulo.setHorizontalAlignment(JLabel.CENTER);
            sidebar.add(LabelTitulo);
            tabronometro = new JButton("CRONOMETRO", imageclientes);
            tabsalon = new JButton("CLASE", imagesalon);
            tabcorredor = new JButton("CORREDOR", imagecorredor);

            tabronometro.setIconTextGap(20);
            tabsalon.setIconTextGap(80);
            tabcorredor.setIconTextGap(40);

            tabronometro.setFont(sizedFont);
            tabsalon.setFont(sizedFont);
            tabcorredor.setFont(sizedFont);
        }

        tabronometro.removeActionListener(this);
        tabsalon.removeActionListener(this);
        tabcorredor.removeActionListener(this);

        tabronometro.addActionListener(this);
        tabsalon.addActionListener(this);
        tabcorredor.addActionListener(this);

        sidebar.add(tabronometro);
        sidebar.add(tabsalon);
        sidebar.add(tabcorredor);
        sizedFont = fontbold.deriveFont(20f);
        JLabel LabelPanel = new JLabel("NOPE");
        if (tab == 1) {
            LabelPanel = new JLabel("Cronometro");
            LabelPanel.setFont(sizedFont);
        }
        if (tab == 2) {
            LabelPanel = new JLabel("Clase");
            LabelPanel.setFont(sizedFont);
        }
        if (tab == 3) {
            LabelPanel = new JLabel("Corredor");
            LabelPanel.setFont(sizedFont);
        }
        mainpane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        Insets inset = new Insets(5, 10, 5, 10);
        if (banderasidebar) {
            buttonshrink = new JButton(imageexpand);
        } else {
            buttonshrink = new JButton(imageshrink);
        }
        buttonshrink.removeActionListener(this);
        buttonshrink.addActionListener(this);
        c.weightx = 0.001;
        c.weighty = 0.001;
        c.gridx = 0;
        c.gridy = 0;
        c.ipady = 30;
        c.ipadx = 30;
        c.insets = inset;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        buttonshrink.setBorder(null);
        buttonshrink.setBorderPainted(false);
        buttonshrink.setContentAreaFilled(false);
        buttonshrink.setOpaque(false);
        mainpane.add(buttonshrink, c);
        c.gridx = 1;
        c.gridy = 0;
        mainpane.add(LabelPanel, c);
    }

    private void removeSidebar() {
        sidebar.removeAll();
    }

    private void removeCurrentPane() {
        innerpane.removeAll();
        mainpane.removeAll();
    }

    private void changeSideBar() {
        this.banderasidebar = !this.banderasidebar;
    }
}