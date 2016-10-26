package net.brainfuck.common;

import java.awt.image.DataBufferInt;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import net.brainfuck.exception.FileNotFoundException;
import net.brainfuck.exception.IOException;

/**
 * The BfImageWriter class write a bitmap image.  It can handle huge image.
 * 
 * It store all instruction in a temp file, squared the image with black pixel, and binary
 * write the image.
 * 
 * @author Jeremy Junac
 *
 */
public class BfImageWriter extends BitmapWriter implements Writer {

	/** The size of the DataBuffer bank */
	public static final int BANK_SIZE = 4096;
	/** The size of the buffer for write the temp file */
	public static final int BUFFER_SIZE = 4096;

	/** The DataOutputStream of the temp file */
	private DataOutputStream tmpOs;
	/** The temp file use for store instructions */
	private File tmpFile;
	/** The buffered bitmap data array */
	private DataBufferInt bitmapBuffer;
	/** the size of the bitmap data array */
	private int entireBufferSize;
	/** The count of instruction after the last flush */
	private int count = 0;

	public BfImageWriter() throws IOException, FileNotFoundException {
		this(System.out);
	}
	
	/**
	 * Instantiates a new bf image writer.
	 *
	 * @param path
	 *            the path of the output file
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws java.io.FileNotFoundException 
	 * @throws FileNotFoundException 
	 */
	@Deprecated
	public BfImageWriter(String path) throws IOException, FileNotFoundException, java.io.FileNotFoundException {
		this(new File(path));
	}

	/**
	 * Instantiates a new bf image writer.
	 *
	 * @param outputFile
	 *            the output file
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws FileNotFoundException 
	 * @throws java.io.FileNotFoundException 
	 */
	@Deprecated
	public BfImageWriter(File outputFile) throws IOException, FileNotFoundException, java.io.FileNotFoundException {
		this(new FileOutputStream(outputFile));
	}

	/**
	 * Instantiates a new bf image writer.
	 *
	 * @param out
	 *            the output stream
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws FileNotFoundException 
	 */
	public BfImageWriter(OutputStream out) throws IOException, FileNotFoundException {
		try {
			super.out = out;
			tmpFile = File.createTempFile("tmp-", null, new File("c:/Users/user/Desktop"));
			tmpFile.deleteOnExit();
			tmpOs = new DataOutputStream(new FileOutputStream(tmpFile));
		} catch (java.io.FileNotFoundException e) {
			throw new FileNotFoundException();
		} catch (java.io.IOException e) {
			throw new IOException();
		}
	}

	/**
	 * Write the pixel in the temp file
	 * 
	 * @param pixel the pixel to write
	 * 
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	public void write(int pixel) throws IOException {
		try {
			tmpOs.writeInt(pixel);
			count++;
			if (count == BUFFER_SIZE) {
				tmpOs.flush();
				count = 0;
			}
		} catch (java.io.IOException e) {
			throw new IOException();
		}
	}

	/**
	 * Write the pixel in the temp file
	 * 
	 * @param hexaChain the hexaCode of the pixel to write
	 * 
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	public void write(String hexaChain) throws IOException {
		write(Integer.parseInt(hexaChain, 16));
	}

	/**
	 * Add black pixel for squared the image, wirte the image and close all I/O streams
	 * 
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	public void close() throws IOException, FileNotFoundException {
		try {
			DataInputStream tmpIs = new DataInputStream(new FileInputStream(tmpFile));
			double square;
			while ((square = Math.sqrt(tmpIs.available() / 4)) != (int) square) {
				write(0x000000);
			}
			tmpOs.close();
			square *= 3;
			entireBufferSize = (int) Math.pow(square * 3, 2);
			bitmapBuffer = new DataBufferInt(BANK_SIZE, entireBufferSize / BANK_SIZE + 1);
			DataBufferInt line = new DataBufferInt(BANK_SIZE, (int) square / BANK_SIZE + 1);
			int color;
			// For all the line, set the global buffer
			for (int lineNumber = 0; lineNumber < square; lineNumber += 3) {
				// Set the line
				for (int indexInLine = 0; indexInLine < square; indexInLine += 3) {
					color = tmpIs.readInt();
					for (int j = 0; j < 3; j++) {
						int postion = indexInLine + j;
						line.setElem(postion / BANK_SIZE, postion % BANK_SIZE, color);
					}
				}
				// Write 3 times the line in the global buffer
				for (int i = 0; i < 3; i++) {
					for (int indexInLine = 0; indexInLine < square; indexInLine++) {
						int position = ((lineNumber + i) * (int) square) + indexInLine;
						bitmapBuffer.setElem(position / BANK_SIZE, position % BANK_SIZE,
								line.getElem(indexInLine / BANK_SIZE, indexInLine % BANK_SIZE));
					}
				}
			}
			tmpIs.close();
			// Count new line code
			biWidth = (int) square;
			biHeight = (int) square;
			int pad = (4 - ((biWidth * 3) % 4)) * biHeight;
			biSizeImage = ((biWidth * biHeight) * 3) + pad;
			bfSize = biSizeImage + BITMAPFILEHEADER_SIZE + BITMAPINFOHEADER_SIZE;
			writeBitmapFileHeader();
			writeBitmapInfoHeader();
			writeBitmap();
		} catch (java.io.FileNotFoundException e) {
			throw new FileNotFoundException();
		} catch (java.io.IOException e) {
			throw new IOException();
		}
	}

	/**
	 * Binary write the image using the data buffer field
	 */
	@Override
	protected void writeBitmap() {
		int size;
		int value;
		int j;
		int i;
		int rowCount;
		int rowIndex;
		int lastRowIndex;
		int pad;
		int padCount;
		byte rgb[] = new byte[3];
		size = (biWidth * biHeight); // Bug correction
		pad = 4 - ((biWidth * 3) % 4);
		if (pad == 4)
			pad = 0;
		rowCount = 1;
		padCount = 0;
		rowIndex = size - biWidth;
		lastRowIndex = rowIndex;
		try {
			for (j = 0; j < size; j++) {
				value = bitmapBuffer.getElem(rowIndex / BANK_SIZE, rowIndex % BANK_SIZE);
				rgb[0] = (byte) (value & 0xFF);
				rgb[1] = (byte) ((value >> 8) & 0xFF);
				rgb[2] = (byte) ((value >> 16) & 0xFF);
				out.write(rgb);
				if (rowCount == biWidth) {
					padCount += pad;
					for (i = 1; i <= pad; i++) {
						out.write(0x00);
					}
					rowCount = 1;
					rowIndex = lastRowIndex - biWidth;
					lastRowIndex = rowIndex;
				} else { // Bug correction
					rowCount++;
					rowIndex++;
				}
			}
			// --- Update the size of the file
			bfSize += padCount - pad;
			biSizeImage += padCount - pad;
		} catch (Exception wb) {
			wb.printStackTrace();
		}
	}

}
