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
        var result = currentOperation.getResult();
        resetOperation();
        return result;
    }

    public Number getOperationResult(double number) {
        var result = currentOperation.getResult(number);
        resetOperation();
        return result;
    }

    public void resetOperation() {
        this.currentOperation = null;
    }
}
