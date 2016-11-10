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
    private Stack<Long> marks;

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
        int rgb = bufferedImage.getRGB(offX, offY);
        Logger.countMove();

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (rgb != bufferedImage.getRGB(offX + x, offY + y)) {
                    return "Error at pixel (" + (offX + x) + ", " + (offY + y) + ")";
                }
            }
        }

        int b = rgb & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int r = (rgb >> 16) & 0xFF;
        offX += 3;

        if (r == 0 && g == 0 && b == 0){
	        int calcul = (offY)*(width)/9+offX/3-1;
	        Logger.countInstruction(calcul);

	        return null;

        }

        return String.format("%02x%02x%02x", r, g, b);
    }

    @Override
    public long getExecutionPointer() {
        return ((offX/3)+(offY*width)/9);
    }

    /**
     * Close the reader once the file is read.
     *
     * @throws BracketsParseException {@link BracketsParseException} if the mark stack is empty.
     */
    @Override
    public void closeReader() throws BracketsParseException {
        if (!marks.isEmpty()) {
            throw new BracketsParseException("]");
        }
    }

    /**
     * Mark the current instruction by adding it into the stack.
     */
    @Override
    public void mark() {
        //Point tmp = new Point(offX, offY);
        marks.push(getExecutionPointer());
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
        seek(marks.peek());
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

	@Override
	public void seek(long pos) {
		System.out.println(pos);
		offX = (int) ((pos*3)%width);
        offY = (int) ((pos*9)/width);
	}


}
