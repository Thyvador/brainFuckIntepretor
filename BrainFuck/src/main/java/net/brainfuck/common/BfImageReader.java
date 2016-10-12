package net.brainfuck.common;


import loci.formats.FormatException;
import loci.formats.in.BMPReader;
import net.brainfuck.exception.IOException;

import java.awt.image.BufferedImage;

/**
 * Created by Alexandre Hiltcher on 05/10/2016.
 */
public class BfImageReader extends BMPReader implements Reader{
    private int x, y;
    private byte[] buffer;

    public BfImageReader(String path) throws IOException {
        super();
        try {
            initFile(path);
            x = 0;
            y = 0;
            buffer = new byte[9];
        } catch (FormatException e) {
            e.printStackTrace();
        } catch (java.io.IOException e) {
            throw new IOException(path);
        }
    }

    @Override
    public String getNext() throws IOException {
        try {
            buffer = openBytes(0, buffer, x, y, 3, 3);
        } catch (FormatException e) {
            e.printStackTrace();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        for (byte q :
                buffer) {
            System.out.println(q);
        }

        return null;
    }

    @Override
    public void close(){
        close();
    }
}
