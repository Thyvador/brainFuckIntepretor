package net.brainfuck.common;


import net.brainfuck.exception.BracketsParseException;
import net.brainfuck.exception.FileNotFoundException;
import net.brainfuck.exception.IOException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Stack;

/**
 * The <code>BfImageReader</code> class is used to read BitMap files.
 * This class implements the <code>Reader</code> Interface.
 *
 * @author Alexandre Hiltcher
 */
public class BfImageReader implements Reader {
    /**
     * The offset of the pixel being read.
     */
    private int offX, offY;
    /**
     * The width and height of the image.
     */
    private int width, height;
    /**
     * The bufferedImage that contains the data from the bitmap file.
     */
    private BufferedImage bufferedImage;
    /**
     * The stack that contains all the points corresponding to the JUMP instructions.
     */
    private Stack<Point> marks;

    // TODO: 03/11/2016 Utiliser des points

    /**
     * Constructs a BfImageReader from the path of a file.
     *
     * @param path the path of the file to read.
     * @throws FileNotFoundException {@link FileNotFoundException} if the file does not exit or cannot be read.
     */
    public BfImageReader(String path) throws FileNotFoundException {
        marks = new Stack<>();
        try {
            bufferedImage = ImageIO.read(new File(path));
        } catch (java.io.IOException e) {
            throw new FileNotFoundException(path);
        }
        offX = 0;
        offY = 0;
        width = bufferedImage.getWidth();
        height = bufferedImage.getHeight();
    }

    /**
     * Return the color of the next instruction.
     *
     * @return the hexadecimal value of the next color as a String.
     * @throws IOException {@link IOException} if an IOException occur.
     */
    @Override
    public String getNext() {
        if (offX >= width) {
            offX = 0;
            offY += 3;
        }
        if (offY >= height) {
            return null;
        }
        int[] rgb = bufferedImage.getRGB(offX, offY, 3, 3, null, 0, 9);

        for (int x = 1; x < 9; x++) {
            if (rgb[0] != rgb[x]) {
//                return "Error at pixel (" + (offX + x%3) + ", " + (offY + (int)(x/3)) + ")";
            }
        }

        int b = rgb[0] & 0xFF;
        int g = (rgb[0] >> 8) & 0xFF;
        int r = (rgb[0] >> 16) & 0xFF;
        offX += 3;

        if (r == 0 && g == 0 && b == 0) return null;
        return String.format("%02x%02x%02x", r, g, b);
    }

    /**
     * Close the reader once the file is read.
     */
    @Override
    public void closeReader() throws IOException, BracketsParseException {
        if (!marks.isEmpty()) {
            throw new BracketsParseException("]");
        }
    }

    /**
     * Mark the current instruction by adding it into the stack.
     */
    @Override
    public void mark() {
        Point tmp = new Point(offX, offY);
        marks.push(tmp);
    }

    /**
     * Reset the index of the next pixel to the last mark.
     *
     * @throws BracketsParseException {@link BracketsParseException} if the mark stack is empty.
     */
    @Override
    public void reset() throws BracketsParseException {
        if (marks.isEmpty()) {
            throw new BracketsParseException("[");
        }
        offX = (int) marks.peek().getX();
        offY = (int) marks.peek().getY();
    }

    /**
     * Unmark the last marked instructions by remove it from the stack.
     *
     * @throws BracketsParseException {@link BracketsParseException} if the mark stack is empty.
     */
    @Override
    public void unmark() throws BracketsParseException {
        if (marks.isEmpty()) {
            throw new BracketsParseException("[");
        }
        marks.pop();
    }
}
