package geneticalgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import neuralnetwork.NeuralNetwork;



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
		ArrayList<Double> startingWeights = neuralNetwork.getDoubleWeights();
		
		Fenotype tmp;
		// Generating first population
		for(int i = 0; i < populationSize; i++)
		{
			tmp = generateRandomFenotype(startingWeights.size());
			tmp.setQuality(0);
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
	 * and c is maximal difference between weights which is... about 255
	 * 
	 */
    //TODO change to double
	private double compareFenotypes(final Fenotype first, final Fenotype second)
	{
		double result = 0;
		if(first.getGenotype().size() != second.getGenotype().size())
		{
			return result;
		}
		Double firstWeight;
		Double secondWeight;
		for(int i = 0; i < first.getGenotype().size(); i++)
		{
			firstWeight = first.getGene(i);
			secondWeight = second.getGene(i);
			result += ( 1 - ( (double)Math.pow((Math.abs(firstWeight - secondWeight)), 2) / 65025.0 ) );
		}
		return result / (first.getGenotype().size());
	}


	private Fenotype generateOffspringBinary(final Fenotype wife, final Fenotype husband, final double mutationProbability, final NeuralNetwork neuralNetwork)
    {
		assert mutationProbability >= 0 && mutationProbability <= 1;

        ArrayList<Double> offspringGenotype = new ArrayList<Double>();
        Random random = new Random();
        char[] wifeGene;
        char[] husbandGene;
        char[] offspringGene;
        for(int i = 0; i < wife.getGenotype().size(); ++i)
        {
            wifeGene = Long.toString(Double.doubleToLongBits(wife.getGene(i)), 2).toCharArray();
            husbandGene = Long.toString(Double.doubleToLongBits(husband.getGene(i)), 2).toCharArray();
            offspringGene = new char[wifeGene.length];
            for(int j = 0; j < wifeGene.length; ++j)
            {
                // If we randomed (50%) true or husband's genotype is exceeded, we take wife's gene
                if(random.nextBoolean() || i >= husband.getGenotype().size())
                {
                    offspringGene[j] = wifeGene[j];
                }
                else
                {
                    offspringGene[j] = husbandGene[j];
                }
                if(random.nextDouble() <= mutationProbability)
                {
                    if(offspringGene[j] == '0')
                    {
                        offspringGene[j] = '1';
                    }
                    else
                    {
                        offspringGene[j] = '0';
                    }
                }
            }
            offspringGenotype.add(Double.longBitsToDouble(Long.parseLong(new String(offspringGene), 2)));
        }
        return new Fenotype(offspringGenotype);
	}


    private Fenotype generateOffspring(final Fenotype wife, final Fenotype husband, final double mutationProbability, final NeuralNetwork neuralNetwork)
    {
        ArrayList<Double> offspringGenotype = new ArrayList<Double>();
        Random random = new Random();
        Double newGene;
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
                newGene *= (random.nextDouble() - 0.5)/10;
            }
            offspringGenotype.add(newGene);
        }
        return new Fenotype(offspringGenotype);
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



    private Fenotype generateRandomFenotype(int genotypeSize)
    {
        ArrayList<Double> genotype = new ArrayList<Double>();
        Random random = new Random();
        for(int i = 0; i < genotypeSize; i++)
        {
            genotype.add(random.nextDouble()*20 - 10);
        }
        return new Fenotype(genotype);
    }

}
