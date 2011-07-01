package strategyLoop;

import org.rsbuddy.tabs.Inventory;

import com.rsbuddy.script.methods.Players;

import dagannoths.Equipment;
import dagannoths.MavenDagannoths;

public class GuthanSpearOn implements Actions {

	public final static int[] guthansspear = { 4726, 4910, 4911, 4912, 4913 };
	public final static int[] guthansbody = { 4728, 4916, 4917, 4918, 4919 };
	
	@Override
	public void execute() {
		if (Inventory.containsOneOf(guthansspear))
		Inventory.getItem(guthansspear).interact("Wield");

	}

	@Override
	public int getSleep() {
		return 1000;
	}

	@Override
	public String getStatus() {
	
		return "Equipping Guthans";
	}

	@Override
	public boolean isValid() {
		
		if (MavenDagannoths.guthanEquip && Inventory.containsOneOf(guthansspear))
			return true;
		return false;
	}

}
