package net.brainfuck.common;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;

import net.brainfuck.exception.FileNotFoundException;
import net.brainfuck.exception.IOException;

/**
 * 
 * The BfImageWriter class represents a class that write a brainfuck program as a BMP image.
 *
 * @author Jeremy Junac
 */
public class BfImageWriter implements Writer {
	
	/** The final output. */
	private OutputStream out;
	/** The tmp output stream. */
	private DataOutputStream tmpOs;
	/** The writer. */
	private ImageWriter wrt;
	/** The tmp file. */
	private File tmpFile;

	/**
	 * Instantiates a new bf image writer.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws FileNotFoundException the file not found exception
	 */
	public BfImageWriter() throws IOException, FileNotFoundException {
		this(System.out);
	}
	
	/**
	 * Instantiates a new bf image writer from a output stream.
	 *
	 * @param out the out stream to write in.
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws FileNotFoundException the file not found exception
	 */
	public BfImageWriter(OutputStream out) throws IOException, FileNotFoundException {
		try {
			this.out = out;
			wrt = ImageIO.getImageWritersByFormatName("bmp").next();
			wrt.setOutput(ImageIO.createImageOutputStream(out));
			tmpFile = File.createTempFile("tmp-", null);
			tmpFile.deleteOnExit();
			tmpOs = new DataOutputStream(new FileOutputStream(tmpFile));
		} catch (java.io.FileNotFoundException e) {
			tmpFile.deleteOnExit();
			throw new FileNotFoundException();
		} catch (java.io.IOException e) {
			tmpFile.deleteOnExit();
			throw new IOException();
}
	}

	/* (non-Javadoc)
	 * @see net.brainfuck.common.Writer#write(int)
	 */
	@Override
	public void write(int pixel) throws IOException {
		try {
			tmpOs.writeInt(pixel);
			tmpOs.flush();
		} catch (java.io.IOException e) {
			throw new IOException();
		}
	}
	
	/* (non-Javadoc)
	 * @see net.brainfuck.common.Writer#write(java.lang.String)
	 */
	@Override
	public void write(String hexaChain) throws IOException {
		try {
			tmpOs.writeInt(Integer.parseInt(hexaChain, 16));
			tmpOs.flush();
		} catch (NumberFormatException e) {
			// Should not append
			e.printStackTrace();
		} catch (java.io.IOException e) {
			throw new IOException();
		}
	}

	/* (non-Javadoc)
	 * @see net.brainfuck.common.Writer#close()
	 */
	@Override
	public void close() throws FileNotFoundException, IOException {
		try {
			DataInputStream tmpIs = new DataInputStream(new FileInputStream(tmpFile));
			double blackNeeded = Math.pow(Math.round(Math.sqrt(tmpIs.available()/4) + 0.5), 2) - tmpIs.available()/4;
			for (int i=0; i < blackNeeded; i++)	 {
				write(0x000000);
			}
			tmpOs.close();
			int square = (int) (3 * Math.sqrt(tmpIs.available()/4));
			BufferedImage bi = new BufferedImage(square, square, BufferedImage.TYPE_INT_RGB);
			int color;
			for (int y = 0; y < square; y+=3) {
				for (int x = 0; x < square; x+=3) {
					color = tmpIs.readInt();
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
		} catch (java.io.FileNotFoundException e) {
			throw new FileNotFoundException();
		}catch (java.io.IOException e) {
			throw new IOException();
		}
	}
	
}
