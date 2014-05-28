package parser;

import data.Constants;
import data.Data;
import data.Input;
import data.Output;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * parser for input txt files.
 * Created by daniel on 10.05.14.
 */
public class Parser {
    private BufferedReader br;
    private Data data = new Data();

    public Parser() {
        try {
            br = new BufferedReader( new FileReader( "input.txt" ) );
            String line = br.readLine();

            while( line != null ) {
                parseLine( line );
                line = br.readLine();
            }
        } catch( FileNotFoundException e ) {
            e.printStackTrace();
        } catch( IOException e ) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch( IOException e ) {
                e.printStackTrace();
            }
        }
    }

    public static void main( String[] args ) {
        Parser parser = new Parser();
        Data data = parser.getData();

    }

    /**
     * Parses Line of txt from <b>Input File</b>, converts it to <b>Input and Output</b> and
     * adds it to local <b>data</b> variable.
     * @param line to parse and convert into useful data.
     */
    private void parseLine( String line ) {
        if( line.startsWith( "#" ) || line.length() < 5 ) return;
        line = line.trim();
        final String[] values = line.split( "\\s+" );
        ArrayList<Double> inputValues = new ArrayList<Double>( Constants.NUMBER_OF_INPUTS );

        int i = 0;
        do {
            inputValues.add( Double.parseDouble( values[ i ] ) );
        }
        while( ++i != Constants.NUMBER_OF_INPUTS );

        final Input input = new Input( inputValues );
        final Output output = new Output( Double.parseDouble( values[ i ] ) >= 0 );
        data.inputData( input, output );
    }

    /**
     * @return expected data values.
     */
    public final Data getData() {
        return data;
    }
}

