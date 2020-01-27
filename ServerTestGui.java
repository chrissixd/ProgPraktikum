package testMains;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.SocketException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import logic.Spieler;
import network.Server;
public class ServerTestGui implements ActionListener{

	private JFrame frame;
	private JTextField input;
	private JTextField Show;
	private Server a;
	private Spieler b;
	public JButton[][] asd;
	private boolean con = false;
	JPanel panel;
	private String lastCom = "";
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(!con)
		{
			try {
				a = new Server(50000,b);
				b.setNetwork(a);
				a.start();
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			con =! con;
		}
		else if(lastCom.equals(""))
		{
			Show.setText(a.getLastMessage());
			if(input.getText().equals("set"));
				lastCom = "set";
		}
		else if(lastCom.equals("set"))
		{
			String coords = input.getText();
			String[] coord = coords.split(",");
			b.setSchiffe(coord);
		}

	}
	
	public ServerTestGui()
	{
		frame = new JFrame("Server");
		frame.setSize(1024,1024);
		input = new JTextField(15);
		Show = new JTextField(15);
		panel = new JPanel();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		asd = new JButton[30][30];
		for(int i = 0; i < 30;i++)
		{
			for(int j = 0; j < 30 ; j++)
			{
				asd[i][j] = new JButton(i + " " +j); 
				panel.add(asd[i][j]);
			}
		}
		panel.setLayout(new java.awt.GridLayout( 30, 30));
		Show.setEnabled(false);
		b = new Spieler(0, true);
		frame.add(panel);
		frame.setVisible(true);
	}
	
	public void getAttack(String asd, int baum)
	{
		if(!asd.equals(""))
		{
			String[] apfel = asd.split(" ");
			this.asd[Integer.parseInt(apfel[0])][Integer.parseInt(apfel[1])].setEnabled(false);
			
			if(baum == 1)
				this.asd[Integer.parseInt(apfel[0])][Integer.parseInt(apfel[1])].setBackground(Color.red);
			else if(baum == 2)
				this.asd[Integer.parseInt(apfel[0])][Integer.parseInt(apfel[1])].setBackground(Color.yellow);
			else
				this.asd[Integer.parseInt(apfel[0])][Integer.parseInt(apfel[1])].setBackground(Color.black);
		}

	}
}
