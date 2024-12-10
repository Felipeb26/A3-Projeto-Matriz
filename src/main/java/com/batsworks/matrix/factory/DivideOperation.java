package com.batsworks.matrix.factory;

import com.batsworks.matrix.enums.OperationEnum;

public class DivideOperation implements Operation {
    @Override
    public int[][] execute(int[][] matA, int[][] matB, int cols, int rows) {
        int[][] result = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matB[i][j] != 0) {
                    result[i][j] = matA[i][j] / matB[i][j];
                } else {
                    result[i][j] = 0;
                }
            }
        }
        return result;
    }

    @Override
    public boolean use(OperationEnum operationEnum) {
        return OperationEnum.DIVIDE.equals(operationEnum);
    }
}