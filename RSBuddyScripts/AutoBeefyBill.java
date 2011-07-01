import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import com.rsbuddy.event.events.MessageEvent;
import com.rsbuddy.event.listeners.MessageListener;
import com.rsbuddy.event.listeners.PaintListener;
import com.rsbuddy.script.ActiveScript;
import com.rsbuddy.script.Manifest;
import com.rsbuddy.script.methods.Calculations;
import com.rsbuddy.script.methods.Camera;
import com.rsbuddy.script.methods.Combat;
import com.rsbuddy.script.methods.Game;
import com.rsbuddy.script.methods.GroundItems;
import com.rsbuddy.script.methods.Inventory;
import com.rsbuddy.script.methods.Mouse;
import com.rsbuddy.script.methods.Npcs;
import com.rsbuddy.script.methods.Players;
import com.rsbuddy.script.methods.Skills;
import com.rsbuddy.script.methods.Walking;
import com.rsbuddy.script.methods.Widgets;
import com.rsbuddy.script.task.LoopTask;
import com.rsbuddy.script.util.Filter;
import com.rsbuddy.script.util.Random;
import com.rsbuddy.script.util.Timer;
import com.rsbuddy.script.wrappers.Area;
import com.rsbuddy.script.wrappers.Component;
import com.rsbuddy.script.wrappers.GroundItem;
import com.rsbuddy.script.wrappers.Item;
import com.rsbuddy.script.wrappers.Npc;
import com.rsbuddy.script.wrappers.Tile;


@Manifest(authors={"Rapid"}, name="AutoBeefyBill", keywords={"combat"}, version=1.2, description="Kills cows, banks hides & beef if needed to")
public class AutoBeefyBill extends ActiveScript implements PaintListener, MouseListener, MessageListener{
	
	public ActiveScript parent = this;
	
	private GUI gui;
	
	private boolean Wait_For_GUI = true;
	
	private int[] Cow_ID = {12364, 12362, 12363};
	
	private Tile Original_Pos;
	
	private int Hide_ID = 1739;
	private int Beef_ID = 2132;
	private int Bone_ID = 526;
	
	private boolean Bury_Bones = false;
	
	private ArrayList<Integer> Pickup = new ArrayList<Integer>();
	
	private int Food_ID = 0;
	private int Eat_At = 150;
	
	private int Bill_ID = 246;
	
	private Tile Beefy_Bill_Location = new Tile(3178, 3321);
	
	private Area Lootable_Area = new Area(new Tile[]{new Tile(3155, 3348), new Tile(3153, 3345), new Tile(3153, 3335), new Tile(3154, 3333), new Tile(3154, 3330), new Tile(3154, 3329), new Tile(3153, 3328), new Tile(3153, 3327), new Tile(3152, 3325), new Tile(3152, 3323), new Tile(3153, 3321), new Tile(3153, 3319), new Tile(3154, 3317), new Tile(3154, 3316), new Tile(3156, 3314), new Tile(3160, 3314), new Tile(3165, 3318), new Tile(3168, 3318), new Tile(3171, 3316), new Tile(3178, 3316), new Tile(3181, 3314), new Tile(3184, 3314), new Tile(3189, 3310), new Tile(3192, 3308), new Tile(3194, 3308), new Tile(3196, 3307), new Tile(3198, 3307), new Tile(3200, 3308), new Tile(3201, 3308), new Tile(3203, 3309), new Tile(3207, 3309), new Tile(3209, 3308), new Tile(3211, 3308), new Tile(3210, 3310), new Tile(3210, 3312), new Tile(3210, 3315), new Tile(3202, 3324), new Tile(3202, 3326), new Tile(3201, 3328), new Tile(3201, 3333), new Tile(3200, 3334), new Tile(3197, 3334), new Tile(3195, 3334), new Tile(3193, 3335), new Tile(3186, 3340), new Tile(3180, 3343), new Tile(3177, 3347)});
	
	private boolean Loot_Within = false;
	private int Distance = 0;
	
	private boolean Range_Mode = false;
	private int Arrow_ID = -1;
	
	private class Inventory_Checker extends LoopTask{
		
		private int Prev_Hide_Count = 0;
		private int Prev_Beef_Count = 0;
		
		public int loop(){
			int Current_Beef_Count = 0;
			int Current_Hide_Count = 0;
			
			if (Game.getCurrentTab() != Game.TAB_INVENTORY){
				for (Item i : Inventory.getCachedItems()){
					if (i.getId() == Hide_ID)
						Current_Hide_Count++;
					else if (i.getId() == Beef_ID)
						Current_Beef_Count++;
				}
			}else{
				Current_Beef_Count = Inventory.getCount(Beef_ID);
				Current_Hide_Count = Inventory.getCount(Hide_ID);
			}
			
			if (Prev_Hide_Count < Current_Hide_Count)
				Hide_Count = Hide_Count + (Current_Hide_Count - Prev_Hide_Count);
			if (Prev_Beef_Count < Current_Beef_Count)
				Beef_Count = Beef_Count + (Current_Beef_Count - Prev_Beef_Count);
			
			Prev_Hide_Count = Current_Hide_Count;
			Prev_Beef_Count = Current_Beef_Count;
			
			return 1000;
		}
		
	}
	
	private class Anti extends LoopTask{
		
		/*
		 * Threaded camera which will move the camera in both pitch-wise and angle-wise
		 * which improves the power of the antiban
		 */
		private Pitch_Camera pitch_camera = new Pitch_Camera();
		private Angle_Camera angle_camera = new Angle_Camera();
		
		public boolean onStart(){
			//Adds the threaded camera classes to the container
			getContainer().submit(pitch_camera);
			getContainer().submit(angle_camera);
			return true;
		}
		
		public int loop() {
			int rnd = random(0, 10);
		
			if (rnd == 1){
				Camera.setCompassAngle(Camera.getCompassAngle() + random(0, 50));
				if (Camera.getPitch() > 50){
					Camera.setPitch(Camera.getPitch() - random(10, 25));
				}else{
					Camera.setPitch(Camera.getPitch() + random(10, 25));
				}
			}else if (rnd == 2){
				Camera.setCompassAngle(Camera.getCompassAngle() + random(50, 100));
				if (Camera.getPitch() > 50){
					Camera.setPitch(Camera.getPitch() - random(15, 35));
				}else{
					Camera.setPitch(Camera.getPitch() + random(15, 35));
				}
			}else if (rnd == 3){
				angle_camera.setChange(Camera.getCompassAngle() - random(50, 150));
				if (Camera.getPitch() > 50){
					pitch_camera.setChange(Camera.getPitch() - random(20, 30));
				}else{
					pitch_camera.setChange(Camera.getPitch() + random(20, 30));
				}
			}else if (rnd == 4){
				angle_camera.setChange(Camera.getCompassAngle() + random(100, 150));
				if (Camera.getPitch() > 50){
					pitch_camera.setChange(Camera.getPitch() - random(25, 40));
				}else{
					pitch_camera.setChange(Camera.getPitch() + random(25, 40));
				}
			}

			return random(10000, 15000);
		}
		
		private class Pitch_Camera extends LoopTask{

			private boolean Move = false;
			private int Dev = 0;
			
			private void setChange(int dev){
				Dev = dev;
				Move = true;
			}
			
			public int loop() {
				if (Move){
					sleep(random(100, 1000));
					log("Threaded Pitch Camera activated.");
					Camera.setPitch(Dev);
					Move = false;
				}
				return 100;
			}
			
			
			
		}
		
		private class Angle_Camera extends LoopTask{

			private boolean Move = false;
			private int Dev = 0;
			
			private void setChange(int dev){
				Dev = dev;
				Move = true;
			}
			
			public int loop() {
				if (Move){
					log("Threaded Angle Camera Activated.");
					Camera.setCompassAngle(Dev);
					Move = false;
				}
				return 100;
			}
			
		}
		
	}
	
	//onStart method which grabs user settings, experience in skills, player position for later use
	public boolean onStart(){
		log("Thank you for using AutoBeefyBill");
		while (!Game.isLoggedIn()){
			sleep(random(100, 250));
		}
		while (Original_Pos == null){
			Original_Pos = Players.getLocal().getLocation();
		}
		log("Player Location: (" + Original_Pos.getX() + "," + Original_Pos.getY() + ")");
		
		try{
	        EventQueue.invokeLater(new Runnable() {
	            public void run() {
	                gui = new GUI();
	                gui.setVisible(true);
	            }
	        });
		}catch (Exception e){
			log("GUI failed to load.");
			return false;
		}

		while (Wait_For_GUI){
			sleep(random(250, 300));
		}
		gui.setVisible(false);
		
		while (Start_Attack_Exp == -1){
			Start_Attack_Exp = Skills.getCurrentExp(Skills.ATTACK);
		}
		while (Start_Strength_Exp == -1){
			Start_Strength_Exp = Skills.getCurrentExp(Skills.STRENGTH);
		}
		while (Start_Defence_Exp == -1){
			Start_Defence_Exp = Skills.getCurrentExp(Skills.DEFENSE);
		}
		while (Start_Prayer_Exp == -1){
			Start_Prayer_Exp = Skills.getCurrentExp(Skills.PRAYER);
		}
		while (Start_Range_Exp == -1){
			Start_Range_Exp = Skills.getCurrentExp(Skills.RANGE);
		}
		Start_Time = System.currentTimeMillis();
		
		/*
		 * Instance of Inventory_Checker class, setup to scan inventory constantly for
		 * changes in Hide/Beef count (Most accurate way to log drops)
		 */
		Inventory_Checker inv = new Inventory_Checker();
		getContainer().submit(inv);
		
		/*
		 * Instance of Anti class, which is used as an anti-ban, only contains basic
		 * anti-ban such as camera movements. Later versions antiban will contain
		 * skill hovering etc, but this would have to be implemented into the main
		 * loop.
		 */
		Anti anti1 = new Anti();
		getContainer().submit(anti1);
		
		return true;
	}
	
	private void bank(){
		Component bank_hide = Widgets.get(236).getComponent(1);
		Component bank_beef = Widgets.get(451).getComponent(1);
		if (bank_hide.isValid()){
			if (bank_hide.click())
				sleep(random(1000, 1250));
			else
				sleep(random(750, 1000));
			
			if (Inventory.contains(Hide_ID) && Inventory.getCount(Hide_ID) > 1 || Inventory.contains(Beef_ID) && Inventory.getCount(Beef_ID) > 1)
					bank();
		}else if (bank_beef.isValid()){
			if (bank_beef.click())
				sleep(random(1000, 1250));
			else
				sleep(random(750, 1000));

			if (Inventory.contains(Hide_ID) && Inventory.getCount(Hide_ID) > 1 || Inventory.contains(Beef_ID) && Inventory.getCount(Beef_ID) > 1)
				bank();
		}else{
			if (Inventory.isItemSelected()){
				Npc bill = Npcs.getNearest(Bill_ID);
				if (bill != null){
					if (bill.isOnScreen() && Calculations.distanceTo(bill.getLocation()) < 5){
						if (bill.interact("Beefy Bill"))
							sleep(random(750, 1000));
						else
							sleep(random(750, 1000));
						
					}else{
						if (Walking.stepTowards(bill.getLocation()))
							sleep(random(750, 1000));
						else
							sleep(random(500, 750));
					}
				}
				
				bank();
			}else{
				if (Inventory.contains(Hide_ID) && Inventory.getCount(Hide_ID) > 1){
					Item Hide = Inventory.getItem(Hide_ID);
					if (Hide.interact("Use"))
						sleep(random(1250, 1500));
					else
						sleep(random(750, 1000));
					bank();
				}else if (Inventory.contains(Beef_ID) && Inventory.getCount(Beef_ID) > 1){
					Item Beef = Inventory.getItem(Beef_ID);
					if (Beef.interact("Use"))
						sleep(random(1250, 1500));
					else
						sleep(random(750, 1000));
					bank();
				}
			}
		}
	}
	
	private int random(int min, int max){
		return Random.nextInt(min, max);
	}
	
	private boolean isDestinationOkay(){
		Tile dest = Walking.getDestination();
		if (dest != null && !Lootable_Area.contains(dest))
			return false;
		return true;
	}
	
	private boolean isCombatNPC(int id){
		for (int i : Cow_ID){
			if (i == id)
				return true;
		}
		return false;
	}
	
	private boolean isInCombat(){
		if (Players.getLocal().isInCombat() || Players.getLocal().getInteracting() != null)
			return true;
		else{
			for (Npc i : Npcs.getLoaded()){
				if (!isCombatNPC(i.getId()))
					continue;
				if (i.getInteracting() != null && i.getInteracting().equals(Players.getLocal()))
					return true;
			}
			return false;
		}
	}
	
	public int loop(){
		
		if (!Game.isLoggedIn() || Game.isWelcomeShowing()){
			log("Not logged in, sleeping...");
			return random(7500, 10000);
		}
		
		if (Range_Mode && Inventory.contains(Arrow_ID)){
			Item arrows = Inventory.getItem(Arrow_ID);
			if (arrows != null){
				if (arrows.interact("Wield"))
					sleep(random(750, 1000));
				else
					sleep(random(500, 750));
			}
		}
		
		if (Inventory.contains(Bone_ID)){
			Item bones = Inventory.getItem(Bone_ID);
			if (bones != null){
				String action;
				if (Bury_Bones)
					action = "Bury";
				else
					action = "Drop";
				if (bones.interact(action)){
					sleep(random(750, 1000));
				}else{
					sleep(random(500, 750));
				}
			}
		}
		
		if (Players.getLocal().isMoving() && isDestinationOkay())
			return random(100, 250);
		
		if (Walking.getEnergy() > 30 && !Walking.isRunEnabled()){
			Walking.setRun(true);
			sleep(random(500, 750));
		}
		
		if (!Inventory.contains(Food_ID) && Combat.getLifePoints() < Eat_At){
			log("Out of food, stopping...");
			stop();
		}else{
			if (Inventory.isFull() && (Inventory.contains(Hide_ID) || Inventory.contains(Beef_ID))){
				Npc bill = Npcs.getNearest(Bill_ID);
				if (bill != null){
					if (bill.isOnScreen()){
						bank();
					}else{
						if (Walking.stepTowards(bill.getLocation()))
							sleep(random(1000, 1250));
						else
							sleep(random(750, 1000));
					}
				}else{
					if (Walking.stepTowards(Beefy_Bill_Location.randomize(2, 2)))
						sleep(random(1000, 1250));
					else
						sleep(random(750, 1000));
				}
			}else{
				if (isInCombat()){
					
					if (Combat.getLifePoints() < Eat_At){
						Item food = Inventory.getItem(Food_ID);
						if (food != null){
							if (food.interact("Eat"))
								sleep(random(1000, 1250));
							else
								sleep(random(750, 1000));
						}
					}
					
					return random(1250, 1500);
				}else{
					
					if (Players.getLocal().isInCombat()){
						log("Sleeping due to still being in combat. NPC might have died.");
						sleep(random(1250, 1500));
					}
					
					GroundItem item = GroundItems.getNearest(new Filter<GroundItem>(){
						public boolean accept(GroundItem i) {
							if (Lootable_Area.contains(i.getLocation())){
								if (Range_Mode && Arrow_ID != -1 && i.getItem().getId() == Arrow_ID){
									return i.getItem().getStackSize() > 5;
								}else{
									if (Pickup.contains(i.getItem().getId())){
										if (Loot_Within)
											return Calculations.distanceTo(i.getLocation()) <= Distance;
									}
								}
							}
							return false;
						}
						
					});
					if (item != null){
						if (item.isOnScreen()){
							if (item.interact("Take " + item.getItem().getName()))
								sleep(random(1000, 1250));
							else
								sleep(random(750, 1000));
						}else{
							if (Walking.stepTowards(item.getLocation().randomize(2, 2))){
								sleep(random(1000, 1250));
							}else{
								sleep(random(500, 750));
							}
						}
					}else{
						Npc cow = Npcs.getNearest(new Filter<Npc>(){
								
							public boolean accept(Npc n) {
								for (int i : Cow_ID){
									if (n.getId() == i){
										if (n.getInteracting() == null && !n.isInCombat())
											return true;
										else
											return false;
									}
								}
								return false;
							}
							
						});
						
						if (cow != null){
							if (cow.isOnScreen()){
								if (cow.interact("Attack " + cow.getName())){
									sleep(random(750, 1000));
								}else{
									sleep(random(500, 750));
								}
							}else{
								if (Walking.stepTowards(cow.getLocation().randomize(2, 2))){
									sleep(random(1000, 1250));
								}else{
									sleep(random(750, 1000));
								}
							}
						}else{
							if (!Calculations.isTileOnScreen(Original_Pos)){
								log("Walking back to original position");
								if (Walking.stepTowards(Original_Pos.randomize(5, 5))){
									sleep(random(750, 1000));
								}else{
									sleep(random(500, 750));
								}
							}
						}
					}
				}
			}
		}
		
		return random(500, 700);
	}
	
	public void onFinish(){
		
	}
	
	private Double getVersion(){
		return 1.2;
	}
	
	public void mouseClicked(MouseEvent arg0) {

	}

	public void mouseEntered(MouseEvent arg0) {

	}

	public void mouseExited(MouseEvent arg0) {

	}

	public void mousePressed(MouseEvent arg0) {
		Rectangle Combat = new Rectangle(253, 37, 128, 17);
		Rectangle General = new Rectangle(35, 37, 123, 18);
		if (Combat.contains(new Point(arg0.getX(), arg0.getY()))){
			if (Draw_Combat)
				Draw_Combat = false;
			else
				Draw_Combat = true;
		}else if (General.contains(new Point(arg0.getX(), arg0.getY()))){
			if (Draw_General)
				Draw_General = false;
			else
				Draw_General = true;
		}
	}

	public void mouseReleased(MouseEvent arg0) {

	}
	
	private Long Start_Time;
	
	private int Start_Attack_Exp = -1;
	private int Start_Strength_Exp = -1;
	private int Start_Defence_Exp = -1;
	private int Start_Prayer_Exp = -1;
	private int Start_Range_Exp = -1;
	
	private int Hide_Count = 0;
	private int Beef_Count = 0;
	
    private Image getImage(String url) {
        try {
            return ImageIO.read(new URL(url));
        } catch(IOException e) {
            return null;
        }
    }
    
    private float perHourExp(Long elapsed, float count){
    	if (count == 0 || elapsed == 0)
    		return 0;
    	elapsed = elapsed / 1000;
    	count = (count / elapsed);
    	count = (count * 60) * 60;
    	return count;
    }
    
    private float perHour(Long elapsed, float count){
    	if (count == 0 || elapsed == 0)
    		return 0;
    	elapsed = elapsed / 1000;
    	count = (count / elapsed);
    	count = (count * 60) * 60;
    	return Math.round(count);
    }
    
    private final Color color1 = new Color(0, 0, 0);

    private final BasicStroke stroke1 = new BasicStroke(1);

    private final Font font1 = new Font("Arial", 1, 20);
    private final Font font2 = new Font("Arial", 1, 12);
    private final Font font3 = new Font("Arial", 0, 12);
    private final Font font4 = new Font("Arial", 0, 13);

    private final Image img1 = getImage("http://images3.wikia.nocookie.net/__cb20101211075644/runescape/images/1/1f/Cow_detail.png");
    private final Image img2 = getImage("http://images.wikia.com/runescape/images/1/1d/Cowhide.png");
    private final Image img3 = getImage("http://images2.wikia.nocookie.net/__cb20101004040634/runescape/images/5/58/Raw_meat.png");
    private final Image img4 = getImage("http://images2.wikia.nocookie.net/__cb20110305042726/runescape/images/5/51/Attack-icon.png");
    private final Image img5 = getImage("http://images.wikia.com/runescape/images/3/3e/Strength-icon.png");
    private final Image img6 = getImage("http://images1.wikia.nocookie.net/__cb20110305043012/runescape/images/d/d8/Defence-icon.png");
    private final Image img7 = getImage("http://images.wikia.com/runescape/images/7/72/Ranged-icon.png");
    private final Image img8 = getImage("http://images.wikia.com/runescape/images/2/24/Prayer-icon.png");

    private boolean Draw_General = true;
    private boolean Draw_Combat = true;
    
    public void onRepaint(Graphics g1) {
        Graphics2D g = (Graphics2D)g1;
        
        try{
        
	        g.setFont(font1);
	        g.setColor(color1);
	        
	        Point Mouse_Point = Mouse.getLocation();
	        int x = (int) Mouse_Point.getX();
	        int y = (int) Mouse_Point.getY();
	        
	        g.drawLine(x, y, x + 5000, y);
	        g.drawLine(x, y, x, y + 5000);
	        g.drawLine(x, y, x - 5000, y);
	        g.drawLine(x, y, x, y - 5000);
	        
	        g.drawString("AutoBeefyBill", 20, 30);
	        g.drawImage(img1, 160, 5, null);
	        g.setFont(font2);
	        g.drawString("Combat Information:", 260, 50);
	        g.drawString("General Information:", 40, 50);
	        g.setFont(font3);
	        
	        if (Draw_General){
		        g.drawString("Runtime: " + Timer.format(System.currentTimeMillis() - Start_Time), 60, 75);
		        g.drawImage(img2, 40, 85, null);
		        g.drawImage(img3, 40, 130, null);
		        g.setStroke(stroke1);
		        g.drawString("x " + Hide_Count, 80, 100);
		        g.drawString("x " + Beef_Count, 80, 140);
		        g.drawString(perHour((System.currentTimeMillis() - Start_Time), Hide_Count) + " Per Hour", 100, 113);
		        g.drawString(perHour((System.currentTimeMillis() - Start_Time), Beef_Count) + " Per Hour", 100, 154);
	        }
	        
	        if (Draw_Combat){
		        g.drawImage(img4, 260, 70, null);
		        g.drawString("x " + (Skills.getCurrentExp(Skills.ATTACK) - Start_Attack_Exp), 290, 80);
		        g.drawImage(img5, 260, 100, null);
		        g.drawImage(img6, 260, 130, null);
		        g.drawString("x " + (Skills.getCurrentExp(Skills.STRENGTH) - Start_Strength_Exp), 290, 110);
		        g.drawString("x " + (Skills.getCurrentExp(Skills.DEFENSE) - Start_Defence_Exp), 290, 140);
		        g.drawString(perHourExp((System.currentTimeMillis() - Start_Time), (Skills.getCurrentExp(Skills.ATTACK) - Start_Attack_Exp)) + " Per Hour", 315, 95);
		        g.drawString(perHourExp((System.currentTimeMillis() - Start_Time), (Skills.getCurrentExp(Skills.STRENGTH) - Start_Strength_Exp)) + " Per Hour", 315, 125);
		        g.drawString(perHourExp((System.currentTimeMillis() - Start_Time), (Skills.getCurrentExp(Skills.DEFENSE) - Start_Defence_Exp)) + " Per Hour", 315, 155);
		        g.drawImage(img7, 255, 160, null);
		        g.drawString("x " + (Skills.getCurrentExp(Skills.RANGE) - Start_Range_Exp), 285, 170);
		        g.drawString(perHourExp((System.currentTimeMillis() - Start_Time), (Skills.getCurrentExp(Skills.RANGE) - Start_Range_Exp)) + " Per Hour", 315, 185);
		        g.drawImage(img8, 255, 190, null);
		        g.drawString("x " + (Skills.getCurrentExp(Skills.PRAYER) - Start_Prayer_Exp), 285, 200);
		        g.drawString(perHourExp((System.currentTimeMillis() - Start_Time), (Skills.getCurrentExp(Skills.PRAYER) - Start_Prayer_Exp)) + " Per Hour", 315, 215);
	        }
	        
	        g.setFont(font2);
	        g.drawString("By rapid", 465, 330);
	        g.drawString("v " + getVersion(), 260, 30);
	        
	        g.drawRect(253, 37, 128, 17);
	        g.drawRect(35, 37, 123, 18);
	        
        }catch(Exception e){
        	
        }
    }
    
    public class GUI extends JFrame {
    	public GUI() {
    		initComponents();
    	}

    	private void button1ActionPerformed(ActionEvent e) {
			try{
				Eat_At = Integer.parseInt(txtHeal.getText());
				
				if (txtId.getText() == null || txtId.getText() == "")
					Food_ID = -1;
				else
					Food_ID = Integer.parseInt(txtId.getText());
				
				Loot_Within = checkDis.isSelected();
				if (Loot_Within){
					if (txtDis.getText() == null || txtDis.getText() == ""){
						Loot_Within = false;
					}else{
						Distance = Integer.parseInt(txtDis.getText());
					}
				}
				
				if (comboStyle.getSelectedItem().toString() == "Range"){
					if (checkArrow.isSelected()){
						Range_Mode = true;
						Arrow_ID = Integer.parseInt(txtArrowId.getText());
					}
				}
				
				if (checkHide.isSelected())
					Pickup.add(Hide_ID);
				
				if (checkBeef.isSelected())
					Pickup.add(Beef_ID);
				
				if (checkBones.isSelected()){
					Bury_Bones = true;
					Pickup.add(Bone_ID);
				}
				
				log("Successfully setup");
				
				Wait_For_GUI = false;
					
			}catch(Exception e1){
				log("Error with setting up. Please make sure you input the data correctly.");
			}
		}

    	private void checkBox1ActionPerformed(ActionEvent e) {
    		if (checkDis.isSelected()){
    			txtDis.setEnabled(true);
    		}else{
    			txtDis.setEnabled(false);
    		}
    	}

    	private void checkArrowActionPerformed(ActionEvent e) {
    		if (checkArrow.isSelected()){
    			txtArrowId.setEnabled(true);
    		}else{
    			txtArrowId.setEnabled(false);
    		}
    	}

    	private void comboStyleActionPerformed(ActionEvent e) {
    		if (comboStyle.getSelectedItem().toString() == "Range"){
    			checkArrow.setEnabled(true);
    			txtArrowId.setEnabled(true);
    		}else{
    			checkArrow.setEnabled(false);
    			checkArrow.setSelected(false);
    			txtArrowId.setEnabled(false);
    		}
    	}

    	private void initComponents() {
    		label1 = new JLabel();
    		checkHide = new JCheckBox();
    		label2 = new JLabel();
    		txtId = new JTextField();
    		label3 = new JLabel();
    		label4 = new JLabel();
    		txtHeal = new JTextField();
    		checkBeef = new JCheckBox();
    		checkBones = new JCheckBox();
    		button1 = new JButton();
    		checkDis = new JCheckBox();
    		txtDis = new JTextField();
    		label5 = new JLabel();
    		label6 = new JLabel();
    		label7 = new JLabel();
    		comboStyle = new JComboBox();
    		checkArrow = new JCheckBox();
    		txtArrowId = new JTextField();
    		
    		txtDis.setEnabled(false);

    		setTitle("AutoBeefyBill");
    		Container contentPane = getContentPane();
    		contentPane.setLayout(null);

    		label1.setText("Loot Options:");
    		label1.setFont(label1.getFont().deriveFont(label1.getFont().getStyle() | Font.BOLD));
    		contentPane.add(label1);
    		label1.setBounds(new Rectangle(new Point(10, 144), label1.getPreferredSize()));

    		checkHide.setText("Pick up Hides?");
    		contentPane.add(checkHide);
    		checkHide.setBounds(new Rectangle(new Point(20, 229), checkHide.getPreferredSize()));

    		label2.setText("Healing Options:");
    		label2.setFont(label2.getFont().deriveFont(label2.getFont().getStyle() | Font.BOLD));
    		contentPane.add(label2);
    		label2.setBounds(new Rectangle(new Point(10, 79), label2.getPreferredSize()));
    		contentPane.add(txtId);
    		txtId.setBounds(15, 113, 100, txtId.getPreferredSize().height);

    		label3.setText("Food ID:");
    		contentPane.add(label3);
    		label3.setBounds(new Rectangle(new Point(17, 99), label3.getPreferredSize()));

    		label4.setText("Heal at:");
    		contentPane.add(label4);
    		label4.setBounds(new Rectangle(new Point(126, 99), label4.getPreferredSize()));
    		contentPane.add(txtHeal);
    		txtHeal.setBounds(125, 114, 50, txtHeal.getPreferredSize().height);

    		checkBeef.setText("Pick up Beef?");
    		contentPane.add(checkBeef);
    		checkBeef.setBounds(new Rectangle(new Point(20, 254), checkBeef.getPreferredSize()));

    		checkBones.setText("Pick up & bury Bones?");
    		contentPane.add(checkBones);
    		checkBones.setBounds(new Rectangle(new Point(20, 279), checkBones.getPreferredSize()));
    		button1.setText("Start");
    		button1.addActionListener(new ActionListener() {
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				button1ActionPerformed(e);
    			}
    		});
    		contentPane.add(button1);
    		button1.setBounds(20, 310, 165, 30);

    		checkDis.setText("Loot within 'x' Tiles");
    		checkDis.addActionListener(new ActionListener() {
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				checkBox1ActionPerformed(e);
    			}
    		});
    		contentPane.add(checkDis);
    		checkDis.setBounds(new Rectangle(new Point(15, 164), checkDis.getPreferredSize()));
    		contentPane.add(txtDis);
    		txtDis.setBounds(35, 204, 55, txtDis.getPreferredSize().height);

    		label5.setText("Max Distance:");
    		contentPane.add(label5);
    		label5.setBounds(new Rectangle(new Point(35, 189), label5.getPreferredSize()));

    		label6.setText("Combat Options:");
    		label6.setFont(label6.getFont().deriveFont(label6.getFont().getStyle() | Font.BOLD));
    		contentPane.add(label6);
    		label6.setBounds(new Rectangle(new Point(10, 10), label6.getPreferredSize()));

    		label7.setText("Style:");
    		contentPane.add(label7);
    		label7.setBounds(new Rectangle(new Point(20, 30), label7.getPreferredSize()));

    		comboStyle.setModel(new DefaultComboBoxModel(new String[] {
    			"Melee",
    			"Range"
    		}));
    		comboStyle.addActionListener(new ActionListener() {

    			public void actionPerformed(ActionEvent e) {
    				comboStyleActionPerformed(e);
    			}
    		});
    		contentPane.add(comboStyle);
    		comboStyle.setBounds(new Rectangle(new Point(25, 50), comboStyle.getPreferredSize()));

    		checkArrow.setText("Pick up arrows?");
    		checkArrow.setEnabled(false);
    		checkArrow.addActionListener(new ActionListener() {
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				checkArrowActionPerformed(e);
    			}
    		});
    		contentPane.add(checkArrow);
    		checkArrow.setBounds(new Rectangle(new Point(100, 30), checkArrow.getPreferredSize()));

    		txtArrowId.setText("Arrow Id");
    		txtArrowId.setEnabled(false);
    		contentPane.add(txtArrowId);
    		txtArrowId.setBounds(120, 55, 55, txtArrowId.getPreferredSize().height);

    		contentPane.setPreferredSize(new Dimension(220, 385));
    		setSize(220, 385);
    		setLocationRelativeTo(getOwner());
    	}

    	private JLabel label1;
    	private JCheckBox checkHide;
    	private JLabel label2;
    	private JTextField txtId;
    	private JLabel label3;
    	private JLabel label4;
    	private JTextField txtHeal;
    	private JCheckBox checkBeef;
    	private JCheckBox checkBones;
    	private JButton button1;
    	private JCheckBox checkDis;
    	private JTextField txtDis;
    	private JLabel label5;
    	private JLabel label6;
    	private JLabel label7;
    	private JComboBox comboStyle;
    	private JCheckBox checkArrow;
    	private JTextField txtArrowId;
    }

	public void messageReceived(MessageEvent m) {
		if (m.getMessage().contains("ammo")){
			log("Out of arrows. Stopping...");
			stop();
		}
	}
}
