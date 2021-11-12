import johnpier.Config;
import johnpier.models.Vehicle;
import johnpier.untils.VehicleHelper;

import java.io.*;
import java.net.*;

public class MainLab4PServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(Config.SERVER_PORT)) {
            System.out.println("Сервер запущен");
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                runTask(socket);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void runTask(Socket clientSocket) {
        new Thread(() -> {
            System.out.println("Соединение с клиентом успешно: " + clientSocket.getLocalAddress() + ":" + clientSocket.getLocalPort());
            try (clientSocket){
                var inputStream = new ObjectInputStream(clientSocket.getInputStream());
                var outputStream = new DataOutputStream(clientSocket.getOutputStream());

                var vehicles = (Vehicle[])inputStream.readObject();
                var result = VehicleHelper.getAveragePriceOfVehicles(vehicles);
                outputStream.writeDouble(result);
                System.out.println("Результат: " + result);
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        }).start();
    }
}
