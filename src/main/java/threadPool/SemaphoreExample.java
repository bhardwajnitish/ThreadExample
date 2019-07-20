package threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreExample {

	public static void main(String[] args) throws InterruptedException {
		Semaphore semaphore = new Semaphore(3);
		ExecutorService service = Executors.newFixedThreadPool(50);
		//IntStream.of(1000).forEach(i -> service.submit(new Task(semaphore)));

		service.submit(new Task(semaphore));
		service.submit(new Task(semaphore));
		service.submit(new Task(semaphore));
		service.submit(new Task(semaphore));
		service.submit(new Task(semaphore));
		service.submit(new Task(semaphore));

		service.shutdown();
		service.awaitTermination(1, TimeUnit.MINUTES);
	}

	private static class Task implements Runnable {
		Semaphore semaphore;

		public Task(Semaphore semaphore) {
			super();
			this.semaphore = semaphore;
		}

		@Override
		public void run() {
			this.semaphore.acquireUninterruptibly();
			System.out.printf("After acquring total %d semaphore available currently %n", this.semaphore.availablePermits());
			System.out.println(Thread.currentThread().getName() + " doing job ----------");
			this.semaphore.release();
			System.out.printf("After releasing total %d semaphore available currently %n", this.semaphore.availablePermits());
		}

	}
}
