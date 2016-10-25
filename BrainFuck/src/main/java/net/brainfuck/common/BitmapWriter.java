package net.brainfuck.common;

import java.awt.*;
import java.io.*;
import java.awt.image.*;

/***
 * The BitmapWriter class write bitmap file. The code is by Jean-Pierre Dubé and find here:
 * <a href= "http://www.javaworld.com/article/2077561/learn-java/java-tip-60--saving-bitmap-files-in-java.html">
 * http://www.javaworld.com/article/2077561/learn-java/java-tip-60--saving-bitmap-files-in-java.html</a>
 * 
 * Some fields and methods visibilities have been changed for our uses.
 * FileOutputStream has been change to OututStream because we have to wirte on stdout
 * 
 * @author Jean-Pierre Dubé
 *
 */
@SuppressWarnings({ "unused", "javadoc" })
public class BitmapWriter {
	// --- Private constants
	
	protected final static int BITMAPFILEHEADER_SIZE = 14;
	protected final static int BITMAPINFOHEADER_SIZE = 40;
	// --- Private variable declaration
	// --- Bitmap file header
	private byte bitmapFileHeader[] = new byte[14];
	private byte bfType[] = { 'B', 'M' };
	protected int bfSize = 0;
	private int bfReserved1 = 0;
	private int bfReserved2 = 0;
	private int bfOffBits = BITMAPFILEHEADER_SIZE + BITMAPINFOHEADER_SIZE;
	// --- Bitmap info header
	private byte bitmapInfoHeader[] = new byte[40];
	private int biSize = BITMAPINFOHEADER_SIZE;
	protected int biWidth = 0;
	protected int biHeight = 0;
	private int biPlanes = 1;
	private int biBitCount = 24;
	private int biCompression = 0;
	protected int biSizeImage = 0x030000;
	private int biXPelsPerMeter = 0x0;
	private int biYPelsPerMeter = 0x0;
	private int biClrUsed = 0;
	private int biClrImportant = 0;
	// --- Bitmap raw data
	protected int bitmap[];
	// --- File section
	protected OutputStream out;

	// --- Default constructor
	public BitmapWriter() {
	}

	public void saveBitmap(String parFilename, Image parImage, int parWidth, int parHeight) {
		try {
			out = new FileOutputStream(parFilename);
			save(parImage, parWidth, parHeight);
			out.close();
		} catch (Exception saveEx) {
			saveEx.printStackTrace();
		}
	}

	/**
	 * The saveMethod is the main method of the process. This method will call the convertImage method to convert the memory image to a byte
	 * array; method writeBitmapFileHeader creates and writes the bitmap file header; writeBitmapInfoHeader creates the information header;
	 * and writeBitmap writes the image.
	 *
	 */
	protected void save(Image parImage, int parWidth, int parHeight) {
		try {
			convertImage(parImage, parWidth, parHeight);
			writeBitmapFileHeader();
			writeBitmapInfoHeader();
			writeBitmap();
		} catch (Exception saveEx) {
			saveEx.printStackTrace();
		}
	}

	/**
	 * convertImage converts the memory image to the bitmap format (BRG). It also computes some information for the bitmap info header.
	 *
	 */
	protected boolean convertImage(Image parImage, int parWidth, int parHeight) {
		int pad;
		bitmap = new int[parWidth * parHeight];
		PixelGrabber pg = new PixelGrabber(parImage, 0, 0, parWidth, parHeight, bitmap, 0, parWidth);
		try {
			pg.grabPixels();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return (false);
		}
		pad = (4 - ((parWidth * 3) % 4)) * parHeight;
		biSizeImage = ((parWidth * parHeight) * 3) + pad;
		bfSize = biSizeImage + BITMAPFILEHEADER_SIZE + BITMAPINFOHEADER_SIZE;
		biWidth = parWidth;
		biHeight = parHeight;
		return (true);
	}

	/**
	 * writeBitmap converts the image returned from the pixel grabber to the format required. Remember: scan lines are inverted in a bitmap
	 * file!
	 *
	 * Each scan line must be padded to an even 4-byte boundary.
	 */
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
		size = biWidth * biHeight; // Bug correction
		pad = 4 - ((biWidth * 3) % 4);
		if (pad == 4)
			pad = 0;
		rowCount = 1;
		padCount = 0;
		rowIndex = size - biWidth;
		lastRowIndex = rowIndex;
		try {
			for (j = 0; j < size; j++) {
				value = bitmap[rowIndex];
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

	/**
	 * writeBitmapFileHeader writes the bitmap file header to the file.
	 *
	 */
	protected void writeBitmapFileHeader() {
		try {
			out.write(bfType);
			out.write(intToDWord(bfSize));
			out.write(intToWord(bfReserved1));
			out.write(intToWord(bfReserved2));
			out.write(intToDWord(bfOffBits));
		} catch (Exception wbfh) {
			wbfh.printStackTrace();
		}
	}

	/**
	 *
	 * writeBitmapInfoHeader writes the bitmap information header to the file.
	 *
	 */
	protected void writeBitmapInfoHeader() {
		try {
			out.write(intToDWord(biSize));
			out.write(intToDWord(biWidth));
			out.write(intToDWord(biHeight));
			out.write(intToWord(biPlanes));
			out.write(intToWord(biBitCount));
			out.write(intToDWord(biCompression));
			out.write(intToDWord(biSizeImage));
			out.write(intToDWord(biXPelsPerMeter));
			out.write(intToDWord(biYPelsPerMeter));
			out.write(intToDWord(biClrUsed));
			out.write(intToDWord(biClrImportant));
		} catch (Exception wbih) {
			wbih.printStackTrace();
		}
	}

	/**
	 *
	 * intToWord converts an int to a word, where the return value is stored in a 2-byte array.
	 *
	 */
	protected byte[] intToWord(int parValue) {
		byte retValue[] = new byte[2];
		retValue[0] = (byte) (parValue & 0x00FF);
		retValue[1] = (byte) ((parValue >> 8) & 0x00FF);
		return (retValue);
	}

	/**
	 *
	 * intToDWord converts an int to a double word, where the return value is stored in a 4-byte array.
	 *
	 */
	protected byte[] intToDWord(int parValue) {
		byte retValue[] = new byte[4];
		retValue[0] = (byte) (parValue & 0x00FF);
		retValue[1] = (byte) ((parValue >> 8) & 0x000000FF);
		retValue[2] = (byte) ((parValue >> 16) & 0x000000FF);
		retValue[3] = (byte) ((parValue >> 24) & 0x000000FF);
		return (retValue);
	}
}
