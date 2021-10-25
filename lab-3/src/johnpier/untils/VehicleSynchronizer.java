package johnpier.untils;

import johnpier.models.Vehicle;

public class VehicleSynchronizer {
    private final Vehicle vehicle;
    private volatile int current = 0;
    private boolean isPairPrinted = false;

    public VehicleSynchronizer(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void printPrice() throws InterruptedException {
        synchronized (vehicle) {
            var prices = vehicle.getModelPrices();

            this.checkCanPrintPrice();
            this.waitUntilNotPairPrinted();

            double val = prices[current++];
            System.out.println("Price: " + val + "\n");
            isPairPrinted = false;
            vehicle.notifyAll();
        }
    }

    public void printModel() throws InterruptedException {
        synchronized (vehicle) {
            var names = vehicle.getModelNames();

            this.checkCanPrintModel();
            this.waitUntilPairPrinted();

            System.out.println("Model: " + names[current]);
            isPairPrinted = true;
            vehicle.notifyAll();
        }
    }

    private void checkCanPrintPrice() throws InterruptedException {
        if (!canPrintPrice()) {
            throw new InterruptedException();
        }
    }

    private void checkCanPrintModel() throws InterruptedException {
        if (!canPrintModel()) {
            throw new InterruptedException();
        }
    }

    private void waitUntilNotPairPrinted() throws InterruptedException {
        while (!isPairPrinted) {
            vehicle.wait();
        }
    }

    private void waitUntilPairPrinted() throws InterruptedException {
        while (isPairPrinted) {
            vehicle.wait();
        }
    }

    public boolean canPrintPrice() {
        return current < vehicle.getModelsSize();
    }

    public boolean canPrintModel() {
        return (!isPairPrinted && current < vehicle.getModelsSize()) ||
                (isPairPrinted && current < vehicle.getModelsSize() - 1);
    }

    public synchronized void reset() {
        current = 0;
        isPairPrinted = false;
    }
}
