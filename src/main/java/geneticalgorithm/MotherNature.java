package geneticalgorithm;

import java.util.*;

import data.Data;
import data.Output;
import neuralnetwork.NeuralNetwork;



public class MotherNature 
{
	private List<Fenotype> population;
	private List<Fenotype> offsprings;
	private NeuralNetwork neuralNetwork;
	private Data learningData;
    private Data testingData;
    private boolean eliteStrategy;
    // Recommended between 0.5 and 1.5
    private Double selectionPression;
    private boolean matchSimilar;
    // Used for comparing genotypes
    private Double lowestGene;
    private Double highestGene;


    public MotherNature(final int populationSize,
        final Data learningData, final Data testingData)
	{
		this.neuralNetwork = new NeuralNetwork();
        this.learningData = learningData;
        this.testingData = testingData;
        this.eliteStrategy = true;
		this.population = new LinkedList<Fenotype>();
		this.offsprings = new LinkedList<Fenotype>();
        this.highestGene = 1.0;
        this.lowestGene = -1.0;
        this.selectionPression = 1.0;
        this.matchSimilar = true;
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
			tmp.setQuality(rateOutputs(receivedOutputs, learningData));
			population.add(tmp);
		}
		Collections.sort(population);
	}
	
	
	public void nextEpoch()
	{
        this.highestGene = 1.0;
        this.lowestGene = -1.0;
		int populationSize = population.size();
        offsprings = new ArrayList<Fenotype>();
		for(int i = 0; i < populationSize; i++)
		{
			reproduce();
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

		// We choose parents for our next epoch
		for(int i = 0; i < populationSize; i++)
		{
			newPopulation.add(getParent(false));
		}
		population = newPopulation;
		Collections.sort(population);
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


    public List<Double> getTestedQualities()
    {
        ArrayList<Double> qualities = new ArrayList<Double>();
        for(Fenotype fenotype : population)
        {
            neuralNetwork.uploadWeights(fenotype.getGenotype());
            qualities.add(rateOutputs(neuralNetwork.computeData(testingData), testingData));
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

    public void setEliteStrategy(boolean eliteStrategy)
    {
        this.eliteStrategy = eliteStrategy;
    }

    public boolean getEliteStrategy()
    {
        return eliteStrategy;
    }

    public Double getSelectionPression()
    {
        return selectionPression;
    }

    public void setSelectionPressure(Double selectionPression)
    {
        if(selectionPression > 0.0)
        {
            this.selectionPression = selectionPression;
        }
    }

    public boolean isMatchSimilar()
    {
        return matchSimilar;
    }

    public void setMatchSimilar(boolean matchSimilar)
    {
        this.matchSimilar = matchSimilar;
    }

    public ArrayList<Double> getQualities()
    {
        ArrayList<Double> qualities = new ArrayList<Double>();
        for(Fenotype fenotype : population)
        {
            qualities.add(fenotype.getQuality());
        }
        return qualities;
    }


    /**
     * Adds one offspring to offspring array.
     */
    private void reproduce()
	{
		Random random = new Random();
		Fenotype wife = getParent(true);
        Fenotype husband;
        if(matchSimilar)
        {
            husband = getParent(wife);
        }
        else
        {
            husband = getParent(true);
        }
		Fenotype offspring = generateOffspring(wife, husband, neuralNetwork);
		offsprings.add(offspring);
	}


    private Fenotype getParent(boolean withReturning)
    {
        Random random = new Random();
        double randomized = Math.abs(random.nextGaussian()/selectionPression) % 1;
        int fenotypeIndex = (int)(randomized*population.size());
        Fenotype result = population.get(fenotypeIndex);
        if(!withReturning)
        {
            population.remove(fenotypeIndex);
        }
        return result;
    }

    /**
     * Returns random parent.
     * Fenotypes with genotype similar to partner have more chances to be randomed
     */
    private Fenotype getParent(final Fenotype partner)
    {
        Random random = new Random();
        Double similaritiesSum = 0.0;
        LinkedList<Double> similarities = new LinkedList<Double>();
        for(int i = 0; i < population.size(); i++)
        {
            similarities.add(compareFenotypes(partner, population.get(i)));
            similaritiesSum += similarities.getLast();
        }
        for(int i = 0; i < population.size()-1; i++)
        {
            for(int j = i+1; j < population.size(); j++)
            {
                if(similarities.get(i) < similarities.get(j))
                {
                    Collections.swap(similarities, i, j);
                    Collections.swap(population, i, j);
                }
            }
        }
        double randomized = random.nextDouble();
        double tmpSum = 0;
        for(int i = 0; i < population.size(); i++)
        {
            tmpSum += similarities.get(i) / similaritiesSum;
            if(tmpSum >= randomized)
            {
                return population.get(i);
            }
        }
        return population.get(population.size()-1);
    }

	
	/**
	 * Calculates similarity of two Fenotypes using the following formula:<br>
	 * <br>
	 * 					| x - y |	    	<br>
	 * 			1  -  ------------- 		<br>
	 * 					    c				<br>
	 * <br>
	 * Where x is single weight of first fenotype, y is from second fenotype
	 * and c is maximal difference between weights, which is equal (highestGene - lowestGene)
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
        Double tmpRes;
        for(int i = 0; i < first.getGenotype().size(); i++)
		{
			firstWeight = first.getGene(i);
			secondWeight = second.getGene(i);
            tmpRes = ( 1 - ( Math.abs(firstWeight - secondWeight) / (highestGene - lowestGene) ) );
			result += tmpRes;
        }
        return result / (first.getGenotype().size());
	}

    /**
     * Generates new offspring from wife and husband using averaging reproduction
     */
    private Fenotype generateOffspring(final Fenotype wife, final Fenotype husband, final NeuralNetwork neuralNetwork)
    {
        if(wife.getGenotype().size() != husband.getGenotype().size())
        {
            throw new RuntimeException();
        }
        ArrayList<Double> genotype = new ArrayList<Double>();
        ArrayList<Double> distribution = new ArrayList<Double>();
        Random random = new Random();
        int n = wife.getGenotype().size();
        Double distributionFactor;
        Double newGene;
        Double globalXi = random.nextGaussian();
        Double tau = 1 / (Math.sqrt(2 * Math.sqrt(n)));
        Double tauPrim = 1 / (Math.sqrt(2 * n));
        for(int i = 0; i < wife.getGenotype().size(); i++)
        {
            newGene = (wife.getGene(i)+husband.getGene(i)) / 2;
            distributionFactor = (wife.getDistributionFactor(i)+husband.getDistributionFactor(i)) / 2;
            distributionFactor *= Math.exp(tauPrim*globalXi + tau*random.nextGaussian());
            newGene = (newGene + distributionFactor*random.nextGaussian()) % 1000000000;
            genotype.add(newGene);
            distribution.add(distributionFactor);
            if(newGene > this.highestGene)
            {
                this.highestGene = newGene;
            }
            if(newGene < this.lowestGene)
            {
                this.lowestGene = newGene;
            }
        }
        Fenotype offspring = new Fenotype(genotype, distribution);
        neuralNetwork.uploadWeights(offspring.getGenotype());
        ArrayList<Output> receivedOutputs = neuralNetwork.computeData(learningData);
        offspring.setQuality(rateOutputs(receivedOutputs, learningData));
        return offspring;
    }


    private Fenotype generateRandomFenotype(int genotypeSize)
    {
        ArrayList<Double> genotype = new ArrayList<Double>();
        ArrayList<Double> distribution = new ArrayList<Double>();
        Random random = new Random();
        for(int i = 0; i < genotypeSize; i++)
        {
            genotype.add(random.nextDouble()*2 - 1.0);
            distribution.add(1.0);
        }
        return new Fenotype(genotype, distribution);
    }


    private Double rateOutputs(final ArrayList<Output> receivedOutputs, final Data data)
    {
        double TP = 0;
        double FP = 0;
        double TN = 0;
        double FN = 0;
        ArrayList<Output> expectedOutputs = data.getOutputs();
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
