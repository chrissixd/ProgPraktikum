package testMains;

import java.io.IOException;

import network.no.implem.*;

public class ApfelMain {
	static String a = "";
	static Server testS = null;
	
	public static void main(String[] args) throws InterruptedException {
		
		try 
		{
			TestGUI gui = new TestGUI();
			testS = new Server(25565, gui);
			gui.setConnect(testS);
			testS.start();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}

			
	}

}
