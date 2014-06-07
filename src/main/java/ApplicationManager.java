import data.Data;
import data.Input;
import data.Output;
import geneticalgorithm.MotherNature;
import parser.Parser;
import utils.EpochCounter;

import java.util.ArrayList;
import java.util.List;

public class ApplicationManager {

    private final EpochCounter epochCounter;
    private Data learningData = new Data();
    private Data testingData = new Data();
    private MotherNature model;

    private Gui applicationGui;

    public ApplicationManager() {
        parseData();
        applicationGui = new Gui(this);
        epochCounter = new EpochCounter();
    }

    public static void main(String[] args) {
        new ApplicationManager();
    }

    private void parseData() {
        Data data = new Parser().getData();
        fillDataLists(data.getInputs(), data.getOutputs());
    }

    private void fillDataLists(ArrayList<Input> inputs, ArrayList<Output> outputs) {
        for (int i = 0; i < inputs.size(); i++) {
            if (i < 0.8 * inputs.size()) {
                learningData.inputData(inputs.get(i), outputs.get(i));
            } else {
                testingData.inputData(inputs.get(i), outputs.get(i));
            }
        }
    }

    public void runAutomatically(int iterations, double startingPressure, double endingPressure) {
        double pressurePerStep = calculateSelectionPressure(iterations, startingPressure, endingPressure);
        for (int i = epochCounter.get(); i < iterations; i++) {
            step(pressurePerStep);
            applicationGui.setCurrentEpochNo((epochCounter.getIncremented()) + "/" + iterations);
            applicationGui.repaint();
        }
    }

    public void runOnce(int iterations, double startingPression, double endingPression) {
        double pression = calculateSelectionPressure(iterations, startingPression, endingPression);
        step(pression);
        applicationGui.setCurrentEpochNo((epochCounter.getIncremented()) + "/" + iterations);
    }

    private double calculateSelectionPressure(int iterations, double startingPressure, double endingPressure) {
        return (endingPressure - startingPressure) / (double) iterations;
    }

    public void step(double pression) {
        model.nextEpoch();
        model.setSelectionPressure(model.getSelectionPression() + pression);
        applicationGui.setCurrentPopulationTextArea(model.getQualities());
    }

    public void setMatchSimilar(boolean value) {
        model.setMatchSimilar(value);
    }

    public void setEliteStategy(boolean value) {
        model.setEliteStrategy(value);
    }

    public void createMotherNature(int populationCount) {
        model = new MotherNature(populationCount, learningData, testingData);
        applicationGui.setStartingPopulationTextArea(model.getQualities());
        epochCounter.reset();
    }

    public List<Double> getTestedQualities() {
        return model.getTestedQualities();
    }
}
