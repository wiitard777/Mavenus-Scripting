package strategyLoop.dagannoth;

import org.rsbuddy.tabs.Inventory;
import org.rsbuddy.widgets.Store;

import com.rsbuddy.script.methods.Skills;

public class PotStrength implements Actions{
	public final static int[] STRENGTH = { 2440, 161, 159, 157, 15312, 15313,
		15314, 15315 };
	@Override
	public void execute() {
		if (Inventory.containsOneOf(STRENGTH))
			Inventory.getItem(STRENGTH).interact("Drink");
		
	}

	@Override
	public String getStatus() {
		
		return "Drinking Strength";
	}

	@Override
	public boolean isValid() {
		if (Inventory.containsOneOf(STRENGTH) && Skills.getRealLevel(2) == Skills.getCurrentLevel(2) && !Store.isOpen())
			return true;
		return false; 
	}

	@Override
	public int getSleep() {
		return 1000;
	}

	
	
	
}
