package utils;

/**
 * Created by lukasz on 6/7/14.
 */
public class EpochCounter {
    private int counter;

    public EpochCounter() {
        this.counter = 0;
    }

    public int get() {
        return counter;
    }

    public int getIncremented() {
        increment();
        return counter;
    }

    private void increment() {
        ++counter;
    }

    public void reset() {
        counter = 0;
    }
}
