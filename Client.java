package network;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.*;
import logic.Spieler;

public class Client extends NetworkBase
{

	private String IP;
	
	public Client(String zielIp, int port, Spieler spiel)
	{
		super(spiel);
		this.port = port;
		this.IP = zielIp;
	}
	
	public boolean connect()
	{
		if(client == null)
		{
			try 
			{
				client = new Socket(IP, port);
			} 
			catch (ConnectException e2) 
			{
				System.out.println("Fehler beim Verbinden.");
				return false;
			} 
			catch(IOException e1)
			{
				e1.getStackTrace();
				return false;
			}

			return true;
		}
		return false;
	}
	
	public void run()
	{
		DataInputStream input = null;
		int connectTry = 0;
		while(!connect() && connectTry < 10)
			connectTry++;
		
		if(connectTry == 10)
			System.out.println("Fehler, Server unerreichbar");
		
		while(client != null) //Nur falls ein Server da ist
		{
			try 
			{	
				input = new DataInputStream(client.getInputStream());
				if(input.available() > 0)
				{
					data = input.readUTF();
					System.out.println("Receive Data: " + data);
					respond(data);
				}

			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}


	}
	

	
}
