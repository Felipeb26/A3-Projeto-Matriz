package com.batsworks.styles;

import javax.swing.*;
import java.awt.*;

public class JButtonStyle {

    public JButtonStyle(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(2, 2, 2, 2)));
        button.setBackground(Color.ORANGE);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);

        var textLenght = button.getText().length();
        if (textLenght <= 1) {
            button.setPreferredSize(new Dimension(50, 30));
            button.setFont(new Font("Arial", Font.BOLD, 20));
        }
    }
}
