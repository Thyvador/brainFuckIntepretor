package net.brainfuck.common;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;

public class BfImageWriter implements Writer {
	
	private OutputStream out;
	private DataOutputStream tmpOs;
	private ImageWriter wrt;
	private File tmpFile;

	public BfImageWriter(String path) throws IOException {
		this(new File(path));
	}
	
	public BfImageWriter(File outputFile) throws IOException {
		this(new FileOutputStream(outputFile));
	}
	
	public BfImageWriter(OutputStream out) throws IOException {
		this.out = out;
		wrt = ImageIO.getImageWritersByFormatName("bmp").next();
		wrt.setOutput(ImageIO.createImageOutputStream(out));
		tmpFile = File.createTempFile("tmp-", null, new File("c:/Users/user/Desktop"));
		tmpFile.deleteOnExit();
		tmpOs = new DataOutputStream(new FileOutputStream(tmpFile));
	}

	@Override
	public void write(int pixel) throws IOException {
		tmpOs.writeInt(pixel);
		tmpOs.flush();
	}
	
	@Override
	public void write(String hexaChain) throws IOException {
		tmpOs.writeInt(Integer.parseInt(hexaChain, 16));
		tmpOs.flush();
	}

	@Override
	public void close() throws IOException {
		DataInputStream tmpIs = new DataInputStream(new FileInputStream(tmpFile));
		double square;
		while ((square = Math.sqrt(tmpIs.available()/4)) != (int) square) {
			write(0x000000);
		}
		tmpOs.close();
		square *= 3;
		BufferedImage bi = new BufferedImage((int)square, (int)square, BufferedImage.TYPE_INT_RGB);
		int color;
		for (int y = 0; y < square; y+=3) {
			for (int x = 0; x < square; x+=3) {
				color = tmpIs.readInt();
				System.out.println(color);
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {
						bi.setRGB(x+j, y+i, color);
					}
				}
			}
		}
		tmpIs.close();
		wrt.write(bi);
		wrt.dispose();
		out.close();
	}
	
}
