package logic;

import network.NetworkBase;
import java.io.PrintStream;
import java.io.File;
import java.io.IOException;
//import java.io.FileReader;


public class Spieler 
{
	protected NetworkBase Netz;
	protected Field field;
	protected volatile int FG = 0;
	protected int AS;			//Anzahl Schiffe
	protected boolean Online = false;
	
	public Spieler(int fg,int as) 
	{	
		this.AS=as;
		field = new Field(AS);
		this.FG=fg;
	}
	public Spieler(boolean online)
	{
		AS = 2;
		this.Online = online;
	}
	
	public boolean saveGame(long ID)//Speicher den Status aller Schiffe in einer ID, falls ein fehler eintritt gebe false aus
	{
		if(ID == 0)
		{
			//Falls keine ID angegeben wird, erstelle ID schicke es dem anderen und speichere selber
			long a = java.lang.System.currentTimeMillis();
			Netz.sendMessage("save " + a);
			return saveGame(a);
		}
		else
		{
			PrintStream speicherFile;
			try 
			{
				speicherFile = new PrintStream(new File(ID + ".save"));
			
				speicherFile.println(FG);//Speichere Feldgröße
				speicherFile.println(!Netz.getOkayToSend());
				
				Ships[] asd = field.getSchiffe();
				
				for(int i = 0; i < asd.length; i++) //Speichere jedes Schiff
				{
					speicherFile.println(asd[i].getCoords());
				}
				
				speicherFile.close();//Schließe Datei
				return true;
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				return false;
			}
		}
	}
	
	
	public int attackOnline(String coord) //Schickt dem Gegner die Shot Message mit den benötigten Coordinaten
	{	
		return Netz.strike(coord);						
	}
	
	public int getAttack(String coord) {	//weiter an Logik
		return field.attack(coord);		//egal ob durch Online
	}									//oder offline
	
	public void setSchiffe(String[] coord) 
	{	//Spieler setzt Schiffe, bekommt Coordinaten vom GUI
		
		if(!Online)
			field.addShips(coord);
		else if(FG > 0)
		{
			if(field.addShips(coord).equals("confirmed")) //Falls Online, setze Schiffe, falls alle gesetzt sende "confirmed"
			{
				Netz.finishShip(); //Falls alle Schiffe gesetzt worden sind, schicke Confirmed Nachricht
			}

		}

	}
	public void setFieldSize(int size) //Fürs Netzwerk, damit der Client/Host die Feldgröße bekommt
	{
		field = new Field(AS); //Die Zahl muss noch angepasst werden, auf die Zahl in der Excel Tabelle
		FG = size;
	}
	public void setNetwork(NetworkBase asd)
	{
		 Netz = asd;
	}
	public String getCoord(String Coord) 
	{										//idee ist das man beim 
		return Coord;						//anklicken eines Feldes
	}										//die Coords zurück bekommt
	public int getFieldSize() {
		return FG;
	}
	
	public int getAnzShips() {
		return AS;
	}
}
