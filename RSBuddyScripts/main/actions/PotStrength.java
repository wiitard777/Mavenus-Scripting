package strategyLoop;

import org.rsbuddy.tabs.Inventory;

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
		if (Inventory.containsOneOf(STRENGTH) && Skills.getRealLevel(2) == Skills.getCurrentLevel(2))
			return true;
		return false; 
	}

	@Override
	public int getSleep() {
		return 1000;
	}

	
	
	
}
