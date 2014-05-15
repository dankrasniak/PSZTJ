package geneticalgorithm;

import NeuralNetwork.NeuralNetwork;

public class Test {
	public static void main(String args[])
	{
		NeuralNetwork network = new NeuralNetwork();
		MotherNature motherNature = new MotherNature(network, 6);
		
		motherNature.printPopulationQualities();
		
		System.out.println("");
		
		motherNature.nextEpoch();
		motherNature.printPopulationQualities();
		System.out.println("");
		motherNature.nextEpoch();
		motherNature.printPopulationQualities();
		System.out.println("");
		motherNature.nextEpoch();
		motherNature.printPopulationQualities();
		System.out.println("");
		motherNature.nextEpoch();
		motherNature.printPopulationQualities();
		System.out.println("");
		motherNature.nextEpoch();
		motherNature.printPopulationQualities();
		System.out.println("");
		motherNature.nextEpoch();
		motherNature.printPopulationQualities();
		System.out.println("");
		
	}
}
