package logic;
import java.util.Random;
public class KI extends Spieler {

	private int lvl;
	private boolean Online;
	private String[] coordfield;
	private int Feld;
	private boolean gerade;
	private int Hit1;
	
	private boolean hit = false;
	private String CoordHit;
	

	public KI(int AS,int FG,int LVL, boolean On)  //On entspricht Online oder nicht online
	{
		super(AS,FG);
		this.lvl=LVL;
		this.Online=On;
		this.Feld=(FG*FG);
		Random B = new Random();
		this.gerade = B.nextBoolean();
		coordfield=new String[Feld];
		Hit1=0;
		int i=0;
		
		for(int y=0;y<Feld;y++) 
		{
			for(int x=0;x<Feld;x++)
			{
				coordfield[i] = x + " " + y;
				i++;
			}
		}
	}
	
	public void KIAttack() 
	{
		String Coord="";
		
		switch(lvl) 
		{
			case 1:
				int x=0,y=0;
				Random Rnd = new Random();
				x=Rnd.nextInt(Feld)+1;
				y=Rnd.nextInt(Feld)+1;
				Coord = x + " " + y;
			break;
			
			case 2:
				int x2=0,y2=0;
				
				Random Rnd2 = new Random();
				
				x2=Rnd2.nextInt(Feld)+1;
				y2=Rnd2.nextInt(Feld)+1;
				
				if((gerade)&&((x2+y2)%2==0)) 
				{
					Coord = x2 + " " + y2;
				}
				else if((!gerade)&&((x2+y2)%2==1))
				{
					Coord = x2 + " " + y2;
				}
			break;
		}
		int z=-1;
		
		if(Online) 
		{
			 z = this.attackOnline(Coord);
		}
		else 
		{
			 z = this.getAttack(Coord);
		}
		
		switch(z) 
		{
			case 1:
				hit=true;
				CoordHit=Coord;
				break;
			case 2:
				hit=false;
				break;
		}
	}
	
	public String getroffen(String Coord) 
	{
		char[] C;
		int z, a;
		C = new char[Coord.length()];
		C = Coord.toCharArray();
		z = C[0];
		a = C[2];
		
		if(Hit1 < 2)
			z = z + 1 + 2*(Hit1%2 * -1); //Rechnet immer +1, aber falls die Zahl ungerade ist wird 2 abgezogen
		else if(Hit1 <= 3)
			a = a + 1 + 2*(Hit1%2 * -1);//Siehe kommentar oben
		
		Coord= z + " " + a;
		Hit1++;
		
		if(Hit1==3) 
			Hit1=0;
		
		return Coord;
	}
	
	public void KIsetShip(String[] Coord) 
	{
		this.setSchiffe(Coord);
	}
}
