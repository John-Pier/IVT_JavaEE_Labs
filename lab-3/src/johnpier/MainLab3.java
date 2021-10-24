package johnpier;

import johnpier.exeptions.DuplicateModelNameException;
import johnpier.fabric.MotorcycleFabric;
import johnpier.models.*;
import johnpier.thread.*;
import johnpier.untils.VehicleHelper;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class MainLab3 {
    public static void main(String[] args) throws DuplicateModelNameException {
        Vehicle vehicle = new Motorcycle("Motorcycle", 2);
        vehicle.addModel("3-T", 123);
        vehicle.addModel("Yamaha", 9878);
        vehicle.addModel("BMW", 13532);
        vehicle.addModel("AUDI", 111.9);
        vehicle.addModel("ИЖ (Рус)", 391.92);
        VehicleHelper.setFabric(new MotorcycleFabric());

        System.out.println("Лабораторная работа №3 (Попов Н. 6133)");

        System.out.println("Тестирование приоритетов потоков:");
        testThreadsPriority(vehicle);

        System.out.println("\nTest write|read from file");
        testWR(vehicle);
        System.out.println("\nTest input|output from file");
        testIO(vehicle);
        System.out.println("\nTest input|output from System");
        testIOFromSystem();
        System.out.println("\nTest serialize|deserialize from file");
        testSerialize(vehicle);
    }

    private static void testThreadsPriority(Vehicle vehicle) {
        Thread writer = new PricesPrintThread(vehicle);
        Thread reader = new ModelNamesPrintThread(vehicle);

        writer.setPriority(Thread.MAX_PRIORITY);
        reader.setPriority(Thread.MIN_PRIORITY);
        try {
            writer.start();
            reader.start();
            writer.join();
            reader.join();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void testWR(Vehicle vehicle) {
        try {
            VehicleHelper.writeVehicle(vehicle, new FileWriter("./dist/lab-2/test.b", false));
            var moto = VehicleHelper.readVehicle(new FileReader("./dist/lab-2/test.b"));
            printVehicle(moto);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void testIO(Vehicle vehicle) {
        try {
            VehicleHelper.outputVehicle(vehicle, new FileOutputStream("./dist/lab-2/test.b", false));
            var moto = VehicleHelper.inputVehicle(new FileInputStream("./dist/lab-2/test.b"));
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

    private static void testIOFromSystem() {
        try {
            var moto = VehicleHelper.readVehicle(
                    new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8))
            );
            VehicleHelper.writeVehicle(moto, new OutputStreamWriter(System.out, StandardCharsets.UTF_8));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void printVehicle(Vehicle vehicle) {
        System.out.println("Vehicle: " + vehicle.getVehicleBrand());
        System.out.println("Vehicle Names:");
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
