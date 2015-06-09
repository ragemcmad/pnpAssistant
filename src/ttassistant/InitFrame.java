package ttassistant;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;

import monsterHandling.*;

import JTrigger.ErrorFrame;
import JTrigger.MyFrame;

@SuppressWarnings("serial")
public class InitFrame extends MyFrame {
	public InitFrame(GameFrame gf){
		super();
		this.setSize(560,110);
		this.setTitle("Gegnerliste");
		
		this.gobutton.setBounds(this.getWidth()/25,(int) (this.getHeight()-2.5*buttonheight), this.getWidth()*3/25,buttonheight);
		this.mlist = (new MonsterList()).mlist;
		this.gobutton.addActionListener(new goInitListener(gf, this));
		
		counterlist = new int[this.mlist.size()];
		for (Monster m : this.mlist){
			this.addEnemy(m);
		}
		
		this.add(this.gobutton);
	}
	
	protected Vector<Monster> getEnemyList(){
		Vector<Monster> retVec = new Vector<Monster>();
			int i=0;
			for (int x : counterlist){
				for (int j=0;j<x;j++){
					Monster m = (Monster)(this.mlist.get(i).clone());
					m.name = m.name+(j+1);
					retVec.add(m);
				}					
				i++;
			}
		return retVec;
	}
	protected void addEnemy(Monster m){
		
		JButton addButton = new JButton("+");
		JLabel nameLabel = new JLabel(""+m.challenge+"|"+m.name+"(0)");
		JButton substButton = new JButton("-");	
		this.counterlist[this.addButtons.size()] = 0;
		addButton.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		substButton.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		if (left){	// y platz machen, hinschreiben(links)
			this.setSize(this.getWidth(), this.getHeight()+buttonheight);	
			this.gobutton.setBounds(this.gobutton.getX(), this.gobutton.getY()+buttonheight, this.gobutton.getWidth(),this.gobutton.getHeight());
			
			if (this.addButtons.size() == 0){
				addButton.setBounds(this.getWidth()/25, buttonheight/2, buttonheight+20, buttonheight);
			}else {
				addButton.setBounds(this.getWidth()/25, buttonheight+this.addButtons.get(this.addButtons.size()-2).getY() ,buttonheight+20, buttonheight);
			}
		}
		else {
			addButton.setBounds(this.getWidth()/25+this.substButtons.get(this.substButtons.size()-1).getX()+this.substButtons.get(this.substButtons.size()-1).getWidth(), this.addButtons.get(this.addButtons.size()-1).getY() ,buttonheight+20, buttonheight);
		}		
		 
		nameLabel.setBounds(addButton.getX()+addButton.getWidth(),addButton.getY(),this.namebuttonwidth,buttonheight);
		substButton.setBounds(nameLabel.getX()+nameLabel.getWidth(), addButton.getY(), addButton.getWidth(),buttonheight);
		
		addButton.addActionListener(new incMonsterListener(this, this.addButtons.size(), nameLabel, m, true));
		substButton.addActionListener(new incMonsterListener(this, this.addButtons.size(), nameLabel, m, false));
		
		this.addButtons.add(addButton);
		this.substButtons.add(substButton);
		this.mNameList.add(nameLabel);
		
		this.add(addButton);
		this.add(substButton);
		this.add(nameLabel);
		
		if (left)
			left = false;
		else
			left = true;
		
	}
	
	private Vector<JButton> addButtons =new Vector<JButton>();
	private Vector<JButton> substButtons = new Vector<JButton>();
	private Vector<JLabel> mNameList = new Vector<JLabel>();
	private JButton gobutton = new JButton("go");
	
	Vector<Monster> mlist;
	protected int[] counterlist;
	
	int buttonheight = 30;
	int namebuttonwidth = 130;
	boolean left = true;
	
}

class incMonsterListener implements ActionListener{

	InitFrame f;
	int pos;
	JLabel b;
	Monster m;
	boolean up;
	
	public incMonsterListener(InitFrame f, int pos,
			JLabel b, Monster m, boolean up) {
		super();
		this.f = f;
		this.pos = pos;
		this.b = b;
		this.m= m;
		this.up = up;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (this.up)		
			f.counterlist[pos]++;
		else
			if (f.counterlist[pos] != 0) 
				f.counterlist[pos]--;
		b.setText(""+m.challenge+"|"+m.name+"("+f.counterlist[pos]+")");
	}
	
}

class goInitListener implements ActionListener{

	private GameFrame gf;
	
	InitFrame f;
	
	public goInitListener(GameFrame gf, InitFrame frame){
		super();
		this.gf = gf;
		this.f = frame;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		//int len = gf.thisparty.size();
		Vector<Monster> enemyList = this.f.getEnemyList();
		ArrayList<String> sArr = new ArrayList<String>();
		
		//double[] dArr = new double[len+enemyList.size()];
		double[] dArr = new double[enemyList.size()];
		int[] hpArr = new int[enemyList.size()];
		
		
		//roll für chars
		/*
		for (int i =0; i<gf.thisparty.size(); i++){
			dArr[i] = getInitMod(gf.thisparty.getChara(i).getStat("dex").getWertTxt());		
			dArr[i] += ((rnd.nextInt((20 - 1) + 1) + 1));
			sArr.add(gf.thisparty.getChara(i).getStat("cname").getWertTxt());
			System.out.println(sArr.get(sArr.size()-1)+" rollt "+dArr[i]);
		}
		*/
		
		//roll für enemy
		int i=0;
		for (Monster m : enemyList){
			//dArr[gf.thisparty.size()+i] = m.initroll();
			dArr[i] = m.initroll();
			hpArr[i] = m.maxhp;
			sArr.add(m.name);
			i++;
		}		
		
		this.gf.addSubFrame(new InitListFrame(dArr, hpArr, sArr, enemyList));
		f.dispose();
	}
	/*
	private double getInitMod(String s){
		
		try {
			double numwert = Double.parseDouble(s);
			return numwert;
		}  
		catch(NumberFormatException nfe)  
		{  
			for (int i=0;i<20;i++){
				if (s.equals(new String("-"+i)))
					return i;
				else if (s.equals(new String("+"+i))){
					return (-1.)*i;
				}
			}
		}  
		new ErrorFrame(s+" keine Zahl");
		return 0;
	}	*/
}

@SuppressWarnings("serial")
class InitListFrame extends MyFrame{
	
	public InitListFrame (double[] initRollList, int[] hpArr, ArrayList<String> nameList, Vector<Monster> enemyList){

		this.setSize(700, 110+60*nameList.size());
		this.setTitle("Initiative Rolls");
		
		JTextField ac = new JTextField("12");
		ac.setBounds(10,10,40,40);
		ac.setHorizontalAlignment(SwingConstants.CENTER);
		
		JTextField out = new JTextField("-");
		out.setBounds(ac.getX()+ac.getWidth()+10, ac.getY(), this.getWidth()-ac.getWidth()-40, ac.getHeight());
		out.setHorizontalAlignment(SwingConstants.CENTER);
		
		boolean swapped = true;
		int i=0;

		//quickn dirty bubble
		while (swapped == true){
			swapped = false;
			for (int j=0; j<nameList.size()-1-i;j++){
				if (initRollList[j]<initRollList[j+1]){
					swapPos(initRollList, nameList, enemyList, j, j+1);
					swapped = true;
				}
			}
			i++;
		}
		
		for (i = 0; i<nameList.size();i++){
			JTextField jl = new JTextField("("+initRollList[i]+") "+nameList.get(i));
			jl.setHorizontalAlignment(SwingConstants.CENTER);	
			jl.setBounds(10, i*60+ac.getY()+ac.getHeight()+20, 100, ac.getHeight());
			JButton plus = new JButton("+");
			plus.setBounds(jl.getX()+jl.getWidth()+20,jl.getY(),45,jl.getHeight());
			JLabel hp = new JLabel(""+hpArr[i]);
			hp.setHorizontalAlignment(SwingConstants.CENTER);
			hp.setBounds(plus.getX()+plus.getWidth(), jl.getY(), 45, jl.getHeight());
			JButton minus = new JButton("-");
			minus.setBounds(hp.getX()+hp.getWidth(),jl.getY(),45,jl.getHeight());
			
			plus.addActionListener(new hpListener(jl,hp, 1));
			minus.addActionListener(new hpListener(jl,hp,-1));
			
			int xstart = minus.getX()+minus.getWidth()+10;
			
			for (int j=0;j<enemyList.get(i).attackList.size();j++){
				JButton attButton = new JButton(enemyList.get(i).getAttName(j));
				attButton.setBounds(xstart, minus.getY(), 100, jl.getHeight());
				attButton.addActionListener(new attackListener(ac, out, enemyList.get(i), j));
				this.add(attButton);
				xstart = attButton.getX()+attButton.getWidth()+10;
			}
			this.add(ac);
			this.add(out);
			this.add(jl);
			this.add(plus);
			this.add(hp);
			this.add(minus);
		}
	}
	private void swapPos(double[] initRollList, ArrayList<String> nameList, Vector<Monster> enemyList, int a, int b){
		double d = initRollList[a];
		initRollList[a] = initRollList[b];
		initRollList[b] = d;
		
		String s = nameList.get(a);
		nameList.set(a, nameList.get(b));
		nameList.set(b, s);
		
		Monster m = enemyList.get(a);
		enemyList.set(a, enemyList.get(b));
		enemyList.set(b, m);
		
	}

}

class hpListener implements ActionListener{

	JTextField name;
	JLabel hp;
	int delta;
	public hpListener(JTextField jl, JLabel hp, int delta){
		this.name = jl;
		this.hp = hp;
		this.delta = delta;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		this.hp.setText(""+(Integer.parseInt(this.hp.getText())+this.delta));
		if (Integer.parseInt(this.hp.getText()) <=0)
			this.name.setEnabled(false);
		else
			this.name.setEnabled(true);
	}	
}
class attackListener implements ActionListener{
	public attackListener(JTextField acField, JTextField out, Monster m, int attackPos) {
		super();
		this.acField = acField;
		this.out = out;
		this.m = m;
		this.attackPos = attackPos;
	}
	JTextField acField;
	JTextField out;
	Monster m;
	int attackPos;
	@Override
	public void actionPerformed(ActionEvent arg0) {
		int ac=12;
		try {
			ac = Integer.parseInt(this.acField.getText());
		} catch (Exception e){
			new ErrorFrame("invalid AC");
		}
		Vector<String> al = m.getAttackrolls(ac);
		this.out.setText(al.get(this.attackPos));
	}
}
