package com.batsworks.matrix.factory;

import com.batsworks.matrix.enums.OperationEnum;

public interface Operation {
    double[][] execute(double[][] matA, double[][] matB, int cols, int rows);

    boolean use(OperationEnum operationEnum);
}
