package threadPool;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CyclicBarrierExample {

	public static void main(String[] args) throws InterruptedException {
		CyclicBarrier barrier = new CyclicBarrier(3);

		ExecutorService service = Executors.newFixedThreadPool(4);

		service.submit(new DepedentService(barrier));
		TimeUnit.SECONDS.sleep(2);
		service.submit(new DepedentService(barrier));
		TimeUnit.SECONDS.sleep(2);
		service.submit(new DepedentService(barrier));
		TimeUnit.SECONDS.sleep(2);

		//System.out.println("All depedent services completed...");

	}

	private static class DepedentService implements Runnable {

		private final CyclicBarrier barrier;

		public DepedentService(CyclicBarrier barrier) {
			this.barrier = barrier;
		}

		@Override
		public void run() {
			while (true) {
				System.out.println(Thread.currentThread().getName() + " with barrier - " + this.barrier.getNumberWaiting());
				try {
					this.barrier.await();
				} catch (InterruptedException | BrokenBarrierException e) {
					e.printStackTrace();
				}

				System.out.println("All depedent services completed...");
				this.barrier.reset();
			}
		}

	}
}
