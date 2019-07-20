package threadPool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CountDownLatchExample {

	public static void main(String[] args) throws InterruptedException {
		CountDownLatch latch = new CountDownLatch(3);
		ExecutorService service = Executors.newFixedThreadPool(4);
		service.submit(new DepedentService(latch));
		TimeUnit.SECONDS.sleep(2);
		service.submit(new DepedentService(latch));
		TimeUnit.SECONDS.sleep(2);
		service.submit(new DepedentService(latch));
		TimeUnit.SECONDS.sleep(2);

		latch.await();

		System.out.println("All depedent services completed...");

	}

	private static class DepedentService implements Runnable {

		private final CountDownLatch latch;

		public DepedentService(CountDownLatch latch) {
			this.latch = latch;
		}

		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName() + " with latch - " + this.latch.getCount());
			this.latch.countDown();
		}

	}

}
