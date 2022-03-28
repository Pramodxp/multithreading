package com.pramod.creation.example3;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		NewThread thread = new NewThread();
		thread.start();
	}

	public static class NewThread extends Thread {
		@Override
		public void run() {
			System.out.println("Hello from : " + this.getName());
		}
	}
}
