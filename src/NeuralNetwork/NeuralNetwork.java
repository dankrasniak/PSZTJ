package NeuralNetwork;

import java.util.ArrayList;
import java.util.Iterator;

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
     * Adds up <b>Weights</b> from  all the <b>Neurons</b> from every <b>Layer</b> in the <b>Network</b>.
     * @return weights.
     */
    public final ArrayList<Weights> getWeights() { // TODO
        ArrayList<Weights> weights = new ArrayList<Weights>();
        for( NeuralLayer neuralLayer : neuralLayers )
            weights.addAll( neuralLayer.getWeights() );
        return weights;
    }

    /**
     * Uploads new <b>Weights</b> to the <b>NeuralNetwork</b>.
     */
    public final void uploadWeights( ArrayList<Weights> weights ) {
        Iterator<NeuralLayer> nerualLayerIterator = neuralLayers.iterator();
        while( nerualLayerIterator.hasNext() ) {
            nerualLayerIterator.next().uploadNewWeights( weights );
        }
    }


}
