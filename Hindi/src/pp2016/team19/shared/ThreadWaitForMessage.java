package pp2016.team19.shared;

/**
 * <h1>Class for yielding a thread for a specified time.</h1>
 * 
 * Provides a method for yielding the currently executing Thread for a specified
 * time.
 * 
 * @author Taner Bulut, 5298261
 */
public class ThreadWaitForMessage {
	/**
	 * Causes the currently executing Thread to wait for the specified time.
	 * 
	 * @author Taner Bulut, 5298261
	 * @param waitingTime
	 *            the time that the Thread is waiting
	 * 
	 * 
	 */
	public static void waitFor(long waitingTime) {
		// Gets the current Time in milliseconds
		long currentTime = System.currentTimeMillis();
		// Changes the priority of this Thread
		Thread.currentThread().setPriority(1);

		// Waits until the set Time is passed
		while (currentTime + waitingTime > System.currentTimeMillis())
			// Yields the Thread, so that other Threads are run instead first
			Thread.yield();
	}
}