package strategyLoop.dagannoth;

import org.rsbuddy.tabs.Inventory;
import com.rsbuddy.script.methods.Players;

import dagannoths.Equipment;
import dagannoths.MavenDagannoths;

public class GuthanSkirtOn implements Actions{

	public final static int[] guthansskirt = { 4730, 4922, 4923, 4924, 4925 };
	public final static int[] guthansbody = { 4728, 4916, 4917, 4918, 4919 };
	
	@Override
	public void execute() {
		if (Inventory.containsOneOf(guthansskirt))
		Inventory.getItem(guthansskirt).interact("Wear");
		
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
		
		if (MavenDagannoths.guthanEquip && Inventory.containsOneOf(guthansskirt))
			return true;
		return false;
	}

}
