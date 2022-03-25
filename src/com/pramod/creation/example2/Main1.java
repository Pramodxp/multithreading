package com.pramod.creation.example2;

public class Main1 {

	public static void main(String[] args) throws InterruptedException {

		// create a thread object and pass runnable object for it.
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {

				throw new RuntimeException("Intentional Exception");
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

		thread.setName("misbehaviour thread");

		// setting the static component of the dynamic priority.
		thread.setPriority(Thread.MAX_PRIORITY);

		thread.start();

	}

}
