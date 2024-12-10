package com.batsworks.matrix.styles;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class JTextFieldStyle {

    public JTextFieldStyle(JTextField jTextField) {
        style(jTextField);
    }

    public JTextFieldStyle(JTextField[][] jTextFields) {
        Arrays.stream(jTextFields).forEach(jTextField -> Arrays.stream(jTextField).forEach(this::style));
    }

    private void style(JTextField jTextField) {
        jTextField.setFont(new Font("Arial", Font.BOLD, 16));
        jTextField.setPreferredSize(new Dimension(50, 30));
        jTextField.setHorizontalAlignment(JTextField.CENTER);
        jTextField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
    }
}
