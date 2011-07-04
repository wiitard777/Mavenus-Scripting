package strategyLoop.dagannoth;

import org.rsbuddy.tabs.Inventory;

import dagannoths.Equipment;
import dagannoths.MavenDagannoths;

public class Normalize implements Actions {

	public final static int[] guthanshelm = { 4724, 4904, 4905, 4906, 4907 };
	public final static int[] guthansspear = { 4726, 4910, 4911, 4912, 4913 };
	public final static int[] guthansbody = { 4728, 4916, 4917, 4918, 4919 };
	public final static int[] guthansskirt = { 4730, 4922, 4923, 4924, 4925 };
	Equipment equips = new Equipment();
	@Override
	public void execute() {
		MavenDagannoths.guthanDequip = false;

	}

	@Override
	public int getSleep() {
		
		return 0;
	}

	@Override
	public String getStatus() {
		
		return "Finished Equipping";
	}

	@Override
	public boolean isValid() {
		if (!equips.containsOneOf(guthanshelm) 
				&& !equips.containsOneOf(guthansspear) 
				&& !equips.containsOneOf(guthansskirt) 
				&& !equips.containsOneOf(guthansbody) 
				&& MavenDagannoths.guthanDequip)
			if (!Inventory.containsOneOf(MavenDagannoths.shieldId))
			return true;
		return false;
	}

}
