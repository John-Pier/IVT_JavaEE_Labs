package johnpier.fabric;

import johnpier.models.Vehicle;

public interface VehicleFabric {
   Vehicle createVehicle(String brand, int sizeOfModels);
}
