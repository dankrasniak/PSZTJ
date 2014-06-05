import javax.swing.*;
import java.util.List;

/**
 * Created by lukasz on 5/31/14.
 */
public class Gui extends JFrame{

    private static final String title = "Podstawy Sztucznej Inteligencji";

    private JButton nextEpoch;
    private JProgressBar progress;
    private JPanel root;
    private JTextField startingSelectionPression;
    private JTextField endingSelectionPression;
    private JTextField epochCount;
    private JCheckBox eliteStrategy;
    private JButton run;
    private JTextArea startingPopulationTextArea;
    private JTextArea currentPopulationTextArea;
    private JCheckBox similarMatch;
    private JTextField textField1;
    private JTable epochResults;

    public Gui() {
        super(title);
        setContentPane(root);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }



    public void setPopulationTextArea(JTextArea area, List<Double> startingPopulation) {
        area.setText("");
        for (Double fenotype : startingPopulation) {
            area.append(fenotype + "\n");
        }
    }
}
