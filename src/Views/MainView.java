package Views;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class MainView extends JFrame implements ActionListener {
    JSplitPane splitPane = new JSplitPane();
    Panel sidebar = new Panel(), mainpane = new Panel();
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
    ImageIcon imagecitas = new ImageIcon(new ImageIcon("src/Views/assets/documents.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
    ImageIcon imagetrabajadores = new ImageIcon(new ImageIcon("src/Views/assets/employees.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
    ImageIcon imageshrink = new ImageIcon(new ImageIcon("src/Views/assets/arrow-left-solid.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
    ImageIcon imageexpand = new ImageIcon(new ImageIcon("src/Views/assets/arrow-right-solid.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
    JButton tabclientes = new JButton("Cronometro", imageclientes), tabcitas = new JButton("Clases", imagecitas);
    JButton tabtrabajadores = new JButton("Corredor", imagetrabajadores), buttonshrink = new JButton(imageshrink);
    JMenuBar menu;
    JCheckBoxMenuItem alarma;
    JLabel stringalarma;
    int pestanaactual = 1;
    boolean banderasidebar = false;

    private volatile boolean status = false;

    public void setAlarma() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    if (status) {
                        while (true) {
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
                        }
                    } else {
                        menu.remove(stringalarma);
                        stringalarma = new JLabel();
                        menu.add(stringalarma);
                        menu.revalidate();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
        this.setClientesPane();
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
            if (Objecto == tabclientes && pestanaactual != 1) {
                int response = JOptionPane.showConfirmDialog(this, "¿Estas seguro de salir de este panel? podria perderse progreso", "Cambiar de panel", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == 0) {
                    this.removeSidebar();
                    this.removeCurrentPane();
                    this.setSideBar(1);
                    this.setClientesPane();
                } else {
                    return;
                }
            }
            if (Objecto == tabcitas && pestanaactual != 2) {
                int response = JOptionPane.showConfirmDialog(this, "¿Estas seguro de salir de este panel? podria perderse progreso", "Cambiar de panel", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == 0) {
                    this.removeSidebar();
                    this.removeCurrentPane();
                    this.setSideBar(2);
                    this.setCitasPane();
                    innerpane.repaint();
                    innerpane.revalidate();
                    mainpane.repaint();
                    mainpane.revalidate();
                } else {
                    return;
                }
            }
            if (Objecto == tabtrabajadores && pestanaactual != 3) {
                int response = JOptionPane.showConfirmDialog(this, "¿Estas seguro de salir de este panel? podria perderse progreso", "Cambiar de panel", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (response == 0) {
                    this.removeSidebar();
                    this.removeCurrentPane();
                    this.setSideBar(3);
                    this.setTrabajadoresPane();
                } else {
                    return;
                }
            }
            if (Objecto == alarma) {
                assert alarma != null;
                this.setStatus(alarma.getState());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        repaint();
    }

    public void setClientesPane() throws IOException {
        innerpane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        Insets inset = new Insets(5, 5, 5, 5);
        c.gridy = 0;
        c.insets = inset;
        c.ipady = 0;
        c.ipadx = 0;
        c.weightx = 0.1;
        c.weighty = 0.1;
        c.fill = GridBagConstraints.HORIZONTAL;
        innerpane.add(new JButton("miau"), c);
        inset = new Insets(5, 10, 50, 10);
        c.gridx = 0;
        c.gridy = 1;
        c.insets = inset;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Nuevo Cliente", TitledBorder.LEFT, TitledBorder.TOP);
        border.setTitleFont(sizedFontRegular);
        innerpane.setBorder(border);
        mainpane.add(innerpane, c);
    }

    public void setCitasPane() throws IOException {

    }

    public void setTrabajadoresPane() throws IOException {

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
            tabclientes = new JButton(imageclientes);
            tabcitas = new JButton(imagecitas);
            tabtrabajadores = new JButton(imagetrabajadores);
        } else {
            splitPane.setDividerLocation(195);
            LabelTitulo = new JLabel(imagebrand);
            LabelTitulo.setHorizontalAlignment(JLabel.CENTER);
            sidebar.add(LabelTitulo);
            tabclientes = new JButton("CRONOMETRO", imageclientes);
            tabcitas = new JButton("CLASE", imagecitas);
            tabtrabajadores = new JButton("CORREDOR", imagetrabajadores);

            tabclientes.setIconTextGap(20);
            tabcitas.setIconTextGap(80);
            tabtrabajadores.setIconTextGap(40);

            tabclientes.setFont(sizedFont);
            tabcitas.setFont(sizedFont);
            tabtrabajadores.setFont(sizedFont);
        }

        tabclientes.removeActionListener(this);
        tabcitas.removeActionListener(this);
        tabtrabajadores.removeActionListener(this);

        tabclientes.addActionListener(this);
        tabcitas.addActionListener(this);
        tabtrabajadores.addActionListener(this);

        sidebar.add(tabclientes);
        sidebar.add(tabcitas);
        sidebar.add(tabtrabajadores);
        sizedFont = fontbold.deriveFont(20f);
        JLabel LabelPanel = new JLabel("NOPE");
        if (tab == 1) {
            LabelPanel = new JLabel("Clientes");
            LabelPanel.setFont(sizedFont);
        }
        if (tab == 2) {
            LabelPanel = new JLabel("Citas");
            LabelPanel.setFont(sizedFont);
        }
        if (tab == 3) {
            LabelPanel = new JLabel("Trabajadores");
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