package view;

import controller.AppController;
import controller.UserManager;
import model.User;
import view.StartGUI;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class RegisterGUI extends JFrame {

    private String imagenRutaSeleccionada = ""; 

    public RegisterGUI(AppController controller, UserManager userManager) {
        setTitle("Vibe - Registro");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 2)); 

        
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(0x1B264F));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(50, 40, 50, 40));

       
        JLabel nombreLabel = crearLabel("USER");
        JTextField nombreField = new JTextField();
        limitar(nombreField);

        JLabel claveLabel = crearLabel("PASSWORD");
        JPasswordField claveField = new JPasswordField();
        limitar(claveField);

        JLabel correoLabel = crearLabel("EMAIL");
        JTextField correoField = new JTextField();
        limitar(correoField);

        JLabel edadLabel = crearLabel("AGE");
        JTextField edadField = new JTextField();
        limitar(edadField);

        
        JButton subirFotoBtn = new JButton("Subir Imagen");
        subirFotoBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        subirFotoBtn.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int opcion = chooser.showOpenDialog(this);
            if (opcion == JFileChooser.APPROVE_OPTION) {
                imagenRutaSeleccionada = chooser.getSelectedFile().getAbsolutePath();
                JOptionPane.showMessageDialog(this, "\ud83d\udcf8 Imagen seleccionada.");
            }
        });

        
        JButton registrarBtn = new JButton("Registrarse");
        registrarBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        registrarBtn.addActionListener(e -> {
            try {
                String nombre = nombreField.getText().trim();
                String correo = correoField.getText().trim();
                String clave = new String(claveField.getPassword()).trim();
                int edad = Integer.parseInt(edadField.getText().trim());

                if (userManager.buscarUsuario(correo) != null) {
                    JOptionPane.showMessageDialog(this, "Correo ya registrado.");
                    return;
                }

                String rutaImagen = imagenRutaSeleccionada.isEmpty() ? "default.png" : imagenRutaSeleccionada;
                User nuevo = userManager.registrarUsuario(nombre, correo, clave, edad, rutaImagen);

                controller.setCurrentUser(nuevo);
                new VibeGUI(controller, userManager).setVisible(true);
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Datos inválidos.");
            }
        });
        
JButton volverBtn = new JButton("⬅️ Volver al inicio");
volverBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
volverBtn.addActionListener(e -> {
    dispose(); 
    new StartGUI(controller, userManager).setVisible(true); 
});


        
        leftPanel.add(nombreLabel);    leftPanel.add(nombreField);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(claveLabel);     leftPanel.add(claveField);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(correoLabel);    leftPanel.add(correoField);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(edadLabel);      leftPanel.add(edadField);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(subirFotoBtn);
        leftPanel.add(Box.createVerticalStrut(15));
        leftPanel.add(registrarBtn);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(volverBtn);

        
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(new Color(0x1B264F));

        JLabel logo = new JLabel("LOGO", SwingConstants.CENTER);
        logo.setPreferredSize(new Dimension(200, 200));
        logo.setOpaque(false);
        

        File logoFile = new File("data/logo.png");
        if (logoFile.exists()) {
            ImageIcon icon = new ImageIcon(logoFile.getAbsolutePath());
            Image scaled = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            logo.setIcon(new ImageIcon(scaled));
            logo.setText("");
        }

        rightPanel.add(logo, new GridBagConstraints());

        
        add(leftPanel);
        add(rightPanel);

        setVisible(true);
    }

    private JLabel crearLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        return label;
    }

    private void limitar(JTextField field) {
        field.setMaximumSize(new Dimension(250, 25));
    }
}
