package net.brainfuck.common;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BfImageReader {
    BufferedImage bufferedImage;
    int x, y;

    public BfImageReader(String path) {
        try {
            FileImageInputStream is = new FileImageInputStream( new File(path));
            bufferedImage = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readNext() throws IOException {
        return String.valueOf(bufferedImage.getRGB(x, y));
    }

    public boolean hasNext() {
        x += 3;
        if (x >= bufferedImage.getWidth()) {
            x = 0;
            y += 3;
        }
        if (y >= bufferedImage.getHeight()) {
            return false;
        }
        return true;
    }
}
