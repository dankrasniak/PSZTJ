package geneticalgorithm;

import java.util.ArrayList;
import java.util.Random;



public class Fenotype implements Comparable<Fenotype>
{
	private ArrayList<Double> genotype;
	
	/**
	 * Objective function
	 */
	private double quality;
	
	
	public Fenotype(ArrayList<Double> genotype)
	{
		// generate random genotype
		genotype = new ArrayList<Double>();
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
        //TODO
		return genotype.get(index);
	}
	
	public void setGene(final int index, final Double value)
	{
        //TODO
		genotype.set(index, value);
	}
	
	public ArrayList<Double> getGenotype()
	{
		return genotype;
	}

	@Override
	public int compareTo(final Fenotype other)
	{
		if(other.getQuality() < this.quality)
			return -1;
		if(other.getQuality() == this.quality)
			return 0;
		if(other.getQuality() > this.quality)
			return 1;
		return 0;
	}
}
