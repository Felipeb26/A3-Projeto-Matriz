package com.batsworks.matrix;

import com.batsworks.matrix.frame.LoadingFrame;

import javax.swing.*;

public class MainApplication {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoadingFrame::new);
    }
}
