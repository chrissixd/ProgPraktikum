package testMains;

import java.io.IOException;
import java.net.SocketException;

import logic.*;
import network.*;

public class ServerLogicMain 
{
	
	public static void main(String[] args) throws SocketException, IOException
	{
		Spieler asd = new Spieler(true);
		Server server = new Server(50000,asd);
		asd.setNetwork(server);
		server.start();
		
		String[] schiffCoord = {"0 0","0 1","0 2"};
		String[] schiffCoord2 = {"2 0", "2 1", "2 2", "2 3"};
		
		while(asd.getFieldSize() < 4);
		
		asd.setSchiffe(schiffCoord);
		asd.setSchiffe(schiffCoord2);
		
		while(!server.getOkayToSend());
	
		asd.saveGame(0);

	}
	
}
