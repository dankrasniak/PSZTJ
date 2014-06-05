import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created by lukasz on 5/31/14.
 */
public class Gui extends JFrame {

    private static final String title = "Podstawy Sztucznej Inteligencji";

    private JButton nextEpochBtn;
    private JProgressBar progress;
    private JPanel root;
    private JTextField startingSelectionPression;
    private JTextField endingSelectionPression;
    private JTextField epochCount;
    private JCheckBox eliteStrategy;
    private JButton runAutomaticallyBtn;
    private JTextArea startingPopulationTextArea;
    private JTextArea currentPopulationTextArea;
    private JCheckBox similarMatch;
    private JTextField textField1;
    private JTable epochResults;

    private ApplicationManager manager;

    public Gui(ApplicationManager manager) {
        super(title);
        this.manager = manager;
        setContentPane(root);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        nextEpochBtn.addActionListener(nextEpochBtnClicked);
        runAutomaticallyBtn.addActionListener(runAutomaticallyBtnClicked);
    }

    public void setStartingPopulationTextArea(List<Double> population) {
        setPopulationTextAreaText(startingPopulationTextArea, population);
    }

    public void setCurrentPopulationTextArea(List<Double> population) {
        setPopulationTextAreaText(currentPopulationTextArea, population);
    }

    private void setPopulationTextAreaText(JTextArea area, List<Double> population) {
        area.setText("");
        for (Double fenotype : population) {
            area.append(fenotype + "\n");
        }
    }

    ActionListener nextEpochBtnClicked = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int iterations = Integer.parseInt(epochCount.getText());
                double startingPression = Double.parseDouble(startingSelectionPression.getText());
                double endingPression = Double.parseDouble(endingSelectionPression.getText());
                manager.runOnce(iterations, startingPression, endingPression);
            } catch (NumberFormatException ex) {
                numberFormatErrorMsg();
            }
        }
    };

    ActionListener runAutomaticallyBtnClicked = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int iterations = Integer.parseInt(epochCount.getText());
                double startingPression = Double.parseDouble(startingSelectionPression.getText());
                double endingPression = Double.parseDouble(endingSelectionPression.getText());
                manager.runAutomatically(iterations, startingPression, endingPression);
            } catch (NumberFormatException ex) {
                numberFormatErrorMsg();
            }
        }
    };

    private void numberFormatErrorMsg() {
        JOptionPane.showMessageDialog(this, "Please provide valid epochs number, starting pression and ending pression!", "Failure", JOptionPane.ERROR_MESSAGE);
    }
}
