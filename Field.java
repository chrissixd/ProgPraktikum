package logic;
public class Field {
	
	private Ships[] ships;
	private int countShips;
	private int anzShips;
	
	public Field(int ShipAnz) 
	{
		countShips=0;
		anzShips=ShipAnz;
		ships = new Ships[anzShips];	//array der schiffe zum durchsuchen
	}
	
	public String addShips(String[] Coord) //Coord gleiche länge wie schiffe
	{	
		String s;
		s = checkCollision(Coord);
		
		if(countShips < anzShips) 
		{
			Ships Pship;
			Pship = new Ships(Coord.length);	//Schiffe erstellen in
			Pship.setShip(Coord);				//ein array reinschreiben
			ships[countShips] = Pship;			//und zählen
			countShips++;
			if(countShips == anzShips)
				s = "confirmed"; //Fürs Netzwerk, falls alle Schiffe gesetzt worden sind, muss Confirmed gesendet werden
		}
		else
		{
			s="";
		}
		return s;
	}
	
	public int attack(String Coord) //schaut nach treffer usw.
	{	
		int z=-1;//fehler
		
		for(int i=0;i<ships.length;i++)
		{				
			if(ships[i].checkHit(Coord)) 
			{
				if(ships[i].checkDead()) 
				{
					z=2;		//schiff zerstört
					break;
				}
				z=1;			//schiff getroffen
				break;
			}
			z=0;		//wasser
		}

		return z;
	}
	
	public int getSchiffSize(int Index) {	//0 erstes schiff n letztes 
		return ships[Index].getSize();		//schiff man muss sich die 
	}										//schiffsreihenfolge merken
	
	public String checkCollision(String[] C) {
		return C[0];
	}

	public Ships[] getSchiffe()
	{
		return ships;
	}
}
