package Data;

import java.util.ArrayList;

/**
 * Created by daniel on 11.05.14.
 */
public class Input {
    private final ArrayList<Double> input;

    public Input( final ArrayList<Double> input ) {
        this.input = input;
    }

    /**
     * @return input values.
     */
    public final ArrayList<Double> getInput() {
        return input;
    }

    /**
     * @return the number of inputs.
     */
    public final int size() {
        return input.size();
    }

    /**
     * @return the value of input of given index.
     */
    public final Double get( final int index ) {
        return input.get( index );
    }
}
