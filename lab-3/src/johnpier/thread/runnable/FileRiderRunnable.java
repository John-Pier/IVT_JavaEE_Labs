package johnpier.thread.runnable;

import johnpier.models.Vehicle;
import johnpier.untils.VehicleHelper;

import java.io.*;
import java.util.concurrent.BlockingQueue;

public class FileRiderRunnable implements Runnable {
    private final String fileName;
    private final BlockingQueue<Vehicle> blockingQueue;

    public FileRiderRunnable(String fileName, BlockingQueue<Vehicle> blockingQueue) {
        this.fileName = fileName;
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        try {
            var bufferedReader = new BufferedReader(new FileReader(fileName));
            var brandName = bufferedReader.readLine();
            bufferedReader.close();
            this.blockingQueue.put(VehicleHelper.getFabric().createVehicle(brandName, 0));
//            this.blockingQueue.add(VehicleHelper.getFabric().createVehicle(brandName, 0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
