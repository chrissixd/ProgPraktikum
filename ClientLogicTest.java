package testMains;

import java.io.IOException;
import java.net.SocketException;

import logic.Spieler;
import network.Client;;

public class ClientLogicTest {

	public static void main(String[] args) throws SocketException, IOException 
	{
		Spieler asd = new Spieler(0, true);
		Client client = new Client("141.18.106.247", 50000, asd);
		asd.setNetwork(client);
		client.start();
		
		
		while(!client.sendMessage("size 6"));
		asd.setFieldSize(6);
		String[] schiffCoord = {"0 0","0 1"};
		String[] schiffCoord2 = {"2 0", "2 1", "2 2", "2 3"};
		String[] schiffCoord3 = {"4 0","4 1","4 2"};
		
		while(asd.getFieldSize() < 4 );
		
		asd.setSchiffe(schiffCoord2);
		asd.setSchiffe(schiffCoord);
		asd.setSchiffe(schiffCoord3);
		
		while(!client.getOkayToSend()); //Potenziell wird das Probleme machen
		System.out.println("Ich eh darf senden");


	}

}
