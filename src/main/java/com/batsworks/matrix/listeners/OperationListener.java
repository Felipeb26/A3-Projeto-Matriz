package com.batsworks.matrix.listeners;

import com.batsworks.matrix.enums.OperationEnum;
import com.batsworks.matrix.factory.OperationFactory;
import com.batsworks.matrix.utils.Log;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OperationListener implements ActionListener {

    static Log<OperationListener> log = new Log<>(OperationListener.class);
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
            double[][] matA = getMatrixFromInput(matrixA);
            double[][] matB = getMatrixFromInput(matrixB);

            double[][] result = OperationFactory.find(operationEnum).execute(matA, matB, cols, rows);
            displayResult(result);
        } catch (NumberFormatException ex) {
            log.error(ex);
            JOptionPane.showMessageDialog(null, "Por favor, insira apenas números.");
        } catch (Exception ex) {
            log.info(ex);
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    private double[][] getMatrixFromInput(JTextField[][] matrix) throws NumberFormatException {
        double[][] result = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = Double.parseDouble(matrix[i][j].getText());
            }
        }
        return result;
    }

    private void displayResult(double[][] result) {
        StringBuilder sb = new StringBuilder();
        for (double[] row : result) {
            for (double val : row) {
                sb.append(String.format("%.2f", val)).append("\t");
            }
            sb.append("\n");
        }
        resultArea.setText(sb.toString());
    }
}