package geneticalgorithm;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import NeuralNetwork.NeuralNetwork;



public class MotherNature 
{
	private List<Fenotype> population;
	private List<Fenotype> offsprings;
	private NeuralNetwork neuralNetwork;
	// Used in getParent() for unlinear randoming
	private double populationQualitySum;
	
	
	public MotherNature(final NeuralNetwork neuralNetwork, final int populationSize)
	{
		this.neuralNetwork = neuralNetwork;
		population = new ArrayList<Fenotype>();
		offsprings = new ArrayList<Fenotype>();
		// Get actual weights of neural network, which will tell by the way about quantity of all weights.
		NewBitSet startingWeights = neuralNetwork.getWeights();
		
		Fenotype tmp;
		// Generating first population
		for(int i = 0; i < populationSize; i++)
		{
			tmp = new Fenotype(startingWeights.realSize());
			tmp.setQuality(neuralNetwork.testFenotype(tmp));
			population.add(tmp);
		}
		Collections.sort(population);
		calcPopulationQualitySum();
	}
	
	
	public void nextEpoch()
	{
		int populationSize = population.size();
		for(int i = 0; i < populationSize; i++)
		{
			reproduce();
		}
		population.addAll(offsprings);
		Collections.sort(population);
		
		List<Fenotype> newPopulation = new ArrayList<Fenotype>();
		// Elite strategy - we always take the best Fenotype to the next population
		newPopulation.add(population.get(0));
		population.remove(0);
		
		calcPopulationQualitySum();
		// We choose parents for our next epoch
		for(int i = 1; i < populationSize; i++)
		{
			newPopulation.add(getParent(false));
		}
		population = newPopulation;
		Collections.sort(population);
		calcPopulationQualitySum();
	}
	
	
	private void reproduce()
	{
		Random random = new Random();
		Fenotype wife = getParent(true);
		Fenotype husband = getParent(true);
		for(int i = 0; i < 1000; i++)
		{
			if(husband.getQuality() != wife.getQuality() && 
				random.nextDouble() < compareFenotypes(wife, husband))
			{
				break;
			}
			husband = getParent(true);
		}
		
		Fenotype offspring = generateOffspring(wife, husband, 0.01, neuralNetwork);
		offsprings.add(offspring);
	}
	
	
	private Fenotype getParent(boolean withReturning)
	{
		Random random = new Random();
		double randomed = random.nextDouble();
		double tmpSum = 0;
		for(int i = 0; i < population.size(); i++)
		{
			tmpSum += population.get(i).getQuality() / populationQualitySum;
			if(tmpSum >= randomed)
			{
				Fenotype result = population.get(i);
				if(!withReturning)
				{
					populationQualitySum -= result.getQuality();
					population.remove(i);
				}
				return result;
			}
		}
		return population.get(population.size()-1);
	}
	
	
	/**
	 * Used in getParent() for unlinear randoming
	 */
	private void calcPopulationQualitySum()
	{
		populationQualitySum = 0;
		for(Fenotype fenotype : population)
		{
			populationQualitySum += fenotype.getQuality();
		}
	}
	
	
	/**
	 * FOR EXPERIMENTAL USAGE - not sure if it will reduce runtime
	 * Calculates similarity of two Fenotypes using the following formula:<br>
	 * <br>
	 * 					|| x - y ||^2		<br>
	 * 			1  -  ----------------		<br>
	 * 						c^2				<br>
	 * <br>
	 * Where x is single weight of first fenotype, y is from second fenotype
	 * and c is maximal difference between weights which is 255
	 * 
	 */
	private double compareFenotypes(final Fenotype first, final Fenotype second)
	{
		double result = 0;
		if(first.getGenotype().realSize() != second.getGenotype().realSize())
		{
			return result;
		}
			
		byte firstWeight;
		byte secondWeight;
		for(int i = 0; i < first.getGenotype().realSize(); i += 8)
		{
			firstWeight = toByte(new NewBitSet(8, first.getGenotype().get(i, i+8)));
			secondWeight = toByte(new NewBitSet(8, second.getGenotype().get(i, i+8)));
			result += ( 1 - ( (double)Math.pow((Math.abs(firstWeight - secondWeight)), 2) / 65025.0 ) );
		}
		return result / (first.getGenotype().realSize() / 8);
	}
	
	
	private byte toByte(NewBitSet bitSet)
	{
		if(bitSet.realSize() != 8)
			throw new RuntimeException();
		byte result = 0;
		if(bitSet.get(0))
			result += -128;
		if(bitSet.get(1))
			result += 64;
		if(bitSet.get(2))
			result += 32;
		if(bitSet.get(3))
			result += 16;
		if(bitSet.get(4))
			result += 8;
		if(bitSet.get(5))
			result += 4;
		if(bitSet.get(6))
			result += 2;
		if(bitSet.get(7))
			result += 1;
		
		return result;
	}
	

	private Fenotype generateOffspring(final Fenotype wife, final Fenotype husband, final double mutationProbability, final NeuralNetwork neuralNetwork)
	{		
		assert mutationProbability >= 0 && mutationProbability <= 1;
		Fenotype offspring = new Fenotype(wife.getGenotype().realSize());
		Random random = new Random();
		boolean newGene;
		for(int i = 0; i < wife.getGenotype().realSize(); i++)
		{
			// If we randomed (50%) true or husband's genotype is exceeded, we take wife's gene
			if(random.nextBoolean() || i >= husband.getGenotype().realSize())
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
		//offspring.setQuality( neuralNetwork.testFenotype(offspring) );
		// TODO FOR TESTING ONLY! Replace with above
		offspring.setQuality((wife.getQuality() + husband.getQuality()) / 2);
		//System.out.println(offspring.getQuality() + "   " + wife.getQuality() + "   " + husband.getQuality());
		return offspring;
	}
	
	

	/**
	 //TODO
	 * For testing only
	 */
	public void printPopulationQualities()
	{
		for(int i = 0; i < population.size(); i++)
		{
			System.out.println(population.get(i).getQuality());
		}
	}
	
	/**
	 //TODO
	 * For testing only
	 */
	public void printOffspringsQualities()
	{
		for(int i = 0; i < offsprings.size(); i++)
		{
			System.out.println(offsprings.get(i).getQuality());
		}
	}

}
