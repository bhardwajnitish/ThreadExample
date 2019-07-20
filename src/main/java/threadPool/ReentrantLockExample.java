package threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockExample {

	public static void main(String[] args) throws InterruptedException {
		Lock aLock = new ReentrantLock(true);
		ExecutorService service = Executors.newFixedThreadPool(3);
		//IntStream.of(10).forEach(i -> service.submit(new Task(aLock)));
		service.submit(new Task(aLock));
		service.submit(new Task(aLock));
		service.submit(new Task(aLock));
		service.submit(new Task(aLock));
		service.submit(new Task(aLock));

		service.shutdown();
		service.awaitTermination(1, TimeUnit.MINUTES);
	}

	private static class Task implements Runnable {

		private final Lock aLock;

		public Task(Lock aLock) {
			this.aLock = aLock;
		}

		@Override
		public void run() {
			try {
				this.aLock.lock();
				System.out.printf("%s is doing task%n", Thread.currentThread().getName());
			} finally {
				this.aLock.unlock();
			}

		}

	}

}
