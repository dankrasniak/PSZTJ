package neuralnetwork;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import data.Data;
import data.Input;
import data.Output;
import geneticalgorithm.Fenotype;
import geneticalgorithm.NewBitSet;

/**
 * Manages Neural transmissions.
 * Created by daniel on 08.05.14.
 */
public class NeuralNetwork {
    private ArrayList<NeuralLayer> neuralLayers;

    //TODO for testing only!
    public NeuralNetwork() {
    }

    public NeuralNetwork(final ArrayList<NeuralLayer> neuralLayers) {
        this.neuralLayers = neuralLayers;
    }

    /**
     * Adds up <b>Weights</b> from  all the <b>Neurons</b> from every <b>Layer</b> in the <b>Network</b>.
     *
     * @return weights.
     */
    private final ArrayList<Weights> getWeights() {
        ArrayList<Weights> weights = new ArrayList<Weights>();
        for( NeuralLayer neuralLayer : neuralLayers )
            weights.addAll( neuralLayer.getWeights() );
        return weights;
    }

    public final ArrayList<Double> getDoubleWeights() {
        ArrayList<Double> weightsInDouble = new ArrayList<Double>();
        ArrayList<Weights> allWeights = getWeights();
        for( Weights weights : allWeights ) {
            weightsInDouble.addAll( weights.getValues() );
        }
        return weightsInDouble;
    }

    /**
     * Uploads new <b>Weights</b> to the <b>NeuralNetwork</b>.
     */
//     public final void uploadWeights(ArrayList<Double> weights) {
//        Iterator<NeuralLayer> neuralLayerIterator = neuralLayers.iterator();
//        while (neuralLayerIterator.hasNext()) {
//            neuralLayerIterator.next().uploadNewWeights(weights);
//        }
//    }

    /**
     * Uploads new <b>Weights</b> to the <b>NeuralNetwork</b>.
     */
    public final void uploadWeights(final ArrayList<Double> newWeights) {
        ArrayList<Weights> weightsReferences = getWeights();
        int index = 0;
        for(Weights weights : weightsReferences) {
            weights.set(i++, newWeights.get(index++));
        }
    }

    public final ArrayList<Output> computeData( final Data data ) {
        ArrayList<Output> outputs = new ArrayList<Output>();
        ArrayList<Input> inputs = data.getInputs();
        for(Input input : inputs) {
            outputs.add(new Output(compute(input)));
        }
        return outputs;
    }

    public final boolean compute( final Input input ) {
        Input tmpInput = input;
        for( NeuralLayer neuralLayer : neuralLayers ) {
            tmpInput = neuralLayer.compute(tmpInput);
        }
        return tmpInput.get( 0 ) >= 0;
    }
}
