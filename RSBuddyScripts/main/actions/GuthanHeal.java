package strategyLoop;

import com.rsbuddy.script.methods.Players;

import dagannoths.MavenDagannoths;

public class GuthanHeal implements Actions {

	@Override
	public void execute() {
		MavenDagannoths.guthanEquip = true;
		
	}

	@Override
	public int getSleep() {
		
		return 0;
	}

	@Override
	public String getStatus() {
		
		return "Healing with guthans...";
	}

	@Override
	public boolean isValid() {
		if (Players.getLocal().getHpPercent() < 60 && !MavenDagannoths.guthanEquip)
			return true;
		return false;
	}

}
