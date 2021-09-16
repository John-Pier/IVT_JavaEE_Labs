package johnpier.models;

import johnpier.exeptions.DuplicateModelNameException;
import johnpier.exeptions.NoSuchModelNameException;

public interface Vehicle {

    String getVehicleBrand();

    void setVehicleBrand(String carModel);

    void setNameByName(String name, String newName) throws NoSuchModelNameException, DuplicateModelNameException;

    String[] getModelNames();

    double getModelPriceByName(String name) throws NoSuchModelNameException;

    void setModelPriceByName(String name, double price) throws NoSuchModelNameException;

    double[] getModelPrices();

    void addModel(String name, double price) throws DuplicateModelNameException;

    void deleteModel(String name) throws NoSuchModelNameException;

    int getModelsSize();
}
