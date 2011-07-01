package strategyLoop;

import com.rsbuddy.script.methods.Players;

import dagannoths.MavenDagannoths;

public class GuthanOff implements Actions {

	@Override
	public void execute() {
		MavenDagannoths.guthanDequip = true;
		MavenDagannoths.guthanEquip = false;
	}

	@Override
	public int getSleep() {
		
		return 0;
	}

	@Override
	public String getStatus() {
		
		return "Unequipping guthans";
	}

	@Override
	public boolean isValid() {
		if (Players.getLocal().getHpPercent() == 100 && MavenDagannoths.guthanEquip && !MavenDagannoths.guthanDequip)
			return true;
		return false;
	}

}
