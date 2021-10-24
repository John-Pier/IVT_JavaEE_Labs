package johnpier.thread;

import johnpier.models.Vehicle;
import johnpier.untils.VehicleHelper;

public class ModelNamesPrintThread extends Thread {
    private final Vehicle vehicle;

    public ModelNamesPrintThread(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public void run() {
        VehicleHelper.printModelsInPrintStream(this.vehicle, System.out);
    }
}
