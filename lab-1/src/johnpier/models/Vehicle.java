package johnpier.models;

import johnpier.exeptions.DuplicateModelNameException;
import johnpier.exeptions.NoSuchModelNameException;

public interface Vehicle {

    public String getVehicleModel();

    public void setVehicleModel(String carModel);

    public void setNameByName(String name, String newName) throws NoSuchModelNameException, DuplicateModelNameException;

    public String[] getModelNames();

    public double getModelPriceByName(String name) throws NoSuchModelNameException;

    public void setModelPriceByName(String name, double price) throws NoSuchModelNameException;

    public double[] getModelPrices();

    public void addModel(String name, double price) throws DuplicateModelNameException;

    public void deleteModel(String name) throws NoSuchModelNameException;

    public int getModelsSize();
}
