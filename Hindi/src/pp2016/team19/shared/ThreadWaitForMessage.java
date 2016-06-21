package pp2016.team19.shared;

public class ThreadWaitForMessage
{
	
	public static void waitFor(long waitingTime)
	{
		long currentTime = System.currentTimeMillis();
		Thread.currentThread().setPriority(1);

		while (currentTime + waitingTime > System.currentTimeMillis())
			Thread.yield();
	}
}