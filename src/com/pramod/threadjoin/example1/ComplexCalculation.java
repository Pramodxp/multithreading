package com.pramod.threadjoin.example1;

import java.math.BigInteger;

public class ComplexCalculation {

	public static void main(String[] args) throws InterruptedException {
		System.out.println(
				calculateResult(new BigInteger("2"), new BigInteger("10"), new BigInteger("2"), new BigInteger("20")));

	}

	public static BigInteger calculateResult(BigInteger base1, BigInteger power1, BigInteger base2, BigInteger power2)
			throws InterruptedException {
		BigInteger result;
		PowerCalculatingThread thread = new PowerCalculatingThread(new BigInteger("2"), new BigInteger("10"));
		PowerCalculatingThread thread1 = new PowerCalculatingThread(new BigInteger("2"), new BigInteger("20"));

		thread.start();
		thread1.start();
		thread.join();
		thread1.join();
		result = thread.getResult().add(thread1.getResult());
		return result;
	}

	private static class PowerCalculatingThread extends Thread {
		private BigInteger result = BigInteger.ONE;
		private BigInteger base;
		private BigInteger power;

		public PowerCalculatingThread(BigInteger base, BigInteger power) {
			this.base = base;
			this.power = power;
		}

		@Override
		public void run() {
			for (BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)) {
				result = result.multiply(base);
			}
		}

		public BigInteger getResult() {
			return result;
		}
	}
}