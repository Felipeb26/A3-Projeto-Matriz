package com.batsworks.factory;

import com.batsworks.enums.OperationEnum;

import java.util.List;

public class OperationFactory {

    private final List<Operation> operacoes;

    public OperationFactory(){
        this.operacoes = List.of(new MultiplyOperation(), new DivideOperation(), new SubtractOperation(), new SubtractOperation());
    }

    public Operation find(OperationEnum operationEnum){
        return operacoes.stream().filter(operation -> operation.use(operationEnum))
                .findFirst().orElseThrow();
    }

}
