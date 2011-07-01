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

import strategyLoop.Actions;

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
		"money making", "summoning", "combat" }, version = 2.0, description = "Start at the Dagannoth Lighthouse with guthans or b2p tabs")
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

			if (skillsloot) {
				skillsloot = false;
			} else {
				skillsloot = true;
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
	Skill[] skills = {attack,strength,ranged,defence,constitution};

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

		g.setColor(color2);
		g.fillRect(0, 0, 100, 20);
		g.setColor(color1);
		g.setFont(font3);
		g.drawString("Show/Hide", 0, 16);
		g.setFont(font1);
		if (showhide) {
			g.setColor(color2);
			g.fillRect(110, 0, 100, 20);
			g.fillRect(230, 0, 100, 20);
			g.setColor(color1);
			g.setFont(font3);
			g.drawString("Skills/Loot", 110, 16);
			g.drawString("Open GUI", 230, 16);
			g.setColor(color2);

			g.setColor(color1);
			g.setFont(font3);

			if (skillsloot) {

				g.drawImage(img1, 7, 343, null);
				g.drawString("Time running:" + hours + ":" + mins + ":" + secs,
						10, 360);

				g.drawString("Status: " + status, 239, 360);
				g.setFont(font2);

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
				if (!gui.isVisible()){
					skillExpCount = 0;
					for (int i = 0;i < addSkills.size();i++){
						skillExpCount++;
						if (skillExpCount ==1){
							addSkills.get(i).drawSkill(g, 10, topLine);
						}else if (skillExpCount ==2){
							addSkills.get(i).drawSkill(g, 10, secLine);
						}else if (skillExpCount==3){
							addSkills.get(i).drawSkill(g,10,triLine);
						}else if (skillExpCount==4){
							addSkills.get(i).drawSkill(g, 10, quaLine);
						}else if (skillExpCount==5){
							addSkills.get(i).drawSkill(g, 10, quiLine);
						}
					}
				}

			} else {
				g.drawImage(img1, 7, 343, null);
				g.drawString("Time running:" + hours + ":" + mins + ":" + secs,
						10, 360);
				g.setFont(font2);
				if (Game.getCurrentTab() == 4) {
					lantGained = Inventory.getCount(true, 5302) - lantStart;
					dwarfGained = Inventory.getCount(true, 5303) - dwarfStart;
					snapGained = Inventory.getCount(true, 5300) - snapStart;
					torstolGained = Inventory.getCount(true, 5304)
					- torstolStart;
					greenGained = Inventory.getCount(true, 12159) - greenStart;
					goldGained = Inventory.getCount(true, 12158) - goldStart;
					crimsonGained = Inventory.getCount(true, 12160)
					- crimsonStart;
					blueGained = Inventory.getCount(true, 12163) - blueStart;
				}
				g.drawString("Torstols: " + torstolGained, 10, 390);

				g.drawString("Snapdragons: " + snapGained, 10, 410);

				g.drawString("Lantadymes: " + lantGained, 10, 430);

				g.drawString("Dwarf Weeds: " + dwarfGained, 10, 450);

				g.drawString("Gold Charms: " + goldGained, 239, 390);

				g.drawString("Green Charms: " + greenGained, 239, 410);

				g.drawString("Crimson Charms: " + crimsonGained, 239, 430);

				g.drawString("Blue Charms: " + blueGained, 239, 450);

			}

		}

	}

	private final Color color1 = new Color(255, 255, 0, 255);
	private final Color color2 = new Color(255, 0, 0, 255);
	private final Font font1 = new Font("URW Palladio L Italic", 2, 20);
	private final Font font2 = new Font("URW Palladio L Italic", 2, 16);

	private final Font font3 = new Font("Arial", 2, 19);
	final Image img1 = getImage("http://i53.tinypic.com/fvigpj.png");
	public boolean showhide = true;
	public boolean guiIsComplete = true;
	public boolean potting = true;
	public boolean skillsloot = true;
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
	public static int topLine = 390;
	public static int secLine = 410;
	public static int triLine = 430;
	public static int quaLine = 450;
	public static int quiLine = 470;
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
	crimsonStart, totalProfit;
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
			for (int i = 0;i <WANTLOOT.size();i++) {
				LOOTITEMS[i] = WANTLOOT.get(i);
			}
			for (int i = 0;i <WANTJUNK.size();i++) {
				JUNKITEMS[i] = WANTJUNK.get(i);
			}
			
			
			startTime = System.currentTimeMillis();
			status = "Starting Up";
			attack = new Skill(0);
			defence = new Skill(1);
			strength = new Skill(2);
			constitution = new Skill(3);
			ranged = new Skill(4);
			lantStart = Inventory.getCount(true, 5302);
			dwarfStart = Inventory.getCount(true, 5303);
			snapStart = Inventory.getCount(true, 5300);
			torstolStart = Inventory.getCount(true, 5304);
			greenStart = Inventory.getCount(true, 12159);
			goldStart = Inventory.getCount(true, 12158);
			crimsonStart = Inventory.getCount(true, 12160);
			blueStart = Inventory.getCount(true, 12163);

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
		
		if (Mouse.getSpeed() != setMouseSpeed){
			Mouse.setSpeed(setMouseSpeed);
		}
		if (!antibanTimer.isRunning()){
			performAntiban(100);
			antibanTimer = new Timer(Random.nextInt(90000, 180000));
		}else{
			for (Actions e:actions){
				if (e.isValid()){
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

	public void lootBones() {
		GroundItem bone = null;
		bone = GroundItems.getNearest(526);
		if (bone != null) {
			if (bone.isOnScreen()) {
				bone.interact("Take " + bone.getItem().getName());
			} else {
				Walking.stepTowards(bone.getLocation());
			}
			sleep(1200, 1400);
		}
	}

	void eatFood() {

		if (Inventory.containsOneOf(food)) {
			Inventory.getItem(food).interact("Eat");
			sleep(2000);

		} else if (peachesIsOn) {
			bonesToPeaches();
		} else {
			sleep(500);
		}

	}

	void bonesToPeaches() {

		if (Inventory.contains(bones) && Inventory.contains(tablets)) {
			status = "Using B2P Tablet";
			Inventory.getItem(tablets).interact("Break");
			sleep(7000);
		} else {
			sleep(10);
		}

	}

	void guthanHeal() {
		if (!equips.containsOneOf(guthansbody)) {
			Inventory.getItem(guthansbody).interact("Wear");
			sleep(1000);
		}
		if (!equips.containsOneOf(guthanshelm)) {
			Inventory.getItem(guthanshelm).interact("Wear");
			sleep(1000);
		}
		if (!equips.containsOneOf(guthansskirt)) {

			Inventory.getItem(guthansskirt).interact("Wear");
			sleep(1000);
		}
		if (!equips.containsOneOf(guthansspear)) {
			Inventory.getItem(guthansspear).interact("Wield");
			sleep(1000);
		}

		if (equips.containsOneOf(guthansbody)
				&& equips.containsOneOf(guthanshelm)
				&& equips.containsOneOf(guthansskirt)
				&& equips.containsOneOf(guthansspear)) {
			guthansequipped = true;
		}
	}

	void guthanOff() {

		if (!equips.contains(chestId)) {
			Inventory.getItem(chestId).interact("Wear");
			sleep(1000);
		}
		if (!equips.contains(helmId)) {
			Inventory.getItem(helmId).interact("Wear");
			sleep(1000);
		}
		if (!equips.contains(weaponId)) {
			Inventory.getItem(weaponId).interact("Wield");
			sleep(1000);
		}
		if (!equips.contains(legsId)) {
			Inventory.getItem(legsId).interact("Wear");
			sleep(1000);
		}
		if (shieldInUse) {
			if (!equips.contains(shieldId)) {
				Inventory.getItem(shieldId).interact("Wield");
				sleep(1000);
			}
			if (equips.contains(chestId, helmId, weaponId, legsId,
					shieldId)) {
				guthansequipped = false;
			}
		} else {
			if (!Inventory.containsAll(chestId, helmId, weaponId, legsId)) {
				guthansequipped = false;
			}
		}

	}

	void potUp() {
		if (Inventory.getItem(ATTACK) != null
				&& Skills.getCurrentLevel(0) == Skills.getRealLevel(0)) {
			Inventory.getItem(ATTACK).interact("Drink");
		}
		sleep(2000);

		if (Inventory.getItem(STRENGTH) != null
				&& Skills.getCurrentLevel(2) == Skills.getRealLevel(2)) {
			Inventory.getItem(STRENGTH).interact("Drink");
		}
		sleep(2000);
		if (Inventory.getItem(DEFENCE) != null
				&& Skills.getCurrentLevel(1) == Skills.getRealLevel(1)) {
			Inventory.getItem(DEFENCE).interact("Drink");
		}
	}

	void lootItems() {

		GroundItem groundloot;
		groundloot = GroundItems.getNearest(LOOTITEMS);
		if (groundloot != null) {
			if (groundloot.isOnScreen()) {
				groundloot.interact("Take " + groundloot.getItem().getName());
			} else {
				Walking.stepTowards(groundloot.getLocation());
			}
			sleep(1200, 1400);
		}
	}
	public static int[] getLoot() {
		return LOOTITEMS;
	}
	public static int[] getJunk() {
		return JUNKITEMS;
	}

	void attackDag() {
		Npc dag;

		dag = Npcs.getNearest("Dagannoth");
		if (Players.getLocal().getInteracting() == null
				&& !Players.getLocal().isMoving()) {
			if (dag != null) {
				if (dag.isOnScreen()) {
					if (dag.getAnimation() == 1342) {
						sleep(200);
					} else {
						dag.interact("Attack");
						sleep(500);
					}
				} else if (!dag.isOnScreen()) {
					Walking.stepTowards(dag.getLocation());
				}
			} else {
				sleep(500, 700);
			}
		}
	}

	int walkJossik() {

		if (dagArea.contains(Players.getLocal().getLocation())) {
			GameObject ladder;

			ladder = Objects.getNearest(ladderId);
			if (ladder != null) {
				if (ladder.isOnScreen()) {
					if (!Players.getLocal().isMoving()) {
						ladder.interact("Climb");
						sleep(1000, 1200);
						return 0;
					}
				} else {
					Walking.stepTowards(ladder.getLocation());
					return 0;
				}
			}

		} else if (pastDoor.contains(Players.getLocal().getLocation())) {
			GameObject ladder;

			ladder = Objects.getNearest(4412);
			if (ladder != null) {
				if (!ladder.isOnScreen()) {
					Walking.stepTowards(ladder.getLocation());
					return 0;
				} else if (ladder.isOnScreen()) {
					if (!Players.getLocal().isMoving()) {
						ladder.interact("Climb");
						sleep(1000, 1200);
						return 0;
					}
				}
			}
		} else if (topLadder.contains(Players.getLocal().getLocation())) {
			GameObject door;

			door = Objects.getNearest(4546);
			if (door != null) {
				if (door.isOnScreen()) {
					if (!Players.getLocal().isMoving()) {
						door.interact("Open");
						sleep(1000, 1200);
					}
				} else if (!door.isOnScreen()) {
					Walking.stepTowards(door.getLocation());
					return 0;
				}
				return 0;
			} else {
				return 0;
			}
		} else if (midFloor.contains(Players.getLocal().getLocation())) {
			if (Objects.getNearest(4568) != null) {
				GameObject stairs;
				stairs = Objects.getNearest(4568);
				if (stairs != null) {

					if (stairs.isOnScreen()) {
						if (!Players.getLocal().isMoving()) {
							stairs.interact("Climb-up");
							sleep(1200);
							return 0;
						}
					} else if (!stairs.isOnScreen()) {
						Walking.stepTowards(stairs.getLocation());
						return 0;
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
							sleep(1500, 2000);
							return 0;
						}
					} else if (!jossik.isOnScreen()) {
						Walking.stepTowards(jossik.getLocation());
						return 0;
					}
				}
			} else {
				GameObject stairs;
				stairs = Objects.getNearest(4570);
				if (stairs != null) {

					if (stairs.isOnScreen()) {
						stairs.interact("Climb-down");
						sleep(1000);
						return 0;
					} else {
						Walking.stepTowards(stairs.getLocation());
						return 0;
					}
				}
			}
		}

		return 0;

	}

	int walkDag() {

		status = "Heading to Dags";
		if (Players.getLocal().getLocation() != null) {
			if (midFloor.contains(Players.getLocal().getLocation())) {
				GameObject ladder;
				GameObject stairs;
				if (Objects.getNearest(4383) != null) {

					ladder = Objects.getNearest(4383);
					if (ladder != null) {
						if (ladder.isOnScreen()) {
							if (!Players.getLocal().isMoving()) {
								ladder.interact("Climb");
								sleep(1000);
								return 0;
							}
						} else if (!ladder.isOnScreen()) {
							Walking.stepTowards(ladder.getLocation());
							return 0;
						}
					}
				} else if (Objects.getNearest(4569) != null) {

					stairs = Objects.getNearest(4569);
					if (stairs != null) {
						if (stairs.isOnScreen()) {
							if (!Players.getLocal().isMoving()) {
								stairs.interact("Climb-down");
								sleep(1000, 1200);
							}
						} else if (!stairs.isOnScreen()) {
							Walking.stepTowards(stairs.getLocation());
						}
					}
				}
			} else if (topLadder.contains(Players.getLocal().getLocation())) {
				GameObject ladder;

				ladder = Objects.getNearest(4485);
				if (ladder != null) {
					if (ladder.isOnScreen()) {
						if (!Players.getLocal().isMoving()) {
							ladder.interact("Climb");
							sleep(1000, 1500);
							return 0;
						}
					} else if (!ladder.isOnScreen()) {
						Walking.stepTowards(ladder.getLocation());
						return 0;
					}
				} else
					return 0;
			} else if (pastDoor.contains(Players.getLocal().getLocation())) {
				GameObject door;

				door = Objects.getNearest(4545);
				if (door != null) {
					if (door.isOnScreen()) {
						if (!Players.getLocal().isMoving()) {
							door.interact("Open");
							sleep(1000, 1200);
							return 0;
						}
					} else if (!door.isOnScreen()) {
						Walking.stepTowards(door.getLocation());
						return 0;
					}
				} else
					return 0;
			} else if (dagArea.contains(Players.getLocal().getLocation())) {

				walkingFrom = false;
				return 0;
			}
		}
		return 0;

	}

	void useShop() {
		// this method returns an int so the loop will keep calling it
		if (Store.isOpen()) {
			if (Inventory.isFull()) {
				Store.close();
				// these are my walking triggers, you can use whatever you want
				// though
				walkingTo = false;
				walkingFrom = true;
			}

			if (test1 == 0) {
				// counting empty spaces
				invoSpace1 = 28 - Inventory.getCount();
				invoSpace = 28 - Inventory.getCount();
				// keeping it from counting over and over unless necessary
				test1++;
			} else if (test1 != 0) {
				// this is for potions, seeing as your script doesn't use them
				// you can
				// delete up to...
				if (invoSpace > 0) {
					if (Store.getItem(2440) != null) {
						Store.getItem(2440).interact("Buy 1");
						invoSpace--;
						sleep(1500);

					} else if (Store.getItem(2436) != null) {
						Store.getItem(2436).interact("Buy 1");
						invoSpace--;
						sleep(1500);

					} else if (Store.getItem(2442) != null) {
						Store.getItem(2442).interact("Buy 1");
						invoSpace--;
						sleep(1500);

					} else if (Store.getItem(pouch) != null) {
						Store.getItem(pouch).interact("Buy 1");
						sleep(1500);

					} else if (Store.getItem(12140) != null) {
						Store.getItem(12140).interact("Buy 1");
						sleep(1500);

					} else if (!Inventory.containsOneOf(STRENGTH)
							&& Inventory.containsOneOf(2441)) {

						Inventory.getItem(2441).interact("Sell 1");
						sleep(2000);
					} else if (!Inventory.containsOneOf(ATTACK)
							&& Inventory.containsOneOf(2437)) {
						Inventory.getItem(2437).interact("Sell 1");
						sleep(2000);
						// ..right here
					} else if (!Inventory.containsOneOf(DEFENCE)
							&& Inventory.containsOneOf(2443)) {
						Inventory.getItem(2443).interact("Sell 1");
						sleep(2000);
					} else if (!Inventory.containsOneOf(pouch)
							&& Inventory.containsOneOf(pouchNote)) {
						Inventory.getItem(pouchNote).interact("Sell 1");
						invoSpace--;
						sleep(1000);
					} else if (Inventory.containsOneOf(pouch)
							&& !Inventory.containsOneOf(sumPot)
							&& Inventory.getItem(notePot) != null) {
						Inventory.getItem(notePot).interact("Sell 1");
						invoSpace--;
						sleep(2000);
					}
					else if (Store.getItem(7946) != null) {
						Store.buy(7946, invoSpace1);
					} else if (Store.getItem(361) != null) {
						Store.buy(361, invoSpace1);
					} else if (Store.getItem(373) != null) {
						Store.buy(373, invoSpace1);
					} else if (Store.getItem(385) != null) {
						Store.buy(385, invoSpace1);
					} else if (Store.getItem(329) != null) {
						Store.buy(329, invoSpace1);
					}

					else if (invoSpace >= 10) {
						// selling based on the number of empty spaces
						if (Inventory.containsOneOf(notedFood)) {
							Inventory.getItem(notedFood).interact("Sell 10");
							invoSpace -= 10;
							sleep(1500);

						}
					} else if (invoSpace >= 5) {
						if (Inventory.containsOneOf(notedFood)) {
							Inventory.getItem(notedFood).interact("Sell 5");
							invoSpace -= 5;
							sleep(1500);

						}
					} else if (invoSpace > 0) {
						if (Inventory.containsOneOf(notedFood)) {
							Inventory.getItem(notedFood).interact("Sell 1");
							invoSpace -= 1;
							sleep(1500);

						}
					}
					// buying one of the supported foods (lobster, swordfish,
					// monkfish
					// tuna, salmon sharks
				} 
				// if it does it a lot without filling the inventory it will
				// recheck
				// and resell so it doesn't just sit there
				test2++;
				sleep(500, 700);
				if (test2 > 5) {
					test1 = 0;
					test2 = 0;
				}
			}
		}

	}

	public int abort() {
		if (dagArea.contains(Players.getLocal().getLocation())) {
			GameObject ladder;
			ladder = Objects.getNearest(ladderId);
			if (ladder.isOnScreen()) {
				ladder.interact("Climb");
				sleep(1000);
			} else if (!ladder.isOnScreen()) {
				Walking.stepTowards(ladder.getLocation());
				return 0;
			}
		} else if (topLadder.contains(Players.getLocal().getLocation())) {
			return -1;
		}
		return 0;
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
