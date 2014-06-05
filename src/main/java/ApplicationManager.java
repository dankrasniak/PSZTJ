import data.Data;
import data.Input;
import data.Output;
import geneticalgorithm.MotherNature;
import neuralnetwork.NeuralLayer;
import neuralnetwork.NeuralNetwork;
import neuralnetwork.Neuron;
import neuralnetwork.Weights;
import parser.Parser;

import java.util.ArrayList;
import java.util.Random;

public class ApplicationManager {

    private MotherNature motherNature;
    private  Gui applicationGui;
    private  NeuralNetwork neuralNetwork;
    private Data learningData = new Data();
    private Data testingData = new Data();
    private int stepCounter = 0;

    public ApplicationManager() {
        parseData();
        neuralNetwork = new NeuralNetwork(createNeuralLayers());
        applicationGui = new Gui(this);
    }

    private ArrayList<NeuralLayer> createNeuralLayers() {
        Random random = new Random();
        ArrayList<Neuron> neurons = new ArrayList<Neuron>();
        ArrayList<Double> weights;
        ArrayList<NeuralLayer> neuralLayers = new ArrayList<NeuralLayer>();
        for (int i = 0; i < 3; i++) {
            weights = new ArrayList<Double>();
            for (int j = 0; j < 3; j++) {
                weights.add(random.nextDouble());
            }
            neurons.add(new Neuron(new Weights(weights)));
        }
        neuralLayers.add(new NeuralLayer(neurons));
        neurons = new ArrayList<Neuron>();
        weights = new ArrayList<Double>();
        for (int j = 0; j < 3; j++) {
            weights.add(random.nextDouble());
        }
        neurons.add(new Neuron(new Weights(weights)));
        neuralLayers.add(new NeuralLayer(neurons));
        return neuralLayers;
    }

    private void parseData() {
        Parser parser = new Parser();
        Data data = parser.getData();

        ArrayList<Input> inputs = data.getInputs();
        ArrayList<Output> outputs = data.getOutputs();

        for (int i = 0; i < inputs.size(); i++) {
            if (i < 0.8 * inputs.size()) {
                learningData.inputData(inputs.get(i), outputs.get(i));
            } else {
                testingData.inputData(inputs.get(i), outputs.get(i));
            }
        }
    }

    public void runAutomatically(int iterations, double startingPression, double endingPression) {
        double pression = calculatePression(iterations, startingPression, endingPression);
        for (int i = 0; i < iterations; i++) {
            step(pression);
            applicationGui.setCurrentEpochNo(i + 1 + "/" + iterations);
        }
    }

    public void runOnce(int iterations, double startingPression, double endingPression) {
        double pression = calculatePression(iterations, startingPression, endingPression);
        step(pression);
        applicationGui.setCurrentEpochNo("*/" + iterations);

    }

    private double calculatePression(int iterations, double startingPression, double endingPression) {
        return (endingPression - startingPression) / (double) iterations;
    }

    public void step(double pression) {
        motherNature.nextEpoch();
        motherNature.setSelectionPressure(motherNature.getSelectionPressure() + pression);
        applicationGui.setCurrentPopulationTextArea(motherNature.getQualities());
    }

    public void setMatchSimilar(boolean value) {
        motherNature.setMatchSimilar(value);
    }

    public void setEliteStategy(boolean value) {
        motherNature.setEliteStrategy(value);
    }

    public void createMotherNature(int populationCount) {
        motherNature = new MotherNature(neuralNetwork, populationCount , learningData, testingData);
        applicationGui.setStartingPopulationTextArea(motherNature.getQualities());
        stepCounter = 0;
    }

    public static void main(String[] args) {
        new ApplicationManager();
    }


}
