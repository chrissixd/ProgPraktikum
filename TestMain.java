package testMains;
import logic.*;

public class TestMain {

	static ServerTestGui b;
	public static void main(String[] args) {

		KI();
	}
	
	public static void KI()
	{
		b = new ServerTestGui();
		KI a = new KI(30, 1, false);
		a.KIsetShip();
		for(int i = 0; i<100;i++)
		{
			int c = a.KIAttack();
			if(!a.getLastHit().equals(""))
				b.getAttack(a.getLastHit(),c);
		}
		a.saveGame(123);
	}
	public static void HKI()
	{
		for(int j = 100; j > 0; j++)
		{
			KI a = new KI(30, 2, false);
			a.KIsetShip();
			for(int i = 0; i<100;i++)
			{
				a.KIAttack();
			}
			a.saveGame(java.lang.System.currentTimeMillis());
		}

	}
	
	public static void loadSpieler()
	{
		Spieler b = new Spieler(false);
		b.loadGame(123);
		System.out.println(b.getAttack("5 7"));
		b.saveGame(124);
	}

}
