package banane;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public abstract class NetworkBase extends Thread{

	protected Socket client;
	protected String data;
	protected DataInputStream input = null;
	protected int port;
	//protected SpielerBasis Spieler;
	protected TestGUI gui;
	
	
	public NetworkBase(TestGUI gui) 
	{
		//Spieler = Spiel; //Braucht das Spiel/Logik um die Daten zu versenden und die Aufrufe zu machen
		this.gui = gui;
	}
	
	public boolean sendMessage(String command) throws IOException 
	{
		
		if(client == null) //Prüft ob der Client noch verbunden ist
			return false;
		else
		{
			DataOutputStream output = new DataOutputStream(client.getOutputStream());
			output.writeUTF(command);
			System.out.println("Send Data: " + command);
			return true;
		}

	}
	protected boolean respond(String information)
	{
		String[] a = information.split("\\s+");
		switch(a[0])
		{
			case "size":
				
				break;
				
			case "shot":
				//Spieler.attack(a[1] + "," + a[2]);
				break;
			case "quit":
				gui.stopFrame();
				break;
			default:
				gui.setInputText(a[0]);
				return false;
		}
		return true;
	}

}
