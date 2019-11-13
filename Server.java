package banane;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

public class Server extends NetworkBase
{
	
	private ServerSocket server;
	private String data = "";
	private DataInputStream input;
	public Server(int port, TestGUI gui/*SpielerBasis spiel*/) throws IOException, SocketException
	{
		//super(spiel);
		super(gui);
		server = new ServerSocket(port);
		server.setSoTimeout(100000);
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
