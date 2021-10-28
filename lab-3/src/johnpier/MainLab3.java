package johnpier;

import johnpier.exeptions.DuplicateModelNameException;
import johnpier.fabric.MotorcycleFabric;
import johnpier.models.*;
import johnpier.thread.*;
import johnpier.thread.runnable.*;
import johnpier.thread.runnable.secuences.*;
import johnpier.untils.*;

import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class MainLab3 {
    private static Vehicle vehicle;

    public static void main(String[] args) throws DuplicateModelNameException {
        VehicleHelper.setFabric(new MotorcycleFabric());
        initVehicle();

        System.out.println("Лабораторная работа №3 (Попов Н. 6133)");
        System.out.println("\nТестирование приоритетов потоков:\n");
        testThreadsPriority();
        System.out.println("\nТестирование попеременной работы потоков:\n");
        testThreadsAlternating();
        System.out.println("\nТестирование последовательной работы потоков:\n");
        testThreadsSequence();
        System.out.println("\nТестирование работы Executors:\n");
        testThreadsPool();
        System.out.println("\nТестирование работы ArrayBlockingQueue:\n");
        testBlockingQueue();
    }

    private static void initVehicle() throws DuplicateModelNameException {
        vehicle = new Motorcycle("Motorcycle", 2);
        vehicle.addModel("3-T", 123);
        vehicle.addModel("Yamaha", 9878);
        vehicle.addModel("BMW", 13532);
        vehicle.addModel("AUDI", 111.9);
        vehicle.addModel("ИЖ (Рус)", 391.92);
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

    private static void testThreadsPool() {
       var motorcycle = new Motorcycle("Motorcycle", 2);
       var motoBryce = new Motorcycle("BMV", 2);
       var car = new Car("Car", 1);
       var auto = new Car("WAC", 1);

        var threadPool = Executors.newFixedThreadPool(2);
        threadPool.submit(new ModelBrandPrintRunnable(motorcycle));
        threadPool.submit(new ModelBrandPrintRunnable(motoBryce));
        threadPool.submit(new ModelBrandPrintRunnable(car));
        threadPool.submit(new ModelBrandPrintRunnable(auto));

        threadPool.shutdown();
    }

    private static void testBlockingQueue() {
       var blockingQueue = new ArrayBlockingQueue<Vehicle>(4);
       var fileNames = new String[] {"brand0.txt", "brand1.txt", "brand2.txt", "brand3.txt", "brand4.txt"};
       for(var name : fileNames) {
           new Thread(new FileRiderRunnable("./dist/lab-3/" + name, blockingQueue)).start();
       }

       for(int i = 0; i < 5; i ++) {
           try {
               System.out.println("Read Brand: " + blockingQueue.take().getVehicleBrand());
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
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
