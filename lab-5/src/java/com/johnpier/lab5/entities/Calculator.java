package com.johnpier.lab5.entities;

public class Calculator {
    private static Calculator instance;

    public CalcOperation currentOperation;

    private Calculator() {}

    public static Calculator getInstance() {
        if(instance != null) {
            return instance;
        }
        return (instance = new Calculator());
    }

    public void setOperation(CalcOperation operation) {
        this.currentOperation = operation;
    }

    public Number getOperationResult() {
        return currentOperation.getResult();
    }

    public Number getOperationResult(double number) {
        return currentOperation.getResult(number);
    }
}
