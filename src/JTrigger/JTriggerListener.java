package JTrigger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class JTriggerListener implements ActionListener{

	JTriggerObject t;
	JFrame f;
	public JTriggerListener(JTriggerObject tr, JFrame fr){
		super();
		t=tr;
		f=fr;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		t.pull();
		f.dispose();
	}	
}