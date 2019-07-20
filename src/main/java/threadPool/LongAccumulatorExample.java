package threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAccumulator;

public class LongAccumulatorExample {

	public static void main(String[] args) throws InterruptedException {
		LongAccumulator counter = new LongAccumulator((x, y) -> x + y, 1);
		ExecutorService service = Executors.newFixedThreadPool(20);
		for (int i = 0; i < 100; i++) {
			service.submit(new MyTask(counter));
		}
		TimeUnit.SECONDS.sleep(2);
		System.out.println("Final value of LongAccumulator - " + counter.get());
	}

	static class MyTask implements Runnable {

		private final LongAccumulator counter;

		public MyTask(LongAccumulator counter) {
			this.counter = counter;
		}

		@Override
		public void run() {
			this.counter.accumulate(1);
			//System.out.println("Thread name - " + Thread.currentThread().getName() + " value = " + this.counter.increment());
		}

	}

}
