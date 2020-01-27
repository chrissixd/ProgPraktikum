package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;  	 	// KI: ToDo bearbeitung der collisionen die auftreten können wenn die ki schiffe setzt

								
								// Protokollieren von allem was passiert fürs speichern
								// evtl wenn alles fertig ist KI Lvl 0 und 3 wie gedacht
								//
public class KI extends Spieler {

	private int lvl;
	private boolean Online;
//	private String[] coordfield;
	//private int ;
	private int AnzHit;
	private boolean gerade;
	private boolean hit = false;
	private String CoordHit;
	private int Hit1;
	private List<String> AttacksI;
	private int verhor;
	private String firstCoord;
	private int turn;
	private boolean ende;
	
	
	public KI(int AS,int LVL, boolean On)  //On entspricht Online oder nicht online
	{
		super(AS,On);
		this.lvl=LVL;
		this.Online=On;
		Random B = new Random();
		this.gerade = B.nextBoolean();
		Hit1=0;
		this.AttacksI = new ArrayList<String>();
		verhor=0;
		AnzHit=0;
		turn=0;
		ende=false;
	}
	
	public int KIAttack() 
	{
		String Coord="   ";
		switch(lvl) 
		{
			case 1:
				int x=0,y=0;
				Random Rnd = new Random();
				do
				{
					if(hit)
					{
						Coord=getroffen(CoordHit,verhor);
					}
					else
					{
						x=Rnd.nextInt(FG);
						y=Rnd.nextInt(FG);
						Coord = x + " " + y;
					}
				}
				while(AttacksI.contains(Coord));
			break;
			case 2:
				int x2=0,y2=0;
				Random Rnd2 = new Random();
				do
				{
					if(!hit)
					{
						x2=Rnd2.nextInt(FG);
						y2=Rnd2.nextInt(FG);
						if((gerade)&&((x2+y2)%2==0)) {
							Coord = x2 + " " + y2;
						}
						else if((!gerade)&&((x2+y2)%2==1)) {
							Coord = x2 + " " + y2;
						}
						else if(gerade)
						{
							Coord = x2 + " " + y2;
						}
						else
						{
							Coord = x2 + " " + y2;
						}	
					}
					else
					{
						Coord=getroffen(CoordHit,verhor);
					}
				}
				while(AttacksI.contains(Coord));
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
		AttacksI.add(Coord);
		System.out.println(z + " : " + Coord);
		
		switch(z) 
		{
			case 0:
				if(verhor!=0)
				{
					CoordHit=firstCoord;
					ende=true;
				}
				break;
			case 1:
				if((!hit)&&(AnzHit==0)) {
					hit=true;
					Hit1 = 0;
					firstCoord=Coord;
					CoordHit=Coord;
					AnzHit++;
				}
				else if(AnzHit==1)
				{
					verhor=VerHor(firstCoord,Coord); //1 --> vertikal, 2 --> horizontal
					AnzHit++;
					CoordHit=Coord;
				}
				else
					CoordHit=Coord;
				break;
			case 2:
				hit=false;
				verhor=0;
				AnzHit=0;
				turn=0;
				firstCoord=null;
				ende=false;
				break;
		}
		return z;
	}
	
	private int VerHor(String coordHit, String coord) { //1 --> vertikal, 2 --> horizontal
		String[] C1 = coord.split("\\s+");
		String[] C2 = coordHit.split("\\s+");
		int x1,x2;
		x1 = Integer.parseInt(C1[0]);
		x2 = Integer.parseInt(C2[0]);
		
		if(x1==x2) {
			return 1;
		}
		else {
			return 2;
		}
		
	}

	private String getroffen(String Coord,int ver) //ver  1--> vertikal  2  --> horizontal  0 --> ka
	{
		int x, y,fx,fy;
		String[] C = Coord.split("\\s+");
		String[] fC = firstCoord.split("\\s+");
		//System.out.println("Test: " + Hit1 +" ," +ver);
		x = Integer.parseInt(C[0]);
		y = Integer.parseInt(C[1]);
		
		switch(ver)
		{
		case 0:
			if(Hit1 < 2)
			{
				x = x + 1 + 2*((Hit1)%2 * -1);
			
				if((x<0)||(x>=FG))
				{					
					Hit1++;
					Coord=getroffen(CoordHit,verhor);
					Hit1--;
				}
				else
					Coord= x + " " + y;
			}
			else if(Hit1 <= 3)
			{
				y = y + 1 + 2*(Hit1%2 * -1);
				if((y<0)||(y>=FG))
				{					
					Hit1++;
					Coord= getroffen(CoordHit,verhor);
					Hit1--;
				}
				else
					Coord= x + " " + y;
			}
			
			Hit1++;
			if(Hit1>=4)
			{
				Hit1=0;
			}
			return Coord;
		case 1:
			fy = Integer.parseInt(fC[1]);
			if((y<=fy)&&(turn==2)&&ende) {
				y=y-1;
			}
			else if((y>=fy)&&(turn==1)&&ende) {
				y=y+1;
			}
			else if(y>fy&&!(ende)){
				y=y+1;
				turn=2;
			}
			else if(y<fy&&!(ende))
			{
				y=y-1;
				turn=1;
			}
			if(y>=FG||y<0)
			{
				ende=true;
				CoordHit=firstCoord;
				Coord=getroffen(CoordHit,verhor);
			}
			else
				Coord= x + " " + y;
			
			if(AttacksI.contains(Coord))
			{
				ende=true;
				CoordHit=firstCoord;
				Coord=getroffen(CoordHit,verhor);
			}
			return Coord;
		case 2:
			fx = Integer.parseInt(fC[0]);
			if((x<=fx)&&(turn==2)&&ende) {
				x=x-1;
			}
			else if((x>=fx)&&(turn==1)&&ende) {
				x=x+1;
			}
			else if(x>fx&&!(ende)){
				x=x+1;
				turn=2;
			}
			else if(x<fx&&!(ende))
			{
				x=x-1;
				turn=1;
			}
			
			if(x>=FG||x<0)
			{			
				ende=true;
				CoordHit=firstCoord;
				Coord=getroffen(CoordHit,verhor);
			}
			else
				Coord= x + " " + y;
			if(AttacksI.contains(Coord))
			{
				ende=true;
				CoordHit=firstCoord;
				Coord=getroffen(CoordHit,verhor);
			}
			return Coord;
		default :
			return Coord;
		}
	}
	
	public void KIsetShip() {
		this.setRndShip();
	}
	
	public String getLastHit()
	{
		int a = AttacksI.size();
		if(a == 0)
			return "";
		return AttacksI.get(a-1);
	}
}
