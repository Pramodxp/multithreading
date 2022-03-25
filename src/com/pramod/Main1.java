package com.pramod;

public class Main1 {

	public static void main(String[] args) throws InterruptedException {

		// create a thread object and pass runnable object for it.
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
//				System.out.println("we are now in thread : " + Thread.currentThread().getName());
//				System.out.println("Current Thread priority is : " + Thread.currentThread().getPriority());

				throw new RuntimeException("Internal Exception");
			}
		});
		
//-------------------------------------------------------------------------------------------
		// handler for the thread, if the exception or error has been not caught the
		// handler will handle.
		thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

			@Override
			public void uncaughtException(Thread t, Throwable e) {
				System.out.println(
						"A cretical Error happened in thread : " + thread.getName() + " Error is " + e.getMessage());
			}
		});
//-------------------------------------------------------------------------------------------

		thread.setName("New Worker Thread");

		// setting the static component of the dynamic priority.
		thread.setPriority(Thread.MAX_PRIORITY);

		System.out.println("We are in thread : " + Thread.currentThread().getName() + " Before starting the thread");
		thread.start();
		System.out.println("We are in thread : " + Thread.currentThread().getName() + " After starting the thread");

		// instructs the operating system , do not schedule this thread until that time
		// passes.
		// during that time this thread is not consuming any CPU.
		Thread.sleep(10000);
	}

}
