package net.brainfuck.common;

import java.util.Hashtable;
import java.util.Map;

import net.brainfuck.exception.MemoryOutOfBoundsException;
import net.brainfuck.exception.MemoryOverFlowException;

public class Memory {

    public static final int MAX_CAPACITY = 30000;
    public static final byte MAX_VALUE = Byte.MAX_VALUE;
    public static final byte MIN_VALUE = 0;
    private static final int ARRAY_SIZE = 1000;

    private byte start[] = new byte[ARRAY_SIZE];
    private Map<Integer, Byte> end = new Hashtable<>();
    int index = 0;
    private static Memory instance = new Memory();

    private Memory() {
        if (instance != null)
            throw new IllegalStateException("Already instantiated");
    }

    /**
     * @return the instance
     */
    public static Memory getInstance() {
        return instance;
    }

    /**
     * Override of the clone method because of the singleton
     *
     * @throws CloneNotSupportedException because this instance is a singleton
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Cannot clone instance of this class");
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        byte current;
        int i = 0;
        try {
            for (; i < MAX_CAPACITY; i++) {
                if ((current = get(i)) != 0) {
                    builder.append("C" + i + ": " + current + "\n");
                }
            }
        } catch (MemoryOutOfBoundsException e) {
//			System.out.println(i);
//			e.printStackTrace();
        }
        return builder.toString();
    }

    public byte get() throws MemoryOutOfBoundsException {
        return get(index);
    }

    private byte get(int index) throws MemoryOutOfBoundsException {
        checkIndex(index);
        if (index < ARRAY_SIZE)
            return start[index];
        return (end.get(index - ARRAY_SIZE) != null) ? end.get(index - ARRAY_SIZE) : (byte) 0;
    }

    private Memory set(int index, int changeValue) throws MemoryOverFlowException, MemoryOutOfBoundsException {
        if (get(index) > (MAX_VALUE - changeValue) || get(index) < (MIN_VALUE - changeValue))
            throw new MemoryOverFlowException();
        if (index < ARRAY_SIZE) {
            start[index] += changeValue;
            return this;
        }
        end.put(index - ARRAY_SIZE, (byte) (get(index) + changeValue));
        return this;

    }

    private void checkIndex(int index) throws MemoryOutOfBoundsException {
        if (index < 0 || index >= MAX_CAPACITY)
            throw new MemoryOutOfBoundsException();
    }

    public Memory right() throws MemoryOutOfBoundsException {
        index++;
        checkIndex(index);
        return this;
    }

    public Memory left() throws MemoryOutOfBoundsException {
        index--;
        checkIndex(index);
        return this;
    }

    public Memory incr() throws MemoryOverFlowException, MemoryOutOfBoundsException {
        return set(index, 1);
    }

    public Memory decr() throws MemoryOverFlowException, MemoryOutOfBoundsException {
        return set(index, -1);
    }

}
