package johnpier.models;

import johnpier.exeptions.*;

import java.io.Serializable;

public class Motorcycle extends Vehicle {

    private final Model head = new Model();

    {
        head.prev = head;
        head.next = head;
    }

    public Motorcycle(String motoBrand, int sizeOfModels) {
        super(motoBrand, sizeOfModels);
    }

    public String getVehicleBrand() {
        return brand;
    }

    public void setVehicleBrand(String model) {
        this.brand = model;
    }

    public String[] getModelNames() {
        String[] names = new String[getNonNullSize()];
        Model currentModel = head;

        int count = 0;
        while (currentModel.next != head) {
            names[count] = currentModel.next.name;
            currentModel = currentModel.next;
            count++;
        }

        return names;
    }

    public double getModelPriceByName(String name) throws NoSuchModelNameException {
        Model currentModel = head;
        while (currentModel.next != head) {
            if (currentModel.next.name.equals(name)) {
                return currentModel.next.price;
            }
            currentModel = currentModel.next;
        }
        throw new NoSuchModelNameException("Model with name '" + name + "' not found!");
    }

    public double[] getModelPrices() {
        double[] prices = new double[getNonNullSize()];
        Model currentModel = head;

        int count = 0;
        while (currentModel.next != head) {
            prices[count] = currentModel.next.price;
            currentModel = currentModel.next;
            count++;
        }

        return prices;
    }

    public void addModel(String name, double price) throws DuplicateModelNameException {
        if (price < 0) {
            throw new ModelPriceOutOfBoundsException("Model Price Out Of Bounds!");
        }

        if (isModelNameExist(name)) {
            throw new DuplicateModelNameException("Duplicate model name!");
        }

        Model newModel = new Model(name, price);
        Model lastModel = head.prev;

        lastModel.next = newModel;
        newModel.next = head;
        head.prev = newModel;
        newModel.prev = lastModel;
        if(getNonNullSize() == sizeOfModels) {
            sizeOfModels++;
        }
    }

    private int getNonNullSize() {
        int count = 0;
        Model currentModel = head.next;
        while (currentModel != head) {
            if(currentModel.name != null) {
                count++;
            }
            currentModel = currentModel.next;
        }
        return count;
    }

    public void setNameByName(String name, String newName) throws NoSuchModelNameException, DuplicateModelNameException {
        if (isModelNameExist(newName)) {
            throw new DuplicateModelNameException("Duplicate model name!");
        }
        Model currentModel = head;
        while (currentModel.next != head) {
            if (currentModel.next.name.equals(name)) {
                currentModel.next.name = newName;
                return;
            }
            currentModel = currentModel.next;
        }
        throw new NoSuchModelNameException("Model with name '" + name + "' not found!");
    }

    private boolean isModelNameExist(String name) {
        Model currentModel = head;
        while (currentModel.next != head) {
            if (currentModel.next.name.equals(name)) {
                 return true;
            }
            currentModel = currentModel.next;
        }
        return false;
    }

    public void deleteModel(String name) throws NoSuchModelNameException {
        Model currentModel = head;
        while (currentModel.next != head) {
            if (currentModel.next.name.equals(name)) {
                currentModel.next.prev = currentModel.prev;
                currentModel.prev.next = currentModel.next;
                sizeOfModels--;
                return;
            }
            currentModel = currentModel.next;
        }
        throw new NoSuchModelNameException("Model with name '" + name + "' not found!");
    }

    public void setModelPriceByName(String name, double price) throws NoSuchModelNameException {
        if (price < 0) {
            throw new ModelPriceOutOfBoundsException("Model Price Out Of Bounds!");
        }
        Model lastModel = head.prev;
        while (lastModel.next != head) {
            if (lastModel.next.name.equals(name)) {
                lastModel.next.setPrice(price);
                return;
            }
            lastModel = lastModel.next;
        }
        throw new NoSuchModelNameException("Model with name '" + name + "' not found!");
    }

    public int getModelsSize() {
        return this.getNonNullSize();
    }

    private class Model implements Serializable {

        private String name = null;
        private double price = Double.NaN;
        private Model prev = null;
        private Model next = null;

        public Model() {
        }

        public Model(String name, double price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public Model getPrev() {
            return prev;
        }

        public void setPrev(Model prev) {
            this.prev = prev;
        }

        public Model getNext() {
            return next;
        }

        public void setNext(Model next) {
            this.next = next;
        }
    }
}
