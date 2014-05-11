package NeuralNetwork;

import java.util.ArrayList;

/**
 * Base computing entity.
 * <p>Receives input and returns an output.</p>
 * Created by daniel on 08.05.14.
 */
public final class Neuron{
    private ArrayList<Double> weights;

    public Neuron( final ArrayList<Double> weights ){
        uploadWeights( weights );
    }

    /**
     * Processes input and returns a value.
     * @param input
     * @return
     */
    public final Double compute( final ArrayList<Double> input ) {
        int i = input.size();
        Double result = 0.0;
        while( ( i-- ) != 0 ){
            result += input.set( i, ( input.get( i ) * weights.get( i ) ) );
        }
        return result;
    }

    /**
     * Uploads new <b>Weights</b>.
     */
    public final void uploadWeights( final ArrayList<Double> weights ){
        this.weights = weights;
    }

    /**
     * @return the weights.
     */
    public ArrayList<Double> getWeights(){
        return weights;
    }
}
