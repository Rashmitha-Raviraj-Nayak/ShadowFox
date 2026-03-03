package ui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class ModernButton extends JButton {
    private Color baseColor;
    private Color hoverColor;

    public ModernButton(String text) {
        super(text);
        styleButton();
    }

    private void styleButton() {
        setFocusPainted(false);
        setFont(new Font("Segoe UI", Font.BOLD, 13));
        setForeground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setContentAreaFilled(true);
        setOpaque(true);
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (hoverColor != null) {
                    setBackground(hoverColor);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (baseColor != null) {
                    setBackground(baseColor);
                }
            }
        });
    }

    public void setButtonStyle(Color bgColor) {
        this.baseColor = bgColor;
        this.hoverColor = brighten(bgColor, 20);
        setBackground(bgColor);
    }

    private Color brighten(Color color, int amount) {
        int r = Math.min(255, color.getRed() + amount);
        int g = Math.min(255, color.getGreen() + amount);
        int b = Math.min(255, color.getBlue() + amount);
        return new Color(r, g, b);
    }
}
