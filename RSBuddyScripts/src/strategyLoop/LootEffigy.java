package strategyLoop;

import org.rsbuddy.tabs.Inventory;

import com.rsbuddy.script.methods.GroundItems;
import com.rsbuddy.script.methods.Walking;
import com.rsbuddy.script.wrappers.GroundItem;

public class LootEffigy implements Actions {
	public int i = 0;
	public int EFFIGY = 18778;
	public int[] food = { 6883, 361, 373, 7946, 385, 329, 379 };
	
	@Override
	public void execute() {
		
		GroundItem effigy = GroundItems.getNearest(EFFIGY);
		if (effigy != null && effigy.isOnScreen()) {
			i = 1500;
			if (!Inventory.isFull()) {
			effigy.interact("Take");
			} else if (Inventory.isFull()){
				if (Inventory.containsOneOf(food))
					Inventory.getItem(food).interact("Eat");
			} else if (Inventory.contains(526)){
				Inventory.getItem(526).interact("Bury");
			}
		} else if (effigy != null && !effigy.isOnScreen()) {
			Walking.stepTowards(effigy.getLocation());
		}

	}

	@Override
	public int getSleep() {
		
		return i;
	}

	@Override
	public String getStatus() {
		return "Found an Effigy!";
	}

	@Override
	public boolean isValid() {
		if (GroundItems.getNearest(EFFIGY) != null)
			return true;
		return false;
	}

}
