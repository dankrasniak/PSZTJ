package neuralnetwork;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import geneticalgorithm.Fenotype;
import geneticalgorithm.NewBitSet;

/**
 * Manages Neural transmissions.
 * Created by daniel on 08.05.14.
 */
public class NeuralNetwork {
    private ArrayList<NeuralLayer> neuralLayers;

    //TODO for testing only!
    public NeuralNetwork()
    {
    }
    
    public NeuralNetwork( final ArrayList<NeuralLayer> neuralLayers ){
        this.neuralLayers = neuralLayers;
    }

    /**
     * Adds up <b>Weights</b> from  all the <b>Neurons</b> from every <b>Layer</b> in the <b>Network</b>.
     * @return weights.
     */
    /*public final ArrayList<Weights> getWeights() { // TODO
        ArrayList<Weights> weights = new ArrayList<Weights>();
        for( NeuralLayer neuralLayer : neuralLayers )
            weights.addAll( neuralLayer.getWeights() );
        return weights;
    }*/
    // TODO for testing only!
    public final NewBitSet getWeights() {
        NewBitSet result = new NewBitSet(40);
        System.out.println(result.realSize());
        
        
        for(int i = 0; i < 40; i++)
        {
        	Random random = new Random();
        	if(random.nextBoolean())
        		result.set(i);
        }
        return result;
    }
    
    /**
     * Uploads new <b>Weights</b> to the <b>neuralnetwork</b>.
     */
    public final void uploadWeights( ArrayList<Weights> weights ) {
        Iterator<NeuralLayer> nerualLayerIterator = neuralLayers.iterator();
        while( nerualLayerIterator.hasNext() ) {
            nerualLayerIterator.next().uploadNewWeights( weights );
        }
    }
    
    
    
    // TODO for testing only!
    public final double testFenotype(final Fenotype fenotype)
    {
    	Random random = new Random();
    	return random.nextDouble();
    }
}
