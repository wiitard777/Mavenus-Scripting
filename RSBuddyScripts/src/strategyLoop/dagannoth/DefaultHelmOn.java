package strategyLoop.dagannoth;

import org.rsbuddy.tabs.Inventory;

import com.rsbuddy.script.methods.Players;

import dagannoths.Equipment;
import dagannoths.MavenDagannoths;

public class DefaultHelmOn implements Actions {
	public final static int[] guthansbody = { 4728, 4916, 4917, 4918, 4919 };
	@Override
	public void execute() {
		if (Inventory.containsOneOf(MavenDagannoths.helmId))
			Inventory.getItem(MavenDagannoths.helmId).interact("Wear");

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
		
		if (MavenDagannoths.guthanDequip && Inventory.containsOneOf(MavenDagannoths.helmId))
			return true;
		return false;
	}

}
