package threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class AtomicIntegerExample {

	public static void main(String[] args) throws InterruptedException {
		AtomicInteger counter = new AtomicInteger();
		ExecutorService service = Executors.newFixedThreadPool(20);
		IntStream.range(0, 100).forEach(i -> {
			System.out.println(i);
			service.submit(new MyTask(counter));
		});

		TimeUnit.SECONDS.sleep(5);
		System.out.println("Final value of AtomicInteger - " + counter.get());
	}

	static class MyTask implements Runnable {

		private final AtomicInteger counter;

		public MyTask(AtomicInteger counter) {
			this.counter = counter;
		}

		@Override
		public void run() {
			System.out.println("Thread name - " + Thread.currentThread().getName() + " value = " + this.counter.incrementAndGet());
		}

	}

}
