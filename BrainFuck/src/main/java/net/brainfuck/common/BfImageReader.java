package net.brainfuck.common;


import loci.formats.FormatException;
import loci.formats.in.BMPReader;
import net.brainfuck.exception.IOException;
import net.brainfuck.exception.SyntaxErrorException;

/**
 * Created by Alexandre Hiltcher on 05/10/2016.
 */
public class BfImageReader extends BMPReader implements Reader{
    private int x, y;
    private int width, height;
    private byte[] buffer;

    public BfImageReader(String path) throws IOException {
        super();
        try {
            initFile(path);
            x = 0;
            y = 0;
            width = getSizeX();
            height = getSizeY();
            buffer = new byte[27];
        } catch (FormatException e) {
            e.printStackTrace();
        } catch (java.io.IOException e) {
            throw new IOException(path);
        }
    }

    @Override
    public String getNext() throws IOException {
        if (y >= height){
            return null;
        }
        try {
            buffer = openBytes(0, buffer, x, y, 3, 3);
        } catch (FormatException e) {
            e.printStackTrace();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        x+=3;
        if ( x >= width){
            x = 0;
            y += 3;
        }
        for (int i = 0; i < 24; i++) {
            if(buffer[i]!=buffer[i+3]){
                //throw new SyntaxErrorException("Image incorect");
            }
        }
        return String.valueOf(String.format("%02x%02x%02x", buffer[0], buffer[1], buffer[2]));
    }

    @Override
    public void close(){
//        close();
    }
}
