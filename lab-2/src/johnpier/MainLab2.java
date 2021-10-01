package johnpier;

import johnpier.exeptions.DuplicateModelNameException;
import johnpier.fabric.MotorcycleFabric;
import johnpier.models.Motorcycle;
import johnpier.models.Vehicle;
import johnpier.untils.VehicleHelper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainLab2 {
    public static void main(String[] args) throws DuplicateModelNameException, IOException, ClassNotFoundException {

        Vehicle vehicle = new Motorcycle("Motorcycle", 2);
        vehicle.addModel("3-T", 123);
        vehicle.addModel("Yamaha", 9878);
        vehicle.addModel("BMW", 13532);
        vehicle.addModel("AUDI", 111.9);
        vehicle.addModel("ИЖ", 391.92);

        VehicleHelper.setFabric(new MotorcycleFabric());
//        new Thread(() -> {
//            try {
//                System.out.println(VehicleHelper.inputVehicle(System.in).getVehicleBrand());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }).start();

        VehicleHelper.outputVehicle(vehicle, new FileOutputStream("./dist/lab-2/test.b", false));
        var moto = VehicleHelper.inputVehicle(new FileInputStream("./dist/lab-2/test.b"));
        System.out.println("Auto Names:");
        printNames(moto);
        System.out.println("Auto Prices:");
        printPrices(moto);
        System.out.println("Models Size:");
        printSizeModels(moto);
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
