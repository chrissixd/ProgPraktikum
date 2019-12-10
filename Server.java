package network;

import java.io.DataInputStream;
import java.net.InetAddress;
import java.io.IOException;
import java.net.*;
import logic.Spieler;

public class Server extends NetworkBase
{
	
	private ServerSocket server;
	private String data = "";
	private DataInputStream input;
	public Server(int port, Spieler spiel) throws IOException, SocketException
	{
		super(spiel);
		server = new ServerSocket(port);
		server.setSoTimeout(100000);
		expectMessage = true;
	}
	
	public String getMyIP() // gibt die Locale IP aus, damit die IP vom Server nicht gesucht werden muss,
	{						// falls mich das leben hasst, gebe 0.0.0.0 aus, normalerweise 127.0.0.1
		try
		{
			return InetAddress.getLocalHost().toString();
		}
		catch(Exception ex)
		{
			return "0.0.0.0";
		}

	}
	
	public void run()
	{
		while(true) //Soll die ganzezeit geprüft werden
		{
			try
			{
				if(client == null)
				{
					System.out.println("Waiting for client at Port: " + server.getLocalPort());
					System.out.println("Waiting at IP: " + getMyIP());
					client = server.accept();
					System.out.println("Client connected: " + client.getLocalSocketAddress());

					input = new DataInputStream(client.getInputStream());
				}
				else if(input.available() > 0)
				{
					data = input.readUTF();
					System.out.println("Received Data: " + data);
					respond(data);
				}

				
			}
			catch(Exception ex)
			{
				System.out.print("Fehler, Server wird geschlossen!");
				break;
			}
		}
	}


	

	
}
