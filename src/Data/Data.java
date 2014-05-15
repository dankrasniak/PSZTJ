package Data;

import java.util.ArrayList;

/**
 * Created by daniel on 11.05.14.
 */
public class Data {
    private ArrayList<Input> inputs = new ArrayList<Input>();
    private ArrayList<Output> outputs = new ArrayList<Output>();

  
    public void inputData( final Input input, final Output output ) {
        this.inputs.add( input );
        this.outputs.add( output );
    }

    /**
     * Compares the <b>Outputs</b> of given <b>Data Objects</b>.
     * @return percentage of tested data that output values equal expected output values.
     */
    public static final Double compare( final Data testedData, final Data expectedData ) {
        // TODO Comparing algorithm.
        return null;
    }
}
