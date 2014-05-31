package geneticalgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import data.Data;
import data.Output;
import neuralnetwork.NeuralNetwork;



public class MotherNature 
{
	private List<Fenotype> population;
	private List<Fenotype> offsprings;
	private NeuralNetwork neuralNetwork;
	// Used in getParent() for unlinear randoming
	private double populationQualitySum;
	private Data learningData;
    private boolean eliteStrategy;
	
	public MotherNature(final NeuralNetwork neuralNetwork, final int populationSize, final Data learningData)
	{
		this.neuralNetwork = neuralNetwork;
        this.learningData = learningData;
        this.eliteStrategy = true;
		this.population = new ArrayList<Fenotype>();
		this.offsprings = new ArrayList<Fenotype>();
		// Get actual weights of neural network, which will tell by the way about quantity of all weights.
		ArrayList<Double> startingWeights = neuralNetwork.getDoubleWeights();
		
		Fenotype tmp;
        ArrayList<Output> receivedOutputs;
		// Generating first population
		for(int i = 0; i < populationSize; i++)
		{
			tmp = generateRandomFenotype(startingWeights.size());
            neuralNetwork.uploadWeights(tmp.getGenotype());
            receivedOutputs = neuralNetwork.computeData(learningData);
			tmp.setQuality(rateOutputs(receivedOutputs));
            System.out.println(tmp.getQuality());
			population.add(tmp);
		}
		Collections.sort(population);
		calcPopulationQualitySum();
	}
	
	
	public void nextEpoch(final double mutationIntensity)
	{
        if(mutationIntensity < 0 || mutationIntensity > 1){
            //TODO
            throw new RuntimeException();
        }
		int populationSize = population.size();
		for(int i = 0; i < populationSize; i++)
		{
			reproduce(mutationIntensity);
		}
		population.addAll(offsprings);
		Collections.sort(population);
		
		List<Fenotype> newPopulation = new ArrayList<Fenotype>();
		// Elite strategy - we always take the best Fenotype to the next population
        if(eliteStrategy)
        {
            newPopulation.add(population.get(0));
            population.remove(0);
            populationSize--;
        }
		
		calcPopulationQualitySum();
		// We choose parents for our next epoch
		for(int i = 0; i < populationSize; i++)
		{
			newPopulation.add(getParent(false));
		}
		population = newPopulation;
		Collections.sort(population);
		calcPopulationQualitySum();
	}


    public void setEliteStrategy(final boolean eliteStrategy)
    {
        this.eliteStrategy = eliteStrategy;
    }


    public boolean getEliteStrategy()
    {
        return eliteStrategy;
    }


    public List<Double> getPopulationQualities()
    {
        ArrayList<Double> qualities = new ArrayList<Double>();
        for(Fenotype fenotype : population)
        {
            qualities.add(fenotype.getQuality());
        }
        return qualities;
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
    public void printWeights()
    {
        for(Fenotype fenotype : population)
        {
            for(Double weight : fenotype.getGenotype())
            {
                System.out.print(weight + "  ");
            }
            System.out.println();
        }
    }


    private void reproduce(final double mutationIntensity)
	{
		Random random = new Random();
		Fenotype wife = getParent(true);
		Fenotype husband = getParent(true);
        double similarity;
        //TODO maybe something more elegant
		for(int i = 0; i < 10; i++)
		{
            similarity = compareFenotypes(wife, husband);
			if(similarity > (0.7-mutationIntensity/2) && similarity != 1)
			{
				break;
			}
			husband = getParent(true);
		}
		
		Fenotype offspring = generateOffspring(wife, husband, neuralNetwork, mutationIntensity, mutationIntensity/2);
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
	 * and c is maximal difference between weights which is... about 2
	 * 
	 */
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
			result += ( 1 - ( (double)Math.pow((Math.abs(firstWeight - secondWeight)), 2) / 4.0 ) );
		}
		return result / (first.getGenotype().size());
	}


    private Fenotype generateOffspring(final Fenotype wife, final Fenotype husband, final NeuralNetwork neuralNetwork,
        final double mutationProbability, final double mutationStrength)
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
                newGene += newGene*(random.nextDouble() - 0.5)*mutationStrength;
            }
            offspringGenotype.add(newGene);
        }
        Fenotype offspring = new Fenotype(offspringGenotype);
        neuralNetwork.uploadWeights(offspring.getGenotype());
        ArrayList<Output> receivedOutputs = neuralNetwork.computeData(learningData);
        offspring.setQuality(rateOutputs(receivedOutputs));
        return offspring;
    }


    private Fenotype generateRandomFenotype(int genotypeSize)
    {
        ArrayList<Double> genotype = new ArrayList<Double>();
        Random random = new Random();
        for(int i = 0; i < genotypeSize; i++)
        {
            genotype.add(random.nextDouble() - 0.5);
        }
        return new Fenotype(genotype);
    }


    private Double rateOutputs(final ArrayList<Output> receivedOutputs)
    {
        double TP = 0;
        double FP = 0;
        double TN = 0;
        double FN = 0;
        ArrayList<Output> expectedOutputs = learningData.getOutputs();
        for(int i = 0; i < receivedOutputs.size(); i++)
        {
            if(expectedOutputs.get(i).getOutput() && receivedOutputs.get(i).getOutput())
                ++TP;
            if(expectedOutputs.get(i).getOutput() && !receivedOutputs.get(i).getOutput())
                ++FN;
            if(!expectedOutputs.get(i).getOutput() && !receivedOutputs.get(i).getOutput())
                ++TN;
            if(!expectedOutputs.get(i).getOutput() && receivedOutputs.get(i).getOutput())
                ++FP;
        }
        Double precision = ( TP / (TP + FP) );
        Double recall = ( TP / (TP + FN) );
        return ( precision + recall ) / 2;
    }
}
