package strategyLoop.dagannoth;

import org.rsbuddy.tabs.Inventory;
import org.rsbuddy.widgets.Store;

import com.rsbuddy.script.methods.Skills;

public class PotDefense implements Actions {
	public final static int[] DEFENSE = { 2442, 167, 165, 163, 15316, 15317,
		15318, 15319 };
	@Override
	public void execute() {
		if (Inventory.containsOneOf(DEFENSE)) 
			Inventory.getItem(DEFENSE).interact("Drink");
		
	}

	@Override
	public String getStatus() {
		
		return "Drinking Defense";
	}

	@Override
	public boolean isValid() {
		if (Inventory.containsOneOf(DEFENSE) && Skills.getCurrentLevel(1) == Skills.getRealLevel(1) && !Store.isOpen())
			return true;
		return false;
	}

	@Override
	public int getSleep() {
		return 1000;
	}

}
