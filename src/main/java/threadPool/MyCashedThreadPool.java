package threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class MyCashedThreadPool {

	private static final Logger LOGGER = Logger.getLogger(MyCashedThreadPool.class.getName());

	public static void main(String[] args) {
		sleepSeconds(10); // give 10 seconds to connect to JVisualMV

		ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();

		newCachedThreadPool.submit(() -> sleepSeconds(5)); // starts only first thread
		sleepSeconds(6); // wait 6s so first thread finishes running the task

		newCachedThreadPool.submit(() -> sleepSeconds(5)); // reuses 1nd thread as
															// available/waiting for 2nd task
		sleepSeconds(1); // wait 1s to add 3rd task

		newCachedThreadPool.submit(() -> sleepSeconds(5)); // starts 2nd thread for 3rd task
															// as 1st thread still busy
		sleepSeconds(1); // wait 1s to add 4rd task

		newCachedThreadPool.submit(() -> sleepSeconds(5)); // starts 3rd thread for 4rd task
															// as 1st and 2nd thread still busy
		sleepSeconds(70); // wait 70s to so 1st, 2nd and 3rd thread terminate after 60s of being idle

		newCachedThreadPool.submit(() -> sleepSeconds(5)); // starts 4th thread for 5th task
															// as no previous threads were available
		sleepSeconds(10);
	}

	private static void sleepSeconds(int seconds) {
		try {
			LOGGER.info(currentThreadId() + "About to sleep {} seconds " + seconds + "\n");
			TimeUnit.SECONDS.sleep(seconds);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String currentThreadId() {
		long id = Thread.currentThread().getId();
		return String.format("Thread id=%-2s|", id);
	}

}
