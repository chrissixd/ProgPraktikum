package logic;
public class Ships {
	
	private int size;
	private int hits;
	private String[] shipcoord;	//Coords wo das schiff liegt
	
	public Ships(int Size) 
	{
		size=Size;
		shipcoord=new String[size];	//coords d�rfen keine andere gr��e haben
		hits=0;
	}
	
	public void setShip(String[] coord) //Speichert alle Coordinaten von den Schiffen in das Array
	{ 
		if(coord.length == size) 
		{
			shipcoord = coord;
		}
		else 
		{
			System.out.print("Fehler, gr��e falsch");
		}
	}
	
	public boolean checkHit(String C) //pr�ft jede Coord ob sie gleich
	{	
		for(int i=0;i<size;i++) 			//ist der angegriffenen stelle
		{			
			if((C.equals(shipcoord[i]))&&(C!="")) 
			{
				hits++;						//wie oft ein schiff getroffen wurde
				shipcoord[i]="";		//sonst k�nnte man immer des gleiche  
				return true;			//feld treffen und ein schiff zerst�ren
			}
		}
		return false;
	}
	
	public String getCoords() //Gibt die Coords in Form 0 0,0 1 aus
	{
		String Coords = "";
		for(int i = 0; i < shipcoord.length;i++)
		{
			Coords = Coords + shipcoord[i];
			if(!((i+1) == shipcoord.length))
				Coords = Coords + ",";
		}
		 

		
		return Coords;
	}
	
	public boolean checkDead() 
	{
		return(hits==size);
	}
	
	public int getSize() {
		return shipcoord.length;
	}
}
