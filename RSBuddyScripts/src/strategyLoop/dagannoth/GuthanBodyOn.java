package strategyLoop.dagannoth;

import org.rsbuddy.tabs.Inventory;

import com.rsbuddy.script.methods.Players;

import dagannoths.Equipment;
import dagannoths.MavenDagannoths;

public class GuthanBodyOn implements Actions{

	public final static int[] guthansbody = { 4728, 4916, 4917, 4918, 4919 };

	@Override
	public void execute() {
		if (Inventory.containsOneOf(guthansbody))
			Inventory.getItem(guthansbody).interact("Wear");

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
		
		if (MavenDagannoths.guthanEquip && Inventory.containsOneOf(guthansbody))
			return true;
		return false;
	}



}
