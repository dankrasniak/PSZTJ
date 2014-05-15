package geneticalgorithm;

import java.util.Random;


public class Fenotype implements Comparable<Fenotype>
{
	private NewBitSet genotype;
	
	/**
	 * Objective function
	 */
	private double quality;
	
	
	public Fenotype(final int genotypeLength)
	{
		// generate random genotype
		genotype = new NewBitSet(genotypeLength);
		for(int i = 0; i < genotypeLength; i++)
        {
        	Random random = new Random();
        	if(random.nextBoolean())
        		genotype.set(i);
        }
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
	
	public boolean getGene(final int index)
	{
		return genotype.get(index);
	}
	
	public void setGene(final int index, final boolean value)
	{
		genotype.set(index, value);
	}
	
	public NewBitSet getGenotype()
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
