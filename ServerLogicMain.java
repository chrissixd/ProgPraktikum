package testMains;

import java.io.IOException;
import java.net.SocketException;

import logic.*;
import network.*;

public class ServerLogicMain 
{
	
	public static void main(String[] args) throws SocketException, IOException
	{
		Spieler asd = new Spieler(0, true);
		Server server = new Server(50000,asd);
		asd.setNetwork(server);
		server.start();
		
		String[] schiffCoord3 = {"0 0","0 1","4 2"};
		String[] schiffCoord = {"0 0","0 1"};
		String[] schiffCoord2 = {"2 0", "2 1", "2 2", "2 3"};
		while(asd.getFieldSize() < 4);
		
		asd.setSchiffe(schiffCoord);
		asd.setSchiffe(schiffCoord3);
		asd.setSchiffe(schiffCoord2);	
		
		while((!server.getOkayToSend()) || !asd.getConf());
		
		for(int i = 0; i < schiffCoord2.length;i++)
		{
			if(server.strike(schiffCoord2[i]) == 0)
				break;
		}
			
		
		while(!server.getOkayToSend());
		asd.saveGame(0);

	}
	
}
