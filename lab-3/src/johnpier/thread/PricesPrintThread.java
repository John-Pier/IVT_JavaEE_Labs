package johnpier.thread;

import johnpier.models.Vehicle;

public class PricesPrintThread extends Thread {
    private Vehicle vehicle;

    public PricesPrintThread(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public void run() {

    }
}
