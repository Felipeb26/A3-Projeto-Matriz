package com.batsworks.factory;

import com.batsworks.enums.OperationEnum;

public interface Operation {
    int[][] execute(int[][] matA, int[][] matB, int cols, int rows);

    boolean use(OperationEnum operationEnum);
}
