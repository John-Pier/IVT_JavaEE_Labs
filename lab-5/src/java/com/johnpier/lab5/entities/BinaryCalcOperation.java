package com.johnpier.lab5.entities;

public abstract class BinaryCalcOperation implements CalcOperation {
    protected Number firstValue;
    protected Number secondValue;

    public BinaryCalcOperation(Number number) {
        firstValue = number;
    }

    @Override
    public double getResult(double number) {
        if (secondValue == null) {
            secondValue = number;
        }
        return getResult();
    }

    @Override
    public abstract double getResult();
}
