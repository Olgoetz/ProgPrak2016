package pp2016.team19.shared;

/**
 * Der Progprak-Server erlaubt nur ein minimum Sleep von 500 ms, mit dieser Methode ist es moeglich weniger als 500 ms zu sleepen.
 */
public class SleepTool
{

	/**
	 * Let the current thread "sleep" for the specific time.
	 *
	 * @param sleepTime
	 *         - the sleeptime in millis
	 */
	public static void sleepFor(long sleepTime)
	{
		long currentTime = System.currentTimeMillis();
		Thread.currentThread().setPriority(1);

		while (currentTime + sleepTime > System.currentTimeMillis())
			Thread.yield();
	}
}