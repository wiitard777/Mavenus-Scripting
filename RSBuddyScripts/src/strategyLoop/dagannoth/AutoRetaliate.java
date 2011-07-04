package strategyLoop.dagannoth;

import org.rsbuddy.tabs.Attack;

public class AutoRetaliate implements Actions{

	@Override
	public void execute() {
		Attack.setAutoRetaliate(true);
		
	}

	@Override
	public int getSleep() {
		
		return 2000;
	}

	@Override
	public String getStatus() {
		
		return "Enabling Auto Retaliate";
	}

	@Override
	public boolean isValid() {
		if (!Attack.isAutoRetaliateEnabled())
			return true;
		return false;
	}

}
