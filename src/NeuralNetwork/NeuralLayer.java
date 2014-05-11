package NeuralNetwork;

import java.util.ArrayList;

/**
 * Created by daniel on 09.05.14.
 */
public class NeuralLayer{
    private ArrayList<Neuron> neurons;

    public NeuralLayer( final ArrayList<Neuron> neurons ){
        uploadNewNeuronLayer( neurons );
    }

    /**
     * Uploads new Neurons to the layer.
     * @param neurons
     */
    public final void uploadNewNeuronLayer( final ArrayList<Neuron> neurons ){
        this.neurons = neurons;
    }

    /**
     * Adds up <b>Weights</b> from  all the <b>Neurons</b> from the <b>Layer</b>.
     * @return weights.
     */
    public final Weights getWeights(){ // TODO
        Weights weights = new Weights();
        for( Neuron neuron : neurons )
            weights.addAll( neuron.getWeights() );
        return weights;
    }

    public final void uploadNewWeights( final Weights weights ){
        for( Neuron neuron : neurons ){
            // TODO
        }
    }
}
