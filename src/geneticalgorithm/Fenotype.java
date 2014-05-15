package geneticalgorithm;

import java.util.ArrayList;
import java.util.BitSet;


class Fenotype 
{
	private BitSet genotype;
	
	/**
	 * Wartosc funkcji celu
	 */
	private double quality;
	
	
	Fenotype(final int genotypeLength)
	{
		quality = 0;
	}
	
	void setQuality(final double quality)
	{
		this.quality = quality;
	}
	
	double getQuality()
	{
		return quality;
	}
	
	boolean getGene(final int index)
	{
		return genotype.get(index);
	}
	
	void setGene(final int index, final boolean value)
	{
		genotype.set(index, value);
	}
	
	BitSet getGenotype()
	{
		return genotype;
	}
}
