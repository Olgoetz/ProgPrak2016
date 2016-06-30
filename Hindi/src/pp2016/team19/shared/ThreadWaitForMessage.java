package pp2016.team19.shared;

/**
 * <h1>Class for yielding a thread for a specified time.</h1>
 * 
 * Provides a method for yielding the currently executing Thread for a specified time. 
 * 
 * @author Taner Bulut, 5298261
*/
public class ThreadWaitForMessage
{
	/**
	 * Causes the currently executing Thread to wait for the specified time.
	 * 
	 * @author Taner Bulut, 5298261
	 * @param waitingTime - the time that the Thread is waiting
	 * 
	 * 
	 */
	public static void waitFor(long waitingTime)
	{
		long currentTime = System.currentTimeMillis();
		Thread.currentThread().setPriority(1);

		while (currentTime + waitingTime > System.currentTimeMillis())
			Thread.yield();
	}
}