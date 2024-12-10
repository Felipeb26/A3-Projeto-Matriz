package com.batsworks.matrix.enums;

public enum OperationEnum {
    SUM("sum", "+"),
    SUBTRACT("subtract", "-"),
    MULTIPLY("multiply", "x"),
    DIVIDE("divide", "÷");

    OperationEnum(String operation, String simbolo) {
        this.operation = operation;
        this.simbolo = simbolo;
    }

    private final String operation;
    private final String simbolo;

    public String getOperation() {
        return this.operation;
    }

    public String getSimbolo() {
        return simbolo;
    }
}
