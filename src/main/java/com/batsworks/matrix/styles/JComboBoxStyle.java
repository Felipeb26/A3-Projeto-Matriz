package com.batsworks.matrix.styles;

import javax.swing.*;
import java.awt.*;

public class JComboBoxStyle {

    public <T> JComboBoxStyle(JComboBox<T> comboBox){
        comboBox.setFont(new Font("Arial", Font.BOLD, 14));
        comboBox.setPreferredSize(new Dimension(80, 30));
        comboBox.setForeground(Color.BLACK); // Fonte preta
        comboBox.setBackground(Color.CYAN); // Fundo ciano
        comboBox.setBorder(new RoundedBorder());
    }
}
