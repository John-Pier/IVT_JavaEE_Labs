package johnpier;

import johnpier.exeptions.DuplicateModelNameException;
import johnpier.models.Motorcycle;
import johnpier.models.Vehicle;
import johnpier.untils.VehicleHelper;

import java.io.IOException;

public class MainLab2 {
    public static void main(String[] args) throws DuplicateModelNameException, IOException, ClassNotFoundException {
        Vehicle vehicle = new Motorcycle("ere", 1);
        vehicle.addModel("3-T", 123);
        vehicle.addModel("Yamaha", 9878);
        vehicle.addModel("BMW", 13532);
        vehicle.addModel("AUDI", 111.9);
        vehicle.addModel("ИЖ", 391.92);
        VehicleHelper.outputVehicle(vehicle, System.out);

        System.out.println(VehicleHelper.inputVehicle(System.in).getVehicleBrand());
    }
}
