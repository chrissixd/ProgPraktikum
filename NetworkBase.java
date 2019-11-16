package banane;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public abstract class NetworkBase extends Thread{

	protected Socket client;
	protected String data;
	protected boolean expectMessage = false; // Für das PingPong
	protected DataInputStream input = null;
	protected int port;
	//protected SpielerBasis Spieler;
	protected TestGUI gui;
	
	
	public NetworkBase(TestGUI gui) 
	{
		this.gui = gui;
	}
	
	public boolean sendMessage(String command) throws IOException 
	{
		
		if(client == null) //Prüft ob der Client noch verbunden ist	
		{
			System.out.println("Es besteht keine Verbindung");
			return false;
		}
		else
		{
			DataOutputStream output = new DataOutputStream(client.getOutputStream()); //Setzt Client für den Output
			output.writeUTF(command); //Sende daten
			System.out.println("Send Data: " + command);
			expectMessage = true; 
			return true;
		}

	}
	protected boolean respond(String information)
	{
		String[] a = information.split("\\s+");
		if(expectMessage)
		{
			expectMessage = false;
			switch(a[0]) //hier werden die Befehle aufgeteilt und die bestimmten methoden aufgerufen
			{
				case "size":
					gui.setInputText( "Setze Spielfeld auf: " + a[1]+ ","+ a[1]);
					break;
					
				case "shot":

					break;
				case "quit":
					try {
						this.sendMessage("quit");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					gui.stopFrame();
					break;
				default:
					gui.setInputText(a[0]);
					return false;
			}
		}
		else
		{
			System.out.println("Unerwartete Message: Cheat Versuch?");
			return false;
		}

		return true;
	}

}
