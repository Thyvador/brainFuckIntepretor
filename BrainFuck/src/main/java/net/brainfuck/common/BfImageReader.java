package net.brainfuck.common;


import loci.formats.FormatException;
import loci.formats.in.BMPReader;
import net.brainfuck.exception.BracketsParseException;
import net.brainfuck.exception.IOException;
import net.brainfuck.exception.SyntaxErrorException;

import java.util.Stack;

/**
 * The <code>BfImageReader</code> class is used to read BitMap files.
 * This class extends <code>BMPReader</code> from the bioformats library.
 *
 * @author Alexandre Hiltcher
 */
public class BfImageReader extends BMPReader implements Reader {
    /**
     * The offset of the pixel being read.
     */
    private int offX, offY;
    /**
     * The width and height of the image.
     */
    private int width, height;
    /**
     * The buffer that contains the 9 pixels forming an instruction.
     */
    private byte[] buffer;
    private Stack<Integer[]> markIndex;
    private Stack<Byte> marks;

    /**
     * Constructs a BfImageReader from the path of a file.
     *
     * @param path the path of the file to read.
     * @throws IOException {@link IOException} if an IOException occur.
     */
    public BfImageReader(String path) throws IOException {
        super();
        marks = new Stack<>();
        markIndex = new Stack<>();
        try {
            initFile(path);
            offX = 0;
            offY = 0;
            width = getSizeX();
            height = getSizeY();
            buffer = new byte[27];
        } catch (FormatException e) {
            e.printStackTrace();
            // TODO: 22/10/2016 find the exception to throw.
        } catch (java.io.IOException e) {
            throw new IOException(path);
        }
    }

    /**
     * Return the color of the next instruction.
     *
     * @return the hexadecimal value of the next color.
     * @throws IOException {@link IOException} if an IOException occur.
     */
    @Override
    public String getNext() throws IOException {
        if (offY >= height) {
            return null;
        }
        try {
            buffer = openBytes(0, buffer, offX, offY, 3, 3);
        } catch (FormatException e) {
            e.printStackTrace();
        } catch (java.io.IOException e) {
            throw new IOException();
        }
        offX += 3;
        if (offX >= width) {
            offX = 0;
            offY += 3;
        }
        if (buffer[0] == 0 && buffer[1] == 0 && buffer[2] == 0) return null;
        for (int i = 0; i < 8; i++) {
            if (buffer[i * 3] != buffer[i * 3 + 3] || buffer[i * 3 + 1] != buffer[i * 3 + 4] || buffer[i * 3 + 2] != buffer[i * 3 + 5]) {
                // TODO: 26/10/2016 Probleme avec cette exception.
//                throw new SyntaxErrorException("Image incorect at index("+offX+","+offY+")");
                System.out.println("TA MERE");
            }
        }
        return String.format("%02x%02x%02x", buffer[0], buffer[1], buffer[2]);
    }

    /**
     * Close the reader once the file is read.
     */
    @Override
    public void closeReader() throws IOException, BracketsParseException {
        if (!marks.isEmpty()) {
            throw new BracketsParseException("]");
        }
        try {
            super.close();
        } catch (java.io.IOException e) {
            throw new IOException();
        }
    }

    @Override
    public void mark() throws IOException {
        Integer[] tmp = {offX, offY};
        markIndex.push(tmp);
        marks.push(buffer[0]);
    }

    @Override
    public void reset() throws IOException, BracketsParseException {
        if (marks.isEmpty()) {
            throw new BracketsParseException("[");
        }
        offX = markIndex.peek()[0];
        offY = markIndex.peek()[1];
    }

    @Override
    public void unmark() throws BracketsParseException {
        if (marks.isEmpty()) {
            throw new BracketsParseException("[");
        }
        markIndex.pop();
        marks.pop();
    }
}
