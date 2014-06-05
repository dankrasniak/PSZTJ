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
import java.util.Collections;
import java.util.Random;

/**
 * Created by Maciej on 2014-05-28.
 */
public class Psztj {
    public static void main(String[] args) {
        Parser parser = new Parser();
        Data data = parser.getData();
        Data learningData = new Data();
        Data testingData = new Data();
        ArrayList<Input> inputs = data.getInputs();
        ArrayList<Output> outputs = data.getOutputs();
        for(int i = 0; i < inputs.size(); i++){
            if(i < 0.7*inputs.size()) {
                learningData.inputData(inputs.get(i), outputs.get(i));
            } else {
                testingData.inputData(inputs.get(i), outputs.get(i));
            }
        }

        Random random = new Random();
        ArrayList<Double> weights;
        ArrayList<Neuron> neurons = new ArrayList<Neuron>();
        ArrayList<NeuralLayer> neuralLayers = new ArrayList<NeuralLayer>();
        for(int i = 0; i < 3; i++) {
            weights = new ArrayList<Double>();
            for(int j = 0; j < 3; j++) {
                weights.add(random.nextDouble());
            }
            neurons.add(new Neuron(new Weights(weights)));
        }
        neuralLayers.add(new NeuralLayer(neurons));
        neurons = new ArrayList<Neuron>();
        weights = new ArrayList<Double>();
        for(int j = 0; j < 3; j++) {
            weights.add(random.nextDouble());
        }
        neurons.add(new Neuron(new Weights(weights)));
        neuralLayers.add(new NeuralLayer(neurons));

        NeuralNetwork neuralNetwork = new NeuralNetwork(neuralLayers);
        MotherNature motherNature = new MotherNature(neuralNetwork, 20, learningData, testingData);
        System.out.println("Poczatkowe: ");
        motherNature.printPopulationQualities();
        /*motherNature.nextEpoch(0.5);
        System.out.println("Nowe: ");
        motherNature.printPopulationQualities();
        motherNature.nextEpoch(0.5);
        System.out.println("Nowe: ");
        motherNature.printPopulationQualities();
        motherNature.nextEpoch(0.5);
        System.out.println("Nowe: ");
        motherNature.printPopulationQualities();
        motherNature.nextEpoch(0.5);
        System.out.println("Nowe: ");
        motherNature.printPopulationQualities();*/
        //motherNature.printPopulationQualities();
        double mutationIntensity = 1.0;
        for(int i = 0; i < 100; i++) {
            motherNature.nextEpoch();
        }

        System.out.println("Nowe: ");
        motherNature.printPopulationQualities();

        System.out.println("Przetestowane: ");
        ArrayList<Double> qualities = (ArrayList)motherNature.getTestedQualities();
        for(Double quality : qualities)
        {
            System.out.println(quality);
        }

        System.out.println("Learning data:");
        for(Output output : learningData.getOutputs())
        {
            if(output.getOutput())
                System.out.print("1");
            else
                System.out.print("0");
        }
        System.out.println();
        System.out.println("Testing data:");
        for(Output output : testingData.getOutputs())
        {
            if(output.getOutput())
                System.out.print("1");
            else
                System.out.print("0");
        }



        //Gui applicationGui = new Gui();


    }
}
