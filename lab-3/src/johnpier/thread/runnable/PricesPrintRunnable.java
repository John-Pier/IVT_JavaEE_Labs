package johnpier.thread.runnable;

import johnpier.untils.VehicleSynchronizer;

public class PricesPrintRunnable implements Runnable {
    private final VehicleSynchronizer vehicleSynchronizer;

    public PricesPrintRunnable(VehicleSynchronizer vehicleSynchronizer) {
        this.vehicleSynchronizer = vehicleSynchronizer;
    }

    @Override
    public void run() {
        try {
            while (vehicleSynchronizer.canPrintPrice()) {
                vehicleSynchronizer.printPrice();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
