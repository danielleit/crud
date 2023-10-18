import javax.swing.SwingUtilities;

import views.CadastroCelular;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CadastroCelular.criarGUI();
        });
    }
}
