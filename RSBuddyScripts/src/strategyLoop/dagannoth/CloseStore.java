package strategyLoop.dagannoth;

import org.rsbuddy.tabs.Inventory;
import org.rsbuddy.widgets.Store;

import dagannoths.MavenDagannoths;

public class CloseStore implements Actions {

	@Override
	public void execute() {
		Store.close();
		MavenDagannoths.walkingTo = false;
		MavenDagannoths.walkingFrom = true;

	}

	@Override
	public int getSleep() {
		
		return 1000;
	}

	@Override
	public String getStatus() {
		
		return "Unnoting";
	}

	@Override
	public boolean isValid() {
		if (Store.isOpen() && Inventory.isFull())
			return true;
		return false;
	}

}
