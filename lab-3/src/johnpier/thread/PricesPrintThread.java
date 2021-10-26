package johnpier.thread;

import johnpier.models.Vehicle;
import johnpier.untils.VehicleHelper;

public class PricesPrintThread extends Thread {
    private final Vehicle vehicle;

    public PricesPrintThread(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public void run() {
        printPrices(this.vehicle);
    }

    private void printPrices(Vehicle vehicle) {
        VehicleHelper.printPricesInPrintStream(vehicle, System.out);
    }
}
