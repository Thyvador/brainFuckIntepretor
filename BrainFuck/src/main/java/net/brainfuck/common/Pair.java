package net.brainfuck.common;

/**
 * The Class Pair.
 *
 * @param <F>
 *            the generic type
 * @param <S>
 *            the generic type
 */
public class Pair<F, S> {
    
    /** The first. */
    private F first;
    /** The second. */
    private S second;

    /**
	 * Instantiates a new pair.
	 *
	 * @param first
	 *            the first
	 * @param second
	 *            the second
	 */
    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    /**
	 * Gets the first.
	 *
	 * @return the first
	 */
    public F getFirst() {
        return first;
    }

    /**
	 * Gets the second.
	 *
	 * @return the second
	 */
    public S getSecond() {
        return second;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 0711;
		result = prime * result + ((first == null) ? 0 : first.hashCode());
		result = prime * result + ((second == null) ? 0 : second.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pair<?, ?> other = (Pair<?, ?>) obj;
		if (first == null) {
			if (other.first != null)
				return false;
		} else if (!first.equals(other.first))
			return false;
		if (second == null) {
			if (other.second != null)
				return false;
		} else if (!second.equals(other.second))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Pair [first=" + first + ", second=" + second + "]";
	}
}