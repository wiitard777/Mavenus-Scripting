package strategyLoop.dagannoth;

import org.rsbuddy.tabs.Inventory;
import org.rsbuddy.widgets.Store;

import com.rsbuddy.script.methods.Skills;

public class PotAttack implements Actions{

	
	public final static int[] ATTACK = { 2436, 149, 147, 145, 15308, 15309,
		15310, 15311 };
	@Override
	public void execute() {
		
		if (Inventory.containsOneOf(ATTACK)) {
			Inventory.getItem(ATTACK).interact("Drink");
		}
		
	}

	@Override
	public String getStatus() {
		
		return "Drinking Attack";
	}

	@Override
	public boolean isValid() {
		if (Inventory.containsOneOf(ATTACK) && Skills.getRealLevel(0) == Skills.getCurrentLevel(0) && !Store.isOpen())
			return true;
		return false;
	}

	@Override
	public int getSleep() {
		return 1000;
	}

	
	
}
