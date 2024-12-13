package com.batsworks.matrix.factory;

import com.batsworks.matrix.enums.OperationEnum;

public class MultiplyOperation implements Operation{
    @Override
    public double[][] execute(double[][] matA, double[][] matB, int cols, int rows) {
        double[][] result = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = 0;
                for (int k = 0; k < cols; k++) {
                    result[i][j] += matA[i][k] * matB[k][j];
                }
            }
        }
        return result;
    }

    @Override
    public boolean use(OperationEnum operationEnum) {
        return OperationEnum.MULTIPLY.equals(operationEnum);
    }
}
