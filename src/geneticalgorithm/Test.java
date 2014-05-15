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
		
		
		/*int quantities[] = new int[4];
		quantities[0] = 0;
		quantities[1] = 0;
		quantities[2] = 0;
		quantities[3] = 0;
		double tmp;
		for(int i = 0; i < 1000; i++)
		{
			tmp = motherNature.getParent().getQuality();
			if(tmp == motherNature.getFenotype(0).getQuality())
				quantities[0]++;
			if(tmp == motherNature.getFenotype(1).getQuality())
				quantities[1]++;
			if(tmp == motherNature.getFenotype(2).getQuality())
				quantities[2]++;
			if(tmp == motherNature.getFenotype(3).getQuality())
				quantities[3]++;
		}
		System.out.println(quantities[0]);
		System.out.println(quantities[1]);
		System.out.println(quantities[2]);
		System.out.println(quantities[3]);
		System.out.println("");
		
		System.out.println(motherNature.getFenotype(0).getGenotype().realSize());
		for(int i = 0; i < 40; i++)
		{
			if(motherNature.getFenotype(0).getGene(i))
				System.out.print(1);
			else
				System.out.print(0);
		}
		System.out.println("");
		for(int i = 0; i < 40; i++)
		{
			if(motherNature.getFenotype(1).getGene(i))
				System.out.print(1);
			else
				System.out.print(0);
		}
		System.out.println("");

		System.out.println(motherNature.compareFenotypes(motherNature.getFenotype(0), motherNature.getFenotype(1)));*/
		
		
	}
}
