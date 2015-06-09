package ttassistant;

import monsterHandling.*;

public class Mainclass {

	public static void main(String[] args) {
		MonsterList ml = new MonsterList();
		Monster m = new Monster("Bullywug",1, 11, 15, 0.25);
		m.addAttack(3, 1, 4, 1, "Bite+Spear", "pierce");
		m.addAttack(3, 1, 6, 1, "Spear+Bite", "pierce");
		m.addAttack(3, 1, 6, 1, "Throw(20/60))", "pierce");
		//m.addAttack(5, 1, 8, 3, "Pounce", "slash + DC13str prone" );
		
		ml.addMonster(m);
		
		
		
		
		new GameFrame();
		}

}
