package view;

import controller.AppController;
import controller.UserManager;
import model.Post;
import model.Reply;
import model.User;
import model.interfaces.Tuit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.List;

public class VibeGUI extends JFrame {

    private AppController controller;
    private JTextArea tweetInput;
    private JTextField linkInput;
    private JPanel feedPanel;
    private String selectedImagePath = "";
    private boolean mostrandoFeedGlobal = true;
    private UserManager userManager;

    public VibeGUI(AppController controller, UserManager userManager) {
        this.controller = controller;
        this.userManager = userManager;
        initUI();
    }

    private void initUI() {
        setTitle("Vibe");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        Color fondoAzul = new Color(0x1B264F);
        getContentPane().setBackground(fondoAzul);

        
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(fondoAzul);
        leftPanel.setPreferredSize(new Dimension(150, 0));

        JButton btnInicio = new JButton("Inicio");
        JButton btnMisTuits = new JButton("Mis Tuits");
        JButton perfilButton = new JButton("Ver perfil");

        for (JButton b : new JButton[]{btnInicio, btnMisTuits, perfilButton}) {
            b.setAlignmentX(Component.CENTER_ALIGNMENT);
            b.setMaximumSize(new Dimension(130, 40));
            b.setBackground(Color.WHITE);
            b.setForeground(fondoAzul);
            b.setFocusPainted(false);
            leftPanel.add(Box.createVerticalStrut(20));
            leftPanel.add(b);
        }

        btnInicio.addActionListener(e -> {
            mostrandoFeedGlobal = true;
            showFeed();
        });

        btnMisTuits.addActionListener(e -> {
            mostrandoFeedGlobal = false;
            showFeed();
        });

        perfilButton.addActionListener(e -> mostrarPerfil());

        add(leftPanel, BorderLayout.WEST);
        // Panel superior (publicación de tuit)
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setBackground(fondoAzul);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel logoLabel = new JLabel();
        logoLabel.setPreferredSize(new Dimension(100, 100)); // tamaño del logo
        File logoFile = new File("data/logo.png");
    if (logoFile.exists()) {
        ImageIcon icon = new ImageIcon(logoFile.getAbsolutePath());
        Image scaled = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        logoLabel.setIcon(new ImageIcon(scaled));
}
topPanel.add(logoLabel, BorderLayout.WEST);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBackground(fondoAzul);

        tweetInput = new JTextArea(3, 40);
        tweetInput.setLineWrap(true);
        tweetInput.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(tweetInput);
        scroll.setMaximumSize(new Dimension(2800, 60));
        inputPanel.add(scroll);
        inputPanel.add(Box.createVerticalStrut(5));

        linkInput = new JTextField();
        linkInput.setMaximumSize(new Dimension(500, 25));
        inputPanel.add(linkInput);
        inputPanel.add(Box.createVerticalStrut(5));

        JButton imageButton = new JButton("Subir Imagen");
        imageButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        imageButton.setBackground(Color.WHITE);
        imageButton.setForeground(fondoAzul);
        imageButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int opcion = chooser.showOpenDialog(this);
            if (opcion == JFileChooser.APPROVE_OPTION) {
                selectedImagePath = chooser.getSelectedFile().getAbsolutePath();
                JOptionPane.showMessageDialog(this, "📸 Imagen seleccionada.");
            }
        });
        inputPanel.add(imageButton);

        JButton postButton = new JButton("Publicar");
        postButton.setBackground(Color.WHITE);
        postButton.setForeground(fondoAzul);
        postButton.addActionListener(e -> {
            String content = tweetInput.getText().trim();
            String link = linkInput.getText().trim();
            String media = selectedImagePath;

            if (!content.isEmpty() && content.length() <= 280) {
                controller.postTuit(content, media, link);
                tweetInput.setText("");
                linkInput.setText("");
                selectedImagePath = "";
                showFeed();
            } else {
                JOptionPane.showMessageDialog(this, "El tuit debe tener entre 1 y 280 caracteres.");
            }
        });

        topPanel.add(inputPanel, BorderLayout.CENTER);
        topPanel.add(postButton, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        
        feedPanel = new JPanel();
        feedPanel.setLayout(new BoxLayout(feedPanel, BoxLayout.Y_AXIS));
        feedPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(feedPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);

        showFeed();
    }


private JPanel crearPanelTuit(Tuit tuit, boolean esRespuesta) {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 400));
    panel.setBackground(Color.WHITE);
    panel.setAlignmentX(Component.LEFT_ALIGNMENT);

    int indent = esRespuesta ? 30 : 0;
    panel.setBorder(BorderFactory.createEmptyBorder(5, indent + 5, 5, 5));

    
    JPanel contenidoPanel = new JPanel();
    contenidoPanel.setLayout(new BoxLayout(contenidoPanel, BoxLayout.Y_AXIS));
    contenidoPanel.setBackground(Color.WHITE);
    contenidoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    String fechaFormateada = sdf.format(tuit.getDate());

    String texto = "<html><b>" + tuit.getAuthor().getName() + "</b>: " +
            tuit.getContent() +
            "<br><span style='font-size:10px; color:gray;'>" +
            fechaFormateada + "</span></html>";

    JLabel contentLabel = new JLabel(texto);
    contentLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
    contentLabel.setForeground(Color.BLACK);
    contentLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    contentLabel.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
            User autor = tuit.getAuthor();
            if (!autor.equals(controller.getCurrentUser())) {
                mostrarPerfilDeOtro(autor);
            } else {
                mostrarPerfil();
            }
        }
    });
    contenidoPanel.add(contentLabel);

    if (tuit instanceof Post post && !post.getLink().isEmpty()) {
        JLabel linkLabel = new JLabel("<html><a href=\"" + post.getLink() + "\">" + post.getLink() + "</a></html>");
        linkLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        linkLabel.setForeground(Color.BLUE);
        linkLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        linkLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(post.getLink()));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "No se pudo abrir el enlace.");
                }
            }
        });
        contenidoPanel.add(linkLabel);
    }

    if (tuit instanceof Post post && !post.getMedia().isEmpty()) {
        try {
            ImageIcon imagen = new ImageIcon(post.getMedia());
            Image original = imagen.getImage();
            int maxAncho = 320;
            int maxAlto = 240;
            Image escalada = original.getScaledInstance(maxAncho, maxAlto, Image.SCALE_SMOOTH);
            JLabel imagenLabel = new JLabel(new ImageIcon(escalada));
            imagenLabel.setPreferredSize(new Dimension(maxAncho, maxAlto));
            imagenLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            imagenLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
            contenidoPanel.add(imagenLabel);
        } catch (Exception e) {
            System.out.println("⚠️ No se pudo cargar la imagen del tuit.");
        }
    }

    JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
    bottom.setAlignmentX(Component.LEFT_ALIGNMENT);
    JLabel likesLabel = new JLabel("❤️ " + tuit.getLikes());
    JButton likeButton = new JButton("Like");
    likeButton.addActionListener(e -> {
        controller.getCurrentUser().likeTuit(tuit);
        showFeed();
    });
    bottom.add(likesLabel);
    bottom.add(likeButton);

    if (!esRespuesta) {
        JButton replyButton = new JButton("Responder");
        replyButton.addActionListener(e -> responderATuit(tuit));
        bottom.add(replyButton);
    }

    if (tuit.getAuthor().getEmail().equals(controller.getCurrentUser().getEmail())) {
        JButton deleteButton = new JButton("Eliminar");
        deleteButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar este tuit?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                controller.eliminarTuit(tuit);
                showFeed();
            }
        });
        bottom.add(deleteButton);
    }

    contenidoPanel.add(bottom);
    panel.add(contenidoPanel);

    return panel;
}

private void responderATuit(Tuit tuitOriginal) {
    String respuesta = JOptionPane.showInputDialog(this, "Escribe tu respuesta:");
    if (respuesta != null && !respuesta.trim().isEmpty()) {
        controller.replyToTuit(tuitOriginal, respuesta.trim());
        showFeed();
    }
}
private void mostrarPerfil() {
    feedPanel.removeAll();

    var user = controller.getCurrentUser();

    JLabel titulo = new JLabel("👤 Perfil de " + user.getName());
    titulo.setFont(new Font("Arial", Font.BOLD, 18));
    titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

    JLabel nombre = new JLabel("🧑 Nombre: " + user.getName());
    JLabel correo = new JLabel("📧 Correo: " + user.getEmail());
    JLabel edad = new JLabel("🎂 Edad: " + user.getAge());
    JLabel seguidores = new JLabel("👥 Seguidores: " + user.getFollowers().size());
    JLabel siguiendo = new JLabel("➡️ Siguiendo: " + user.getFollowing().size());
    JLabel totalTuits = new JLabel("📝 Tuits: " + user.getHistory().size());

    JLabel imagenPerfil;
try {
    String rutaFoto = user.getProfilePicture(); 
    ImageIcon icono = new ImageIcon(rutaFoto);
    Image imagenEscalada = icono.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
    imagenPerfil = new JLabel(new ImageIcon(imagenEscalada));
} catch (Exception ex) {
    imagenPerfil = new JLabel("Sin imagen");
}

    JPanel perfilPanel = new JPanel();
    perfilPanel.setLayout(new BoxLayout(perfilPanel, BoxLayout.Y_AXIS));
    perfilPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

    perfilPanel.add(imagenPerfil);
    perfilPanel.add(Box.createVerticalStrut(10));
    perfilPanel.add(titulo);
    perfilPanel.add(nombre);
    perfilPanel.add(correo);
    perfilPanel.add(edad);
    perfilPanel.add(seguidores);
    perfilPanel.add(siguiendo);
    perfilPanel.add(totalTuits);

    JButton logoutButton = new JButton("Cerrar sesión");
    logoutButton.addActionListener(e -> {
        controller.guardarDatos();
        dispose();
        new LoginGUI(controller, userManager);
    });

    perfilPanel.add(Box.createVerticalStrut(20));
    perfilPanel.add(logoutButton);

    feedPanel.add(perfilPanel);
    feedPanel.revalidate();
    feedPanel.repaint();
}

private void mostrarPerfilDeOtro(User usuario) {
    feedPanel.removeAll();

    JLabel titulo = new JLabel("👤 Perfil de " + usuario.getName());
    titulo.setFont(new Font("Arial", Font.BOLD, 18));
    titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

    JLabel nombre = new JLabel("🧑 Nombre: " + usuario.getName());
    JLabel correo = new JLabel("📧 Correo: " + usuario.getEmail());
    JLabel edad = new JLabel("🎂 Edad: " + usuario.getAge());
    JLabel seguidores = new JLabel("👥 Seguidores: " + usuario.getFollowers().size());
    JLabel siguiendo = new JLabel("➡️ Siguiendo: " + usuario.getFollowing().size());
    JLabel totalTuits = new JLabel("📝 Tuits: " + usuario.getHistory().size());

    JLabel imagenPerfil;
    try {
        String rutaFoto = "data/" + usuario.getProfilePicture();
        ImageIcon icono = new ImageIcon(rutaFoto);
        Image imagenEscalada = icono.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        imagenPerfil = new JLabel(new ImageIcon(imagenEscalada));
    } catch (Exception ex) {
        imagenPerfil = new JLabel("Sin imagen");
    }

    JButton seguirButton = new JButton(
        controller.getCurrentUser().getFollowing().contains(usuario) ? "Dejar de seguir" : "Seguir"
    );
    seguirButton.addActionListener(e -> {
        if (controller.getCurrentUser().getFollowing().contains(usuario)) {
            controller.getCurrentUser().unfollowUser(usuario);
            seguirButton.setText("Seguir");
        } else {
            controller.getCurrentUser().followUser(usuario);
            seguirButton.setText("Dejar de seguir");
        }
        userManager.guardarUsuarios();
    });

    JButton volverButton = new JButton("⬅️ Volver");
    volverButton.addActionListener(e -> showFeed());

    JPanel perfilPanel = new JPanel();
    perfilPanel.setLayout(new BoxLayout(perfilPanel, BoxLayout.Y_AXIS));
    perfilPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    perfilPanel.add(imagenPerfil);
    perfilPanel.add(Box.createVerticalStrut(10));
    perfilPanel.add(titulo);
    perfilPanel.add(nombre);
    perfilPanel.add(correo);
    perfilPanel.add(edad);
    perfilPanel.add(seguidores);
    perfilPanel.add(siguiendo);
    perfilPanel.add(totalTuits);
    perfilPanel.add(Box.createVerticalStrut(10));
    perfilPanel.add(seguirButton);
    perfilPanel.add(Box.createVerticalStrut(10));
    perfilPanel.add(volverButton);

    feedPanel.add(perfilPanel);
    feedPanel.revalidate();
    feedPanel.repaint();
}

private void showFeed() {
    feedPanel.removeAll();

    List<Tuit> tuits = mostrandoFeedGlobal ?
        controller.getFeedGlobal() :
        controller.getFeedDelUsuario();

    tuits.sort((a, b) -> b.getDate().compareTo(a.getDate()));

    for (Tuit t : tuits) {
        if (t instanceof Post post) {
            JPanel postPanel = crearPanelTuit(post, false);
            feedPanel.add(postPanel);

            for (Tuit r : tuits) {
                if (r instanceof Reply reply && reply.getReplyTo() == t) {
                    JPanel replyPanel = crearPanelTuit(reply, true);
                    feedPanel.add(replyPanel);
                }
            }
        }
    }

    feedPanel.revalidate();
    feedPanel.repaint();
}
}
