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
    private JCheckBox eliteStrategyCheckBox;
    private JButton runAutomaticallyBtn;
    private JTextArea startingPopulationTextArea;
    private JTextArea currentPopulationTextArea;
    private JCheckBox matchSimilarCheckBox;
    private JTextField currentEpochNo;
    private JButton newPopulationBtn;
    private JTextField populationSize;
    private JButton testPopulationBtn;
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
        newPopulationBtn.addActionListener(newPopulationBtnClicked);
        testPopulationBtn.addActionListener(testPopulationBtnClicked);
    }

    public void setCurrentEpochNo(String value) {
        currentEpochNo.setText(value);
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

    private void setCheckboxParameters() {
        manager.setEliteStategy(eliteStrategyCheckBox.isSelected());
        manager.setMatchSimilar(matchSimilarCheckBox.isSelected());
    }

    private double getStartingSelectionPression() {
        return Double.parseDouble(startingSelectionPression.getText());
    }

    private double getEndingSelectionPrecision() {
        return Double.parseDouble(endingSelectionPression.getText());
    }

    private void runNumberParametersErrorMsg() {
        JOptionPane.showMessageDialog(this, "Please provide valid epochs number, starting pression and ending pression!", "Failure", JOptionPane.ERROR_MESSAGE);
    }

    private void invalidPopulationSizeErrorMsg() {
        JOptionPane.showMessageDialog(this, "Please provide valid population size!", "Failure", JOptionPane.ERROR_MESSAGE);
    }

    ActionListener nextEpochBtnClicked = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int iterations = Integer.parseInt(epochCount.getText());
                double startingPression = getStartingSelectionPression();
                double endingPression = getEndingSelectionPrecision();
                setCheckboxParameters();

                manager.runOnce(iterations, startingPression, endingPression);
            } catch (NumberFormatException ex) {
                runNumberParametersErrorMsg();
            }
        }
    };
    ActionListener runAutomaticallyBtnClicked = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int iterations = Integer.parseInt(epochCount.getText());
                double startingPression = getStartingSelectionPression();
                double endingPression = getEndingSelectionPrecision();
                setCheckboxParameters();
                manager.runAutomatically(iterations, startingPression, endingPression);
            } catch (NumberFormatException ex) {
                runNumberParametersErrorMsg();
            }
        }
    };
    ActionListener newPopulationBtnClicked = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int populationQuantity = Integer.parseInt(populationSize.getText());
                runAutomaticallyBtn.setEnabled(true);
                nextEpochBtn.setEnabled(true);
                startingPopulationTextArea.setText("");
                currentPopulationTextArea.setText("");
                manager.createMotherNature(populationQuantity);

            } catch (NumberFormatException ex) {
                invalidPopulationSizeErrorMsg();
            }
        }
    };
    ActionListener testPopulationBtnClicked = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            setCurrentPopulationTextArea(manager.getTestedQualities());
        }
    };
}
