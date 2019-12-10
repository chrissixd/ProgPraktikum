package testMains;

import java.io.IOException;
import java.net.SocketException;

import logic.Spieler;
import network.Client;;

public class ClientLogicTest {

	public static void main(String[] args) throws SocketException, IOException 
	{
		Spieler asd = new Spieler(true);
		Client client = new Client("127.0.0.1", 50000, asd);
		asd.setNetwork(client);
		client.start();
		
		
		while(!client.sendMessage("size 12"));
		asd.setFieldSize(12);
		String[] schiffCoord = {"0 0","0 1","0 2"};
		String[] schiffCoord2 = {"2 0", "2 1", "2 2", "2 3"};
		
		
		while(asd.getFieldSize() < 4);
		
		asd.setSchiffe(schiffCoord2);
		asd.setSchiffe(schiffCoord);


	}

}
