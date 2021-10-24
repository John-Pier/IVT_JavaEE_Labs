package johnpier.thread;

import johnpier.models.Vehicle;

public class ModelNamesPrintThread extends Thread {
    private Vehicle vehicle;

    public ModelNamesPrintThread(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public void run() {
        //
    }
}
