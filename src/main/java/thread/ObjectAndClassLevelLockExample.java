package thread;

import java.util.concurrent.TimeUnit;

public class ObjectAndClassLevelLockExample {

	public static void main(String[] args) {
		Task task = new Task();
		task.start();
		task = new Task();
		task.start();
		task = new Task();
		task.start();
		task = new Task();
		task.start();
		task = new Task();
		task.start();
	}

	private static class Task extends Thread {

		@Override
		public void run() {
			m2();
			m1();
			//this.m3();
			//this.m4();
			//m5();
			//this.m6();
		}

		private static void m1() {
			System.out.println(Thread.currentThread().getName() + " - This is static m1 method");
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		private static synchronized void m2() {
			System.out.println(Thread.currentThread().getName() + " - This is static synchronized m2 method");
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		private void m3() {
			System.out.println(Thread.currentThread().getName() + " - This is m3 method");
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		private synchronized void m4() {
			System.out.println(Thread.currentThread().getName() + " - This is synchronized m4 method");
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		private static void m5() {
			synchronized (Task.class) {
				System.out.println(Thread.currentThread().getName() + " - This is static m5 with synchronized block method");
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		private void m6() {
			synchronized (this) {
				System.out.println(Thread.currentThread().getName() + " - This is m6 synchronized block method");
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
