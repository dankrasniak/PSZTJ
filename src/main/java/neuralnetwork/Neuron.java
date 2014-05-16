package neuralnetwork;

import data.Input;

/**
 * Base computing entity.
 * <p>Receives input and returns an output.</p>
 * Created by daniel on 08.05.14.
 */
public final class Neuron{
    private Weights weights;

    public Neuron( final Weights weights ){
        uploadWeights( weights );
    }

    /**
     * Processes input and returns a value.
     * @param input
     * @return
     */
    public final Double compute( final Input input ) {
        int i = input.size();
        Double result = 0.0;
        while( ( i-- ) != 0 ) {
            result += ( input.get( i ) * weights.get( i ) ); // TODO Update with proper formula.
        }
        return result;
    }

    /**
     * Uploads new <b>Weights</b>.
     */
    public final void uploadWeights( final Weights weights ){
        this.weights = weights;
    }

    /**
     * @return the weights.
     */
    public Weights getWeights(){
        return weights;
    }
}
