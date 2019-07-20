package threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class PhaserExample {

	public static void main(String[] args) throws InterruptedException {

		Phaser phaser = new Phaser(3);

		ExecutorService service = Executors.newFixedThreadPool(4);

		service.submit(new BarrierTask(phaser));
		TimeUnit.SECONDS.sleep(2);
		service.submit(new BarrierTask(phaser));
		TimeUnit.SECONDS.sleep(2);
		service.submit(new BarrierTask(phaser));
		TimeUnit.SECONDS.sleep(2);

		phaser.bulkRegister(3);

		service.submit(new CountDownTask(phaser));
		TimeUnit.SECONDS.sleep(2);
		service.submit(new CountDownTask(phaser));
		TimeUnit.SECONDS.sleep(2);
		service.submit(new CountDownTask(phaser));
		TimeUnit.SECONDS.sleep(2);

		phaser.arriveAndDeregister();

		System.out.println("All phaser arrived--------------");
	}

	private static class BarrierTask implements Runnable {

		private final Phaser phaser;

		public BarrierTask(Phaser phaser) {
			this.phaser = phaser;
		}

		@Override
		public void run() {
			//this.phaser.register();
			System.out.println("Phaser " + this.phaser.getArrivedParties() + " arrived--------------");
			this.phaser.arriveAndAwaitAdvance();

			System.out.println("All phaser arrived--------------");
		}

	}

	private static class CountDownTask implements Runnable {

		private final Phaser phaser;

		public CountDownTask(Phaser phaser) {
			this.phaser = phaser;
		}

		@Override
		public void run() {
			System.out.println("Phaser " + this.phaser.getArrivedParties() + " arrived--------------");
			this.phaser.arrive();
		}

	}
}
