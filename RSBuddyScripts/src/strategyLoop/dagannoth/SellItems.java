package strategyLoop.dagannoth;

import org.rsbuddy.tabs.Inventory;
import org.rsbuddy.widgets.Store;

import dagannoths.MavenDagannoths;

public class SellItems implements Actions {

	public int invoSpace;
	public int test1 = 0;

	@Override
	public void execute() {

		invoSpace = 28 - Inventory.getCount();

		if (!Inventory.containsOneOf(MavenDagannoths.STRENGTH)
				&& Inventory.containsOneOf(2441)) {

			Inventory.getItem(2441).interact("Sell 1");

		} else if (!Inventory.containsOneOf(MavenDagannoths.ATTACK)
				&& Inventory.containsOneOf(2437)) {
			Inventory.getItem(2437).interact("Sell 1");
			invoSpace--;

		} else if (!Inventory.containsOneOf(MavenDagannoths.DEFENCE)
				&& Inventory.containsOneOf(2443)) {
			Inventory.getItem(2443).interact("Sell 1");
			invoSpace--;

		} else if (!Inventory.containsOneOf(MavenDagannoths.pouch)
				&& Inventory.containsOneOf(MavenDagannoths.pouchNote)) {
			Inventory.getItem(MavenDagannoths.pouchNote).interact("Sell 1");
			invoSpace--;

		} else if (Inventory.containsOneOf(MavenDagannoths.pouch)
				&& !Inventory.containsOneOf(MavenDagannoths.sumPot)
				&& Inventory.getItem(MavenDagannoths.notePot) != null) {
			Inventory.getItem(MavenDagannoths.notePot).interact("Sell 1");
			invoSpace--;

		} else if (invoSpace >= 10) {
			// selling based on the number of empty spaces
			if (Inventory.containsOneOf(MavenDagannoths.notedFood)) {
				Inventory.getItem(MavenDagannoths.notedFood)
						.interact("Sell 10");
				invoSpace -= 10;

			}
		} else if (invoSpace >= 5) {
			if (Inventory.containsOneOf(MavenDagannoths.notedFood)) {
				Inventory.getItem(MavenDagannoths.notedFood).interact("Sell 5");
				invoSpace -= 5;

			}
		} else if (invoSpace > 0) {
			if (Inventory.containsOneOf(MavenDagannoths.notedFood)) {
				Inventory.getItem(MavenDagannoths.notedFood).interact("Sell 1");
				invoSpace -= 1;

			}
		}

	}

	@Override
	public int getSleep() {

		return 1500;
	}

	@Override
	public String getStatus() {

		return "Unnoting...";
	}

	@Override
	public boolean isValid() {
		if (Store.isOpen() && !Inventory.isFull())
			return true;
		return false;
	}

}
