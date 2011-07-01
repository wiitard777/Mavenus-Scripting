package strategyLoop;



import com.rsbuddy.script.methods.Objects;
import com.rsbuddy.script.methods.Players;
import com.rsbuddy.script.methods.Walking;
import com.rsbuddy.script.wrappers.GameObject;

import dagannoths.MavenDagannoths;

public class WalkDags implements Actions {

	@Override
	public void execute() {
		if (Players.getLocal().getLocation() != null) {
			if (MavenDagannoths.midFloor.contains(Players.getLocal().getLocation())) {
				GameObject ladder;
				GameObject stairs;
				if (Objects.getNearest(4383) != null) {

					ladder = Objects.getNearest(4383);
					if (ladder != null) {
						if (ladder.isOnScreen()) {
							if (!Players.getLocal().isMoving()) {
								ladder.interact("Climb");
								
							}
						} else if (!ladder.isOnScreen()) {
							Walking.stepTowards(ladder.getLocation());
							
						}
					}
				} else if (Objects.getNearest(4569) != null) {

					stairs = Objects.getNearest(4569);
					if (stairs != null) {
						if (stairs.isOnScreen()) {
							if (!Players.getLocal().isMoving()) {
								stairs.interact("Climb-down");
								
							}
						} else if (!stairs.isOnScreen()) {
							Walking.stepTowards(stairs.getLocation());
						}
					}
				}
			} else if (MavenDagannoths.topLadder.contains(Players.getLocal().getLocation())) {
				GameObject ladder;

				ladder = Objects.getNearest(4485);
				if (ladder != null) {
					if (ladder.isOnScreen()) {
						if (!Players.getLocal().isMoving()) {
							ladder.interact("Climb");
							
						}
					} else if (!ladder.isOnScreen()) {
						Walking.stepTowards(ladder.getLocation());
						
					}
				}
			} else if (MavenDagannoths.pastDoor.contains(Players.getLocal().getLocation())) {
				GameObject door;

				door = Objects.getNearest(4545);
				if (door != null) {
					if (door.isOnScreen()) {
						if (!Players.getLocal().isMoving()) {
							door.interact("Open");
							
						}
					} else if (!door.isOnScreen()) {
						Walking.stepTowards(door.getLocation());
						
					}
				}
			} else if (MavenDagannoths.dagArea.contains(Players.getLocal().getLocation())) {

				MavenDagannoths.walkingFrom = false;
				
			}
		}
		

	

	}

	@Override
	public int getSleep() {
		
		return 1000;
	}

	@Override
	public String getStatus() {
		
		return "Heading back";
	}

	@Override
	public boolean isValid() {
		if (MavenDagannoths.walkingFrom)
			return true;
		return false;
	}

}
