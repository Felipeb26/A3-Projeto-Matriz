package com.batsworks.factory;

import com.batsworks.enums.OperationEnum;

public class MultiplyOperation implements Operation{
    @Override
    public int[][] execute(int[][] matA, int[][] matB, int cols, int rows) {
        int[][] result = new int[rows][cols];
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
