package com.nitsshukla;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws Exception {
		FileStreamIterator<String> iterator = new FileStreamIterator<String>(
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
		int i=0;
		while(iterator.hasNext()) {
			System.out.println(iterator.next());
			if(i++>4)break;
		}
		System.out.println("------------");
		@SuppressWarnings("unchecked")
		FileStreamIterator<String> iteratorClone = (FileStreamIterator<String>) iterator.clone();
		while(iterator.hasNext()) {
			System.out.println(iterator.next());
		}
		System.out.println("------------");
		while(iteratorClone.hasNext()) {
			System.out.println(iteratorClone.next());
		}
		System.out.println("------------");
		
	}
}
