package neuralnetwork;

import java.util.ArrayList;

/**
 * Created by daniel on 11.05.14.
 */
public class Weights {
    private ArrayList<Double> weights;

    public Weights( final ArrayList<Double> weights ) {
        this.weights = weights;
    }

    /**
     * @return weight values.
     */
    public final ArrayList<Double> getWeights() {
        return weights;
    }

    /**
     * @return the number of weights.
     */
    public final int size() {
        return weights.size();
    }

    /**
     * @return the value of weight of given index.
     */
    public final Double get( final int index ) {
        return weights.get( index );
    }

    /**
     * Set the value of <b>Weight</b> of given <b>Index number</b>.
     */
    public final void set( final int index, final Double weight ) {
        this.weights.set( index, weight );
    }
}
