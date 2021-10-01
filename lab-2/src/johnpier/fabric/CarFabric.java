package johnpier.fabric;

import johnpier.models.Car;
import johnpier.models.Vehicle;

public class CarFabric implements VehicleFabric {
    @Override
    public Vehicle createVehicle(String brand, int sizeOfModels) {
        return new Car(brand, sizeOfModels);
    }
}
