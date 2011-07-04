package strategyLoop.dagannoth;

import org.rsbuddy.tabs.Inventory;

import com.rsbuddy.script.methods.GroundItems;

import com.rsbuddy.script.methods.Players;
import com.rsbuddy.script.methods.Walking;
import com.rsbuddy.script.wrappers.GroundItem;

import dagannoths.MavenDagannoths;

public class LootDags implements Actions{
public int e = 0;
public int[] food = { 6883, 361, 373, 7946, 385, 329, 379 };
	@Override
	public void execute() {
		GroundItem loot = GroundItems.getNearest(MavenDagannoths.getLoot());
		if (loot != null){
			int lootItem = loot.getItem().getId();
			if (loot.isOnScreen() && !Players.getLocal().isMoving()) {
				if (!Inventory.isFull()) {
				loot.interact("Take "+loot.getItem().getName());
				e = 1000;
				} else if (Inventory.isFull() && Inventory.contains(lootItem)) { 
					loot.interact("Take "+loot.getItem().getName());
					e = 1000;
				}
					else if (Inventory.isFull() && Inventory.containsOneOf(food)){
				
					EatFood junk = new EatFood();
					junk.execute();
					e = 1000;
				} else if (Inventory.isFull() && Inventory.containsOneOf(526)) {
					LootBones junk = new LootBones();
					junk.execute();
					e = 1000;
				}
			}else if (!loot.isOnScreen()){
				Walking.stepTowards(loot.getLocation());
			}
		}
		
	}

	@Override
	public String getStatus() {
		
		return "Looting Items";
	}

	@Override
	public boolean isValid() {
		if (GroundItems.getNearest(MavenDagannoths.getLoot()) != null)
			return true;
		return false;
	}

	@Override
	public int getSleep() {
		
		return e;
	}

}
