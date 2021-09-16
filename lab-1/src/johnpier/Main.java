package johnpier;

import johnpier.exeptions.DuplicateModelNameException;
import johnpier.exeptions.ModelPriceOutOfBoundsException;
import johnpier.exeptions.NoSuchModelNameException;
import johnpier.models.Car;
import johnpier.models.Motorcycle;
import johnpier.models.Vehicle;
import johnpier.untils.VehicleHelper;

public class Main {
    private static final String TERMINATOR_STRING = "--------------------";

    public static void main(String[] args) {
        try {
           testModels();
        } catch (NoSuchModelNameException | DuplicateModelNameException | ModelPriceOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void testModels() throws NoSuchModelNameException, DuplicateModelNameException, ModelPriceOutOfBoundsException {
        Vehicle carVehicle = new Car("Super Auto Brand", 0);
        Vehicle motoVehicle = new Motorcycle("Super Moto Brand", 0);

        System.out.println("Vehicle 1 Brand: ");
        System.out.println(carVehicle.getVehicleBrand());
        System.out.println("Vehicle 2 Brand: ");
        System.out.println(motoVehicle.getVehicleBrand());

        System.out.println(TERMINATOR_STRING);

        String testDeleteName = "1118";
        String testNameModel = "1119";

        addCarModels(carVehicle);
        addMotoModels(motoVehicle);

        System.out.println("Print Vehicle 1");
        System.out.println("Auto Names:");
        printNames(carVehicle);
        System.out.println("Auto Prices:");
        printPrices(carVehicle);
        System.out.println("Models Size:");
        printSizeModels(carVehicle);

        System.out.println(TERMINATOR_STRING);

        System.out.println("Print Vehicle 2");
        System.out.println("Moto Names:");
        printNames(motoVehicle);
        System.out.println("Moto Prices:");
        printPrices(motoVehicle);
        System.out.println("Models Size:");
        printSizeModels(motoVehicle);

        System.out.println(TERMINATOR_STRING);

        System.out.println("Delete by name: " + testDeleteName + " ... \nResults:");
        deleteModelByName(carVehicle, testDeleteName);
        printNames(carVehicle);
        System.out.println("Models Size:");
        printSizeModels(carVehicle);

        System.out.println(TERMINATOR_STRING);

        System.out.println("Display exist car price with name " + testNameModel);
        System.out.println(carVehicle.getModelPriceByName(testNameModel));

        System.out.println(TERMINATOR_STRING);

        System.out.println("Average car models price:");
        System.out.println(VehicleHelper.getAveragePrice(carVehicle));
        System.out.println("Average moto models price:");
        System.out.println(VehicleHelper.getAveragePrice(motoVehicle));

        System.out.println(TERMINATOR_STRING);

        System.out.println("Print vehicle using VehicleHelper");
        System.out.println("Auto: ");
        VehicleHelper.printModelsInPrintStream(carVehicle, System.out);
        System.out.println("Moto: ");
        VehicleHelper.printModelsInPrintStream(motoVehicle, System.out);

        System.out.println(TERMINATOR_STRING);

        System.out.println("Update car model name Toyota.... \nResult:");
        carVehicle.setNameByName("Toyota", "2106");
        VehicleHelper.printModelWithPrisesInPrintStream(carVehicle, System.out);
    }

    public static void addCarModels(Vehicle vehicle) throws DuplicateModelNameException {
        vehicle.addModel("21017", 234);
        vehicle.addModel("1118", 5878);
        vehicle.addModel("1119", 324532);
        vehicle.addModel("Toyota", 7685768.9);
        vehicle.addModel("1117", 891.92);
    }

    public static void addMotoModels(Vehicle vehicle) throws DuplicateModelNameException {
        vehicle.addModel("3-T", 123);
        vehicle.addModel("Yamaha", 9878);
        vehicle.addModel("BMW", 13532);
        vehicle.addModel("AUDI", 111.9);
        vehicle.addModel("ИЖ", 391.92);
    }

    private static void printNames(Vehicle vehicle) {
        for (String name : vehicle.getModelNames()) {
            System.out.println(name);
        }
    }

    private static void printPrices(Vehicle vehicle) {
        for (double price : vehicle.getModelPrices()) {
            System.out.println(price);
        }
    }

    private static void deleteModelByName(Vehicle vehicle, String deleteName) throws NoSuchModelNameException {
        vehicle.deleteModel(deleteName);
    }

    public static void printSizeModels(Vehicle vehicle) {
        System.out.println(vehicle.getModelsSize());
    }
}
