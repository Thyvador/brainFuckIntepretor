package brainfuck;

class Reader{
	
	public interface Reader {
	    public char decode();
	    public boolean hasNext();
	    public String getNext();
	    public void close();
	}
}