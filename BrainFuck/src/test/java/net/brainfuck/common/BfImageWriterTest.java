package net.brainfuck.common;

import net.brainfuck.exception.FileNotFoundException;
import net.brainfuck.exception.IOException;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertArrayEquals;

/**
 * Created by Alexandre on 16/11/2016.
 */
public class BfImageWriterTest {

	BfImageWriter writer;
	byte[] imageArray;
	private static String filename;

	@Before
	public void setUp() throws IOException, FileNotFoundException, java.io.FileNotFoundException {
		filename ="test.bmp";
		writer = new BfImageWriter(new FileOutputStream(filename));
		imageArray = new byte[]{
				0x42, 0x4d, 0x5a, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x36, 0x00, 0x00, 0x00, 0x28, 0x00
				, 0x00, 0x00, 0x03, 0x00, 0x00, 0x00, 0x03, 0x00, 0x00, 0x00, 0x01, 0x00, 0x18, 0x00, 0x00, 0x00
				, 0x00, 0x00, 0x24, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
				, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, 0x00
				, 0x00, 0x00, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, 0x00, 0x00, 0x00, (byte) 0xff, (byte) 0xff
				, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, 0x00, 0x00, 0x00};

	}

	@Test
	public void write() throws Exception {
		writer.write(0xffffff);
		writer.close();
		byte[] tmp = Files.readAllBytes(Paths.get(filename));
		assertArrayEquals(imageArray, tmp);
	}

	@Test
	public void write1() throws Exception {
		writer.write("ffffff");
		writer.close();
		//"src/test/resources/assets/brainfucktest/common/", "test.bmp"
		byte[] tmp = Files.readAllBytes(Paths.get(filename));
		assertArrayEquals(imageArray, tmp);
	}

	@Test(expected = IllegalArgumentException.class)
	public void close() throws Exception {
		writer.close();
		writer.write(111);
	}

	@AfterClass
	public static void cleanUp() throws java.io.IOException {
		new File(filename).delete();
	}
}