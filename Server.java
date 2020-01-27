package network;

import java.io.DataInputStream;
import java.util.Enumeration;
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
		try
		{
			server = new ServerSocket(port);	
			server.setSoTimeout(100000);
		}
		catch(BindException ex)
		{
			System.out.println("Port ist schon in verwendung, läuft es noch"
					+ " im hintergrund?");
		}
		
		expectMessage = true;
	}
	
	public String getMyIP() // gibt die Locale IP aus, damit die IP vom Server nicht gesucht werden muss,
	{						// falls mich das leben hasst, gebe 0.0.0.0 aus, normalerweise 127.0.0.1
		try
		{
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces.hasMoreElements())
			{
				NetworkInterface networkInterface = interfaces.nextElement(); //Bekomme neues Netzwerkinterface
				Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
				while(inetAddresses.hasMoreElements()) //Prüfe alle IPs vom Netzwerkinterface ob es IPv4 ist
				{
					InetAddress a = inetAddresses.nextElement();
					if(a instanceof Inet4Address)
					{
						if(!a.toString().equals("/192.168.56.1") && !a.toString().equals("/127.0.0.1"))
							return a.toString(); //wenn es nicht von Virtualbox ist oder die LocalHost addresse geb die aus
					}
				}

			}
			return "0.0.0.0";
			
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
