import johnpier.Config;
import johnpier.exeptions.DuplicateModelNameException;
import johnpier.fabric.*;
import johnpier.models.*;
import johnpier.untils.VehicleHelper;

import java.io.*;
import java.net.*;

public class MainLab4Client {
    public static void main(String[] args) {
        try (Socket clientSocket = new Socket(Config.SERVER_HOST_NAME, Config.SERVER_PORT)) {
            System.out.println("Клиент запущен.");
            var inputStream = new DataInputStream(clientSocket.getInputStream());
            var outputStream = new ObjectOutputStream(clientSocket.getOutputStream());

            outputStream.writeObject(generateVehicle(10));

            System.out.println("Результат работы: " + inputStream.readDouble());
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static Vehicle[] generateVehicle(int count) throws DuplicateModelNameException {
        if (Math.random() > 0.5) {
            VehicleHelper.setFabric(new MotorcycleFabric());
        } else {
            VehicleHelper.setFabric(new CarFabric());
        }
        var vehicles = new Vehicle[count];
        for (int i = 0; i < count; i ++) {
            Vehicle vehicle = VehicleHelper.getFabric().createVehicle("Vehicle-" + Math.floor(Math.random() * 100), 1);
            vehicle.addModel("3-T", Math.random() * 100);
            vehicle.addModel("Yamaha", Math.random() * 100);
            vehicle.addModel("BMW", Math.random() * 100);
            vehicle.addModel("AUDI", Math.random() * 100);
            vehicle.addModel("ИЖ (Рус)", Math.random() * 100);
            vehicles[i] = vehicle;

            VehicleHelper.printModelWithPrisesInPrintStream(vehicle, System.out);
        }
        return vehicles;
    }
}
