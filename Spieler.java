package logic;

import network.NetworkBase;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;


public class Spieler 
{
	protected NetworkBase Netz;
	protected Field field;
	protected int[] z;   //für KI
	protected volatile int FG = 0;
	protected int AS=0;			//Anzahl Schiffe
	protected boolean Online = false;
	private boolean confirmed = false;
	private List<String> AttacksI;
	private String F;
	
	
	protected int[] getShipForSize() throws IOException //Im Projektordner soll die Datei liegen
	{
		BufferedReader bufferreader = new BufferedReader(new FileReader("schiffstabelle.CSV"));
		for(int i = 0; i < FG-4;i++) //Lese alle zeilen bis zu der benötigten
			bufferreader.readLine();
		int ArrayField[] = new int[4];
		String SchiffListe = bufferreader.readLine();
		bufferreader.close();
		
		String[] InfoArray = SchiffListe.split(";");
		if(InfoArray.length == 5)
		{
			for(int i = 0; i < 4;i++) //Addiere alle Zahlen auf
			{
				ArrayField[3-i] = Integer.parseInt(InfoArray[i]);
				AS = AS + Integer.parseInt(InfoArray[i]);
				//System.out.println(Integer.parseInt(InfoArray[i]));
			}
		}

		return ArrayField; //Uebergibt dem Feld die Schiffgroessen
		
	}
	
	public Spieler(int fg, boolean online) 
	{	
		
		this.FG=fg;
		this.Online = online;
		this.AttacksI = new ArrayList<String>();
		if(!Online)
		{
			z=setFieldSize(FG);
		}
		
	}
	public Spieler(boolean online)
	{
		this.Online = online;
		this.AttacksI = new ArrayList<String>();
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
				if(Online)
				{
					speicherFile.println(!Netz.getOkayToSend());
				}
				else
					speicherFile.println(false);
				
				List<Ships> asd = field.getSchiffe();
				
				for(int i = 0; i < asd.size(); i++) //Speichere jedes Schiff
				{
					speicherFile.println(asd.get(i).getCoords());
				}
				
				for(int i = 0; i < AttacksI.size();i++) //Speichert jede Coordinate, wo bereits geschossen wurde
				{
					speicherFile.println(AttacksI.get(i));
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
	
	public boolean loadGame(long ID)
	{
		if(ID == 0)
		{
			return false;
		}
		else
		{
			try
			{
				String line = "";
				FileReader SaveFile = new FileReader(ID + ".save"); //Oeffne die Datei
				BufferedReader br = new BufferedReader(SaveFile);
				if((line = br.readLine()) != null)
				{
					this.setFieldSize(Integer.parseInt(line));
					if((line = br.readLine()) != null)
					{
						if(line.equals("false"))
							this.Online = false;
						else
							this.Online = true;
					}
				}
				for(int i = 0; i<AS;i++)//Ließt die Schiffe aus der Datei aus
				{
					if((line = br.readLine()) != null)
					{
						field.addShips(line.split(",")); //Teilt die Coordinaten aus der Zeile zu nem String Array und fügt das Schiff dann ein
					}
					else
					{
						br.close();
						throw new Exception("Error, File does not have enough Entitys");
					}
				}

				while((line = br.readLine()) != null)
				{
					AttacksI.add(line);
				}
				br.close();
				return true; //hats geklappt
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return false;
			}
		}
	}
	
	public int attackOnline(String coord) //Schickt dem Gegner die Shot Message mit den benötigten Coordinaten
	{	
		AttacksI.add(coord);
		return Netz.strike(coord);						
	}
	
	public int getAttack(String coord) {	//weiter an Logik
		AttacksI.add(coord);
		return field.attack(coord);		//egal ob durch Online
	}									//oder offline
	
	public String setSchiffe(String[] coord) 
	{	//Spieler setzt Schiffe, bekommt Coordinaten vom GUI
		
		if(!Online)
		{
			return field.addShips(coord);		
		}
		else if(FG > 0 && coord.length > 1)
		{
			try
			{
				if(field.addShips(coord).equals("confirmed")) //Falls Online, setze Schiffe, falls alle gesetzt sende "confirmed"
				{
					Netz.finishShip(); //Falls alle Schiffe gesetzt worden sind, schicke Confirmed Nachricht
					confirmed = true;
					return "confirmed";
				}
			}
			catch(NullPointerException ex)
			{
				//if(coord.length > 1)
					//setSchiffe(coord);
			}
		}
		return "kein feld oder zu kleines ship";
	}
	
	public int[] setFieldSize(int size) //Fürs Netzwerk, damit der Client/Host die Feldgröße bekommt
	{
		FG = size;
		int[] a = {0,0,0,0};
		try {
			a = getShipForSize();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Für Size: "+ FG + " " + AS + " Schiffe");

		field = new Field(AS,FG); 
		field.setShipTypes(a);
		return a;
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
	
	public boolean getConf() //gibt zurück ob Confirmed aufgerufen wurde
	{						//Alle Schiffe platziert wurden
		return confirmed;
	}
	public List<String> getattacks(){
		return AttacksI;
	}
	
	public void setRndShip() {
		field.Clear();
		int x,y;
		boolean b;
		String[] s2= new String[2];
		String[] s3= new String[3];
		String[] s4= new String[4];
		String[] s5= new String[5];
		Random Rnd = new Random();
		for(int j=0;j<4;j++) {
			for(int u=0;u<z[j];u++) 
			{
				try {
					TimeUnit.MILLISECONDS.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				b=Rnd.nextBoolean(); //false --> vertikal, true --> horizontal
				if(b) {

					x=Rnd.nextInt((FG-1)-j);
					y=Rnd.nextInt(FG);
				}
				else {

					x=Rnd.nextInt(FG);
					y=Rnd.nextInt((FG-1)-j);

				}
				
				for(int i=0;i<j+2;i++) //
				{
					switch(j) {
						case 0:
							if(b)
								s2[i]=(x+i)+" "+y;
							else
								s2[i]=x+" "+(y+i);
							break;
						case 1:
							if(b)
								s3[i]=(x+i)+" "+y;
							else
								s3[i]=x+" "+(y+i);
							break;
						case 2:
							if(b)
								s4[i]=(x+i)+" "+y;
							else
								s4[i]=x+" "+(y+i);
							break;
						case 3:
							if(b)
								s5[i]=(x+i)+" "+y;
							else
								s5[i]=x+" "+(y+i);
							break;
						}
					}
				switch(j) {
				case 0:
					F=this.setSchiffe(s2);
					break;
				case 1:
					F=this.setSchiffe(s3);
					break;
				case 2:
					F=this.setSchiffe(s4);
					break;
				case 3:
					F=this.setSchiffe(s5);
					break;
				}
				
				if(!(F.equals("confirmed")||F.equals("hinzugefügt")))
				{
					u--;
				}
			}
		}
	}
	
	public void Loeschen(String s) {
		field.loeschSchiff(s);
	}
	public List<Ships> getShips() {
		return field.getSchiffe();
	}
}
