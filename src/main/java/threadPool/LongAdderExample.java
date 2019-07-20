package threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

public class LongAdderExample {

	public static void main(String[] args) throws InterruptedException {
		LongAdder counter = new LongAdder();
		ExecutorService service = Executors.newFixedThreadPool(20);
		for (int i = 0; i < 100; i++) {
			service.submit(new MyTask(counter));
		}
		TimeUnit.SECONDS.sleep(5);
		System.out.println("Final value of LongAdder - " + counter.sum());

		service.shutdown();
		service.awaitTermination(5, TimeUnit.SECONDS);
	}

	static class MyTask implements Runnable {

		private final LongAdder counter;

		public MyTask(LongAdder counter) {
			this.counter = counter;
		}

		@Override
		public void run() {
			synchronized (this) {
				this.counter.increment();
			}
			System.out.println("Thread name - " + Thread.currentThread().getName() + " value = " + this.counter.sum());
			try {
				TimeUnit.MICROSECONDS.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
