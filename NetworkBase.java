package network;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import logic.*;

public abstract class NetworkBase extends Thread{

	protected volatile Socket client;
	protected volatile String data;
	protected volatile boolean expectMessage = false; // Für das PingPong
	protected DataInputStream input = null;
	protected int port;
	protected Spieler Spiel;
	private String lastMessage = "";
	
	public NetworkBase(Spieler spiel) 
	{
		this.Spiel = spiel;
	}
	
	public String getLastMessage()
	{
		return lastMessage;
	}
	
	public boolean getOkayToSend()
	{
		//Gibt aus ob man was Senden darf oder nicht
		return !expectMessage;
	}
	public boolean sendMessage(String command) 
	{
		try
		{
			if(client == null) //Prüft ob der Client noch verbunden ist	
			{
				return false;
			}
			else
			{
				data = "";
				DataOutputStream output = new DataOutputStream(client.getOutputStream()); //Setzt Client für den Output
				output.writeUTF(command); //Sende daten
				System.out.println("Send Data: " + command);
				expectMessage = true;
				return true;
			}
		}
		catch(IOException ex)
		{
			return false;
		}


	}
	
	protected boolean respond(String information)
	{
		String[] a = information.split("\\s+");
		if(expectMessage)
		{
			expectMessage = false;
			lastMessage = information;
			switch(a[0]) //hier werden die Befehle aufgeteilt und die bestimmten methoden aufgerufen
			{
				case "size":
					Spiel.setFieldSize(Integer.parseInt(a[1])); //Setze Spielfeld größe
					break;
				
					
				case "shot":
					
					if(a.length >= 3)
					{
						sendMessage("answer " + Spiel.getAttack(a[1] + " " + a[2])); //Gebe zurück ob getroffen wurde oder nicht
					}
					else
						System.out.println("Fehleingabe");
					break;
					
				
				case "save":
					long save = Long.parseLong(a[1]);
					System.out.println("Save: " + save);
					Spiel.saveGame(save);
					break;
				
				case "load":
					System.out.println();
					break;
				case "answer":
					data = information;
					break;
					
			}

		}
		else
		{
			System.out.println("Unerwartete Message");
			return false;
		}

		return true;
	}

	public int strike(String a)
	{
		sendMessage("shot " + a);
		
		while(data == "");
		System.out.println(data);
		String[] b = data.split("\\s+");

		if(b[0].equals("answer"))
		{
			int ergebnis = Integer.parseInt(b[1]);	
			if(ergebnis == 0) // falls das Ergebnis 0 ist, sende pass
				sendMessage("pass");
			data = "";
			return ergebnis;
		}
		else
			return -1;
	}
	
	public boolean finishShip()
	{
		try
		{
			while(expectMessage)
			{
				Thread.sleep(10);
			}
		}
		catch(Exception ex)
		{
			return false;
		}


			//Nur falls keine Nachricht erwartet wird, darf man eine Nachricht schicken
		if(sendMessage("confirmed"))
			return true;
		return false;


	}
	

}
