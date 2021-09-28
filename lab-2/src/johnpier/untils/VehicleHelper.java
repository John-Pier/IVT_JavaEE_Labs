package johnpier.untils;

import johnpier.models.Vehicle;

import java.io.*;
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

    public static void outputVehicle(Vehicle vehicle, OutputStream outputStream) throws IOException {
        var stream = new ObjectOutputStream(outputStream);
        stream.writeObject(vehicle);
        stream.flush(); // DataOutputStream
        //outputStream.write( "\n".getBytes());
    }

    public static Vehicle inputVehicle(InputStream inputStream) throws IOException, ClassNotFoundException {
        var stream = new ObjectInputStream(inputStream); // DataInputStream
        return (Vehicle)(stream.readObject());
    }

    public static void writeVehicle(Vehicle vehicle, Writer writer) throws IOException {
        writer.write(vehicle.toString().toCharArray()); // PrintWriter
        writer.write( "\n");
        writer.flush();
    }

    public static Vehicle readVehicle(Reader reader) {
        StringBuilder builder = new StringBuilder(); // BufferedReader или StreamTokenizer
        try {
            char buffer;
            while (reader.ready() && (buffer = (char) reader.read()) != '\n') {
                builder.append(buffer);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        String[] args = builder.toString().split(";");
        return createByFabric(args);
    }

    private static Vehicle createByFabric(String[] args) {
        return null;
    }
}
