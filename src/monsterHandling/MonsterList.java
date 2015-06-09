package monsterHandling;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;

public class MonsterList implements Serializable{
	private static final long serialVersionUID = 2096160540512796730L;
	private String path = "C:\\Users\\Olli\\Dropbox\\DnD\\monsters.s";
	public Vector<Monster> mlist;
	
	public MonsterList(){
		try{
			FileInputStream fs = new FileInputStream(this.path);
			ObjectInputStream is = new ObjectInputStream(fs);
			MonsterList geladen = (MonsterList) is.readObject();
			this.mlist = geladen.mlist;
			is.close();
			System.out.println("MonsterDB: geladen");
			this.printMonsterList();
		} catch (Exception e){
			System.out.println("MonsterDB: fail2load -> reInit");
			mlist = new Vector<Monster>();
		} 
	}

	public Monster getMonsterbyPos(int i){
		return mlist.get(i);
	}
	void printMonsterList(){
		int i=0;
		for (Monster m : mlist){
			System.out.println(""+i+" "+m.toString());
			i++;
		}
	}
	public void addMonster(Monster m){		
		boolean found = false;
		for (Monster mCheck : this.mlist)
			if (m.name.equals(mCheck.name))
				found=true;
		if (!found){
			mlist.add(m);
			try {
				FileOutputStream fs = new FileOutputStream(path);
				ObjectOutputStream os = new ObjectOutputStream(fs);
				os.writeObject(this);
				os.close();
				System.out.println("added "+m.toString()+" to MonsterDB");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else 
			System.out.println("Doppelter Eintrag "+m.name);
		
	}
	public void delMonster(int delPos){
		Monster m = mlist.get(delPos);
		mlist.remove(delPos);
		
		try {
			FileOutputStream fs = new FileOutputStream(path);
			ObjectOutputStream os = new ObjectOutputStream(fs);
			os.writeObject(this);
			os.close();
			System.out.println("deleted "+m.toString()+" from MonsterDB");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
