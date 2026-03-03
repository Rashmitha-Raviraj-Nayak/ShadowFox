import ui.DashboardFrame;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() ->
                new DashboardFrame().setVisible(true)
        );
    }
}
