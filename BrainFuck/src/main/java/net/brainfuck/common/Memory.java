package net.brainfuck.common;

import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

import net.brainfuck.exception.BracketsParseException;
import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.MemoryOverFlowException;
import net.brainfuck.exception.SegmentationFaultException;

/**
 * The <code>Memory</code> class represents the memory of the BrainFuck interpreter.
 *
 * @author Jeremy Junac
 */
public class Memory {

	/**
	 * Max capacity
	 */
	private static final int MAX_CAPACITY = 30000;
	/**
	 * Max value in the memory (255)
	 */
	private static final short MAX_VALUE = 255;
	/**
	 * Min value in the memory (0)
	 */
	private static final short MIN_VALUE = 0;

	/**
	 * First cells of the memory in the array
	 */
	private short memory[];
	/**
	 * Index of the current cell
	 */
	private int index;
	private Stack<Integer> scope;
	private Logger logger;

	/**
	 * Default constructor
	 */
	public Memory() {
		this.logger = Logger.getInstance();
		scope = new Stack<>();
		clean();
	}

	/**
	 * Return the memory index.
	 *
	 * @return the memory index.
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Sets the index.
	 *
	 * @param index            the new index
	 * @throws MemoryOutOfBoundsException             the memory out of bounds exception
	 * @throws SegmentationFaultException the segmentation fault exception
	 */
	private void setIndex(int index) throws MemoryOutOfBoundsException, SegmentationFaultException {
		try {
			if (index < scope.peek())
				throw new SegmentationFaultException();
		} catch (EmptyStackException e) {
			// The stack is empty, we are in the "main", so np
		}
		this.index = index;
		logger.countMemoryMove();
		checkIndex(index);
	}

	/**
	 * Read the current memory cell.
	 *
	 * @return the value of the current memory cell
	 * @throws MemoryOutOfBoundsException
	 *             if the index isn't valid.
	 */
	public short get() throws MemoryOutOfBoundsException {
		return get(index);
	}

	/**
	 * Read the specified memory cell
	 *
	 * @param index
	 *            the index of the memory cell to read
	 * @return the value of the memory cell at the index
	 * @throws MemoryOutOfBoundsException
	 *             if the index isn't valid
	 */
	private short get(int index) throws MemoryOutOfBoundsException {
		checkIndex(index);
		logger.countMemoryRead();
		return memory[index];
	}

	/**
	 * Set the value of the specified memory cell.
	 *
	 * @param index
	 *            the index of the memory cell to set
	 * @param changeValue
	 *            the value to set
	 * @return the current memory
	 * @throws MemoryOverFlowException
	 *             if the value overflow or underflow the cell capacity
	 */
	private Memory set(int index, int changeValue) throws MemoryOverFlowException {
		return set(memory[index] + changeValue);
	}

	/**
	 * Set the value of the specified memory cell to argumentAnalyzer specific value (used with IN instruction).
	 *
	 * @param newValue
	 *            the value to set
	 * @return the current memory
	 * @throws MemoryOverFlowException
	 *             the memory over flow exception
	 */
	public Memory set(int newValue) throws MemoryOverFlowException {
		if (newValue > MAX_VALUE || newValue < MIN_VALUE)
			throw new MemoryOverFlowException("Invalid value " + newValue + " at index " + index);
		logger.countMemoryWrite();
		memory[index] = (short) newValue;
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
			throw new MemoryOutOfBoundsException("Invalid index " + index);
	}

	/**
	 * Move the pointer to the memory cell to the right.
	 *
	 * @return the current memory
	 * @throws MemoryOutOfBoundsException
	 *             if the pointer move out of the memory
	 * @throws SegmentationFaultException 
	 */
	public Memory right() throws MemoryOutOfBoundsException {
		try {
			setIndex(index + 1);
		} catch (SegmentationFaultException e) {
			// Might not append
		}
		return this;
	}

	/**
	 * Move the pointer to the memory cell to the left.
	 *
	 * @return the current memory
	 * @throws MemoryOutOfBoundsException
	 *             if the pointer move out of the memory
	 * @throws SegmentationFaultException 
	 */
	public Memory left() throws MemoryOutOfBoundsException, SegmentationFaultException {
		setIndex(index - 1);
		return this;
	}

	/**
	 * Increase by one the value of the current memory cell.
	 *
	 * @return the current memory
	 * @throws MemoryOverFlowException
	 *             if the value exceed the max capacity of the cell
	 */
	public Memory incr() throws MemoryOverFlowException {
		return set(index, 1);
	}

	/**
	 * Decrease by one the value of the current memory cell.
	 *
	 * @return the current memory
	 * @throws MemoryOverFlowException
	 *             if the value exceed the minimum capacity of the cell
	 */
	public Memory decr() throws MemoryOverFlowException {
		return set(index, -1);
	}

	/**
	 * Clean all the memory.
	 *
	 * @return the current memory
	 */
	private Memory clean() {
		memory = new short[MAX_CAPACITY];
		index = 0;
		return this;
	}

	/**
	 * Lock the memory scope and move the pointer to the right.
	 *
	 * @return the memory
	 * @throws MemoryOutOfBoundsException
	 *             the memory out of bounds exception
	 */
	public Memory lock() throws MemoryOutOfBoundsException {
		right();
		scope.push(getIndex());
		return this;
	}

	/**
	 * Unlock the memory scope and copy the eventual return value in the cell at the right of the cell before the call
	 *
	 * @param returnValue
	 *            if there is a return value
	 * @return the memory
	 * @throws MemoryOutOfBoundsException
	 *             the memory out of bounds exception
	 */
	public Memory unlock(boolean returnValue) throws MemoryOutOfBoundsException, BracketsParseException {
		short tmp = get();
		try {
			setIndex(scope.pop());
			if (returnValue)
				set(tmp);
		} catch (SegmentationFaultException | MemoryOverFlowException e1) {
			// Might not append
		} catch (EmptyStackException e){
			throw new BracketsParseException();
		}
		return this;
	}
	
	/**
	 * Sets the arguments.
	 *
	 * @param args the args
	 * @return the memory
	 * @throws MemoryOutOfBoundsException 
	 * @throws MemoryOverFlowException 
	 * @throws SegmentationFaultException 
	 */
	public Memory setArguments(int... args) throws MemoryOutOfBoundsException, MemoryOverFlowException, SegmentationFaultException {
		for (int s: args) {
			int tmp = scope.pop();
			if (tmp + s < 0 || tmp + s >= MAX_CAPACITY)
				throw new MemoryOutOfBoundsException();
			if (s < 0 || s >= tmp)
				throw new SegmentationFaultException();
			int startScope = 0;
			try {
				startScope = scope.peek();
			} catch (EmptyStackException e) {
			}
			
			set(get(s+startScope));
			scope.push(tmp);
			right();
		}
		return this;
	}
	
	/**
	 * Sets the arguments.
	 *
	 * @param args the args
	 * @return the memory
	 * @throws MemoryOverFlowException 
	 * @throws MemoryOutOfBoundsException 
	 * @throws SegmentationFaultException 
	 */
	public Memory setArguments(List<Integer> args) throws MemoryOutOfBoundsException, MemoryOverFlowException, SegmentationFaultException {
		int[] res = new int[args.size()];
		for (int i = 0; i < res.length; i++) {
			res[i] = args.get(i);
		}
		return setArguments(res);
	}

	/**
	 * Return argumentAnalyzer representation of the memory. Empty cell are not printed. The n-th cell (if not empty) is : "Cn : [value of
	 * n-th cell]"
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		short current;
		for (int i = 0; i < MAX_CAPACITY; i++) {
			if ((current = memory[i]) != 0) {
				builder.append("C").append(i).append(": ").append(current).append("\n");
			}
		}
		return builder.toString();
	}

	/**
	 * Return true if the scope stack is empty, false otherwise.
	 * @return true if the scope stack is empty.
	 */
	public boolean isScopeEmpty(){
		return scope.isEmpty();
	}
}