package ttassistant;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.*;

import JTrigger.MyFrame;
import monsterHandling.Monster;

@SuppressWarnings("serial")
public class FightFrame extends MyFrame {
	
	party p;
	GameFrame gf;
	Vector<Monster> enemyList;
	
	Vector<JLabel> heroNameList = new Vector<JLabel>();
	Vector<ActiveCheckBox> activeBoxList = new Vector<ActiveCheckBox>();	
	int active = 0;
	Vector<JTextField> heroEditHPTxTList = new Vector<JTextField>();
	
	Vector<JLabel> enemyNameList = new Vector<JLabel>();
	Vector<JButton> enemyAttackButtonList = new Vector<JButton>();
	Vector<JLabel> enemyAttackRollList = new Vector<JLabel>();	
	Vector<JButton> commitAttackButtonList = new Vector<JButton>();
	
	public FightFrame(Vector<Monster> enemyList, GameFrame g) {
		super();
		
		for (chara c : p.charas){
			JLabel jl = new JLabel(c.getStat("cname").getWertTxt());
			heroNameList.add(jl);
			activeBoxList.add(new ActiveCheckBox(this,activeBoxList.size()+1));
			
			this.add(jl);
		}
		
		this.enemyList = enemyList;
		this.gf = g;
		
		this.updatePos();
	}
	
	private void updatePos(){
		
	}
	
	public void dispose(){
		
	}
	
}

@SuppressWarnings("serial")
class ActiveCheckBox extends JCheckBox {
	FightFrame f;
	int pos;
	
	public ActiveCheckBox(final FightFrame f, int p){
		super();		
		this.f = f;
		this.pos = p;
		
		this.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (isEnabled())	{
					setEnabled(false);
					f.active = 0;
				}					
				else {
					for (ActiveCheckBox acb : f.activeBoxList){
						acb.setEnabled(false);
					}
					f.active=pos;
					setEnabled(true);
				}
			}			
		});
	}	
}

@SuppressWarnings("serial")
class EditHpButton extends JButton {
	public EditHpButton(int pos, FightFrame f) {
		super();
		this.pos = pos;
		this.f = f;
		this.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
	}
	int pos;
	FightFrame f;
}
