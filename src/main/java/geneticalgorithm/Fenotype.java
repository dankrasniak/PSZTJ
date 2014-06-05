package geneticalgorithm;

import java.util.ArrayList;
import java.util.Random;



public class Fenotype implements Comparable<Fenotype>
{
	private ArrayList<Double> genotype;
    private ArrayList<Double> distribution;


	/**
	 * Objective function
	 */
	private double quality;
	
	
	public Fenotype(final ArrayList<Double> genotype, final ArrayList<Double> distribution)
	{
		this.genotype = genotype;
        this.distribution = distribution;
		quality = 0;
	}
	
	public void setQuality(final double quality)
	{
		this.quality = quality;
	}
	
	public double getQuality()
	{
		return quality;
	}

	public Double getGene(final int index)
	{
		return genotype.get(index);
	}
	
	public void setGene(final int index, final Double value)
	{
		genotype.set(index, value);
	}

    public Double getDistributionFactor(final int index)
    {
        return distribution.get(index);
    }

    public void setDistributionFactor(final int index, final Double factor)
    {
        distribution.set(index, factor);
    }

    public ArrayList<Double> getGenotype()
    {
        return genotype;
    }

    public ArrayList<Double> getDistribution()
    {
        return distribution;
    }

    @Override
	public int compareTo(final Fenotype other)
	{
        if(other == null)
            return 0;
		if(other.getQuality() < this.quality)
			return -1;
		if(other.getQuality() == this.quality)
			return 0;
		return 1;
	}
}
