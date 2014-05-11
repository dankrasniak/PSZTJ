package Parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Parser for input txt files.
 * Created by daniel on 10.05.14.
 */
public class Parser {
    private BufferedReader br;

    public Parser() {
        try {
            br = new BufferedReader( new FileReader( "input.txt" ) );
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while( line != null ) {
                sb.append( line );
                sb.append( System.lineSeparator() );
                line = br.readLine();
            }
            String everything = sb.toString();
        } catch ( FileNotFoundException e ) {
            e.printStackTrace();
        } catch ( IOException e ) {
            e.printStackTrace();
        } finally {
            br.close();
        }
    }


}
