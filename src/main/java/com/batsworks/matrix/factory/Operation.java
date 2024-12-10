package com.batsworks.matrix.factory;

import com.batsworks.matrix.enums.OperationEnum;

public interface Operation {
    int[][] execute(int[][] matA, int[][] matB, int cols, int rows);

    boolean use(OperationEnum operationEnum);
}
