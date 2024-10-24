package com.batsworks;

import com.batsworks.listeners.MatrixCalculator;

import javax.swing.*;

public class MainApplication {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MatrixCalculator::new);
    }
}
