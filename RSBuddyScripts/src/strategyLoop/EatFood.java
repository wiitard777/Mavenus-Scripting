package strategyLoop;

import org.rsbuddy.tabs.Inventory;

import com.rsbuddy.script.methods.Players;

public class EatFood implements Actions{
	
	public int[] food = { 6883, 361, 373, 7946, 385, 329, 379 };
	
	@Override
	public void execute() {
		
		if (Inventory.containsOneOf(food))
			Inventory.getItem(food).interact("Eat");
		
		
	}

	@Override
	public String getStatus() {
		
		return "Eating";
	}

	@Override
	public boolean isValid() {
		if (Players.getLocal().getHpPercent() < 75 && Inventory.containsOneOf(food))
			return true;
		return false;
	}

	@Override
	public int getSleep() {
		
		return 1000;
	}

}
