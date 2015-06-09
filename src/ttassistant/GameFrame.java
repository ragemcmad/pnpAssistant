package ttassistant;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.Border;

import JTrigger.MyFrame;
import JTrigger.MyListener;

@SuppressWarnings("serial")
public class GameFrame extends MyFrame{

	public GameFrame() {
		super();
		String path1="C:\\Users\\Olli\\Dropbox\\DnD\\party\\partyrh.s";
		String path2="C:\\Users\\Olli\\Dropbox\\DnD\\party\\partyac.s";
		this.thisparty = new party(path1);
		this.setTitle("PnP_helper");
		this.setSize(new Dimension(600,300));	
			
		updatePos();		
		
		this.nameButton.addActionListener(new nameListener(this.gButton, this.nameButton));
		this.gButton.addActionListener(new genderListener(this.gButton));		
		this.addButton.addActionListener(new addPlayerListener(this));
		
		this.loadrhbutton.addActionListener(new loadListener(this.loadrhbutton, this.loadacbutton, this, path1));
		this.loadacbutton.addActionListener(new loadListener(this.loadacbutton, this.loadrhbutton, this, path2));
		
		//this.initbutton.addActionListener(new startCombatListener(this));
		this.initbutton.addActionListener(new MyListener(this){
			public void actionPerformed(ActionEvent e){
				mf.addSubFrame(new InitFrame((GameFrame)mf));
			}
		});
		
		this.d4box.setHorizontalAlignment(SwingConstants.CENTER);		
		this.d4button.addActionListener(new diceListener(4, this.d4box));
		this.d4box.setBorder(border);		
		
		this.d6box.setHorizontalAlignment(SwingConstants.CENTER);		
		this.d6button.addActionListener(new diceListener(6, this.d6box));
		this.d6box.setBorder(border);		
		
		this.d8box.setHorizontalAlignment(SwingConstants.CENTER);	
		this.d8button.addActionListener(new diceListener(8, this.d8box));
		this.d8box.setBorder(border);	
		
		this.d10box.setHorizontalAlignment(SwingConstants.CENTER);		
		this.d10button.addActionListener(new diceListener(10, this.d10box));
		this.d10box.setBorder(border);		
		
		this.d20box.setHorizontalAlignment(SwingConstants.CENTER);
		this.d20button.addActionListener(new diceListener(20, this.d20box));
		this.d20box.setBorder(border);		
		
		/*
		this.addButton.setEnabled(false);
		this.loadacbutton.setEnabled(false);
		*/
		this.loadrhbutton.setEnabled(false);
		
		
		this.add(this.addButton);
		this.add(this.gButton);
		this.add(this.nameButton);
		this.add(this.d4button);
		this.add(this.d6button);
		this.add(this.d8button);
		this.add(this.d10button);
		this.add(this.d20button);
		
		this.add(this.d4box);
		this.add(this.d6box);
		this.add(this.d8box);
		this.add(this.d10box);		
		this.add(this.d20box);
		
		this.add(this.loadacbutton);
		this.add(this.loadrhbutton);
		
		this.add(this.initbutton);
		
		for (int i=0; i<this.thisparty.size(); i++)
			this.addChar(thisparty.getChara(i));
				
	}	
	private JButton addButton = new JButton("add Player");
	protected ArrayList<JLabel> levelboxlist = new ArrayList<JLabel>();	
	protected ArrayList<JLabel> nameboxlist = new ArrayList<JLabel>();
	protected ArrayList<JLabel> raceboxlist = new ArrayList<JLabel>();
	protected ArrayList<JLabel> classboxlist = new ArrayList<JLabel>();
	protected ArrayList<JButton> detailsbuttonlist = new ArrayList<JButton>();
	
	
	protected ArrayList<chara> charlist = new ArrayList<chara>();
	
	private JButton gButton = new JButton("male name");
	private JButton nameButton = new JButton("roll name");
	private JLabel d4box = new JLabel("4");	
	private JLabel d6box = new JLabel("6");
	private JLabel d8box = new JLabel("8");
	private JLabel d10box = new JLabel("10");
	private JLabel d20box = new JLabel("20");
	private JButton d4button = new JButton("d4");
	private JButton d6button = new JButton("d6");
	private JButton d8button = new JButton("d8");
	private JButton d10button = new JButton("d10");
	private JButton d20button = new JButton("d20");
	private JButton loadrhbutton = new JButton("Load1");
	private JButton loadacbutton = new JButton("Load2");
	private JButton initbutton = new JButton("init");
	protected party thisparty;
	protected int buttonheigth=30;
	Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
	//private DnDDB db = new DnDDB();
	
	protected void updatePos(){
		int buttonwidth = this.getWidth()/4;
		int yAnkerRandomButtons = this.getHeight()-buttonheigth*10;
		int xstart = this.getWidth()/6;
		int xstart2 = (this.getWidth()/2)-buttonwidth-5;	
				
		this.addButton.setBounds(30, yAnkerRandomButtons, this.getWidth()-70, buttonheigth);				
		this.gButton.setBounds(xstart-5,(int)(this.addButton.getY()+buttonheigth*1.5), xstart*2, buttonheigth);		
		this.nameButton.setBounds(this.gButton.getX()+this.gButton.getWidth(),this.gButton.getY(), this.gButton.getWidth(), buttonheigth);
		this.d4button.setBounds(xstart2, (int) (this.gButton.getY()+buttonheigth*1.5), buttonwidth, buttonheigth);
		this.d6button.setBounds(this.d4button.getX(), this.d4button.getY()+buttonheigth, buttonwidth, buttonheigth);
		
		this.d8button.setBounds(this.d4button.getX(), this.d4button.getY()+buttonheigth*2, buttonwidth, buttonheigth);		
		this.d10button.setBounds(this.d4button.getX(), this.d4button.getY()+buttonheigth*3, buttonwidth, buttonheigth);
		this.d20button.setBounds(this.d4button.getX(), this.d4button.getY()+buttonheigth*4, buttonwidth, buttonheigth);
		
		
		this.d4box.setBounds(this.d10button.getX()+buttonwidth, this.d4button.getY(), buttonwidth, buttonheigth);
		this.d6box.setBounds(this.d4button.getX()+buttonwidth, this.d4button.getY()+buttonheigth, buttonwidth, buttonheigth);
		this.d8box.setBounds(this.d4button.getX()+buttonwidth, this.d4button.getY()+buttonheigth*2, buttonwidth, buttonheigth);
		this.d10box.setBounds(this.d4button.getX()+buttonwidth, this.d4button.getY()+buttonheigth*3, buttonwidth, buttonheigth);
		this.d20box.setBounds(this.d4button.getX()+buttonwidth,this.d4button.getY()+buttonheigth*4, buttonwidth, buttonheigth);
		
		this.loadacbutton.setBounds(this.d10box.getX()-buttonwidth-buttonwidth/4-buttonwidth/2, this.d10button.getY(), buttonwidth/2, buttonheigth*2);
		this.loadrhbutton.setBounds(this.d10box.getX()-buttonwidth-buttonwidth/4-buttonwidth/2,  this.d4button.getY(), buttonwidth/2, buttonheigth*2);
		
		this.initbutton.setBounds(this.d10box.getX()+buttonwidth-buttonwidth/4+buttonwidth/2, this.d10button.getY(), buttonwidth/2, buttonheigth*2);
		
		for (int i=0; i<nameboxlist.size();i++){
			int yAnker = i*this.buttonheigth*2+this.buttonheigth;
			this.levelboxlist.get(i).setBounds(20, yAnker, 40 , this.buttonheigth);
			this.nameboxlist.get(i).setBounds(20+this.levelboxlist.get(i).getWidth(), yAnker, (int) ((this.getWidth()-70)/3.5), this.buttonheigth);
			this.raceboxlist.get(i).setBounds(this.nameboxlist.get(i).getX()+this.nameboxlist.get(i).getWidth(), yAnker,(int) ( ((this.getWidth()-70)/3.5)), this.buttonheigth);
			this.classboxlist.get(i).setBounds(this.raceboxlist.get(i).getX()+this.raceboxlist.get(i).getWidth(), yAnker, (int) ((this.getWidth()-70)/3.5)-10, this.buttonheigth);
			this.detailsbuttonlist.get(i).setBounds(this.getWidth()-((this.getWidth()-70)/8)-25, yAnker, ((this.getWidth()-70)/8), this.buttonheigth);
		}
	}
	private void updateLabels(){
		for (int i=0; i<this.thisparty.size(); i++){
			this.levelboxlist.get(i).setText(this.thisparty.getChara(i).getStat("lvl").getWertTxt());
			this.nameboxlist.get(i).setText(this.thisparty.getChara(i).getStat("cname").getWertTxt());
			this.raceboxlist.get(i).setText(this.thisparty.getChara(i).getStat("race").getWertTxt());
			this.classboxlist.get(i).setText(this.thisparty.getChara(i).getStat("class").getWertTxt());
		}
	}
	public void load(String path){
		//reset
		int s = this.levelboxlist.size();
		for (int i=0; i<s;i++){
			this.remove(this.levelboxlist.remove(0));
			this.remove(this.nameboxlist.remove(0));
			this.remove(this.raceboxlist.remove(0));
			this.remove(this.classboxlist.remove(0));
			this.remove(this.detailsbuttonlist.remove(0));
			System.out.println("remove pos "+ i);
			System.out.println("lblistsize="+this.levelboxlist.size());
			this.setSize(this.getWidth(), this.getHeight()-this.buttonheigth*2);
			
		}
		this.closeSubFrames();
		this.updatePos();
		
		//load
		this.thisparty = new party(path);		
		for (int i=0; i<this.thisparty.size(); i++)
			this.addChar(thisparty.getChara(i));
	}
	public void addChar(chara c){
		this.setSize(new Dimension(this.getWidth(),(int)(300+(this.nameboxlist.size()+1.5)*this.buttonheigth*2)));
		
		int yAnker = this.nameboxlist.size()*this.buttonheigth*2+this.buttonheigth;
		
		JLabel lvlbox = new JLabel(c.getStat("lvl").getWertTxt());
		lvlbox.setEnabled(false);
		lvlbox.setHorizontalAlignment(SwingConstants.CENTER);	
		lvlbox.setBounds(20, yAnker, 30 , this.buttonheigth);
		lvlbox.setBorder(border);
		this.levelboxlist.add(lvlbox);
		this.add(lvlbox);
		
		JLabel namebox = new JLabel(c.getStat("cname").getWertTxt());
		namebox.setEnabled(false);
		namebox.setHorizontalAlignment(SwingConstants.CENTER);	
		namebox.setBounds(10+lvlbox.getWidth(), yAnker, (int) ((this.getWidth()-70)/3.5), this.buttonheigth);
		namebox.setBorder(border);
		this.nameboxlist.add(namebox);
		this.add(namebox);
		
		JLabel racebox = new JLabel(c.getStat("race").getWertTxt());
		racebox.setEnabled(false);
		racebox.setHorizontalAlignment(SwingConstants.CENTER);	
		racebox.setBounds(namebox.getX()+namebox.getWidth(), yAnker,(int) ( ((this.getWidth()-70)/3.5)), this.buttonheigth);
		racebox.setBorder(border);
		this.raceboxlist.add(racebox);
		this.add(racebox);
		
		JLabel classbox = new JLabel(c.getStat("class").getWertTxt());
		classbox.setEnabled(false);
		classbox.setHorizontalAlignment(SwingConstants.CENTER);	
		classbox.setBounds(racebox.getX()+racebox.getWidth(), yAnker, (int) ((this.getWidth()-70)/3.5), this.buttonheigth);
		classbox.setBorder(border);
		this.classboxlist.add(classbox);
		this.add(classbox);
		
		JButton detbutton = new JButton("Detail");
		detbutton.addActionListener(new detailListener(c,this.detailsbuttonlist.size(), this));
		detbutton.setBounds(this.getWidth()-((this.getWidth()-70)/9)-25, yAnker, ((this.getWidth()-70)/9), this.buttonheigth);
		this.detailsbuttonlist.add(detbutton);
		this.add(detbutton);
		
		
		
		this.updatePos();
	}
	public void delChar(int i){
		System.out.println("del char "+i);
		JLabel lvlbox = this.levelboxlist.remove(i);
		JLabel namebox = this.nameboxlist.remove(i);
		JLabel racebox = this.raceboxlist.remove(i);
		JLabel classbox = this.classboxlist.remove(i);
		JButton detbutton = this.detailsbuttonlist.remove(i);
		this.thisparty.delChara(i);
		
		for (int j=i; j<this.levelboxlist.size();j++){
			JLabel jl = this.levelboxlist.get(j);
			jl.setBounds(jl.getX(),jl.getY()-this.buttonheigth*2,jl.getWidth(),jl.getHeight());
			jl = this.nameboxlist.get(j);
			jl.setBounds(jl.getX(),jl.getY()-this.buttonheigth*2,jl.getWidth(),jl.getHeight());
			jl = this.raceboxlist.get(j);
			jl.setBounds(jl.getX(),jl.getY()-this.buttonheigth*2,jl.getWidth(),jl.getHeight());
			jl = this.classboxlist.get(j);
			jl.setBounds(jl.getX(),jl.getY()-this.buttonheigth*2,jl.getWidth(),jl.getHeight());
			JButton jb = this.detailsbuttonlist.get(j);
			jb.setBounds(jb.getX(), jb.getY()-this.buttonheigth*2, jb.getWidth(), jb.getHeight());		
			for (ActionListener al : jb.getActionListeners()){
				jb.removeActionListener(al);
			}
			jb.addActionListener(new detailListener(this.thisparty.getChara(j),j, this));
		}
		
		this.setSize(this.getWidth(), this.getHeight()-this.buttonheigth*2);
		this.remove(lvlbox);
		this.remove(namebox);
		this.remove(racebox);
		this.remove(classbox);
		this.remove(detbutton);
		this.closeSubFrames();
		this.updatePos();
		this.repaint();
	}
	public void updateStat(int i, stat s, boolean delstat){
		this.thisparty.getChara(i).updateStat(s,delstat);
		this.thisparty.save();
		this.updateLabels();
	}
}

class addPlayerListener implements ActionListener{
	GameFrame frame;
	public addPlayerListener(GameFrame f){
		super();
		frame=f;
	}	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		chara c = new chara();
		frame.addChar(c);
		frame.thisparty.addChar(c);
	}	
}
class diceListener implements ActionListener{
	int wuerfelseiten;
	JLabel output;
	public diceListener(int i, JLabel label){
		super();
		wuerfelseiten = i;
		output= label;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Random rnd = new Random();
		if (wuerfelseiten>2)
			output.setText(String.valueOf((rnd.nextInt((wuerfelseiten - 1) + 1) + 1)));
		else if ((rnd.nextInt((wuerfelseiten - 1) + 1)) == 1)
			output.setText("heads");
		else 
			output.setText("tails");
			
	}
}

class genderListener implements ActionListener{
	JButton out;
	public genderListener(JButton output){
		super();
		out = output;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (out.getText().equals(new String("male name"))){
			out.setText("female name");
		} 
		else if (out.getText().equals(new String("female name"))){
			out.setText("male name");
		}
	}
}
class detailListener implements ActionListener{
	chara c;
	GameFrame f;
	int p;
	public detailListener(chara ch,int pos, GameFrame fr){
		super();
		c = ch;
		f=fr;
		p=pos;
	}
	public void actionPerformed(ActionEvent e){
		f.addSubFrame(new DetailsFrame(c,p , f));		
	}	
}

class nameListener implements ActionListener{
	JButton gender;
	JButton output;	
	public nameListener(JButton gender, JButton out) {
		super();
		this.gender=gender;
		this.output=out;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (gender.getText().equals(new String("male name"))){
			output.setText(getName("C:\\Users\\Olli\\Dropbox\\DnD\\namelistm.txt"));
		}
		else {
			output.setText(getName("C:\\Users\\Olli\\Dropbox\\DnD\\namelistw.txt"));
		}
	}	
	private String getName(String s){
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(s));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<String> lines = new ArrayList<String>();

		String line = null;
		try {
			line = reader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while( line != null ) {
		    lines.add(line);
		    try {
				line = reader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lines.get((new Random()).nextInt(lines.size()));
	}
}

class loadListener implements ActionListener {

	JButton mybutton, otherbutton;
	GameFrame gf;
	String p;
	public loadListener(JButton myBut, JButton otherBut, GameFrame oldFrame, String path){
		mybutton = myBut;
		otherbutton = otherBut;
		gf = oldFrame;
		p= path;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		mybutton.setEnabled(false);
		otherbutton.setEnabled(true);
		gf.load(p);
	}
	
}
