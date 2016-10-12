package net.brainfuck.common;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;


import net.brainfuck.exception.IOException;

import java.awt.image.BufferedImage;
import java.io.File;


/**
 * Created by Alexandre Hiltcher on 05/10/2016.
 */
public class BfImageReader {
    private FileImageInputStream is;
    private ImageReader reader;
    private int width, height;
    private int x, y;

    public BfImageReader(String path) throws IOException {
        try {
            is = new FileImageInputStream(new File(path));
            reader = ImageIO.getImageReadersByFormatName("bmp").next();
            reader.setInput(is);
            width = reader.getWidth(reader.getMinIndex());
            height = reader.getHeight((reader.getMinIndex()));
            x = 0;
            y = 0;
        } catch (java.io.IOException e) {
            throw new IOException();
        }
    }

    public String readNext() throws IOException, java.io.IOException {
        BufferedImage bi = new BufferedImage(3, 3, BufferedImage.TYPE_INT_RGB);
//        try {
            bi = reader.read(0);
            x += 3;
            if (x >= width) y += 3;
//        } catch (java.io.IOException e) {
//            throw new IOException();
//        }
        return Integer.toHexString(bi.getRGB(0,0));

    }

    public boolean hasNext(){
        return y < height && x < width;
    }


}
