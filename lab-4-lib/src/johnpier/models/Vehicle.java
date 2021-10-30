package johnpier.models;

import johnpier.exeptions.*;

import java.io.Serializable;

public abstract class Vehicle implements Serializable {
    protected String brand;
    protected int sizeOfModels;

    public Vehicle(String brand, int sizeOfModels){
        this.brand = brand;
        this.sizeOfModels = sizeOfModels;
    }

    public abstract String getVehicleBrand();

    public abstract void setVehicleBrand(String carModel);

    public abstract void setNameByName(String name, String newName) throws NoSuchModelNameException, DuplicateModelNameException;

    public abstract String[] getModelNames();

    public abstract double getModelPriceByName(String name) throws NoSuchModelNameException;

    public abstract void setModelPriceByName(String name, double price) throws NoSuchModelNameException;

    public abstract double[] getModelPrices();

    public abstract void addModel(String name, double price) throws DuplicateModelNameException;

    public abstract void deleteModel(String name) throws NoSuchModelNameException;

    public abstract int getModelsSize();
}
