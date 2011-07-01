package strategyLoop;

import org.rsbuddy.tabs.Inventory;
import org.rsbuddy.widgets.Store;

public class BuyItems implements Actions {

	public final static int pouch = 12029;
	
	@Override
	public void execute() {
		
		if (Store.getItem(2440) != null) {
			Store.getItem(2440).interact("Buy 1");
			

		} else if (Store.getItem(2436) != null) {
			Store.getItem(2436).interact("Buy 1");
			

		} else if (Store.getItem(2443) != null) {
			Store.getItem(2443).interact("Buy 1");
			

		} else if (Store.getItem(pouch) != null) {
			Store.getItem(pouch).interact("Buy 1");
			

		} else if (Store.getItem(12140) != null) {
			Store.getItem(12140).interact("Buy 1");
			

		}else if (Store.getItem(7946) != null) {
			Store.buy(7946, 50);
		} else if (Store.getItem(361) != null) {
			Store.buy(361, 50);
		} else if (Store.getItem(373) != null) {
			Store.buy(373, 50);
		} else if (Store.getItem(385) != null) {
			Store.buy(385, 50);
		} else if (Store.getItem(329) != null) {
			Store.buy(329, 50);
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
		if (Store.isOpen()){
		if (Store.getItem(2440) != null) {
			return true;

		} else if (Store.getItem(2436) != null) {
			return true;
		} else if (Store.getItem(2443) != null) {
			return true;
		} else if (Store.getItem(pouch) != null) {
			return true;
		} else if (Store.getItem(12140) != null) {
			return true;
		}
		else if (Store.getItem(7946) != null) {
			return true;
		} else if (Store.getItem(361) != null) {
			return true;
		} else if (Store.getItem(373) != null) {
			return true;
		} else if (Store.getItem(385) != null) {
			return true;
		} else if (Store.getItem(329) != null) {
			return true;
		}
		
	}
		return false;
	}

}
