package johnpier.thread.runnable.secuences;

import johnpier.models.Vehicle;
import johnpier.untils.VehicleHelper;

import java.util.concurrent.locks.ReentrantLock;

public class SequenceModelNamesPrintRunnable implements Runnable {
    private final Vehicle vehicle;
    private final ReentrantLock lock;

    public SequenceModelNamesPrintRunnable(Vehicle vehicle, ReentrantLock lock) {
        this.vehicle = vehicle;
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            lock.lock();
            VehicleHelper.printModelsInPrintStream(vehicle, System.out);
            lock.unlock();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
