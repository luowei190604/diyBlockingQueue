package com.gupao.io.multiThreadDemo.queue.entity;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import lombok.Data;

@Data
public class User {
	
	public User(){
//		Random random = new Random(50);
//		this.setId(random.);
//		this.setName(random.nextInt()+"hello");
	}
	
	private Integer id;
	
	private String name;
	
	public static void main(String[] args) {
		Integer arr[]=new Integer[10];
		arr[0]=1;
		List<Integer> asList = Arrays.asList(arr);
		System.out.println(asList.size());
	}
}
