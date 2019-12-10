package testMains;

import network.no.implem.Client;
import network.no.implem.TestGUI;

public class TestMain {

	public static void main(String[] args) throws InterruptedException {

		try 
		{
			TestGUI gui = new TestGUI();
			Client testcl = new Client("127.0.0.1", 25565,gui);
			gui.setConnect(testcl);
			testcl.start();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}

	}

}
