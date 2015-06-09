package ttassistant;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;

import JTrigger.*;

@SuppressWarnings("serial")
public class DetailsFrame extends MyFrame {

	private chara c;
	private GameFrame f;
	private int pos;
	ArrayList<JLabel> namelabellist = new ArrayList<JLabel>();
	ArrayList<JLabel> wertlabellist=new ArrayList<JLabel>();
	ArrayList<JButton> editbuttonlist = new ArrayList<JButton>();
	private int basesize=110;
	private int buttonheight =20;
	private boolean left = true;
	protected boolean toDel = false;
	
	
	private JButton exitButton = new JButton("exit");
	private JButton addButton = new JButton("add Stat");
	private JButton delButton = new JButton("delete char");
	private JTextArea descField = new JTextArea("notes");
	private PropertyChangeListener pcL;
	
	public DetailsFrame(chara ch, int position, GameFrame fr) {
		super();
		c=ch;
		f=fr;
		pos=position;
		this.setTitle((((textStat)c.getStat("cname")).wert));
		this.setSize(800,basesize+(int)(5.3*buttonheight));		
		
		this.addButton.setBounds(10,buttonheight*6, (int)((this.getWidth()/2-45)/2),buttonheight);
		this.delButton.setBounds(20+(int)((this.getWidth()-45)/2),this.addButton.getY(), (int)((this.getWidth()/2-45)/2),buttonheight);
		this.delButton.addActionListener(
				new confirmDelListener(f, pos, this));
		this.exitButton.setBounds(10,(int) (this.addButton.getY()+buttonheight*1.5), this.getWidth()-33,buttonheight);
		this.exitButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						f.updateStat(pos, new textStat(new String("desc"),new String(descField.getText())),false);
						dispose();
					}
				});
		
		this.pcL = new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				if (!toDel)
					f.updateStat(pos, new textStat(new String("desc"),new String(descField.getText())),false);
			}
		};
		
		this.descField.addPropertyChangeListener(pcL);
		
		this.descField.setBounds(10,buttonheight, this.getWidth()-35,buttonheight*4);
		
		for (stat s :c.stats){
			addStat(s);
		}
		this.add(this.exitButton);
		this.addButton.setEnabled(false);
		this.add(this.addButton);
		this.add(this.delButton);
		this.add(this.descField);
	}
	private void addStat(stat s){
		if (s.name.equals(new String("desc"))){
			this.descField.setText(s.getWertTxt());
			return;
		}
		int dx = 10+((int)this.getWidth())/2;;
		int dy = buttonheight;
		if (left) {
			dy=0;
			dx = 10;
			this.setSize(800, this.getHeight()+buttonheight*2);
			this.addButton.setBounds(this.addButton.getX(), this.addButton.getY()+buttonheight*2,this.addButton.getWidth(), this.addButton.getHeight());
			this.delButton.setBounds(this.delButton.getX(), this.delButton.getY()+buttonheight*2, this.delButton.getWidth(), this.delButton.getHeight());
			this.exitButton.setBounds(this.exitButton.getX(), this.exitButton.getY()+buttonheight*2,this.exitButton.getWidth(), this.exitButton.getHeight());
			this.descField.setBounds(this.descField.getX(), this.descField.getY()+buttonheight*2,this.descField.getWidth(), this.descField.getHeight());
		}
		
		Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
		
		JLabel newlabel = new JLabel(s.name);		
		newlabel.setBounds(dx,namelabellist.size()*buttonheight+buttonheight-dy,(int)((this.getWidth()/2-45)*2/5), buttonheight);
		newlabel.setHorizontalAlignment(SwingConstants.CENTER);		
		newlabel.setBorder(border);
		namelabellist.add(newlabel);
		this.add(newlabel);
		
		JLabel wertlabel = new JLabel(s.getWertTxt());		
		wertlabel.setBounds(newlabel.getX()+newlabel.getWidth()+5,wertlabellist.size()*buttonheight+buttonheight-dy,(int)((this.getWidth()/2-45)*2/5), buttonheight);
		wertlabel.setHorizontalAlignment(SwingConstants.CENTER);		
		wertlabel.setBorder(border);
		wertlabellist.add(wertlabel);
		this.add(wertlabel);
		
		JButton editbutton = new JButton(s.name);
		editbutton.setBounds(wertlabel.getX()+wertlabel.getWidth()+2, editbuttonlist.size()*buttonheight+buttonheight-dy,(int)((this.getWidth()/2-45)*1/5), buttonheight);
		editbutton.addActionListener(new updFrListener(f, this, pos, s));
		
		this.editbuttonlist.add(editbutton);
		this.add(editbutton);
		
		if (left){
			left = false;
		} else {
			left = true;
		}
	}
	public void updateStat(String name, String text) {
		for (int i=0; i<namelabellist.size();i++){
			if (namelabellist.get(i).getText().equals(name)){
				wertlabellist.get(i).setText(text);
				this.closeSubFrames();
				return;
			}
		}		
	}
	public void dispose(){
		descField.removePropertyChangeListener(pcL);
		super.dispose();
	}
}

class confirmDelListener implements ActionListener {
	GameFrame g;
	int i;
	MyFrame f;
	public confirmDelListener(GameFrame gf,int pos, MyFrame fr){
		f=fr;
		i=pos;
		g=gf;
	}
	public void actionPerformed(ActionEvent e){
		ConfirmJFrame cf = new ConfirmJFrame();
		f.addSubFrame(cf);
		cf.giveAL(new deleteListener(g, i, cf, f));
	}
}

class deleteListener implements ActionListener {
	GameFrame g;
	int i;
	MyFrame f;
	MyFrame f2;
	public deleteListener(GameFrame gf,int pos, MyFrame fr, MyFrame fr2){
		f=fr;
		f2=fr2;
		i=pos;
		g=gf;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		f2.dispose();
		g.delChar(i);
		f.dispose();
		
	}	
}

class updFrListener implements ActionListener {

	private GameFrame g;
	private DetailsFrame d;
	private int p;
	private stat s;
	public updFrListener(GameFrame gf, DetailsFrame df, int pos, stat st){
		g=gf;
		d=df;
		p=pos;
		s=st;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		d.addSubFrame(new EditStatFrame(g,d, p, s,false));
		
	}
	
	
}

class newStatListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}

