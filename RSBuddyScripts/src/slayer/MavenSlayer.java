package slayer;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import strategyLoop.*;
import strategyLoop.dagannoth.Actions;


import com.rsbuddy.event.listeners.PaintListener;
import com.rsbuddy.script.ActiveScript;

public class MavenSlayer extends ActiveScript implements PaintListener,
		MouseListener, MouseMotionListener {
	
	public ArrayList<Actions> actions = new ArrayList<Actions>();
	

	public boolean onStart() {
		/*
		*invoke gui
		*check for slayer task, check for combat levels, check for slayer level
		*get starting gear and location
		*initialize variables that need it
		*/
		
		
		return true;
	}
	
	@Override
	public int loop() {
		/*
		 * 1. start the antiban
		 * 2. check if there is an active SlayerMonster object
		 * 3a. if there is an active slayerMosnter object start using it
		 * 3b. if the task was finished return to a slayermaster and get a new task
		 * 4b. if the current task does not match the current slayermonster object reinitialize the slayermonster objects
		 */
		return 0;
	}

	@Override
	public void onRepaint(Graphics arg0) {
		

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		

	}

}
