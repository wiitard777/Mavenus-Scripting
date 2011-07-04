package strategyLoop.dagannoth;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;


import org.rsbuddy.widgets.Store;
import org.rsbuddy.tabs.Attack;
import org.rsbuddy.tabs.Equipment;
import org.rsbuddy.tabs.Inventory;
import com.rsbuddy.event.listeners.PaintListener;
import com.rsbuddy.script.ActiveScript;
import com.rsbuddy.script.methods.Game;

import com.rsbuddy.script.methods.GroundItems;
import com.rsbuddy.script.methods.Mouse;
import com.rsbuddy.script.methods.Npcs;
import com.rsbuddy.script.methods.Objects;
import com.rsbuddy.script.methods.Players;
import com.rsbuddy.script.methods.Skills;
import org.rsbuddy.tabs.Summoning;

import strategyLoop.dagannoth.Actions;

import com.rsbuddy.script.methods.Walking;
import com.rsbuddy.script.util.Random;
import com.rsbuddy.script.util.Timer;
import com.rsbuddy.script.wrappers.Area;
import com.rsbuddy.script.wrappers.GameObject;
import com.rsbuddy.script.wrappers.GroundItem;
import com.rsbuddy.script.wrappers.Item;
import com.rsbuddy.script.wrappers.Npc;
import com.rsbuddy.script.wrappers.Tile;
import com.rsbuddy.script.Manifest;
import com.rsbuddy.script.methods.Npcs;
import com.rsbuddy.script.methods.Players;
import com.rsbuddy.script.methods.Walking;
import com.rsbuddy.script.util.Filter;
import com.rsbuddy.script.wrappers.Npc;

import dagannoths.MavenDagannoths;

public class DagAttack implements Actions {

	public int e = 0;
	Filter<Npc> filter = new Filter<Npc>(){
		@Override
		public boolean accept(Npc i){
			if (i.getLevel() == 92 || i.getLevel() == 74){
				if (i.getAnimation() !=1342){
					if (MavenDagannoths.selectiveAttack){
						if (i.isInCombat()){
							if (i.isInteractingWithLocalPlayer()){
								return true;
							} else {
								return false;
							}
						} else {
							return true;
						}
					} else {
						return true;
					}
				}
				return false;
			}else {
				return false;
			}
		}
	};

	@Override
	public void execute() {

		Npc dag = Npcs.getNearest(filter);
		for (int i = 0;i<20;i++){
			if (Players.getLocal().isInCombat()){
				break;
			} else {

				MavenDagannoths.sleep(20);
			}
		}
		if (isValid()){
			if (dag != null) {
				if (dag.isOnScreen()){
					if (!Players.getLocal().isMoving()){
						dag.interact("Attack");
						e = 1000;
					}
				} else {
					Walking.stepTowards(dag.getLocation());
				}
			}else{

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

				&& Players.getLocal().getInteracting() == null) {
			return true;
		} else if (Players.getLocal().getInteracting() == null)
			return true;
		return false;

	}

	@Override
	public int getSleep() {
		return e;
	}

}
