package JTrigger;

import java.util.ArrayList;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class MyFrame extends JFrame {
	private ArrayList<JFrame> subframes = new ArrayList<JFrame>();
	public void dispose(){
		super.dispose();
		this.closeSubFrames();
	}
	protected void closeSubFrames(){
		for (JFrame f : subframes)
			f.dispose();	
	}
	public void addSubFrame(JFrame jf){
		this.subframes.add(jf);
	}
	public MyFrame(){
		super();
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(null);
		this.setVisible(true);
	}
}
