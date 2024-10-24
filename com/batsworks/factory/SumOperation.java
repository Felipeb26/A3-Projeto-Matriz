package com.batsworks.factory;

import com.batsworks.enums.OperationEnum;

public class SumOperation implements Operation {
    @Override
    public int[][] execute(int[][] matA, int[][] matB, int cols, int rows) {
        int[][] result = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = matA[i][j] + matB[i][j];
            }
        }
        return result;
    }

    @Override
    public boolean use(OperationEnum operationEnum) {
        return OperationEnum.SUM.equals(operationEnum);
    }
}
