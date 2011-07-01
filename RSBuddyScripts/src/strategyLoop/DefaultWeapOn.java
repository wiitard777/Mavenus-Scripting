package strategyLoop;

import org.rsbuddy.tabs.Inventory;

import dagannoths.Equipment;
import dagannoths.MavenDagannoths;

public class DefaultWeapOn implements Actions {

	public final static int[] guthansbody = { 4728, 4916, 4917, 4918, 4919 };
	
	@Override
	public void execute() {
		if (Inventory.contains(MavenDagannoths.weaponId))
			Inventory.getItem(MavenDagannoths.weaponId).interact("Wield");
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
		if (MavenDagannoths.guthanDequip && Inventory.containsOneOf(MavenDagannoths.weaponId))
			return true;
		return false;
	}

}
