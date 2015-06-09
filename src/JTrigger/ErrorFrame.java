package JTrigger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

@SuppressWarnings("serial")
public class ErrorFrame extends JFrame{
	
	public ErrorFrame(String e){
		super();
	
		this.setTitle("Error");
		this.setSize(300, 180);	
		this.setLayout(null);
		
		this.er = new JLabel("Error: "+e);
		this.er.setBounds(this.getWidth()/4, 30, this.getWidth()/2, 30);
		this.er.setHorizontalAlignment(SwingConstants.CENTER);	
		this.add(this.er);
		
		this.ok.setBounds(this.getWidth()/4, 75, this.getWidth()/2, 30);
		this.ok.addActionListener(
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}									
			}
		);
		this.add(this.ok);
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
	JLabel er;
	JButton ok = new JButton("Ok");
}
