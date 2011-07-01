package strategyLoop;

import org.rsbuddy.tabs.Inventory;

import com.rsbuddy.script.methods.GroundItems;
import com.rsbuddy.script.methods.Players;
import com.rsbuddy.script.methods.Walking;
import com.rsbuddy.script.wrappers.GroundItem;

public class LootBones implements Actions {
int e= 0;
	@Override
	public void execute() {
		GroundItem bones = GroundItems.getNearest(526);
		if (bones != null){
			if (bones.isOnScreen() && !Players.getLocal().isMoving()) {
				bones.interact("Take Bones");
				e = 1000;
			} else if (!bones.isOnScreen()) {
				Walking.stepTowards(bones.getLocation());
			}
		}

	}

	@Override
	public int getSleep() {
		
		return e;
	}

	@Override
	public String getStatus() {
		
		return "Looting Bones";
	}

	@Override
	public boolean isValid() {
		if (GroundItems.getNearest(526) != null && !Inventory.isFull())
			return true;
		return false;
	}

}
