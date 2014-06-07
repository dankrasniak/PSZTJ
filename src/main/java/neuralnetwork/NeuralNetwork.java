package neuralnetwork;

import java.util.ArrayList;
import java.util.Random;

import data.Data;
import data.Input;
import data.Output;

/**
 * Manages Neural transmissions.
 * Created by daniel on 08.05.14.
 */
public class NeuralNetwork {
    private ArrayList<NeuralLayer> neuralLayers;

    public NeuralNetwork() {
        this.neuralLayers = createNeuralLayers();
    }


    private ArrayList<NeuralLayer> createNeuralLayers() {
        ArrayList<NeuralLayer> neuralLayers = new ArrayList<NeuralLayer>();
        ArrayList<Neuron> neurons = new ArrayList<Neuron>();
        ArrayList<Double> weights;
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            weights = calculateWeights(random);
            neurons.add(new Neuron(new Weights(weights)));
        }
        neuralLayers.add(new NeuralLayer(neurons));
        neurons = new ArrayList<Neuron>();
        weights = calculateWeights(random);
        neurons.add(new Neuron(new Weights(weights)));
        NeuralLayer lastLayer = new NeuralLayer(neurons);
        neuralLayers.add(lastLayer);
        return neuralLayers;
    }

    private ArrayList<Double> calculateWeights(Random random) {
        ArrayList<Double> weights;
        weights = new ArrayList<Double>();
        for (int j = 0; j < 3; j++) {
            weights.add(random.nextDouble());
        }
        return weights;
    }

    /**
     * Adds up <b>Weights</b> from  all the <b>Neurons</b> from every <b>Layer</b> in the <b>Network</b>.
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
    public final void uploadWeights(final ArrayList<Double> newWeights) {
        ArrayList<Weights> weightsReferences = getWeights();
        int index = 0;
        for(Weights weights : weightsReferences) {
            for( int i = 0; i < weights.size(); ++i ) {
                weights.set(i,
                        newWeights.get(index++));
            }
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
