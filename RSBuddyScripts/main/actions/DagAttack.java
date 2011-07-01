package strategyLoop;

import com.rsbuddy.script.methods.Npcs;
import com.rsbuddy.script.methods.Players;
import com.rsbuddy.script.methods.Walking;
import com.rsbuddy.script.wrappers.Npc;

import dagannoths.MavenDagannoths;

public class DagAttack implements Actions {

	public int e = 0;

	@Override
	public void execute() {
		Npc dag = null;
		Npc[] dags = Npcs.getLoaded();
		if (MavenDagannoths.selectiveAttack) {
			for (int counter = 0; counter < dags.length; counter++) {
				if (dag != null && dag.isOnScreen() && dag.getName() == "Dagannoth"){
					break;
				}
				Npc temp = dags[counter];
				if (temp.getName() == "Dagannoth") {
				if (temp.isInCombat()) {
					if (temp.isInteractingWithLocalPlayer()) {
						dag = temp;
					}
					if (temp.isInCombat()
							&& !temp.isInteractingWithLocalPlayer()) {

					} else {
						dag = temp;
					}
				} else if (!temp.isInCombat()) {
					dag = temp;
				}
				}
			}
			if (dag != null) {
				if (dag.isOnScreen() && !Players.getLocal().isMoving() && Players.getLocal().getInteracting() == null) {
					if (dag.getAnimation() != 1342) {
						dag.interact("Attack");
						e = 1000;
					}
				} else if (!dag.isOnScreen()) {
					Walking.stepTowards(dag.getLocation());

				}
			}
		} else {
			dag = Npcs.getNearest("Dagannoth");
			if (dag != null) {
				if (dag.isOnScreen() && dag.getAnimation() != 1342 && Players.getLocal().getInteracting() == null) {
					dag.interact("Attack");
					e = 1000;
				} else if (!dag.isOnScreen() && dag.getAnimation() != 1342) {
					Walking.stepTowards(dag.getLocation());
				}

			}
		}
	}

	@Override
	public String getStatus() {
		if (Players.getLocal().getInteracting() != null
				|| Players.getLocal().isInCombat())
			return "Attacking";
		return "Finding Dag";

	}

	@Override
	public boolean isValid() {
		if (!Players.getLocal().isInCombat()
				&& !Players.getLocal().isMoving()) {
			return true;
		}
		return false;

	}

	@Override
	public int getSleep() {
		return e;
	}

}
