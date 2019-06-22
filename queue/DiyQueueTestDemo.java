package com.gupao.io.multiThreadDemo.queue;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.gupao.io.multiThreadDemo.queue.entity.User;
import com.gupao.io.multiThreadDemo.queue.impl.DiyQueueImpl;

public class DiyQueueTestDemo {
	
	public static void main(String[] args) {
		try{
			CountDownLatch countDownLatch = new CountDownLatch(100);
			DiyQueueImpl impl=new DiyQueueImpl(50);
			//生产者线程
			Random random = new Random(50);
			for(int i=0;i<50;i++){
				final int count=i;
				new Thread(()->{
					User user = new User();
					user.setId(count);
					user.setName(random.nextInt()+"hello");
					impl.put(user);
				    countDownLatch.countDown();
				}).start();
			}
			//消费者线程
			for(int i=0;i<50;i++){
				new Thread(()->{
					System.err.println(
							Thread.currentThread().
							getName()+"取出元素是:"+
									impl.take().getId());
					countDownLatch.countDown();
				},"取出线程"+i).start();
			}
			countDownLatch.await();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
	}
}
