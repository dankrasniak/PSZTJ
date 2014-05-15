package geneticalgorithm;

import java.util.BitSet;


/**
 * Default BitSet size() method is worth nothing.
 * We need method which tells us the true size of out BitSet.
 * @author Maciej
 *
 */
public class NewBitSet extends BitSet
{
	private static final long serialVersionUID = 1L;
	private int realSize;
	
	public NewBitSet(final int size)
	{
		super(size);
		realSize = size;
	}

	public NewBitSet(final int size, final BitSet bitSet)
	{
		super(size);
		realSize = size;
		for(int i = 0; i < size; i++)
		{
			super.set(i, bitSet.get(i));
		}
	}
	
	public int realSize()
	{
		return realSize;
	}
}
