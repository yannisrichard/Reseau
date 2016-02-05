/**
 * 
 */
package server;

/**
 * @author yarichard1
 *
 */
public class ServerMain {

	/**
	 * Le main.
	 * @param args Les arguments.
	 */
	public static void main(String[] args)
	{
		new Server(16000);
		new ServerSSL(9999);
	}
}
