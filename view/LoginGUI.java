package view;

import controller.AppController;
import controller.UserManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class LoginGUI extends JFrame {
    public LoginGUI(AppController controller, UserManager userManager) {
        setTitle("Vibe - Login");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 2)); 

        
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(0x1B264F));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(100, 40, 100, 40));

        JLabel userLabel = new JLabel("USER");
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JTextField userField = new JTextField();
        userField.setMaximumSize(new Dimension(200, 25)); 

        JLabel passLabel = new JLabel("PASSWORD");
        passLabel.setForeground(Color.WHITE);
        passLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JPasswordField passField = new JPasswordField();
        passField.setMaximumSize(new Dimension(200, 25)); 

        JButton loginBtn = new JButton("Login");
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginBtn.addActionListener(e -> {
            String correo = userField.getText().trim();
            String clave = new String(passField.getPassword());

            var user = userManager.buscarUsuario(correo);
            if (user != null && user.verificarPassword(clave)) {
                controller.setCurrentUser(user);
                new VibeGUI(controller, userManager).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Credenciales incorrectas");
            }
        });
               
        JButton volverBtn = new JButton("⬅️ Volver al inicio");
        volverBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        volverBtn.addActionListener(e -> {
            dispose();
            new StartGUI(controller, userManager).setVisible(true);
        });


        leftPanel.add(userLabel);
        leftPanel.add(userField);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(passLabel);
        leftPanel.add(passField);
        leftPanel.add(Box.createVerticalStrut(30));
        leftPanel.add(loginBtn);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(volverBtn);


        
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(new Color(0x1B264F));

JPanel logoWrapper = new JPanel();
logoWrapper.setBackground(new Color(0x1B264F));
logoWrapper.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));

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

        logoWrapper.add(logo);
        rightPanel.add(logoWrapper, BorderLayout.CENTER);

        add(leftPanel);
        add(rightPanel);

        setVisible(true);
    }
}
