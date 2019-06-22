package com.gupao.io.multiThreadDemo.queue.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import com.gupao.io.multiThreadDemo.queue.DiyQueue;
import com.gupao.io.multiThreadDemo.queue.entity.User;

public class DiyQueueImpl implements DiyQueue<User>{

	private Integer length;
	
	private Integer default_length=1<<4;
	
	private User[] table;
	
	private ReentrantLock lock=new ReentrantLock();
	
	private Condition condition=lock.newCondition();
	
	public DiyQueueImpl(Integer length){
		this.length=length;
		createNodeArr();
	}
	public DiyQueueImpl(){
		createNodeArr();
	}
	private void createNodeArr() {
		Integer tableSize=0==length?default_length:length;
		table=new User[tableSize];
	}
	
	@Override
	public void put(User t) {
		try{
			lock.lock();
			for(;;){
				Integer index = firtNonelementIndex();
				//如果数组满了
				if(index<0){
					condition.await();
				}else{
					//lock.lock();
					table[index]=t;
					System.out.println("线程名称:"+Thread.currentThread().getName()+"数组位置:"+index+"放入的元素是:"+t.getId());
					condition.signalAll();
					return;
				}
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
		
	}
	/**
	 * 返回数组第一个元素为null的下标 如果返回-1表示数组已经满了
	 * @return
	 */
	private Integer firtNonelementIndex() {
		for(int i=0;i<table.length;i++){
			if(Objects.isNull(table[i])){
				return i;
			}
		}
		return -1;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public  User take() {
		User u=null;
		try{
			lock.lock();
			for(;;){
				//System.out.println("开始进入取数过程");
				Integer index = firtNonelementIndex();
				if(0!=index){
					u=table[0];
					condition.signalAll();
					reSetArr();
					return u;
				}else{
					condition.await();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
		return null;
	}
	/**
	 * 重新设置
	 */
	private void reSetArr(){
		User[] oldTable=table;
		table=new User[oldTable.length];
		for(int i=0;i<table.length-1;i++){
			table[i]=oldTable[i+1];
		}
		
	}
	
	public static void main(String[] args) {
		
	}
}
