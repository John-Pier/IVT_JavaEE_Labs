package johnpier;

import johnpier.exeptions.DuplicateModelNameException;
import johnpier.fabric.MotorcycleFabric;
import johnpier.models.Motorcycle;
import johnpier.models.Vehicle;
import johnpier.untils.VehicleHelper;

import java.io.*;

public class MainLab2 {
    public static void main(String[] args) throws DuplicateModelNameException {

        Vehicle vehicle = new Motorcycle("Motorcycle", 2);
        vehicle.addModel("3-T", 123);
        vehicle.addModel("Yamaha", 9878);
        vehicle.addModel("BMW", 13532);
        vehicle.addModel("AUDI", 111.9);
        vehicle.addModel("ИЖ", 391.92);

        VehicleHelper.setFabric(new MotorcycleFabric());

        System.out.println("\nTest write|read from file");
        testWR(vehicle);
        System.out.println("\nTest input|output from file");
        testIO(vehicle);
//        System.out.println("\nTest input|output from System");
//        testIOFromSystem(vehicle);
        System.out.println("\nTest serialize|deserialize from file");
        testSerialize(vehicle);
    }

    private static void testWR(Vehicle vehicle) {
        try {
            VehicleHelper.outputVehicle(vehicle, new FileOutputStream("./dist/lab-2/test.b", false));
            var moto = VehicleHelper.inputVehicle(new FileInputStream("./dist/lab-2/test.b"));
            printVehicle(moto);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void testIO(Vehicle vehicle) {
        try {
            VehicleHelper.writeVehicle(vehicle, new FileWriter("./dist/lab-2/test.b", false));
            var moto = VehicleHelper.readVehicle(new FileReader("./dist/lab-2/test.b"));
            printVehicle(moto);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void testSerialize(Vehicle vehicle) {
        try {
            VehicleHelper.serializeVehicle(vehicle, new FileOutputStream("./dist/lab-2/testS.b"));
            var moto = VehicleHelper.deserializeVehicle(new FileInputStream("./dist/lab-2/testS.b"));
            printVehicle(moto);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void testIOFromSystem(Vehicle vehicle) {
        try {
            var in = System.in;
            var out = System.out;
            new Thread(() -> {
                try {
                    System.setIn(in);
                    System.setOut(out);
                    var moto = VehicleHelper.inputVehicle(in);
                    printVehicle(moto);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
            VehicleHelper.outputVehicle(vehicle, out);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void printVehicle(Vehicle vehicle) {
        System.out.println("vehicle Names:");
        printNames(vehicle);
        System.out.println("vehicle Prices:");
        printPrices(vehicle);
        System.out.println("vehicle Models Size:");
        printSizeModels(vehicle);
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

    public static void printSizeModels(Vehicle vehicle) {
        System.out.println(vehicle.getModelsSize());
    }
}
