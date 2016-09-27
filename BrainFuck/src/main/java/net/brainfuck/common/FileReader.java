package main.java.net.brainfuck.common;



		import main.java.net.brainfuck.common.exception.FileNotFoundException;
		import main.java.net.brainfuck.common.exception.IOException;

		import java.io.BufferedReader;
		import java.io.FileInputStream;
		import java.io.InputStreamReader;
		import java.nio.charset.Charset;

public class FileReader implements Reader{
	private String filename;
	char next;
	/*public char decode();
    public void close();*/
	private BufferedReader reader;
	public FileReader(String filename) throws FileNotFoundException, java.io.FileNotFoundException {
		this.filename = filename;
		reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename),Charset.forName("UTF-8")));
	}

	@Override
	public char decode() {
		return 0;
	}

	public boolean hasNext() throws IOException, java.io.IOException {
		int nextVal  = reader.read();
		if(nextVal == -1){
			return false;
		}
		next = (char)nextVal;
		return true;
	}
	public char getNext(){
		return next;
	}

	public void close() throws IOException{
		try {
			reader.close();
		} catch (java.io.IOException e) {
			throw new IOException();
		}
	}


}
