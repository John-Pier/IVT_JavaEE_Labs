package johnpier.thread.runnable;

import johnpier.untils.VehicleSynchronizer;

public class ModelNamesPrintRunnable implements Runnable {
    private final VehicleSynchronizer vehicleSynchronizer;

    public ModelNamesPrintRunnable(VehicleSynchronizer vehicleSynchronizer) {
        this.vehicleSynchronizer = vehicleSynchronizer;
    }

    @Override
    public void run() {
        try {
            while (vehicleSynchronizer.canPrintModel()) {
                vehicleSynchronizer.printModel();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
