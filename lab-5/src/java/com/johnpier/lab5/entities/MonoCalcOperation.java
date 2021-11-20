package com.johnpier.lab5.entities;

public abstract class MonoCalcOperation implements CalcOperation {
    Number value;

    public MonoCalcOperation(Number number) {
        value = number;
    }

    public MonoCalcOperation() {}

    @Override
    public double getResult(double number) {
        if (value == null) {
            this.value = number;
        }
        return getResult();
    }

    @Override
    public abstract double getResult();
}
