package strategyLoop;

import org.rsbuddy.tabs.Inventory;

import dagannoths.Equipment;
import dagannoths.MavenDagannoths;

public class DefaultLegsOn implements Actions {

	public final static int[] guthansbody = { 4728, 4916, 4917, 4918, 4919 };
	
	@Override
	public void execute() {
		if (Inventory.containsOneOf(MavenDagannoths.legsId))
			Inventory.getItem(MavenDagannoths.legsId).interact("Wear");

	}

	@Override
	public int getSleep() {
		
		return 1000;
	}

	@Override
	public String getStatus() {
		
		return "Removing Guthans";
	}

	@Override
	public boolean isValid() {
		
		if (MavenDagannoths.guthanDequip && Inventory.containsOneOf(MavenDagannoths.legsId))
			return true;
		return false;
	}

}
