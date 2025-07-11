import controller.AppController;
import controller.UserManager;
import view.StartGUI;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        AppController controller = new AppController();
        UserManager userManager = new UserManager();

        SwingUtilities.invokeLater(() -> new StartGUI(controller, userManager).setVisible(true));

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (controller.getCurrentUser() != null)
                controller.guardarDatos();
            userManager.guardarUsuarios();
        }));
    }
}
