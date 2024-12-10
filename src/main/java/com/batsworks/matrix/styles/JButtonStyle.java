package com.batsworks.matrix.styles;

import javax.swing.*;
import java.awt.*;

public class JButtonStyle {

    public JButtonStyle(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(145, 30));
        button.setHorizontalTextPosition(SwingConstants.CENTER);

        button.setForeground(Color.BLACK); // Fonte preta
        button.setBackground(Color.CYAN); // Fundo ciano
        button.setFocusPainted(false);
        button.setBorder(new RoundedBorder());

        var textLenght = button.getText().length();
        if (textLenght <= 1) {
            button.setPreferredSize(new Dimension(50, 30));
            button.setFont(new Font("Arial", Font.BOLD, 20));
        }
    }

    public JButtonStyle(JButton button, boolean isRounded) {
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setOpaque(true);

        if(isRounded){
            button.setBorder(new RoundedBorder());
        }else{
            button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.BLACK, 2),
                    BorderFactory.createEmptyBorder(2, 2, 2, 2)));
            button.setBackground(Color.ORANGE);
            button.setForeground(Color.BLACK);
            button.setFocusPainted(false);
        }

        var textLenght = button.getText().length();
        if (textLenght <= 1) {
            button.setPreferredSize(new Dimension(50, 30));
            button.setFont(new Font("Arial", Font.BOLD, 20));
        }
    }
}
