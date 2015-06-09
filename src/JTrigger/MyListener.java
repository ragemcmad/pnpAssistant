package JTrigger;

import java.awt.event.ActionListener;

public abstract class MyListener implements ActionListener{
	protected MyFrame mf;
	
	public MyListener(MyFrame mf){
		this.mf = mf;
	}		
}
