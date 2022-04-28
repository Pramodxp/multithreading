package com.pramod.inturrupt.example1;

public class Inturrupt1 {

	public static void main(String[] args) {
		Thread thread = new Thread(new BlockingTask());
		
		thread.start();
		
		thread.interrupt();

	}
	
	static class BlockingTask implements Runnable{
		@Override
		public void run() {
			try {
				Thread.sleep(50000);
			} catch (InterruptedException e) {
				System.out.println("Exiting the BLocking Thread");
			}
		}
	}

}
