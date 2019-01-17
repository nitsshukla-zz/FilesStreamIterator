package com.nitsshukla;

import java.io.InputStream;

public interface Parser<T> {
	public T parse(InputStream in);
}