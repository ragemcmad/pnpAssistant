package ttassistant;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import JTrigger.MyFrame;

@SuppressWarnings("serial")
public class EditStatFrame extends MyFrame {
	
	private JTextField statnameTX = new JTextField("");
	private JLabel statnameJL = new JLabel("");
	private JTextField statwert = new JTextField("");
	private JButton updatebutton = new JButton("ok");
	
	private GameFrame g;
	private DetailsFrame d;
	private int p;
	private stat thisstat;
	
	public EditStatFrame(GameFrame gf,DetailsFrame superframe, int pos, stat s, boolean newstat){	
		super();
		this.setTitle("Stat: "+s.name);
		this.setSize(300,100);		
		
		g = gf;
		d = superframe;
		p = pos;
		thisstat = s;
		this.statnameTX.setText(this.thisstat.getName());
		this.statnameJL.setText(this.thisstat.getName());
		
		this.statwert.setText(this.thisstat.getWertTxt());
		this.statnameTX.setBounds(15,15,(this.getWidth()-45)*2/5, 30);
		this.statnameJL.setBounds(15,15,(this.getWidth()-45)*2/5, 30);
		this.statwert.setBounds(15+this.statnameTX.getWidth(),15,(this.getWidth()-45)*2/5, 30);
		this.updatebutton.setBounds(this.statwert.getX()+this.statwert.getWidth(),15,(this.getWidth()-45)/5, 30);
		this.updatebutton.addActionListener(new updateListener(g, d, p, statnameTX.getText(), this.statwert));
		
		if (newstat)
			this.add(statnameTX);
		else
			this.add(statnameJL);
			
		this.add(statwert);
		this.add(updatebutton);
		
	}
}
class updateListener implements ActionListener {

	private GameFrame gf;
	private DetailsFrame f;
	private int p;
	private String name;
	private JTextField q;
	
	public updateListener(GameFrame gframe, DetailsFrame frame, int pos, String statname, JTextField quelle){
		gf = gframe;
		f = frame;
		p = pos;
		name = statname;
		q = quelle;
		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		try {
			double numwert = Double.parseDouble(q.getText());
			gf.updateStat(p, new numStat(name, numwert), false);
		}  
		catch(NumberFormatException nfe)  
		{  
			gf.updateStat(p, new textStat(name, q.getText()), false);
		}  
		
		f.updateStat(name, q.getText());
	}
	
	
}

