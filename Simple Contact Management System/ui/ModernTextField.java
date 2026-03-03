package ui;

import java.awt.*;
import javax.swing.*;

public class ModernTextField extends JTextField {
    public ModernTextField(int columns) {
        super(columns);
        styleField();
    }

    public ModernTextField() {
        super(25);
        styleField();
    }

    private void styleField() {
        setFont(new Font("Segoe UI", Font.PLAIN, 13));
        setMargin(new Insets(8, 10, 8, 10));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(4, 4, 4, 4)
        ));
        setBackground(new Color(250, 250, 250));
    }
}
