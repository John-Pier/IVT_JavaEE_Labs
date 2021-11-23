package com.johnpier.lab5.entities;

public class Calculator {
    private static Calculator instance;

    public CalcOperation currentOperation;

    public boolean operationSecondValueDefined = false;

    private Calculator() {}

    public static Calculator getInstance() {
        if(instance != null) {
            return instance;
        }
        return (instance = new Calculator());
    }

    public void setOperation(CalcOperation operation) {
        operationSecondValueDefined = false;
        this.currentOperation = operation;
    }

    public Number getOperationResult(double number) {
        var result = currentOperation.getResult(number);
        resetOperation();
        return result;
    }

    public void resetOperation() {
        this.currentOperation = null;
    }

    public boolean isOperationExist() {
        return currentOperation != null;
    }

    public boolean isOperationSecondValueNotDefined() {
        return !operationSecondValueDefined;
    }

    public boolean setOperationSecondValueIsDefined() {
        return this.operationSecondValueDefined = true;
    }
}
