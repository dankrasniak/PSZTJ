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

    public MotherNature motherNature;
    public Gui applicationGui;

    public ApplicationManager() {

        Parser parser = new Parser();
        Data data = parser.getData();
        Data learningData = new Data();
        Data testingData = new Data();

        ArrayList<Input> inputs = data.getInputs();
        ArrayList<Output> outputs = data.getOutputs();

        for (int i = 0; i < inputs.size(); i++) {
            if (i < 0.8 * inputs.size()) {
                learningData.inputData(inputs.get(i), outputs.get(i));
            } else {
                testingData.inputData(inputs.get(i), outputs.get(i));
            }
        }

        Random random = new Random();
        ArrayList<Double> weights;
        ArrayList<Neuron> neurons = new ArrayList<Neuron>();
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

        NeuralNetwork neuralNetwork = new NeuralNetwork(neuralLayers);
        motherNature = new MotherNature(neuralNetwork, 20, learningData, testingData);
        System.out.println("Poczatkowe: ");
        motherNature.printPopulationQualities();

//        for(int i = 0; i < 1000; i++) {
//            motherNature.nextEpoch();
//        }

        System.out.println("Nowe: ");
        motherNature.printPopulationQualities();

        System.out.println("Przetestowane: ");
        ArrayList<Double> qualities = (ArrayList) motherNature.getTestedQualities();
        for (Double quality : qualities) {
            System.out.println(quality);
        }

        applicationGui = new Gui(this);
        applicationGui.setStartingPopulationTextArea(motherNature.getQualities());
    }

    public void runAutomatically(int iterations, double startingPression, double endingPression) {
        double pression = calculatePression(iterations, startingPression, endingPression);
        for (int i = 0; i < iterations; i++) {
            step(pression);
        }
    }

    public void runOnce(int iterations, double startingPression, double endingPression) {
        double pression = calculatePression(iterations, startingPression, endingPression);

    }

    private double calculatePression(int iterations, double startingPression, double endingPression) {
        return (endingPression - startingPression) / (double) iterations;
    }

    public void step(double pression) {
        motherNature.nextEpoch();
        motherNature.setSelectionPressure(motherNature.getSelectionPressure() + pression);
        applicationGui.setCurrentPopulationTextArea(motherNature.getQualities());
    }

    public static void main(String[] args) {
        new ApplicationManager();
    }


}
