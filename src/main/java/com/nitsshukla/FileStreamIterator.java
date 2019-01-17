package com.nitsshukla;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.io.input.CountingInputStream;

public class FileStreamIterator<T> implements Iterator<T> {
	private final ListIterator<File> fileIterator;
	private CountingInputStream in;
	private List<File> files;
	Parser<T> parser;
	
	public FileStreamIterator(List<File> files, Parser<T> parser) throws IOException {
		this.fileIterator = files.listIterator();
		init(files, parser);
	}
	
	private void init(List<File> files, Parser<T> parser) throws IOException {
		this.files = files;
		this.parser = parser;
		initStream();
	}

	public FileStreamIterator(List<File> files, int index, long byteOffset, Parser<T> parser) throws IOException {
		fileIterator = files.listIterator(index);
		init(files, parser);
		in.skip(byteOffset);
	}

	public boolean hasNext() {
		try {
			//Avoid using available, it's not guaranteed. May be mark/reset or PushBackInputStream or Scanner
			boolean hasNext = in.available() != 0;
			if (hasNext) {
				return hasNext;
			}
			if (fileIterator.hasNext()) {
				initStream();
				return hasNext();
			}
			return false;
		} catch (IOException e) {
			throw new RuntimeException("exception while reading available " + e.getMessage());
		}
	}

	public T next() {
		return parser.parse(in);
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		try {
			FileStreamIterator<T> iterator = new FileStreamIterator<T>(files, fileIterator.previousIndex(), in.getByteCount(), parser);
			return iterator;
		} catch (IOException e) {
			//Do something plan B
			e.printStackTrace();
			throw new RuntimeException("Exception while cloning");
		}
	}
	
	private void initStream() throws IOException {
		if (in!=null)in.close();
		if (fileIterator.hasNext()) {
			File file = fileIterator.next();
			in = new CountingInputStream(new FileInputStream(file));
		}
	}
}
