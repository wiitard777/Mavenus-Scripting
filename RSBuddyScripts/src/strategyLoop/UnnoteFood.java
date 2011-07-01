package strategyLoop;

import org.rsbuddy.tabs.Inventory;

import dagannoths.MavenDagannoths;

public class UnnoteFood implements Actions{
	public int[] food = { 6883, 361, 373, 7946, 385, 329, 379 };
	@Override
	public void execute() {
		MavenDagannoths.walkingTo = true;
		
	}

	@Override
	public int getSleep() {
		
		return 0;
	}

	@Override
	public String getStatus() {
		
		return "Out of Food";
	}

	@Override
	public boolean isValid() {
		if (!Inventory.containsOneOf(food))
			return true;
		return false;
	}

}
