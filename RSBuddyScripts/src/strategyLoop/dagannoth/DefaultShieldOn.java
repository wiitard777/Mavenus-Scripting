package strategyLoop.dagannoth;

import org.rsbuddy.tabs.Inventory;

import dagannoths.Equipment;
import dagannoths.MavenDagannoths;

public class DefaultShieldOn implements Actions {

	public final static int[] guthansbody = { 4728, 4916, 4917, 4918, 4919 };
	
	@Override
	public void execute() {
		if (Inventory.contains(MavenDagannoths.shieldId))
			Inventory.getItem(MavenDagannoths.shieldId).interact("Wield");
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
		Equipment equips = new Equipment();
		if (MavenDagannoths.guthanDequip && Inventory.containsOneOf(MavenDagannoths.shieldId))
			return true;
		return false;
	}

}
