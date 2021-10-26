package johnpier.thread.runnable.secuences;

import johnpier.models.Vehicle;
import johnpier.untils.VehicleHelper;

import java.util.concurrent.locks.ReentrantLock;

public class SequencePricesPrintRunnable implements Runnable {
    private final Vehicle vehicle;
    private final ReentrantLock lock;

    public SequencePricesPrintRunnable(Vehicle vehicle, ReentrantLock lock) {
        this.vehicle = vehicle;
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            lock.lock();
            VehicleHelper.printPricesInPrintStream(vehicle, System.out);
            lock.unlock();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
