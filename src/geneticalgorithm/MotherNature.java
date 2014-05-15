package geneticalgorithm;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import NeuralNetwork.NeuralNetwork;



public class MotherNature 
{
	private List<Fenotype> population;
	
	public MotherNature(final NeuralNetwork neuralNetwork, final int populationSize)
	{
		population = new LinkedList<Fenotype>();
		// Pobranie wag, ktore aktualnie znajduja sie w sieci.
		// Dowiadujemy sie przy tym o ilosci roznych wag.
		List<Double> startingWeights = neuralNetwork.getWeights();
		
		Fenotype tmp;
		// Generating first population
		for(int i = 0; i < populationSize; i++)
		{
			tmp = new Fenotype(startingWeights.size());
			tmp.setQuality(testFenotype(tmp));
			population.add(tmp);
		}
	}
	
	private double testFenotype(final Fenotype fenotype)
	{
		double result = 0;
		//TODO test this fenotype in NeuralNetwork and save the result quality in this fenotype.
		return result;
	}
	
	/**
	 * Generates new offspring. He will have wife's genotype length.
	 * If father's genotype is too short - all redundant wife's genes
	 * will be taken (with mutation).
	 * @param mutationProbability - 0 - never, 1 - always
	 */
	private Fenotype generateOffspring(final Fenotype wife, final Fenotype husband, final double mutationProbability)
	{		
		//TODO assert
		Fenotype offspring = new Fenotype(wife.getGenotype().size());
		Random random = new Random();
		boolean newGene;
		for(int i = 0; i < wife.getGenotype().size(); i++)
		{
			// If we randomed (50%) true or husband's genotype is exceeded, we take wife's gene
			if(random.nextBoolean() || i >= husband.getGenotype().size())
			{
				newGene =  wife.getGene(i);
			}
			else
			{
				newGene =  husband.getGene(i);
			}
			if(random.nextDouble() <= mutationProbability)
			{
				newGene = !newGene;
			}
			offspring.setGene(i, newGene);
		}
		return offspring;
	}
	
	
}
