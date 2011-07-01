package dagannoths;
import org.rsbuddy.tabs.Inventory;

import com.rsbuddy.script.methods.GrandExchange;


import java.awt.*;

public class ItemPaint {

	private final long startTime;
	private int startCount;
	private final String name;
	private final int itemId;
	private final boolean stacks;
	
	
	
	public ItemPaint(String call, int id, boolean stackable) {
		startTime = System.currentTimeMillis();
		name = call;
		itemId = id;
		
		
		
		stacks = stackable;
		
		startCount = Inventory.getCount(stacks,itemId);
		
	}
	
	public String getName() {
		return name;
	}
	public int getGained() {
		return getCurrent() - startCount;
	}

	public int getCurrent() {
		
		return Inventory.getCount(stacks,itemId);
		
	}
	
	
	public long getTime() {
		return System.currentTimeMillis() - startTime;
	}
	public void drawItem(Graphics2D g, int x, int y) {
		
		g.drawString(getName() + ": "+getGained(), x, y);
		
	}
	
	
}
