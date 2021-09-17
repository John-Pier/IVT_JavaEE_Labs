package johnpier.untils;

import johnpier.models.Vehicle;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Objects;

public class VehicleHelper {
    public static double getAveragePrice(Vehicle vehicle) {
        return Arrays.stream(vehicle.getModelPrices()).average().orElse(Double.NaN);
    }

    public static void printModelsInPrintStream(Vehicle vehicle, PrintStream printStream) {
        String vehicleName = vehicle.getVehicleBrand();
        Arrays.stream(vehicle.getModelNames())
                .filter(Objects::nonNull)
                .forEachOrdered(modelName -> printStream.println(vehicleName + ": " + modelName));
    }

    public static void printModelWithPrisesInPrintStream(Vehicle vehicle, PrintStream printStream) {
        String vehicleName = vehicle.getVehicleBrand();
        String[] names = vehicle.getModelNames();
        double[] prices = vehicle.getModelPrices();

        for (int i = 0; i < names.length; i++) {
            printStream.println(vehicleName + " [" + (i + 1) + ", name: " + names[i] + ", price: " + prices[i] + "]");
        }
    }
}
