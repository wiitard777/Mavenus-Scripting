package strategyLoop.slayer;

import com.rsbuddy.script.methods.Walking;

public class SetRunEnabled implements Actions {

	@Override
	public void execute() {
		Walking.setRun(true);

	}

	@Override
	public int getSleep() {
		
		return 0;
	}

	@Override
	public String getStatus() {
		
		return "Setting run on";
	}

	@Override
	public boolean isValid() {
		if (Walking.getEnergy() > 50 && !Walking.isRunEnabled())
			return true;
		return false;
	}

}
