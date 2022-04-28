package com.pramod.MultiExecutor.example1;

import java.util.ArrayList;
import java.util.List;

public class MultiExecutor {

	// Add any necessary member variables here
	List<Thread> tasks = new ArrayList<Thread>();
	static int count = 0;

	/*
	 * @param tasks to executed concurrently
	 */
	public MultiExecutor(List<Thread> tasks) {
		this.tasks = tasks;
	}

	/**
	 * Starts and executes all the tasks concurrently
	 */
	public void executeAll() {
		for (Thread thread : tasks) {
			thread.start();
		}
	}

	public static void main(String[] args) {
		List<Thread> tasks = new ArrayList<Thread>();
		MyThread thread1 = new MyThread();
		MyThread thread2 = new MyThread();
		MyThread thread3 = new MyThread();
		MyThread thread4 = new MyThread();
		MyThread thread5 = new MyThread();

		tasks.add(thread1);
		tasks.add(thread2);
		tasks.add(thread3);
		tasks.add(thread4);
		tasks.add(thread5);

		new MultiExecutor(tasks).executeAll();
		;
	}

	static class MyThread extends Thread {
		@Override
		public void run() {
			System.out.println("thread is :" + count++);
			super.run();
		}
	}
}