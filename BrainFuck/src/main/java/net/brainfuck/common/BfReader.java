package net.brainfuck.common; import net.brainfuck.common.exception.FileNotFoundException; import net.brainfuck.common.exception.IOException; public class BfReader implements Reader{ private String filename; private char next; private java.io.FileReader reader; public BfReader(String filename) throws FileNotFoundException { this.filename = filename; try { reader = new java.io.FileReader(filename); } catch (java.io.FileNotFoundException e) { throw new FileNotFoundException(); } } @Override public char decode() { return 0; } public boolean hasNext() throws IOException{ int nextVal  = 0;
        try {
            nextVal = reader.read();
        } catch (java.io.IOException e) {
            throw new IOException();
        }
        if(nextVal == -1){
    		return false;
    	}
    	next = (char)nextVal;
    	return true;
    }
    public String getNext(){
    	return Character.toString(next);
    }

    public void close() throws IOException{
		try {
			reader.close();
		} catch (java.io.IOException e) {
			throw new IOException();
		}
	}
	
	
}
