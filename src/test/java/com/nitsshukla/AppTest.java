package com.nitsshukla;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	FileStreamIterator<String> iterator = null;
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 * @throws FileNotFoundException 
	 */
	public AppTest(String testName) throws IOException {
		super(testName);
		iterator = new FileStreamIterator<String>(
				Arrays.asList(new File("tst/a.txt"), new File("tst/b.txt")), (is) -> {
					StringBuilder builder = new StringBuilder();
					int c1;
					char c;
					try {
						while ((c1 = is.read()) != -1) {
							c = (char) c1;
							if (c != '\n')
								builder.append(c);
							else
								return builder.toString();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
					try {
						if (is.available() != 0)
							throw new RuntimeException("Wrong format of file");
					} catch (IOException e) {
						e.printStackTrace();
					}
					return builder.toString();
				});
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	/**
	 * Rigourous Test :-)
	 * @throws FileNotFoundException 
	 * @throws CloneNotSupportedException 
	 */
	public void testApp() throws FileNotFoundException, CloneNotSupportedException {
		for(int i=1;i<6;i++) {
			Assert.assertTrue(iterator.hasNext());
			Assert.assertEquals(iterator.next(), (""+i));
		}
		FileStreamIterator<String> iteratorClone = (FileStreamIterator<String>) iterator.clone();
		for(int i=6;i<11;i++) {
			Assert.assertTrue(iterator.hasNext());
			Assert.assertEquals(iterator.next(), (""+i));
		}
		for(int i=6;i<11;i++) {
			Assert.assertTrue(iteratorClone.hasNext());
			Assert.assertEquals(iteratorClone.next(), (""+i));
		}
	}
	
	/**
	 * Rigourous Test - 2 :-)
	 * @throws FileNotFoundException 
	 * @throws CloneNotSupportedException 
	 */
	public void testApp2() throws FileNotFoundException, CloneNotSupportedException {
		for(int i=1;i<9;i++) {
			Assert.assertTrue(iterator.hasNext());
			Assert.assertEquals(iterator.next(), (""+i));
		}
		FileStreamIterator<String> iteratorClone = (FileStreamIterator<String>) iterator.clone();
		for(int i=9;i<11;i++) {
			Assert.assertTrue(iterator.hasNext());
			Assert.assertEquals(iterator.next(), (""+i));
		}
		for(int i=9;i<11;i++) {
			Assert.assertTrue(iteratorClone.hasNext());
			Assert.assertEquals(iteratorClone.next(), (""+i));
		}
	}
	
}
