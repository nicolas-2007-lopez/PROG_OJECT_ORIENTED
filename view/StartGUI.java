package view;

import controller.AppController;
import controller.UserManager;

import javax.swing.*;
import java.awt.*;

public class StartGUI extends JFrame {

    public StartGUI(AppController controller, UserManager userManager) {
        setTitle("Vibe - Bienvenido");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 500); 
        setLocationRelativeTo(null);
        setResizable(false);

        
        Color fondoAzul = new Color(0x1B2A49);
        getContentPane().setBackground(fondoAzul);

        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(fondoAzul);
        add(panel);

        GridBagConstraints gbc = new GridBagConstraints();

        
        JPanel botonesPanel = new JPanel();
        botonesPanel.setLayout(new BoxLayout(botonesPanel, BoxLayout.Y_AXIS));
        botonesPanel.setBackground(fondoAzul);

        JButton loginBtn = new JButton("log in");
        JButton registerBtn = new JButton("Sign up");

        Font fuenteBoton = new Font("Arial", Font.BOLD, 16);
        Dimension tamanoBoton = new Dimension(150, 40);

        for (JButton b : new JButton[]{loginBtn, registerBtn}) {
            b.setFont(fuenteBoton);
            b.setPreferredSize(tamanoBoton);
            b.setMaximumSize(tamanoBoton);
            b.setBackground(Color.WHITE);
            b.setForeground(fondoAzul);
            b.setFocusPainted(false);
            b.setAlignmentX(Component.CENTER_ALIGNMENT);
            botonesPanel.add(b);
            botonesPanel.add(Box.createVerticalStrut(20));
        }

        loginBtn.addActionListener(e -> {
            dispose();
            new LoginGUI(controller, userManager);
        });

        registerBtn.addActionListener(e -> {
            dispose();
            new RegisterGUI(controller, userManager);
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 50, 0, 50);
        panel.add(botonesPanel, gbc);

        
        try {
            ImageIcon logo = new ImageIcon("data/logo.png");
            Image img = logo.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(img));
            gbc.gridx = 1;
            panel.add(logoLabel, gbc);
        } catch (Exception ex) {
            JLabel logoLabel = new JLabel("LOGO");
            logoLabel.setForeground(Color.WHITE);
            logoLabel.setFont(new Font("Arial", Font.PLAIN, 20));
            gbc.gridx = 1;
            panel.add(logoLabel, gbc);
        }

        setVisible(true);
    }
}
