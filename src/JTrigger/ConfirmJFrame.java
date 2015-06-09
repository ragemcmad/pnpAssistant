package JTrigger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

@SuppressWarnings("serial")
public class ConfirmJFrame extends MyFrame{
	public void giveAL(ActionListener al){
		this.yesbutton.addActionListener(al);
	}
	public ConfirmJFrame() {
		super();
		
		this.setTitle("sicher?");
		this.setSize(300, 180);	
		this.setLayout(null);
		
		this.sicherlabel.setBounds(this.getWidth()/4, 30, this.getWidth()/2, 30);
		this.add(this.sicherlabel);
		this.yesbutton.setBounds(this.getWidth()/8, 75, this.getWidth()/4 , 30);
		//this.yesbutton.addActionListener(new JTriggerListener(t, this));
		this.add(this.yesbutton);
		this.nobutton.setBounds((this.getWidth()/8)+this.yesbutton.getWidth()+10, 75, this.getWidth()/4 , 30);
		this.nobutton.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}									
				}
		);
		this.add(this.nobutton);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
	private JLabel sicherlabel= new JLabel("Sind sie sicher?");
	private JButton yesbutton = new JButton("JA");
	private JButton nobutton = new JButton("NEIN");
}
