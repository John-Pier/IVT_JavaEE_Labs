package johnpier.thread.runnable;

import johnpier.models.Vehicle;

public class ModelBrandPrintRunnable implements Runnable {

    private final Vehicle vehicle;

    public ModelBrandPrintRunnable(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
    
    @Override
    public void run() {
       System.out.println(this.vehicle.getVehicleBrand());
    }
}
