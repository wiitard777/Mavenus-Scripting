package strategyLoop.dagannoth;

import org.rsbuddy.tabs.Inventory;



public class BonesToPeaches implements Actions{
	public final static int tablets = 8015;
	public final static int bones = 526;
	public int[] food = { 6883, 361, 373, 7946, 385, 329, 379 };
	public int e = 0;
	@Override
	public void execute() {
		Inventory.getItem(tablets).interact("Break");
		e = 7000;
		
	}

	@Override
	public int getSleep() {
		
		return e;
	}

	@Override
	public String getStatus() {
		
		return "Using B2P Tab";
	}

	@Override
	public boolean isValid() {
		if (Inventory.contains(tablets) && Inventory.contains(bones) && !Inventory.containsOneOf(food))
			return true;
		return false;
	}

}
