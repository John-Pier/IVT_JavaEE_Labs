public class Sedan implements Car, Test {

    @Override
    public void gas() {
        Car.super.gas();
    }
}
