package net.brainfuck.common;

import java.util.Hashtable;
import java.util.Map;

import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.MemoryOverFlowException;

/**
 * The <code>Memory</code> class represents the memory of the BrainFuck interpreter
 *
 * @author Jeremy Junac
 *
 */
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.MemoryOverFlowException;

public class Memory {

	/** Max capacity */
	public static final int MAX_CAPACITY = 30000;
	/** Max value in the memory (255) */
	public static final short MAX_VALUE = (short) 255;
	/** Min value in the memory (0) */
	public static final short MIN_VALUE = 0;
	/** Size of the memory stocked in the array for direct accessing */
	private static final int ARRAY_SIZE = 1000;

	/** First cells of the memory in the array */
	private short start[] = new short[ARRAY_SIZE];
	/**
	 * Rest of the memory in this map. Key is the number of the cell and value is the value of the cell
	 */
	private Map<Integer, Short> end = new Hashtable<>();
	/** Index of the current cell */
	private int index = 0;

	/**
	 * Default constructor
	 */
	public Memory() {
	}

	/**
	 * Return a representation of the memory. Empty cell are not printed. The n-th cell (if not empty) is : "Cn : [value of n-th cell]"
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		short current;
		try {
			for (int i = 0; i < MAX_CAPACITY; i++) {
				if ((current = get(i)) != 0) {
					builder.append("C" + i + ": " + current + "\n");
				}
			}
		} catch (MemoryOutOfBoundsException e) {
			// Might not happen
			e.printStackTrace();
		}
		return builder.toString();
	}

	/**
	 * Read the current memory cell
	 *
	 * @return the value of the current memory cell
	 * @throws MemoryOutOfBoundsException
	 *             if the index isn't valid
	 */
	public short get() throws MemoryOutOfBoundsException {
		return get(index);
	}

	/**
	 * Read the specified memory cell
	 *
	 * @param index
	 *            the index of the memory cell to read
	 * @return the value of the current memory cell
	 * @throws MemoryOutOfBoundsException
	 *             if the index isn't valid
	 */
	private short get(int index) throws MemoryOutOfBoundsException {
		checkIndex(index);
		if (index < ARRAY_SIZE)
			return start[index];
		return (end.get(index - ARRAY_SIZE) != null) ? end.get(index - ARRAY_SIZE) : (byte) 0;
	}

	/**
	 * Set the value of the specified memory cell
	 *
	 * @param index
	 *            the index of the memory cell to set
	 * @param changeValue
	 *            the value to set
	 * @return current object
	 * @throws MemoryOverFlowException
	 *             if the value overflow the cell capacity
	 */
	private Memory set(int index, int changeValue) throws MemoryOverFlowException {
		try {
			if (get(index) > (MAX_VALUE - changeValue) || get(index) < (MIN_VALUE - changeValue))
				throw new MemoryOverFlowException();
			if (index < ARRAY_SIZE) {
				start[index] += changeValue;
				return this;
			}
			end.put(index - ARRAY_SIZE, (short) (get(index) + changeValue));
		} catch (MemoryOutOfBoundsException e) {
			e.printStackTrace();
		}
		return this;

	}

	/**
	 * Check the specified index
	 *
	 * @param index
	 *            the index to check
	 * @throws MemoryOutOfBoundsException
	 *             if the index isn't valid
	 */
	private void checkIndex(int index) throws MemoryOutOfBoundsException {
		if (index < 0 || index >= MAX_CAPACITY)
			throw new MemoryOutOfBoundsException();
	}

	/**
	 * Move the pointer to the memory cell to the right
	 *
	 * @return current object
	 * @throws MemoryOutOfBoundsException
	 *             if the pointer move out of the memory
	 */
	public Memory right() throws MemoryOutOfBoundsException {
		index++;
		checkIndex(index);
		return this;
	}

	/**
	 * Move the pointer to the memory cell to the left
	 *
	 * @return current object
	 * @throws MemoryOutOfBoundsException
	 *             if the pointer move out of the memory
	 */
	public Memory left() throws MemoryOutOfBoundsException {
		index--;
		checkIndex(index);
		return this;
	}

	/**
	 * Increase by one the value of the current memory cell
	 *
	 * @return current object
	 * @throws MemoryOverFlowException
	 *             if the value exceed the capacity of the cell
	 */
	public Memory incr() throws MemoryOverFlowException {
		return set(index, 1);
	}

	/**
	 * Decrease by one the value of the current memory cell
	 *
	 * @return current object
	 * @throws MemoryOverFlowException
	 *             if the value exceed the capacity of the cell
	 */
	public Memory decr() throws MemoryOverFlowException {
		return set(index, -1);
	}

	/**
	 * Clean all the memory
	 *
	 * @return current object
	 */
	public Memory clean() {
		start = new short[ARRAY_SIZE];
		end = new Hashtable<>();
		index = 0;
		return this;
	}

}
