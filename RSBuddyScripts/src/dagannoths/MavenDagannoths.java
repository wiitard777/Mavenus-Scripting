package dagannoths;

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

//Allow me to apologize ahead of time for the terrible organization of the script.
//it is after all my first.

@Manifest(authors = { "Kratos" }, name = "Maven Dagannoths", keywords = {
		"money making", "summoning", "combat" }, version = 2.1, description = "Start at the Dagannoth Lighthouse with guthans or b2p tabs")
public class MavenDagannoths extends ActiveScript implements PaintListener,
		MouseListener, MouseMotionListener {

	public void mouseClicked(MouseEvent e) {

		int x = e.getX();
		int y = e.getY();
		if (x >= 0 && x < 0 + 100 && y >= 0 && y < 0 + 20) {
			if (showhide) {
				showhide = false;
			} else {
				showhide = true;
			}
		} else if (x >= 110 && x < 110 + 100 && y >= 0 && y < 0 + 20
				&& showhide) {

			if (skillsloot == 0) {
				skillsloot = 1;
			} else if (skillsloot == 1) {
				skillsloot = 2;
			} else if (skillsloot == 2) {
				skillsloot = 0;
			}

		} else if (x >= 230 && x < 230 + 100 && y >= 0 && y < 0 + 20
				&& showhide) {
			try {
				SwingUtilities.invokeAndWait(new Runnable() {
					public void run() {
						gui = new DagannothsGUI();
						gui.setVisible(true);

					}
				});
			} catch (InterruptedException e1) {
			} catch (InvocationTargetException e1) {
			}
			while (gui.isVisible()) {
				sleep(50);
				status = "GUI is Open";
			}
		}

	}

	private final RenderingHints antialiasing = new RenderingHints(
			RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	Skill attack;
	Skill defence;
	Skill strength;
	Skill ranged;
	Skill constitution;
	static Skill[] skills;


	public void onRepaint(Graphics g1) {
		Graphics2D g = (Graphics2D) g1;
		g.setRenderingHints(antialiasing);

		long millis = System.currentTimeMillis() - startTime;
		long hours = millis / (1000 * 60 * 60);
		millis -= hours * (1000 * 60 * 60);
		long minutes = millis / (1000 * 60);
		String mins = "" + minutes;
		while (mins.length() < 2) {
			mins = "0" + mins;
		}
		millis -= minutes * (1000 * 60);
		long seconds = millis / 1000;
		String secs = "" + seconds;
		while (secs.length() < 2) {
			secs = "0" + secs;
		}
		g.setColor(color1);
		g.fillRect(0, 0, 100, 20);
		g.setColor(color4);
		g.setFont(font2);
		g.drawString("Show/Hide", 10, 15);
		

		g.setColor(color1);
		if (showhide) {

			if (attack.xpGained()> 0&&!addSkills.contains(attack)){
				addSkills.add(attack);
			}
			else if (strength.xpGained()>0&&!addSkills.contains(strength)){
				addSkills.add(strength);
			}else if (ranged.xpGained()>0 &&!addSkills.contains(ranged)){
				addSkills.add(ranged);
			}else if (defence.xpGained()>0&&!addSkills.contains(defence)){
				addSkills.add(defence);
			}else if (constitution.xpGained()>0 &&!addSkills.contains(constitution)){
				addSkills.add(constitution);
			}
			
			g.fillRect(110, 0, 100, 20);
			g.fillRect(230, 0, 100, 20);
			g.fillRect(5, 344, 68, 129);
			g.setColor(color4);
			g.drawString("Open GUI", 240, 15);
			g.setColor(color2);
			g.setStroke(stroke1);
			g.drawRect(5, 344, 68, 129);
			g.setColor(color3);
			g.fillRect(73, 344, 441, 129);
			g.setColor(color2);
			g.drawRect(73, 344, 441, 129);
			g.drawImage(img1, 0, 338, null);
			g.drawImage(img7, 10, 252, null);

			if (skillsloot == 0) {

				g.setColor(color4);
				g.drawString("Overview", 120, 15);
				g.drawImage(img3, 70, 383, null);

				g.drawImage(img4, 82, 348, null);

				g.drawImage(img5, 78, 427, null);
				totalExp = 0;
				totalPerHour = 0;
				if (!gui.isVisible()) {

					for (Skill i : addSkills) {

						totalExp += i.xpGained();
						totalPerHour += i.xpPH();
					}

				}
				if (Game.getCurrentTab() == 4) {
					
					
			
				}
				g.drawString("Exp Gained: " + totalExp + " (" + totalPerHour
						+ "/Hour)", 130, 368);
				g.drawString("Total seeds gained: " + totalseeds, 130, 408);
				g.drawString("Total charms gained: " + totalcharms, 130, 448);
				g.setFont(font3);
				g.drawString("Time Running: " + hours + ":" + minutes + ":"
						+ seconds, 390, 368);
				g.drawString("Status: " + status, 390, 378);

			} else if (skillsloot == 1) {

				g.setColor(color4);
				g.setFont(font2);
				g.drawString("Skills", 120, 15);
				g.drawImage(img4, 82, 348, null);
				g.setFont(font3);
				if (!gui.isVisible()) {
					skillExpCount = 0;
					for (Skill i : addSkills) {
						skillExpCount++;
						if (skillExpCount == 1) {
							i.drawSkill(g, firstcolumn, topLine);
						} else if (skillExpCount == 2) {
							i.drawSkill(g, firstcolumn, secLine);
						} else if (skillExpCount == 3) {
							i.drawSkill(g, firstcolumn, triLine);
						} else if (skillExpCount == 4) {
							i.drawSkill(g, firstcolumn, quaLine);
						} else if (skillExpCount == 5) {
							i.drawSkill(g, firstcolumn, quiLine);
						}
					}
				}

			} else if (skillsloot == 2) {
				g.setColor(color4);
				g.setFont(font2);
				g.drawString("Loot", 120, 15);
				g.drawImage(img3, 70, 342, null);
				g.drawImage(img5, 300, 348, null);
				g.setFont(font3);
				if (Game.getCurrentTab() == 4) {
				if (torstol.getGained() > 0 && !addLoot.contains(torstol))
					addLoot.add(torstol);
				else if (snapdragon.getGained() > 0 && !addLoot.contains(snapdragon))
					addLoot.add(snapdragon);
				else if (lantadyme.getGained() > 0 && !addLoot.contains(lantadyme))
					addLoot.add(lantadyme);
				else if (dwarfweed.getGained() > 0 &&!addLoot.contains(dwarfweed))
					addLoot.add(dwarfweed);
				else if (avantoe.getGained() > 0 && !addLoot.contains(avantoe))
					addLoot.add(avantoe);
				else if (toadflax.getGained() > 0 && !addLoot.contains(toadflax))
					addLoot.add(toadflax);
				else if (goldcharm.getGained() > 0 && !addLoot.contains(goldcharm))
					addLoot.add(goldcharm);
				else if (greencharm.getGained() > 0 && !addLoot.contains(greencharm))
					addLoot.add(greencharm);
				else if (crimsoncharm.getGained() > 0 && !addLoot.contains(crimsoncharm))
					addLoot.add(crimsoncharm);
				else if (bluecharm.getGained() > 0 && !addLoot.contains(bluecharm))
					addLoot.add(bluecharm);
				else if (effigy.getGained() > 0 && !addLoot.contains(effigy))
					addLoot.add(effigy);
				else if (seaweed.getGained() > 0 && !addLoot.contains(seaweed))
					addLoot.add(seaweed);
					itemPickCount = 0;
					for (ItemPaint s: addLoot){
						itemPickCount++;
						if (itemPickCount == 1)
							s.drawItem(g, firstcolumn, topLine);
						else if (itemPickCount == 2)
							s.drawItem(g, firstcolumn, secLine);
						else if (itemPickCount == 3)
							s.drawItem(g, firstcolumn, triLine);
						else if (itemPickCount == 4)
							s.drawItem(g, firstcolumn, quaLine);
						else if (itemPickCount == 5)
							s.drawItem(g, secondcolumn, topLine);
						else if (itemPickCount == 6)
							s.drawItem(g, secondcolumn, secLine);
						else if (itemPickCount == 7)
							s.drawItem(g, secondcolumn, triLine);
						else if (itemPickCount == 8)
							s.drawItem(g, secondcolumn, quaLine);
						else if (itemPickCount == 9)
							s.drawItem(g, thirdcolumn, topLine);
						else if (itemPickCount == 10)
							s.drawItem(g, thirdcolumn, secLine);
						else if (itemPickCount == 11)
							s.drawItem(g, thirdcolumn, triLine);
						else if (itemPickCount == 12)
							s.drawItem(g, thirdcolumn, quaLine);
						
					}
					totalseeds = 0;
					totalProfit = 0;
					totalProfitHour = 0;
					for (ItemPaint s: addLoot){
						totalseeds += s.getGained();
						
					}
					
				}

			}
		}

	}

	private final Color color1 = new Color(153, 153, 153, 221);
	private final Color color2 = new Color(0, 0, 0);
	private final Color color3 = new Color(153, 153, 153);
	private final Color color4 = new Color(0, 0, 200);

	private final BasicStroke stroke1 = new BasicStroke(1);

	private final Font font2 = new Font("Arial", 0, 16);
	private final Font font3 = new Font("Arial", 0, 10);

	private final Image img1 = getImage("http://images-mediawiki-sites.thefullwiki.org/09/4/0/8/60913171951238702.png");
	
	private final Image img3 = getImage("http://www.runevillage.com/whatis2/coins.gif");
	private final Image img4 = getImage("http://images.wikia.com/runescape/images/b/bc/XP_Counter_icon.png");
	private final Image img5 = getImage("http://images.wikia.com/runescape/nl/images/1/1a/Gold_charm.gif");
	
	private final Image img7 = getImage("http://img715.imageshack.us/img715/1885/cooltext533574308.png");

	public int totalExp = 0;
	public int totalProfit = 0;
	public int totalProfitHour = 0;
	public int itemPickCount;
	public int totalPerHour = 0;
	public boolean showhide = true;
	public boolean guiIsComplete = true;
	public boolean potting = true;
	public int skillsloot = 0;
	public boolean hasAttack = true;
	public boolean hasStrength = true;
	public boolean guthansequipped = false;
	public static boolean guthanEquip = false;
	public static boolean guthanDequip = false;
	public boolean banking = false;
	public static boolean lootingToad = false;
	public static boolean lootingAvan = false;
	public static boolean lootingTorst = false;
	public static boolean lootingSnap = false;
	public static boolean lootingDwarf = false;
	public static boolean lootingLant = false;
	public static boolean lootingGold = false;
	public static boolean lootingGreen = false;
	public static boolean lootingCrim = false;
	public static boolean lootingBlue = false;
	public static boolean lootingEffi = false;
	public static int topLine = 400;
	public static int secLine = 420;
	public static int triLine = 440;
	public static int quaLine = 460;
	public static int quiLine = 480;
	public static int sexLine = 500;
	public static int firstcolumn = 80;
	public static int secondcolumn = 200;
	public static int thirdcolumn = 320;
	int skillExpCount = 0;

	public static boolean guthansIsOn = false;
	public static boolean peachesIsOn = false;
	public static boolean unnotingIsOn = false;
	public static boolean walkingTo = false;
	public static boolean walkingFrom = false;
	public static boolean selectiveAttack = false;
	public static int setMouseSpeed;
	public boolean shieldInUse = false;
	public boolean helmequip = false;
	public boolean spearequip = false;
	public boolean chestequip = false;
	public boolean skirtequip = false;
	public boolean isInCombat;
	public String status = "GUI is Open";
	public long startTime;
	public static int invoSpace, invoSpace1;
	public static int test1 = 0;
	public static int test2 = 0;
	public int torstolStart, torstolGained, torstolPrice, snapStart,
			snapGained, snapPrice, lantStart, lantGained, lantPrice,
			dwarfStart, dwarfGained, dwarfPrice, goldGained, goldStart,
			greenGained, greenStart, blueGained, blueStart, crimsonGained,
			crimsonStart, avanStart, avanGained, toadflaxStart, toadflaxGained,
			effigyStart, effigyGained;
	public int totalseeds, totalcharms;
	public Tile Location2, Location3;
	public final static int ladderId = 4413;
	public int[] food = { 6883, 361, 373, 7946, 385, 329, 379 };
	public static int[] notedFood = { 362, 374, 7947, 386, 330, 380 };
	public Item chest, helm, legs, weapon, shield;
	public static int chestId;
	public static int helmId;
	public static int legsId;
	public static int weaponId;
	public static int shieldId;
	public DagannothsGUI gui;
	public final static int[] ATTACK = { 2436, 149, 147, 145, 15308, 15309,
			15310, 15311 };
	public final static int[] STRENGTH = { 2440, 161, 159, 157, 15312, 15313,
			15314, 15315 };
	public final static int[] DEFENCE = { 2442, 167, 165, 163, 15316, 15317,
			15318, 15319 };
	public final static int[] POTIONS = { 2436, 149, 147, 145, 15308, 15309,
			15310, 15311, 2440, 161, 159, 157, 15312, 15313, 15314, 15315,
			2442, 167, 165, 163, 15316, 15317, 15318, 15319 };
	public static int[] LOOTITEMS;
	public static int[] JUNKITEMS;
	public static ArrayList<Integer> WANTJUNK = new ArrayList<Integer>();
	public static ArrayList<Integer> WANTLOOT = new ArrayList<Integer>();
	public static ArrayList<Actions> actions = new ArrayList<Actions>();
	public static ArrayList<Skill> addSkills = new ArrayList<Skill>();
	public static ArrayList<ItemPaint> addLoot = new ArrayList<ItemPaint>();
	public final static int[] LOOTABLES = { 5300, 5304, 5303, 5302, 12158,
			12160, 12159, 12163 };

	public final static int[] JUNKSTUFF = { 229, 301, 1237, 1351, 1239, 1243,
			1249, 5280, 5297, 5281, 5106, 5301, 5299, 555, 1452, 1623, 1621,
			1619, 1617, 886, 828, 830, 311, 313, 314, 327, 345, 402, 413, 46,
			359, 377, 45, 405, 987, 985, 2366 };
	public final static int pouch = 12029;
	public final static int pouchNote = 12030;
	public final static int[] sumPot = { 12140, 12142, 12144, 12146 };
	public final static int notePot = 12141;
	public int tablets = 8015;
	public int bones = 526;
	public static boolean firstGUI = true;
	public final static int[] guthanshelm = { 4724, 4904, 4905, 4906, 4907 };
	public final static int[] guthansspear = { 4726, 4910, 4911, 4912, 4913 };
	public final static int[] guthansbody = { 4728, 4916, 4917, 4918, 4919 };
	public final static int[] guthansskirt = { 4730, 4922, 4923, 4924, 4925 };
	public final static Tile tilenw = new Tile(2505, 3645);
	public final static Tile tilese = new Tile(2512, 3636);
	public static Area topLadder = new Area(2510, 10006, 2517, 10002);
	public static Area pastDoor = new Area(2522, 9987, 2507, 10002);
	public static Area midFloor = new Area(tilenw, tilese);
	public Area topFloor = new Area(tilenw, tilese);
	public static Area dagArea = new Area(2500, 10040, 2540, 10006);
	public Area daemon;
	public Area barbOut;
	public Area obstacle1;
	public Area obstacle2;
	public Area obstacle3;
	public Area obstacle4;
	public Area obstacle5;
	public Area outside;
	dagannoths.Equipment equips = new dagannoths.Equipment();
	public Timer antibanTimer;
	public Timer skillCheck;
	public int actionsPerformed = 0;
	Tile[] varTiles = { new Tile(3205, 3437), new Tile(3205, 3438),
			new Tile(3205, 3439), new Tile(3204, 3440), new Tile(3204, 3439),
			new Tile(3205, 3436), new Tile(3205, 3435), new Tile(3205, 3434),
			new Tile(3205, 3433), new Tile(3205, 3432), new Tile(3205, 3431),
			new Tile(3205, 3430), new Tile(3205, 3429), new Tile(3205, 3428),
			new Tile(3206, 3427), new Tile(3207, 3426), new Tile(3207, 3425),
			new Tile(3207, 3424), new Tile(3206, 3424), new Tile(3205, 3424),
			new Tile(3204, 3424), new Tile(3204, 3425), new Tile(3204, 3426),
			new Tile(3208, 3424), new Tile(3208, 3423), new Tile(3208, 3422),
			new Tile(3209, 3421), new Tile(3210, 3421), new Tile(3211, 3421),
			new Tile(3212, 3421), new Tile(3213, 3421), new Tile(3214, 3421),
			new Tile(3215, 3421), new Tile(3216, 3422), new Tile(3217, 3423),
			new Tile(3218, 3423), new Tile(3219, 3422), new Tile(3220, 3422),
			new Tile(3221, 3421), new Tile(3221, 3420), new Tile(3221, 3419),
			new Tile(3222, 3418), new Tile(3223, 3418), new Tile(3224, 3418),
			new Tile(3225, 3418), new Tile(3225, 3419), new Tile(3225, 3420),
			new Tile(3225, 3421), new Tile(3225, 3422), new Tile(3225, 3423),
			new Tile(3225, 3424), new Tile(3226, 3425), new Tile(3227, 3425),
			new Tile(3228, 3425), new Tile(3229, 3425), new Tile(3229, 3426),
			new Tile(3229, 3427), new Tile(3229, 3428), new Tile(3230, 3428),
			new Tile(3231, 3428), new Tile(3232, 3428), new Tile(3233, 3428),
			new Tile(3234, 3428), new Tile(3235, 3428), new Tile(3236, 3428),
			new Tile(3237, 3428), new Tile(3238, 3428), new Tile(3239, 3428),
			new Tile(3240, 3428), new Tile(3241, 3428), new Tile(3242, 3428),
			new Tile(3242, 3427), new Tile(3242, 3426), new Tile(3242, 3425),
			new Tile(3242, 3424), new Tile(3242, 3423), new Tile(3242, 3422),
			new Tile(3242, 3421), new Tile(3242, 3420), new Tile(3242, 3419),
			new Tile(3242, 3418), new Tile(3243, 3417), new Tile(3244, 3416),
			new Tile(3245, 3415), new Tile(3246, 3414), new Tile(3247, 3413),
			new Tile(3248, 3413), new Tile(3249, 3413), new Tile(3250, 3413),
			new Tile(3251, 3413), new Tile(3252, 3413), new Tile(3253, 3413),
			new Tile(3254, 3413), new Tile(3255, 3413), new Tile(3256, 3413),
			new Tile(3257, 3413), new Tile(3258, 3413), new Tile(3259, 3413),
			new Tile(3260, 3413), new Tile(3260, 3414), new Tile(3261, 3414),
			new Tile(3262, 3414), new Tile(3263, 3414), new Tile(3264, 3415),
			new Tile(3265, 3416), new Tile(3266, 3417), new Tile(3267, 3418),
			new Tile(3268, 3419), new Tile(3269, 3420), new Tile(3270, 3421),
			new Tile(3271, 3422), new Tile(3271, 3423), new Tile(3271, 3424),
			new Tile(3271, 3425), new Tile(3271, 3426), new Tile(3271, 3427),
			new Tile(3271, 3428), new Tile(3272, 3428), new Tile(3273, 3428),
			new Tile(3274, 3428), new Tile(3275, 3428), new Tile(3276, 3428),
			new Tile(3277, 3427), new Tile(3277, 3426), new Tile(3276, 3425),
			new Tile(3275, 3424), new Tile(3276, 3424), new Tile(3277, 3424),
			new Tile(3278, 3425), new Tile(3279, 3426), new Tile(3280, 3427),
			new Tile(3281, 3427), new Tile(3282, 3427), new Tile(3283, 3427),
			new Tile(3284, 3427), new Tile(3285, 3427), new Tile(3286, 3427),
			new Tile(3287, 3427), new Tile(3288, 3427), new Tile(3288, 3428),
			new Tile(3288, 3429), new Tile(3288, 3430), new Tile(3288, 3431),
			new Tile(3288, 3432), new Tile(3288, 3433), new Tile(3288, 3434),
			new Tile(3288, 3435), new Tile(3288, 3436), new Tile(3288, 3437),
			new Tile(3288, 3438), new Tile(3289, 3439), new Tile(3290, 3440),
			new Tile(3290, 3441), new Tile(3290, 3442), new Tile(3291, 3443),
			new Tile(3291, 3444), new Tile(3291, 3445), new Tile(3291, 3446),
			new Tile(3291, 3447), new Tile(3291, 3448), new Tile(3292, 3449),
			new Tile(3292, 3450), new Tile(3292, 3451), new Tile(3292, 3452),
			new Tile(3292, 3453), new Tile(3292, 3454), new Tile(3292, 3455),
			new Tile(3292, 3456), new Tile(3292, 3457), new Tile(3292, 3458),
			new Tile(3292, 3459), new Tile(3292, 3460), new Tile(3292, 3461),
			new Tile(3292, 3462), new Tile(3292, 3463), new Tile(3292, 3464),
			new Tile(3292, 3465), new Tile(3292, 3466), new Tile(3292, 3467),
			new Tile(3292, 3468), new Tile(3293, 3469), new Tile(3293, 3470),
			new Tile(3293, 3471), new Tile(3293, 3472), new Tile(3293, 3473),
			new Tile(3293, 3474), new Tile(3293, 3475), new Tile(3293, 3476),
			new Tile(3292, 3476), new Tile(3291, 3476), new Tile(3290, 3476),
			new Tile(3289, 3477), new Tile(3288, 3477), new Tile(3287, 3477),
			new Tile(3286, 3477), new Tile(3286, 3476), new Tile(3286, 3475),
			new Tile(3286, 3474), new Tile(3286, 3473), new Tile(3285, 3472),
			new Tile(3284, 3472), new Tile(3283, 3472), new Tile(3282, 3472),
			new Tile(3282, 3473), new Tile(3281, 3474), new Tile(3280, 3474),
			new Tile(3279, 3474), new Tile(3278, 3474), new Tile(3277, 3474),
			new Tile(3276, 3475), new Tile(3275, 3475), new Tile(3274, 3475),
			new Tile(3276, 3474), new Tile(3276, 3473), new Tile(3276, 3472),
			new Tile(3275, 3472), new Tile(3275, 3471), new Tile(3275, 3470),
			new Tile(3275, 3469), new Tile(3275, 3468), new Tile(3275, 3467),
			new Tile(3275, 3466), new Tile(3275, 3465), new Tile(3275, 3464),
			new Tile(3275, 3463), new Tile(3275, 3462), new Tile(3274, 3461),
			new Tile(3273, 3460), new Tile(3274, 3460), new Tile(3275, 3459),
			new Tile(3276, 3458), new Tile(3277, 3457), new Tile(3278, 3456),
			new Tile(3279, 3455), new Tile(3280, 3454), new Tile(3280, 3453),
			new Tile(3281, 3452), new Tile(3281, 3451), new Tile(3281, 3450),
			new Tile(3281, 3449), new Tile(3281, 3448), new Tile(3281, 3447),
			new Tile(3281, 3446), new Tile(3280, 3446), new Tile(3279, 3446),
			new Tile(3278, 3446), new Tile(3277, 3446), new Tile(3277, 3445),
			new Tile(3277, 3444), new Tile(3276, 3443), new Tile(3275, 3442),
			new Tile(3275, 3441), new Tile(3275, 3440), new Tile(3275, 3439),
			new Tile(3275, 3438), new Tile(3275, 3437), new Tile(3275, 3436),
			new Tile(3275, 3435), new Tile(3275, 3434), new Tile(3275, 3433),
			new Tile(3275, 3432), new Tile(3275, 3431), new Tile(3275, 3430),
			new Tile(3274, 3429), new Tile(3273, 3429), new Tile(3272, 3429),
			new Tile(3271, 3429), new Tile(3271, 3430), new Tile(3271, 3431),
			new Tile(3271, 3432), new Tile(3271, 3433), new Tile(3271, 3434),
			new Tile(3270, 3434), new Tile(3269, 3434), new Tile(3268, 3434),
			new Tile(3267, 3435), new Tile(3266, 3436), new Tile(3266, 3437),
			new Tile(3265, 3437), new Tile(3264, 3437), new Tile(3263, 3437),
			new Tile(3262, 3437), new Tile(3261, 3438), new Tile(3260, 3438),
			new Tile(3259, 3438), new Tile(3258, 3438), new Tile(3257, 3438),
			new Tile(3256, 3438), new Tile(3255, 3439), new Tile(3254, 3439),
			new Tile(3253, 3439), new Tile(3252, 3439), new Tile(3251, 3439),
			new Tile(3250, 3440), new Tile(3249, 3441), new Tile(3249, 3442),
			new Tile(3248, 3443), new Tile(3247, 3444), new Tile(3247, 3445),
			new Tile(3247, 3446), new Tile(3247, 3447), new Tile(3247, 3448),
			new Tile(3247, 3449), new Tile(3247, 3450), new Tile(3247, 3451),
			new Tile(3247, 3452), new Tile(3247, 3453), new Tile(3247, 3454),
			new Tile(3247, 3455), new Tile(3247, 3456), new Tile(3246, 3457),
			new Tile(3245, 3458), new Tile(3245, 3459), new Tile(3245, 3460),
			new Tile(3245, 3461), new Tile(3245, 3462), new Tile(3245, 3463),
			new Tile(3245, 3464), new Tile(3245, 3465), new Tile(3245, 3466),
			new Tile(3245, 3467), new Tile(3245, 3468), new Tile(3245, 3469),
			new Tile(3246, 3470), new Tile(3247, 3470), new Tile(3248, 3470),
			new Tile(3249, 3470), new Tile(3250, 3470), new Tile(3251, 3470),
			new Tile(3252, 3470), new Tile(3253, 3470), new Tile(3254, 3470),
			new Tile(3255, 3470), new Tile(3256, 3470), new Tile(3257, 3470),
			new Tile(3258, 3470), new Tile(3259, 3470), new Tile(3260, 3470),
			new Tile(3261, 3470), new Tile(3261, 3471), new Tile(3261, 3472),
			new Tile(3261, 3473), new Tile(3261, 3474), new Tile(3261, 3475),
			new Tile(3261, 3476), new Tile(3261, 3477), new Tile(3261, 3478),
			new Tile(3261, 3479), new Tile(3261, 3480), new Tile(3261, 3481),
			new Tile(3261, 3482), new Tile(3261, 3483), new Tile(3261, 3484),
			new Tile(3261, 3485), new Tile(3261, 3486), new Tile(3261, 3487),
			new Tile(3261, 3488), new Tile(3260, 3489), new Tile(3259, 3489),
			new Tile(3258, 3489), new Tile(3257, 3489), new Tile(3256, 3489),
			new Tile(3255, 3489), new Tile(3254, 3489), new Tile(3253, 3489),
			new Tile(3252, 3489), new Tile(3251, 3489), new Tile(3251, 3488),
			new Tile(3250, 3487), new Tile(3249, 3486), new Tile(3248, 3485),
			new Tile(3247, 3485), new Tile(3246, 3485), new Tile(3245, 3484),
			new Tile(3244, 3483), new Tile(3244, 3482), new Tile(3243, 3482),
			new Tile(3242, 3482), new Tile(3241, 3481), new Tile(3240, 3481),
			new Tile(3239, 3480), new Tile(3238, 3479), new Tile(3237, 3478),
			new Tile(3236, 3477), new Tile(3236, 3476), new Tile(3236, 3475),
			new Tile(3236, 3474), new Tile(3236, 3473), new Tile(3236, 3472),
			new Tile(3236, 3471), new Tile(3236, 3470), new Tile(3236, 3469),
			new Tile(3236, 3468), new Tile(3235, 3467), new Tile(3234, 3467),
			new Tile(3233, 3467), new Tile(3233, 3466), new Tile(3234, 3465),
			new Tile(3235, 3464), new Tile(3236, 3464), new Tile(3236, 3463),
			new Tile(3236, 3462), new Tile(3236, 3461), new Tile(3236, 3460),
			new Tile(3236, 3459), new Tile(3236, 3458), new Tile(3236, 3457),
			new Tile(3236, 3456), new Tile(3236, 3455), new Tile(3235, 3454),
			new Tile(3235, 3453), new Tile(3235, 3452), new Tile(3235, 3451),
			new Tile(3234, 3450), new Tile(3233, 3449), new Tile(3232, 3448),
			new Tile(3231, 3447), new Tile(3230, 3447), new Tile(3229, 3446),
			new Tile(3228, 3445), new Tile(3227, 3444), new Tile(3226, 3443),
			new Tile(3225, 3442), new Tile(3224, 3441), new Tile(3223, 3440),
			new Tile(3222, 3439), new Tile(3221, 3438), new Tile(3220, 3438),
			new Tile(3219, 3438), new Tile(3218, 3437), new Tile(3218, 3436),
			new Tile(3218, 3435), new Tile(3217, 3435), new Tile(3216, 3435),
			new Tile(3215, 3435), new Tile(3214, 3435), new Tile(3213, 3435),
			new Tile(3212, 3435), new Tile(3211, 3435), new Tile(3210, 3435),
			new Tile(3209, 3436), new Tile(3208, 3436), new Tile(3207, 3437),
			new Tile(3206, 3438), new Tile(3206, 3439) };
	public Area Varrock = new Area(varTiles);
	public ItemPaint torstol;
	public ItemPaint snapdragon;
	public ItemPaint lantadyme;
	public ItemPaint dwarfweed;
	public ItemPaint avantoe;
	public ItemPaint toadflax;
	public ItemPaint goldcharm;
	public ItemPaint greencharm;
	public ItemPaint crimsoncharm;
	public ItemPaint bluecharm;
	public ItemPaint effigy;
	public ItemPaint seaweed;
	public ItemPaint[] items = {torstol,snapdragon,lantadyme,dwarfweed,avantoe,toadflax,goldcharm,greencharm,crimsoncharm,bluecharm,effigy,seaweed};

	

	public boolean onStart() {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					gui = new DagannothsGUI();
					gui.setVisible(true);

				}
			});
		} catch (InterruptedException e) {
		} catch (InvocationTargetException e) {
		}
		while (gui.isVisible()) {
			sleep(50);
		}
		firstGUI = false;

		if (guiIsComplete == true) {
			if (guthansIsOn) {
				log("Guthans mode is on");
			} else if (peachesIsOn) {
				log("Bones to peaches mode is on");
			} else if (unnotingIsOn) {
				log("Unnoting mode is on");
			}
			LOOTITEMS = new int[WANTLOOT.size()];
			JUNKITEMS = new int[WANTJUNK.size()];
			for (int i = 0; i < WANTLOOT.size(); i++) {
				LOOTITEMS[i] = WANTLOOT.get(i);
			}
			for (int i = 0; i < WANTJUNK.size(); i++) {
				JUNKITEMS[i] = WANTJUNK.get(i);
			}

			startTime = System.currentTimeMillis();
			status = "Starting Up";
			attack = new Skill(0);
			defence = new Skill(1);
			strength = new Skill(2);
			constitution = new Skill(3);
			ranged = new Skill(4);
			Skill[] skills = { attack, strength, ranged, defence, constitution };
			torstol = new ItemPaint("Torstol",5304,true);
			snapdragon = new ItemPaint("Snapdragon",5300,true);
			lantadyme = new ItemPaint("Lantaydme",5302,true);
			dwarfweed = new ItemPaint("Dwarf Weed",5303,true);
			avantoe = new ItemPaint("Avantoe",5298,true);
			toadflax = new ItemPaint("Toadflax",5296,true);
			goldcharm = new ItemPaint("Gold Charm",12158,true);
			greencharm = new ItemPaint("Green Charm",12159,true);
			crimsoncharm = new ItemPaint("Crimson Charm",12160,true);
			bluecharm = new ItemPaint("Blue Charm",12163,true);
			effigy = new ItemPaint("Ancient Effigy",18778,false);
			seaweed = new ItemPaint("Seaweed",402,true);
			

			chest = Equipment.getItem(20);
			helm = Equipment.getItem(8);
			legs = Equipment.getItem(26);
			weapon = Equipment.getItem(17);
			if (Equipment.getItem(23) != null) {
				shieldInUse = true;
				shield = Equipment.getItem(23);
				shieldId = shield.getId();
			}
			chestId = chest.getId();
			helmId = helm.getId();
			legsId = legs.getId();
			weaponId = weapon.getId();

		}
		Mouse.setSpeed(setMouseSpeed);

		antibanTimer = new Timer(Random.nextInt(90000, 180000));
		
		if (Game.getCurrentTab() != 4)
			Game.openTab(4);

		return true;
	}

	@Override
	public int loop() {
		

		if (Mouse.getSpeed() != setMouseSpeed) {
			Mouse.setSpeed(setMouseSpeed);
		}
		if (!antibanTimer.isRunning()) {
			performAntiban(100);
			antibanTimer = new Timer(Random.nextInt(90000, 180000));
		} else {
			for (Actions e : actions) {
				if (e.isValid()) {
					status = e.getStatus();
					e.execute();
					sleep(e.getSleep());
					return 0;
				}
			}
		}
		return 0;
	}

	private boolean performAntiban(int chance) {
		switch (Random.nextInt(0, 25)) {
		case 1:
			Skills.hover(0);
			break;

		case 2:
			Skills.hover(1);
			break;
		case 3:
			Skills.hover(2);
			break;
		case 4:
			Skills.hover(3);
			break;
		case 5:
			Game.openTab(0);
			break;
		case 6:
			Game.openTab(1);
			break;
		case 7:
			Game.openTab(2);
			break;
		case 8:
			Game.openTab(3);
			break;
		case 9:
			Game.openTab(4);
			break;
		case 10:
			Game.openTab(5);
			break;
		case 11:
			Game.openTab(6);
			break;
		case 12:
			Game.openTab(7);
			break;
		case 13:
			Game.openTab(8);
			break;
		case 14:
			Game.openTab(9);
			break;
		case 15:
			Game.openTab(10);
			break;
		case 16:
			Game.openTab(11);
			break;
		case 17:
			Game.openTab(12);
			break;
		case 18:
			Game.openTab(13);
			break;
		case 19:
			Game.openTab(14);
			break;
		case 20:
			Game.openTab(15);
			break;
		case 21:
			Game.openTab(16);
			break;
		case 22:
			Skills.hover(23);
			break;
		case 23:
			Skills.hover(5);
			break;
		case 24:
			Skills.hover(4);
			break;
		case 25:
			Skills.hover(6);
			break;
		}
		sleep(1000);
		if (Random.nextInt(0, 200) <= chance) {
			sleep(1000);
			return performAntiban(chance / 2);
		}
		return true;

	}

	public static int[] getLoot() {
		return LOOTITEMS;
	}

	public static int[] getJunk() {
		return JUNKITEMS;
	}

	private Image getImage(String url) {
		try {
			return ImageIO.read(new URL(url));
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
