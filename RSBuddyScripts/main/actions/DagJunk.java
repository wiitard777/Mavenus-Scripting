package strategyLoop;

import org.rsbuddy.tabs.Inventory;

import dagannoths.MavenDagannoths;

public class DagJunk implements Actions {

	
	
	@Override
	public void execute() {
		if (Inventory.containsOneOf(MavenDagannoths.JUNKITEMS)){
			Inventory.getItem(MavenDagannoths.getJunk()).interact("Drop");
		} else if (Inventory.containsOneOf(MavenDagannoths.JUNKSTUFF)){
			Inventory.getItem(MavenDagannoths.JUNKSTUFF).interact("Drop");
		}

	}

	@Override
	public int getSleep() {
		
		return 800;
	}

	@Override
	public String getStatus() {
		
		return "Dropping Junk";
	}

	@Override
	public boolean isValid() {
		if (Inventory.containsOneOf(MavenDagannoths.JUNKITEMS) || Inventory.containsOneOf(MavenDagannoths.JUNKSTUFF))
			return true;
		return false;
	}

}
