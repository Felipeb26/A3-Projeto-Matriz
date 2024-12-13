package com.batsworks.matrix;

import com.batsworks.matrix.frame.MatrixCalculator;

import javax.swing.*;

public class MainApplication {

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(MatrixCalculator::new);
////        SwingUtilities.invokeLater(LoadingFrame::new);
//    }


static class TestArray {
    public static void main(String[] args) {
        int[] numeros = {1, 2, 3, 4, 5, 6, 7, 8};
        int i = 0;
        for(i = 1; i < 8;) {
            if(i > 0 && ++i < 3) {
                System.out.println(numeros[i++]);
            } else if(i < 5) {
                System.out.println(numeros[++i]);
            }
        }
    }
}


}
