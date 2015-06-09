package ttassistant;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;


@SuppressWarnings("serial")
public class DnDDB implements Serializable{
	protected ArrayList<party> partylist;
	//ArrayList<spieler> playerlist;
	private String path = "C:\\Users\\Olli\\Dropbox\\DnD\\party.s";
	
	public DnDDB(){
		System.out.println("DB: Pfad gesetzt zu \"" +path+"\"");
		deserialisieren();
	}
	public DnDDB(String p){
		this.path=p;
		System.out.println("DB: Pfad gesetzt zu "+p);
		deserialisieren();
	}
	protected void char_loeschen(String name, int partyid){
		for ( chara c : partylist.get(partyid).charas ){
			if (c.getStat("name").equals(name)) 
			{
				partylist.get(partyid).charas.remove(c);
				serialisieren();
				return;
			}
		}
	}
	
	private void deserialisieren(){
		try{
			FileInputStream fs = new FileInputStream(this.path);
			ObjectInputStream is = new ObjectInputStream(fs);
			DnDDB geladen = (DnDDB) is.readObject();
			this.partylist = geladen.partylist;
			is.close();
			System.out.println("GameDB: geladen");
		} catch (Exception e){
			e.printStackTrace();
		} 
	
	}
	private void serialisieren(){
		try {
			FileOutputStream fs = new FileOutputStream(path);
			ObjectOutputStream os = new ObjectOutputStream(fs);
			os.writeObject(this);
			os.close();
			System.out.println("DB: gespeichert");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
@SuppressWarnings("serial")
class party implements Serializable{
	ArrayList<chara> charas = new ArrayList<chara>();
	String name = "";
	String path;
	
	public party(String n, String p){
		charas = new ArrayList<chara>();
		name=n;
		path=p;
	}
	public party(String p){
		path=p;
		this.deserialisieren();
	}	
	public void rename(String n){
		name = n;
		this.serialisieren();
	}
	public void addChar(chara c){
		charas.add(c);
		this.serialisieren();
	}
	public int size(){
		return charas.size();
	}
	public chara getChara(int i){
		return charas.get(i);
	}
	public void delChara (int i){
		charas.remove(i);
		this.serialisieren();
	}
	private void deserialisieren(){
		try{
			FileInputStream fs = new FileInputStream(this.path);
			ObjectInputStream is = new ObjectInputStream(fs);
			party geladen = (party) is.readObject();
			this.charas = geladen.charas;
			this.name = geladen.name;
			is.close();
			System.out.println("Party: geladen");
		} catch (FileNotFoundException e){
			this.name = "p1";
			this.serialisieren();
		} catch (Exception e){
			e.printStackTrace();
		} 	
	}
	private void serialisieren(){
		try {
			FileOutputStream fs = new FileOutputStream(path);
			ObjectOutputStream os = new ObjectOutputStream(fs);
			os.writeObject(this);
			os.close();
			System.out.println("Party: gespeichert");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void save(){
		serialisieren();
	}
}

class spieler {
	String name = "";
	ArrayList<chara> chars;
}
@SuppressWarnings("serial")
class chara implements Serializable{
	static int id = 0;
	spieler player = null;
	ArrayList<item> inventory = new ArrayList<item>();
	ArrayList<stat> stats = new ArrayList<stat>();
	public chara(){
		updateStat("cname", "-cname"+chara.id+"-", false);
		updateStat("race", "-race-", false);
		updateStat("class", "-class-", false);
		updateStat("Gesinnung","NG",false);
		updateStat("HP", 10, false);
		updateStat("MaxHP", 10, false);
		updateStat("desc", "-desc-", false);
		updateStat("str", 1, false);
		updateStat("dex", 1, false);
		updateStat("con", 1, false);
		updateStat("int", 1, false);
		updateStat("wis", 1, false);
		updateStat("chr", 1, false);
		updateStat("lvl", 1, false);
		updateStat("ep", 0, false);
		chara.id++;
	}
	public void updateStat(String name, double w1, boolean delstat){
		for (stat s : this.stats){
			if (s.name.equals(name)){
				if (delstat)
				{
					this.stats.remove(s);
					return;
				}
				if (s.getClass() == (new numStat("", 0)).getClass())
					((numStat)s).wert = w1;
				else {
					this.stats.remove(s);
					this.stats.add(new numStat(name, w1));
				}
				return;
			}
		}
		if (!delstat)
			this.stats.add(new numStat(name, w1));
	}
	public void updateStat(String name, String w2,boolean delstat){
		for (stat s : this.stats){
			if (s.name.equals(name)){
				
				if (s.getClass() == (new textStat("", "")).getClass())
					((textStat)s).wert = w2;
				else {
					this.stats.remove(s);
					this.stats.add(new textStat(name, w2));
				}
				return;
			}
		}
		if (!delstat)
			this.stats.add(new textStat(name, w2));
	}
	public void updateStat(stat s, boolean delstat){
		if (s.getClass()== (new textStat("","")).getClass())
			updateStat(s.name,((textStat)s).wert, delstat);
		else
			updateStat(s.name,((numStat)s).wert, delstat);
	}
	public stat getStat(String name){
		for (stat s : this.stats){
			if (s.name.equals(name))
				return s;
		}
		return null;
	}
}
class item {
	String name = "";
	String desc = "";
	public item(String n, String d){
		name=n;
		desc=d;
	}
}
@SuppressWarnings("serial")
abstract class stat implements Serializable{
	String name = "";	
	public stat(String n){
		name=n;
	}
	public String getName(){
		return name;
	}
	public String getWertTxt(){
		if (this.getClass()==(new textStat("", "")).getClass()){
			return ((textStat)this).getStattxt();
		} else {
			return ""+(int)((numStat)this).getStatnum();
		}
	}
}
@SuppressWarnings("serial")
class textStat extends stat{
	String wert;
	public textStat(String n, String w) {
		super(n);
		wert = w;
	}
	public String getStattxt(){
		return wert;
	}
}
@SuppressWarnings("serial")
class numStat extends stat{
	double wert;
	public numStat(String n, double w){
		super(n);
		wert = w;
	}
	public double getStatnum(){
		return wert;
	}
}