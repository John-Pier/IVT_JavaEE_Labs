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
            // считать название марки,
            var file = new FileReader(fileName);
            String brandName = "-";
            this.blockingQueue.put(VehicleHelper.getFabric().createVehicle(brandName, 0));
        } catch (FileNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
