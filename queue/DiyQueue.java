package com.gupao.io.multiThreadDemo.queue;

public interface DiyQueue<T> {

	
	
	void put(T t);
	
	<T> T take();
}
