package com.batsworks.matrix.factory;

import com.batsworks.matrix.enums.OperationEnum;
import com.batsworks.matrix.listeners.OperationListener;
import com.batsworks.matrix.utils.Log;

import java.util.List;

public class OperationFactory {

    static Log<OperationFactory> log = new Log<>(OperationFactory.class);

    private static final List<Operation> operacoes = List.of(new MultiplyOperation(), new DivideOperation(), new SumOperation(), new SubtractOperation());

    public static Operation find(OperationEnum operationEnum){
        log.info("Operacao escolhida ".concat(operationEnum.getOperation()));
        return operacoes.stream().filter(operation -> operation.use(operationEnum))
                .findFirst().orElseThrow();
    }

}
