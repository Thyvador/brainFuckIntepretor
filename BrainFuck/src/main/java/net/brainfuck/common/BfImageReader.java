package net.brainfuck.common;


import net.brainfuck.exception.BracketsParseException;
import net.brainfuck.exception.FileNotFoundException;
import net.brainfuck.exception.IOException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Stack;

/**
 * The <code>BfImageReader</code> class is used to read BitMap files.
 * This class extends <code>BMPReader</code> from the bioformats library.
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
     * The buffer that contains the 9 pixels forming an instruction.
     */
    private BufferedImage bufferedImage;
    private Stack<Integer[]> marks;

    /**
     * Constructs a BfImageReader from the path of a file.
     *
     * @param path the path of the file to read.
     * @throws IOException {@link IOException} if an IOException occur.
     */
    public BfImageReader(String path) throws IOException, FileNotFoundException {
        marks = new Stack<>();
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
     * @return the hexadecimal value of the next color.
     * @throws IOException {@link IOException} if an IOException occur.
     */
    @Override
    public String getNext() throws IOException {
        if (offX >= width) {
            offX = 0;
            offY += 3;
        }
        if (offY >= height) {
            return null;
        }
        int rgb = bufferedImage.getRGB(offX, offY);

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (rgb != bufferedImage.getRGB(offX + x, offY + y)) {
                    return "erreur";
                }
            }
        }

        int b = rgb & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int r = (rgb >> 16) & 0xFF;
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

    @Override
    public void mark() throws IOException {
        Integer[] tmp = {offX, offY};
        marks.push(tmp);
    }

    @Override
    public void reset() throws IOException, BracketsParseException {
        if (marks.isEmpty()) {
            throw new BracketsParseException("[");
        }
        offX = marks.peek()[0];
        offY = marks.peek()[1];
    }

    @Override
    public void unmark() throws BracketsParseException {
        if (marks.isEmpty()) {
            throw new BracketsParseException("[");
        }
        marks.pop();
        marks.pop();
    }
}
