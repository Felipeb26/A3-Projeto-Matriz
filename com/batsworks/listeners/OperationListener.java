package com.batsworks.listeners;

import com.batsworks.enums.OperationEnum;
import com.batsworks.factory.OperationFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OperationListener implements ActionListener {

    OperationFactory factory = new OperationFactory();
    private final OperationEnum operationEnum;
    private final JTextField[][] matrixA;
    private final JTextField[][] matrixB;
    private final JTextArea resultArea;
    private final int cols;
    private final int rows;

    public OperationListener(JTextField[][] matrixA, JTextField[][] matrixB, JTextArea area, int colunas, int linhas, OperationEnum operationEnum) {
        this.operationEnum = operationEnum;
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.resultArea = area;
        this.cols = colunas;
        this.rows = linhas;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int[][] matA = getMatrixFromInput(matrixA);
            int[][] matB = getMatrixFromInput(matrixB);

            var operation = factory.find(operationEnum);

            int[][] result = operation.execute(matA, matB, cols, rows);
            displayResult(result);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Por favor, insira apenas números.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    private int[][] getMatrixFromInput(JTextField[][] matrix) throws NumberFormatException {
        int[][] result = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = Integer.parseInt(matrix[i][j].getText());
            }
        }
        return result;
    }

    private void displayResult(int[][] result) {
        StringBuilder sb = new StringBuilder();
        for (int[] row : result) {
            for (int val : row) {
                sb.append(val).append("\t");
            }
            sb.append("\n");
        }
        resultArea.setText(sb.toString());
    }
}