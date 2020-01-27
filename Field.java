package logic;

public class Field {
	
	private Ships[] ships;
	//private int[] ShipCount;
	private int countShips;
	private int anzShips;
	private int fieldSize;
	private String[] Collision;
	private int countColl;
	
	public boolean setShipTypes(int[] Ships)
	{
		int z=0;
		//Ships[0] -> Amounts of Ships size 2
		//Ships[1] -> the amount of Ships of size 3
		//...
		if(Ships.length == 4) {
			//ShipCount = Ships;
			for(int i=0;i<Ships.length;i++) {
				z+=(Ships[i]*(i+4)*3);
			}
			Collision =new String[z];
		}
		else
			return false;
		return true; //Gibt true zurück falls es erfolgreich war
	}

	public Field(int ShipAnz,int FieldSize) 
	{
		countColl=0;
		countShips=0;
		anzShips=ShipAnz;
		fieldSize=FieldSize;
		ships = new Ships[anzShips];	//array der schiffe zum durchsuchen
	}
	
	public String addShips(String[] Coord) //Coord gleiche länge wie schiffe
	{	
		String[] t;
		String[] r;
		int x, y,x1,y1;
		r=Coord[Coord.length-1].split("\\s+");
		t=Coord[0].split("\\s+");
		x=Integer.parseInt(r[0]);
		y=Integer.parseInt(r[1]);
		x1=Integer.parseInt(t[0]);
		y1=Integer.parseInt(t[1]);
		if((x < 0)||(x > fieldSize)||(y < 0)||(y > fieldSize)||(x1 < 0)||(x1 > fieldSize)||(y1 < 0)||(y1 > fieldSize)) {
			if(!((x == -20 && y == -20) || (x1 == -20 && y1 == -20)))
			{

				return "nicht im Feld";
			}
		}
		
		if(countShips < anzShips) {

				if(checkCollision(Coord).equals("no Collision"))
				{
					Ships Pship;
					Pship = new Ships(Coord.length);	//Schiffe erstellen in
					Pship.setShip(Coord);				//ein array reinschreiben
					ships[countShips] = Pship;			//und zählen
					countShips++;
					setCollision(Coord);
				}
				else
				{
					return "Collision!!!";
				}
				if(countShips == anzShips) {
					return "confirmed"; //Fürs Netzwerk, falls alle Schiffe gesetzt worden sind, muss Confirmed gesendet werden
				}
			}
			else
			{
				return "voll";
			}
			return "hinzugefügt";
		
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
		for(int i=0;i<C.length;i++)
		{
			if(checkCollision(C[i]))
			{
				return "Collision!";
			}
		}
		return "no Collision";
	}
	
	public Boolean checkCollision(String C) {
		if(C.equals("-20 -20"))
			return false;
		for(int i=0;i<Collision.length;i++) {
			if(Collision[i]==null) {
				//System.out.println("no collision");
				return false;
			}
			else if(Collision[i].equals(C))
			{
				//System.out.println("collision");
				return true;
			}
		}
		return false;
	}
	
	private void setCollision(String[] C) {
		if(C.length==5) {
			setVar(C,3);
		}
		else {
			setVar(C,2);
		}
	}

	public Ships[] getSchiffe()
	{
		return ships;
	}
	
	public int GetCollisionLength(){
		return Collision.length;
	}
	
	private void setVar(String[] C,int k)
	{
		int a = 0;
		int z = 0;
		String[] s;
		for(int j=0;j<k;j++)
		{
			if(j==0) {
				s=C[0].split("\\s+");
				z=Integer.parseInt(s[0]);
				a=Integer.parseInt(s[1]);
			}
			else if(((j==1)&&(k==2))||(j==2)&&(k==3))
			{
				s=C[C.length-1].split("\\s+");
				z=Integer.parseInt(s[0]);
				a=Integer.parseInt(s[1]);
			}
			else if((j==1)&&(k==3))
			{
				s=C[2].split("\\s+");
				z=Integer.parseInt(s[0]);
				a=Integer.parseInt(s[1]);
			}
			for(int i=0;i<=8;i++)
			{
				switch(i)
				{
				case 0:
					if(!checkCollision(z+" "+a)) {
					Collision[countColl]=z+" "+a;
					countColl++;
					}
					break;
				case 1:
					if(!checkCollision(z+" "+(a+1))) {
					Collision[countColl]=z+" "+(a+1);
					countColl++;
					}
					break;
				case 2:
					if(!checkCollision((z+1)+" "+a)) {
					Collision[countColl]=(z+1)+" "+a;
					countColl++;
					}
					break;
				case 3:
					if(!checkCollision((z+1)+" "+(a+1))) {
					Collision[countColl]=(z+1)+" "+(a+1);
					countColl++;
					}
					break;
				case 4:
					if(!checkCollision(z+" "+(a-1))) {
					Collision[countColl]=z+" "+(a-1);
					countColl++;
					}
					break;
				case 5:
					if(!checkCollision((z-1)+" "+a)) {
					Collision[countColl]=(z-1)+" "+a;
					countColl++;
					}
					break;
				case 6:
					if(!checkCollision((z-1)+" "+(a-1))) {
					Collision[countColl]=(z-1)+" "+(a-1);
					countColl++;
					}
					break;
				case 7:
					if(!checkCollision((z+1)+" "+(a-1))) {
					Collision[countColl]=(z+1)+" "+(a-1);
					countColl++;
					}
					break;
				case 8:
					if(!checkCollision((z-1)+" "+(a+1))) {
					Collision[countColl]=(z-1)+" "+(a+1);
					countColl++;
					}
					break;
				}
			}
		}
	}
	public void Clear() {
		Collision=new String[Collision.length];
		countColl=0;
		countShips=0;
		ships=new Ships[anzShips];
	}
}
