import ui.LoginUI;

import javax.swing.*;

public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginUI::new);
    }
}