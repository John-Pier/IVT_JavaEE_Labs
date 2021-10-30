package johnpier;

import johnpier.models.Vehicle;
import johnpier.untils.VehicleHelper;

import java.io.*;
import java.net.*;
import java.util.Arrays;

public class MainLab4SServer {
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
        System.out.println("Соединение с клиентом успешно: " + clientSocket.getLocalAddress() + ":" + clientSocket.getLocalPort());
        try (clientSocket) {
            var inputStream = new ObjectInputStream(clientSocket.getInputStream());
            var outputStream = new DataOutputStream(clientSocket.getOutputStream());

            var vehicles = (Vehicle[]) inputStream.readObject();
            var result = Arrays.stream(vehicles).mapToDouble(VehicleHelper::getAveragePrice).average().orElse(Double.NaN);
            outputStream.writeDouble(result);
            System.out.println("Результат: " + result);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}
