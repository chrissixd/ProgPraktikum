package banane;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.*;

public class Client extends NetworkBase
{

	private String IP;
	
	public Client(String zielIp, int port, TestGUI gui)
	{
		super(gui);
		this.port = port;
		this.IP = zielIp;
	}
	
	public void run()
	{
		DataInputStream input = null;
		try 
		{
			client = new Socket(IP, port); //Setzt den Server hier
		} 
		catch (Exception e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 

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
