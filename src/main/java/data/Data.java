package data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by daniel on 11.05.14.
 */
public class Data {
    private ArrayList<Input> inputs = new ArrayList<Input>();
    private ArrayList<Output> outputs = new ArrayList<Output>();

  
    public void inputData( final Input input, final Output output ) {
        Random random = new Random();
        int index = 0;
        if( inputs.size() > 0 ) {
            index = random.nextInt(inputs.size());
        }
        this.inputs.add( index, input );
        this.outputs.add( index, output );
    }

    public ArrayList<Input> getInputs(){
        return inputs;
    }

    public ArrayList<Output> getOutputs() {
        return outputs;
    }
    /**
     * Compares the <b>Outputs</b> of given <b>data Objects</b>.
     * @return percentage of tested data that output values equal expected output values.
     */
    public static final Double compare( final Data testedData, final Data expectedData ) {
        // TODO Comparing algorithm.
        return null;
    }
}
