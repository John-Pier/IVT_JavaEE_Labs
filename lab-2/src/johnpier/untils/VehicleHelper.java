package johnpier.untils;

import johnpier.exeptions.DuplicateModelNameException;
import johnpier.fabric.VehicleFabric;
import johnpier.models.Vehicle;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class VehicleHelper {
    private static VehicleFabric vehicleFabric;

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

    public static void setFabric(VehicleFabric vehicleFabric) {
        VehicleHelper.vehicleFabric = vehicleFabric;
    }

    public static void outputVehicle(Vehicle vehicle, OutputStream outputStream) throws IOException {
        var stream = new DataOutputStream(outputStream);
        var brandSize = vehicle.getVehicleBrand().length();
        var modelsNames = vehicle.getModelNames();
        var modelPrices = vehicle.getModelPrices();

        stream.writeInt(brandSize);
        stream.write(vehicle.getVehicleBrand().getBytes(StandardCharsets.UTF_8));
        stream.writeInt(vehicle.getModelsSize());

        stream.writeInt(modelsNames.length);
        Arrays.stream(modelsNames).forEachOrdered(name -> {
            try {
                stream.writeInt(name.getBytes(StandardCharsets.UTF_8).length);
                stream.write(name.getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        stream.writeInt(modelPrices.length);
        Arrays.stream(modelPrices).forEachOrdered(price -> {
            try {
                stream.writeDouble(price);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        stream.flush();
    }

    public static Vehicle inputVehicle(InputStream inputStream) throws IOException, DuplicateModelNameException {
        var stream = new DataInputStream(inputStream);
        int brandSize = stream.readInt();
        String brand = readString(brandSize, stream);

        int modelsSize = stream.readInt();

        int modelsNamesSize = stream.readInt();
        var modelsNames = new String[modelsNamesSize];
        int i = 0;
        while (i < modelsNamesSize && stream.available() > 0) {
            int nameSize = stream.readInt();
            modelsNames[i] = readString(nameSize, stream);
            i++;
        }

        int modelPricesSize = stream.readInt();
        var modelPrices = new double[modelPricesSize];
        i = 0;
        while (i < modelPricesSize && stream.available() > 0) {
            modelPrices[i] = stream.readDouble();
            i++;
        }

        var result = VehicleHelper.vehicleFabric.createVehicle(brand, modelsSize);
        for (int j = 0; j < modelsNames.length; j++) {
            result.addModel(modelsNames[j], modelPrices[j]);
        }

        return result;
    }

    private static String readString(int bytesCount, DataInputStream stream) throws IOException {
        var bytes = stream.readNBytes(bytesCount);
        StringBuilder builder = new StringBuilder();
        for (byte aByte : bytes) {
            builder.append((char) aByte);
        }
        return builder.toString();
    }

    public static void writeVehicle(Vehicle vehicle, Writer writer) throws IOException {
        var printWriter = new PrintWriter(writer);
        printWriter.println(vehicle.getVehicleBrand());
        printWriter.println(vehicle.getModelsSize());

        var modelNames = Arrays.stream(vehicle.getModelNames()).reduce("", (builder, v) -> {
            builder = builder.concat(v + ",");
            return builder;
        });
        printGuardLine(modelNames, printWriter);

        StringBuilder stringBuilder = new StringBuilder();
        Arrays.stream(vehicle.getModelPrices()).forEachOrdered(price -> stringBuilder.append(price).append(","));
        var modelPrices = stringBuilder.toString();
        printGuardLine(modelPrices, printWriter);

        writer.flush();
    }

    private static void printGuardLine(String line, PrintWriter printWriter) {
        if (line.length() > 1) {
            printWriter.println(line.substring(0, line.length() - 1));
        }else {
            printWriter.println();
        }
    }

    public static Vehicle readVehicle(Reader reader) throws IOException, DuplicateModelNameException {
        var bufferedReader = new BufferedReader(reader);

        var brand = bufferedReader.readLine();
        var modelsSize = Integer.parseInt(bufferedReader.readLine());
        var modelsNames = bufferedReader.readLine().split(",");
        var modelsPrices =  Arrays.stream(bufferedReader.readLine().split(",")).map(Double::parseDouble).toArray();

        var result = VehicleHelper.vehicleFabric.createVehicle(brand, modelsSize);
        for (int j = 0; j < modelsNames.length; j++) {
            result.addModel(modelsNames[j], (Double) modelsPrices[j]);
        }
        return result;
    }

    public static void serializeVehicle(Vehicle vehicle, OutputStream outputStream) throws IOException {
        var stream = new ObjectOutputStream(outputStream);
        stream.writeObject(vehicle);
        stream.flush();
    }

    public static Vehicle deserializeVehicle(InputStream inputStream) throws IOException, ClassNotFoundException {
        var stream = new ObjectInputStream(inputStream);
        return (Vehicle) stream.readObject();
    }
}
