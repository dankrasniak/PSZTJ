package NeuralNetwork;

import java.util.ArrayList;

/**
 * Manages Neural transmissions.
 * Created by daniel on 08.05.14.
 */
public class NeuralNetwork {
    private ArrayList<NeuralLayer> neuralLayers;

    public NeuralNetwork( final ArrayList<NeuralLayer> neuralLayers ){
        this.neuralLayers = neuralLayers;
    }

    /**
     * Adds up <b>Weights</b> from  all the <b>Neurons</b> from the <b>Layer</b> in the <b>Network</b>.
     * @return weights.
     */
    public final ArrayList<Double> getWeights() { // TODO
        ArrayList<Double> weights = new ArrayList<Double>();
//        for( NeuralLayer neuralLayer : neuralLayers )
//            weights.addAll( neuralLayer.getWeights() );
        return weights;
    }


}
