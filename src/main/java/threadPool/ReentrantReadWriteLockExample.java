package threadPool;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockExample {

	private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	private final ReentrantReadWriteLock.ReadLock readLock = this.lock.readLock();
	private final ReentrantReadWriteLock.WriteLock writeLock = this.lock.writeLock();

	public static void main(String[] args) {
		ReentrantReadWriteLockExample example = new ReentrantReadWriteLockExample();
		new Thread(() -> example.readResource()).start();
		new Thread(() -> example.readResource()).start();
		new Thread(() -> example.readResource()).start();
		new Thread(() -> example.writeResource()).start();
		new Thread(() -> example.writeResource()).start();
	}

	private void readResource() {
		this.readLock.lock();
		System.out.printf("%s reading resouce----------%n", Thread.currentThread().getName());
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.readLock.unlock();
	}

	private void writeResource() {
		this.writeLock.lock();
		System.out.printf("%s writing resouce----------%n", Thread.currentThread().getName());
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.writeLock.unlock();
	}

}
