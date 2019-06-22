package com.gupao.io.multiThreadDemo.queue;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Test {

	public static void main(String[] args) throws InterruptedException {
		ReentrantLock lock = new ReentrantLock();
		Condition condition=lock.newCondition();
		
		new Thread(()->{
			try {
				lock.lock();
				TimeUnit.SECONDS.sleep(10);
				condition.await();
				System.out.println("已经获得锁了");
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				System.out.println("马上要释放锁了");
				lock.unlock();
			}
			
		}).start();
		TimeUnit.SECONDS.sleep(15);
		lock.lock();
		condition.signal();
		lock.unlock();
	}
}
