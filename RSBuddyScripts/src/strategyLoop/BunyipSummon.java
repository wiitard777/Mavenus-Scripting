package strategyLoop;

import org.rsbuddy.tabs.Inventory;
import org.rsbuddy.tabs.Summoning;

public class BunyipSummon implements Actions{
	public final static int pouch = 12029;
	public final static int[] sumPot = { 12140, 12142, 12144, 12146 };
	@Override
	public void execute() {
		if (Summoning.getPoints() > 10&& Inventory.contains(pouch)){
			Inventory.getItem(pouch).interact("Summon");
		}else if (Summoning.getPoints()<10 && Inventory.containsOneOf(sumPot)){
			Inventory.getItem(sumPot).interact("Drink");
		}
		
	}

	@Override
	public String getStatus() {
		
		return "Summoning Bunyip";
	}

	@Override
	public boolean isValid() {
		if (Inventory.contains(pouch) && Summoning.getPoints()>10 && !Summoning.isFamiliarSummoned()){
			return true;
		}else if (Inventory.contains(pouch) && Inventory.containsOneOf(sumPot)&&!Summoning.isFamiliarSummoned()){
			return true;
		}
		return false;
	}

	@Override
	public int getSleep() {
		// TODO Auto-generated method stub
		return 2000;
	}

	
	
	
}
