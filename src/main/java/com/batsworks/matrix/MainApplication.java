package com.batsworks.matrix;

import com.batsworks.matrix.frame.LoadingFrame;
import com.batsworks.matrix.frame.MatrixCalculator;

import javax.swing.*;

public class MainApplication {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MatrixCalculator::new);
//        SwingUtilities.invokeLater(LoadingFrame::new);
    }
}
