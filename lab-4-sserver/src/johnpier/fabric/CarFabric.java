package johnpier.fabric;

import johnpier.models.*;

public class CarFabric implements VehicleFabric {
    @Override
    public Vehicle createVehicle(String brand, int sizeOfModels) {
        return new Car(brand, sizeOfModels);
    }
}
