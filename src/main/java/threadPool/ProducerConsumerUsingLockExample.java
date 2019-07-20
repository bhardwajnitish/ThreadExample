package threadPool;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerUsingLockExample {

	public static void main(String[] args) throws InterruptedException {

		ProducerConsumerImpl sharedObject = new ProducerConsumerImpl();
		Producer producer = new Producer(sharedObject);
		Consumer consumer = new Consumer(sharedObject);

		producer.start();
		consumer.start();

		producer.join();
		consumer.join();

		System.out.println("Threads completed--------------");

	}

	private static class ProducerConsumerImpl {

		private static final int CAPACITY = 10;
		private final Queue<Integer> queue = new LinkedList<>();
		private final Random theRandom = new Random();

		// lock and condition variables
		private final Lock aLock = new ReentrantLock();
		private final Condition monitor = this.aLock.newCondition();

		private void put() {
			this.aLock.lock();
			try {
				while (true) {
					TimeUnit.SECONDS.sleep(2);
					if (this.queue.size() == CAPACITY) {
						System.out.println(Thread.currentThread().getName() + " : Buffer is full, waiting");

						this.monitor.await();
					}
					int number = this.theRandom.nextInt(99);
					if (this.queue.offer(number)) {
						System.out.printf("%s added %d into queue %n", Thread.currentThread().getName(), number);

						// signal consumer thread that, buffer has element now
						System.out.println(Thread.currentThread().getName() + " : Signalling that buffer is no more empty now");
						this.monitor.signal();
					}
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				this.aLock.unlock();
			}
		}

		private void get() {
			this.aLock.lock();
			try {
				while (true) {
					TimeUnit.SECONDS.sleep(1);
					if (this.queue.size() == 0) {
						System.out.println(Thread.currentThread().getName() + " : Buffer is empty, waiting");
						this.monitor.await();
					}
					Integer value = this.queue.poll();
					if (value != null) {
						System.out.printf("%s consumed %d from queue %n", Thread.currentThread().getName(), value);
						// signal producer thread that, buffer may be empty now
						System.out.println(Thread.currentThread().getName() + " : Signalling that buffer may be empty now");
						this.monitor.signalAll();

					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				this.aLock.unlock();
			}
		}

	}

	private static class Producer extends Thread {

		private final ProducerConsumerImpl sharedObject;

		public Producer(ProducerConsumerImpl sharedObject) {
			super("PRODUCER");
			this.sharedObject = sharedObject;
		}

		@Override
		public void run() {
			this.sharedObject.put();
		}

	}

	private static class Consumer extends Thread {

		private final ProducerConsumerImpl sharedObject;

		public Consumer(ProducerConsumerImpl sharedObject) {
			super("CONSUMER");
			this.sharedObject = sharedObject;
		}

		@Override
		public void run() {
			this.sharedObject.get();
		}

	}

}
