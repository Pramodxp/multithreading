package com.pramod.threadjoin.example1;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//we will have a race condition, factorial thread is started and main thread is checking for the results.
//factorial threads and the main threads are racing towards their goals independentely.
public class JoinExp1 {

	public static void main(String[] args) throws InterruptedException {
		List<Long> inputNumbers = Arrays.asList(0l, 3435l, 34545l, 2332l, 2324l, 24l, 5556l);

		List<FactorialThread> threads = new ArrayList<>();

		for (Long value : inputNumbers) {
			threads.add(new FactorialThread(value));
		}

		for (FactorialThread thread : threads) {
			thread.setDaemon(true);
			thread.start();
		}
		for (FactorialThread thread : threads) {
			thread.join();
//			thread.join(2000);
		}

		for (int i = 0; i < inputNumbers.size(); i++) {
			FactorialThread factorialThread = threads.get(i);
			if (factorialThread.isFinished) {
				System.out.println("Factorial of " + inputNumbers.get(i) + " is " + factorialThread.getResult());
			} else {
				System.out.println("The calculation for " + inputNumbers.get(i) + " is still progress.");
			}
		}
	}

	public static class FactorialThread extends Thread {

		private BigInteger result = BigInteger.ZERO;
		private long inputNumber;
		private boolean isFinished = false;

		public FactorialThread(long inputNumber) {
			this.inputNumber = inputNumber;
		}

		@Override
		public void run() {
			result = factorial(inputNumber);
			isFinished = true;
		}

		private BigInteger factorial(long n) {
			BigInteger tempResult = BigInteger.ONE;

			for (long i = n; i > 0l; i--) {
				tempResult = tempResult.multiply(new BigInteger(Long.toString(i)));
			}
			return tempResult;
		}

		public BigInteger getResult() {
			return result;
		}

		public boolean isFinished() {
			return isFinished;
		}
	}

}
