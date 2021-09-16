package johnpier.untils;

import johnpier.models.Vehicle;

import java.io.PrintStream;
import java.util.Arrays;

public class VehicleHelper {
    public static double averagePrice(Vehicle vehicle) {
        return Arrays.stream(vehicle.getModelPrices()).average().orElse(Double.NaN);
    }

    public static void writeModels(Vehicle vehicle, PrintStream printStream) {
        String vehicleName = vehicle.getVehicleModel();
        Arrays.stream(vehicle.getModelNames()).forEachOrdered(modelName -> {
            printStream.println(vehicleName + ": " + modelName);
        });
    }

    public static void writeModelWithPrises(Vehicle vehicle, PrintStream printStream) {
        String vehicleName = vehicle.getVehicleModel();
        String[] names = vehicle.getModelNames();
        double[] prices = vehicle.getModelPrices();

        for (int i = 0; i < vehicle.getModelsSize(); i++) {
            printStream.println(vehicleName + " [" + (i + 1) + ", name: " + names[i] + ", price: " + prices[i] + "]");
        }
    }
}
