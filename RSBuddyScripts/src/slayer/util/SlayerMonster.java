package slayer.util;

import java.awt.Graphics;

import com.rsbuddy.script.wrappers.Area;
import com.rsbuddy.script.wrappers.Tile;

import strategyLoop.dagannoth.Actions;

public class SlayerMonster {
	
	private final String name;
	private final int id;
	private final Actions attack;
	private final Actions reqItem;
	private final Tile destination;
	private final Area location;
	private boolean guthans;
	private boolean cannon;
	private boolean summon;

	public SlayerMonster(String thisname, int thisid, Tile dest, Area loc, Actions thisattack, Actions item, boolean guthan, boolean cannon, boolean summon) {
		
		name = thisname;
		id = thisid;
		attack = thisattack;
		reqItem = item;
		destination = dest;
		location = loc;
	}
	
	public String getName() {
		return name;
	}
	
	public int getId() {
		return id;
	}
	
	public Actions getAttack() {
		return attack;
	}
	
	public Actions getReqItem() {
		return reqItem;
	}
	
	public Tile getDestination() {
		return destination;
	}
	
	public Area getLocation() {
		return location;
	}
	
	public void drawTask(Graphics g,int x,int y) {
		
		g.drawString(getName(), x, y);
		
	}
}
