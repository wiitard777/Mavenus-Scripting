package strategyLoop.dagannoth;

import org.rsbuddy.widgets.Store;

import com.rsbuddy.script.methods.Npcs;
import com.rsbuddy.script.methods.Objects;
import com.rsbuddy.script.methods.Players;
import com.rsbuddy.script.methods.Walking;
import com.rsbuddy.script.wrappers.GameObject;
import com.rsbuddy.script.wrappers.Npc;

import dagannoths.MavenDagannoths;

public class WalkJossik implements Actions {

	@Override
	public void execute() {

		if (MavenDagannoths.dagArea.contains(Players.getLocal().getLocation())) {
			GameObject ladder;

			ladder = Objects.getNearest(MavenDagannoths.ladderId);
			if (ladder != null) {
				if (ladder.isOnScreen()) {
					if (!Players.getLocal().isMoving()) {
						ladder.interact("Climb");
						
					}
				} else {
					Walking.stepTowards(ladder.getLocation());
					
				}
			}

		} else if (MavenDagannoths.pastDoor.contains(Players.getLocal().getLocation())) {
			GameObject ladder;

			ladder = Objects.getNearest(4412);
			if (ladder != null) {
				if (!ladder.isOnScreen()) {
					Walking.stepTowards(ladder.getLocation());
					
				} else if (ladder.isOnScreen()) {
					if (!Players.getLocal().isMoving()) {
						ladder.interact("Climb");

					}
				}
			}
		} else if (MavenDagannoths.topLadder.contains(Players.getLocal().getLocation())) {
			GameObject door;

			door = Objects.getNearest(4546);
			if (door != null) {
				if (door.isOnScreen()) {
					if (!Players.getLocal().isMoving()) {
						door.interact("Open");
						
					}
				} else if (!door.isOnScreen()) {
					Walking.stepTowards(door.getLocation());
					
				}
				
			}
		} else if (MavenDagannoths.midFloor.contains(Players.getLocal().getLocation())) {
			if (Objects.getNearest(4568) != null) {
				GameObject stairs;
				stairs = Objects.getNearest(4568);
				if (stairs != null) {

					if (stairs.isOnScreen()) {
						if (!Players.getLocal().isMoving()) {
							stairs.interact("Climb-up");
							
						}
					} else if (!stairs.isOnScreen()) {
						Walking.stepTowards(stairs.getLocation());
						
					}
				}
			}

			else if (Npcs.getNearest(1334) != null && !Store.isOpen()) {

				Npc jossik;
				jossik = Npcs.getNearest(1334);
				if (jossik != null) {
					if (jossik.isOnScreen()) {
						if (!Players.getLocal().isMoving()) {
							jossik.interact("Trade");
							
						}
					} else if (!jossik.isOnScreen()) {
						Walking.stepTowards(jossik.getLocation());
						
					}
				}
			} else {
				GameObject stairs;
				stairs = Objects.getNearest(4570);
				if (stairs != null) {

					if (stairs.isOnScreen()) {
						stairs.interact("Climb-down");
						
					} else {
						Walking.stepTowards(stairs.getLocation());
						
					}
				}
			}
		}

		

	

	}

	@Override
	public int getSleep() {
		
		return 1000;
	}

	@Override
	public String getStatus() {
		
		return "Unnoting Items";
	}

	@Override
	public boolean isValid() {
		if (MavenDagannoths.walkingTo)
			return true;
		return false;
	}

}
