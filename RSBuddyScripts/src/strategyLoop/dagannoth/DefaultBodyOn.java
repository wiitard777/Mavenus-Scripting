package strategyLoop.dagannoth;

import org.rsbuddy.tabs.Inventory;

import com.rsbuddy.script.methods.Players;

import dagannoths.Equipment;
import dagannoths.MavenDagannoths;

public class DefaultBodyOn implements Actions {

	public final static int[] guthansbody = { 4728, 4916, 4917, 4918, 4919 };
	
	@Override
	public void execute() {
		if (Inventory.contains(MavenDagannoths.chestId))
			Inventory.getItem(MavenDagannoths.chestId).interact("Wear");

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
		
		if (MavenDagannoths.guthanDequip && Inventory.containsOneOf(MavenDagannoths.chestId))
			return true;
		return false;
	}

}
