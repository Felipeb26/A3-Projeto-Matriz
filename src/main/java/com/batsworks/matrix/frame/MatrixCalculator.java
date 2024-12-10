package com.batsworks.matrix.frame;

import com.batsworks.matrix.enums.OperationEnum;
import com.batsworks.matrix.listeners.OperationListener;
import com.batsworks.matrix.styles.JButtonStyle;
import com.batsworks.matrix.styles.JComboBoxStyle;
import com.batsworks.matrix.styles.JTextFieldStyle;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.border.EmptyBorder;

import static java.util.Objects.isNull;

public class MatrixCalculator extends JFrame {

    private JTextField[][] matrixAFields;
    private JTextField[][] matrixBFields;
    private final JTextArea resultArea;
    private static final int ROWS = 3;
    private static final int COLS = 3;
    private static final String[] SIZES = new String[]{"2x2","3x3","4x4", "5x5", "6x6"};

    public MatrixCalculator() {
        setFont(new Font("Monospaced", Font.PLAIN, 14));
        setTitle("Calculadora de Matrizes");
        setMinimumSize(new Dimension(650, 550));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout(10, 10));

        // Painel superior onde colocaremos as matrizes A e B
        JPanel matricesPanel = new JPanel(new GridLayout(1, 2, 10, 10));

        // Inicializar as entradas das matrizes A e B
        matrixAFields = new JTextField[ROWS][COLS];
        matrixBFields = new JTextField[ROWS][COLS];

        var matrixA = createMatrixPanel(matrixAFields, "Matriz A");
        var matrixB = createMatrixPanel(matrixBFields, "Matriz B");
        List<JPanel> matrizes = List.of(matrixA, matrixB);
        matrizes.forEach(matricesPanel::add);

        getContentPane().add(matricesPanel, BorderLayout.NORTH);

        // Painel central para botões e resultados
        JPanel centerPanel = new JPanel(new BorderLayout());
        // Painel de botões
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        JButton sumButton = new JButton(OperationEnum.SUM.getSimbolo());
        JButton subtractButton = new JButton(OperationEnum.SUBTRACT.getSimbolo());
        JButton multiplyButton = new JButton(OperationEnum.MULTIPLY.getSimbolo());
        JButton divideButton = new JButton(OperationEnum.DIVIDE.getSimbolo());
        JButton clearButton = new JButton("Limpar Matrizes");
        JButton copyButton = new JButton("Copiar Resultados");


        JComboBox<String> matrixSize = new JComboBox<>(SIZES);
        new JComboBoxStyle(matrixSize);
        createComboBox(matrixSize, matrizes);

        var buttons = List.of(sumButton, subtractButton, multiplyButton, divideButton, clearButton, copyButton);
        buttons.forEach(JButtonStyle::new);
        buttons.forEach(buttonsPanel::add);
        
        buttonsPanel.add(matrixSize);        

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
        scrollPane.setBackground(new Color(0x279696));
        scrollPane.setViewportBorder(new EmptyBorder(5, 10, 10, 10));

        centerPanel.add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(centerPanel, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setButtonsOperation(JButton button, OperationEnum operationEnum) {
        button.addActionListener(action -> new OperationListener(this.matrixAFields, this.matrixBFields, resultArea, COLS, ROWS, operationEnum).actionPerformed(action));
    }

    private JPanel createMatrixPanel(JTextField[][] matrix,  String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(ROWS, COLS));
        panel.setBorder(BorderFactory.createTitledBorder(title));

        // Criar campos de entrada para cada célula da matriz
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                matrix[i][j] = new JTextField("0", 3);
                new JTextFieldStyle(matrix[i][j]);
                panel.add(matrix[i][j]);
            }
        }
        return panel;
    }

    private void clearMatrices(ActionEvent actionEvent) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                matrixAFields[i][j].setText("0");
                matrixBFields[i][j].setText("0");
            }
        }
        resultArea.setText("");
    }

    private void createComboBox(JComboBox<String> comboBox, List<JPanel> matrizes) {
        comboBox.setMaximumSize(new Dimension(Short.MAX_VALUE, 30)); // Tamanho máximo
        comboBox.setMinimumSize(new Dimension(Integer.MAX_VALUE, 40)); // Tamanho mínimo
        comboBox.addActionListener(e -> {
            String selected = (String) comboBox.getSelectedItem();
            int size =  isNull(selected) ? 2 : Integer.parseInt(selected.substring(0, 1));
            matrizes.forEach(matrix -> updateGrid(matrix, size));
        });
    }

    private void updateGrid(JPanel panel, int size) {
        panel.removeAll(); // Remove todos os componentes atuais
        panel.setLayout(new GridLayout(size, size, 5, 5)); // Define o novo layout

        for (int i = 0; i < size * size; i++) {
            var field = new JTextField("0");
            new JTextFieldStyle(field);
            panel.add(field);
        }

        panel.revalidate();
        panel.repaint();
    }

    private void copyResults(ActionEvent actionEvent) {
        String results = resultArea.getText();
        StringSelection selection = new StringSelection(results);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
        JOptionPane.showMessageDialog(null, "Resultados copiados para a área de transferência!");
    }

}