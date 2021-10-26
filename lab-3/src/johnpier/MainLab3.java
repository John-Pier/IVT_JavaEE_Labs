package johnpier;

import johnpier.exeptions.DuplicateModelNameException;
import johnpier.fabric.MotorcycleFabric;
import johnpier.models.*;
import johnpier.thread.*;
import johnpier.thread.runnable.*;
import johnpier.thread.runnable.secuences.*;
import johnpier.untils.*;

import java.util.concurrent.locks.ReentrantLock;

public class MainLab3 {
    private static Vehicle vehicle;

    public static void main(String[] args) throws DuplicateModelNameException {
        vehicle = new Motorcycle("Motorcycle", 2);
        vehicle.addModel("3-T", 123);
        vehicle.addModel("Yamaha", 9878);
        vehicle.addModel("BMW", 13532);
        vehicle.addModel("AUDI", 111.9);
        vehicle.addModel("ИЖ (Рус)", 391.92);
        VehicleHelper.setFabric(new MotorcycleFabric());

        System.out.println("Лабораторная работа №3 (Попов Н. 6133)");

        System.out.println("\nТестирование приоритетов потоков:\n");
        testThreadsPriority();
        System.out.println("\nТестирование попеременной работы потоков:\n");
        testThreadsAlternating();
        System.out.println("\nТестирование последовательной работы потоков:\n");
        testThreadsSequence();
    }

    private static void testThreadsPriority() {
        Thread pricesPrinter = new PricesPrintThread(vehicle);
        Thread modelNamesPrinter = new ModelNamesPrintThread(vehicle);

        pricesPrinter.setPriority(Thread.MAX_PRIORITY);
        modelNamesPrinter.setPriority(Thread.MAX_PRIORITY);
        try {
            pricesPrinter.start();
            modelNamesPrinter.start();
            pricesPrinter.join();
            modelNamesPrinter.join();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void testThreadsAlternating() {
        var synchronizer = new VehicleSynchronizer(vehicle);
        var pricesPrinterRunnable = new PricesPrintRunnable(synchronizer);
        var modelNamesPrinterRunnable = new ModelNamesPrintRunnable(synchronizer);

        Thread pricesPrinter = new Thread(pricesPrinterRunnable);
        Thread modelNamesPrinter = new Thread(modelNamesPrinterRunnable);

        try {
            modelNamesPrinter.start();
            pricesPrinter.start();
            modelNamesPrinter.join();
            pricesPrinter.join();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        synchronizer.reset();
    }

    private static void testThreadsSequence() {
        ReentrantLock reentrantLock = new ReentrantLock();

        var pricesPrinter = new Thread(new SequencePricesPrintRunnable(vehicle, reentrantLock));
        var modelNamesPrinter = new Thread(new SequenceModelNamesPrintRunnable(vehicle, reentrantLock));

        try {
            modelNamesPrinter.start();
            pricesPrinter.start();
            modelNamesPrinter.join();
            pricesPrinter.join();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void printVehicle(Vehicle vehicle) {
        System.out.println("Vehicle: " + vehicle.getVehicleBrand());
        System.out.println("Vehicle Names:");
        printNames(vehicle);
        System.out.println("vehicle Prices:");
        printPrices(vehicle);
        System.out.println("vehicle Models Size:");
        printSizeModels(vehicle);
    }

    private static void printNames(Vehicle vehicle) {
        for (String name : vehicle.getModelNames()) {
            System.out.println(name);
        }
    }

    private static void printPrices(Vehicle vehicle) {
        for (double price : vehicle.getModelPrices()) {
            System.out.println(price);
        }
    }

    public static void printSizeModels(Vehicle vehicle) {
        System.out.println(vehicle.getModelsSize());
    }
}
