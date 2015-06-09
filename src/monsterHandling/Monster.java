package monsterHandling;

import java.io.Serializable;
import java.util.Random;
import java.util.Vector;

@SuppressWarnings("serial")
public class Monster implements Serializable{
	
	@SuppressWarnings("unchecked")
	public Object clone() {
		Monster m = new Monster(name, init, maxhp,ac, challenge);
		m.attackList = (Vector<Attack>) attackList.clone();
		return m;
	}
	public Monster(String name, int init, int maxhp, int ac, double cr){
		this.init = init;
		this.name = name;
		this.maxhp = maxhp;
		this.hp = this.maxhp;
		this.ac = ac;
		this.challenge = cr;
	}
	public Monster(Monster m) {
		
	}
	public int initroll(){
		Random rnd = new Random();
		int roll = rnd.nextInt((20 - 1) + 1) + 1 + init;
		System.out.println(this.name+" rolls "+ roll );
		return roll;
		
	}
	
	public int init = 0;
	public String name;
	public int maxhp;
	public int hp;
	public int ac;
	public double challenge;
	public String desc="doofes Monster";
	
	public Vector<Attack> attackList = new Vector<Attack>();
	
	public Vector<String> getAttackrolls(int ac){
		Vector<String> list = new Vector<String>();
		for (Attack a : this.attackList){
			String s = a.getRoll(this.name,ac);
			list.add(s);
		}
		
		return list;
	}
	public String toString(){
		String s = "";
		for (Attack a : this.attackList){
			s+="\n\t"+a.toString();
		}
		return "["+name+" | hp="+maxhp+"| init="+init+"| challenge="+challenge+"\n\t["+s+"\n\t]\n]";
	}
	public void addAttack(int plushit, int times, int dsize, int basedmg,
	String attName, String typeName){
		this.attackList.add(new Attack(plushit,times,dsize,basedmg,
		attName,typeName));
	}
	public String getAttName(int i){
		return this.attackList.get(i).attName;
	}
}
@SuppressWarnings("serial")
class Attack implements Serializable{
	public Attack(int plushit, int times, int dsize, int basedmg,
			String attName, String typeName) {
		super();
		this.plushit = plushit;
		this.times = times;
		this.dsize = dsize;
		this.basedmg = basedmg;
		this.attName = attName;
		this.typeName = typeName;
	}
	public int plushit = 2;
	public int times = 1;
	public int dsize = 6;
	public int basedmg = 2;
	public String attName = "Hit";
	public String typeName = "slashing";
	String getRoll(String name, int ac){
		Random rnd = new Random();
		int roll =  rnd.nextInt((20 - 1) + 1) + 1 + plushit;
		if (roll-plushit == 1)
			return new String(name+": "+attName +" CRITMISS! " +"["+ (roll-plushit)+"+"+plushit+"="+roll+"] vs ("+ac+")");
		else if	(roll-plushit ==20)
			return new String(name+": "+attName +" CRITHIT! " +"["+ (roll-plushit)+"+"+plushit+"="+roll+"] vs ("+ac+") => " +(times*(rnd.nextInt((dsize - 1) + 1) + 1) + basedmg + times*(rnd.nextInt((dsize - 1) + 1) + 1))+" "+typeName);
		
		else if (roll>ac)
			return new String(name+": "+attName +" HIT " +"["+ (roll-plushit)+"+"+plushit+"="+roll+"] vs ("+ac+") => " +(times*(rnd.nextInt((dsize - 1) + 1) + 1) + basedmg)+" "+typeName);
		else 
			return new String(name+": "+attName +" MISS " +"["+ (roll-plushit)+"+"+plushit+"="+roll+"] vs ("+ac+")");
		}
	
	public String toString(){
		return attName+"+"+plushit+"("+times+"d"+dsize+"+"+basedmg+typeName+"DMG)";
	}
}
