package strategyLoop.dagannoth;

import org.rsbuddy.tabs.Inventory;

import com.rsbuddy.script.wrappers.Item;

public class DropJunk implements Actions {

	public final static int[] JUNKITEMS = { 229, 301, 1237, 1351, 1239, 1243,
		1249, 5280, 5297, 5281, 5106, 5301, 5299, 555, 1452, 1623, 1621,
		1619, 1617, 886, 828, 830, 311, 313, 314, 327, 345, 402, 413, 46,
		359, 377, 45, 405, 987, 985, 2366 };
	
	@Override
	public void execute() {
		Item item = Inventory.getItem(JUNKITEMS);
		int drop;
		drop = item.getId();
		Inventory.getItem(drop).interact("Drop");
	}

	@Override
	public int getSleep() {
		
		return 1500;
	}

	@Override
	public String getStatus() {
		
		return "Dumping Junk";
	}

	@Override
	public boolean isValid() {
		if (Inventory.containsOneOf(JUNKITEMS))
			return true;
		return false;
	}

}
