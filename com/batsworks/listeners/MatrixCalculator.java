package com.batsworks.listeners;

import com.batsworks.enums.OperationEnum;
import com.batsworks.styles.JButtonStyle;
import com.batsworks.styles.JTextFieldStyle;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.util.List;

public class MatrixCalculator extends JFrame {

    private final JTextField[][] matrixA;
    private final JTextField[][] matrixB;
    private final JTextArea resultArea;
    private final int rows = 3;
    private final int cols = 3;

    public MatrixCalculator() {
        setFont(new Font("Monospaced", Font.PLAIN, 14));
        setTitle("Calculadora de Matrizes");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Painel superior onde colocaremos as matrizes A e B
        JPanel matricesPanel = new JPanel(new GridLayout(1, 2, 10, 10));

        // Inicializar as entradas das matrizes A e B
        matrixA = new JTextField[rows][cols];
        matrixB = new JTextField[rows][cols];

        matricesPanel.add(createMatrixPanel(matrixA, "Matriz A"));
        matricesPanel.add(createMatrixPanel(matrixB, "Matriz B"));

        new JTextFieldStyle(matrixA);
        new JTextFieldStyle(matrixB);
        add(matricesPanel, BorderLayout.NORTH);

        // Painel central para botões e resultados
        JPanel centerPanel = new JPanel(new BorderLayout());
        // Painel de botões
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton sumButton = new JButton(OperationEnum.SUM.getSimbolo());
        JButton subtractButton = new JButton(OperationEnum.SUBTRACT.getSimbolo());
        JButton multiplyButton = new JButton(OperationEnum.MULTIPLY.getSimbolo());
        JButton divideButton = new JButton(OperationEnum.DIVIDE.getSimbolo());
        JButton clearButton = new JButton("Limpar Matrizes");
        JButton copyButton = new JButton("Copiar Resultados");

        var buttons = List.of(sumButton, subtractButton, multiplyButton, divideButton, clearButton, copyButton);
        buttons.forEach(JButtonStyle::new);
        buttons.forEach(buttonsPanel::add);

        setButtonsOperation(sumButton, OperationEnum.SUM);
        setButtonsOperation(subtractButton, OperationEnum.SUBTRACT);
        setButtonsOperation(multiplyButton, OperationEnum.MULTIPLY);
        setButtonsOperation(divideButton, OperationEnum.DIVIDE);

        clearButton.addActionListener(this::clearMatrices);
        copyButton.addActionListener(this::copyResults);

        centerPanel.add(buttonsPanel, BorderLayout.NORTH);

        resultArea = new JTextArea(5, 30);
        resultArea.setEditable(false);
        resultArea.setBorder(BorderFactory.createTitledBorder("Resultados"));
        JScrollPane scrollPane = new JScrollPane(resultArea);

        centerPanel.add(scrollPane, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setButtonsOperation(JButton button, OperationEnum operationEnum) {
        button.addActionListener(action -> new OperationListener(matrixA, matrixB, resultArea, cols, rows, operationEnum).actionPerformed(action));
    }



    private JPanel createMatrixPanel(JTextField[][] matrix, String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(rows, cols));
        panel.setBorder(BorderFactory.createTitledBorder(title));

        // Criar campos de entrada para cada célula da matriz
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = new JTextField("0", 3);
                panel.add(matrix[i][j]);
            }
        }
        return panel;
    }

    private void clearMatrices(ActionEvent actionEvent) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrixA[i][j].setText("0");
                matrixB[i][j].setText("0");
            }
        }
        resultArea.setText("");
    }

    private void copyResults(ActionEvent actionEvent) {
        String results = resultArea.getText();
        StringSelection selection = new StringSelection(results);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
        JOptionPane.showMessageDialog(null, "Resultados copiados para a área de transferência!");
    }

}
