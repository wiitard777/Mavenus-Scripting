import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import org.rsbot.event.events.MessageEvent;
import org.rsbot.event.listeners.MessageListener;
import org.rsbot.event.listeners.PaintListener;
import org.rsbot.script.Script;
import org.rsbot.script.ScriptManifest;
import org.rsbot.script.methods.Skills;
import org.rsbot.script.methods.GrandExchange.GEItem;
import org.rsbot.script.util.Filter;
import org.rsbot.script.wrappers.RSArea;
import org.rsbot.script.wrappers.RSGroundItem;
import org.rsbot.script.wrappers.RSItem;
import org.rsbot.script.wrappers.RSNPC;
import org.rsbot.script.wrappers.RSObject;
import org.rsbot.script.wrappers.RSPath;
import org.rsbot.script.wrappers.RSTile;
import org.rsbot.script.wrappers.RSTilePath;

@ScriptManifest(authors = "MrSharingan", name = "Sharingan Hill Giants", keywords = "Combat", description = "Kills Hill Giants with the option of Resource Dung", version = 5.28)
public class SharinganHillGiants extends Script implements PaintListener,
MouseListener, MessageListener {

	private int foodWithdraw = -1;
	private int withdrawAmount = 1;
	private int startBones, startLimp, startFire, startNat;
	private final int doorID = 1804;
	private final int keyID = 983;
	private final int ladderDownID = 12389;
	private final int resDoorID = 52853;
	private final int[] npcID = { 4692, 4691, 4690, 4693, 4689, 117 };
	private int[] lootID;
	private int safeTileSpot;
	private int bonesBuried;
	private final int bigBonesID = 532;
	private final int resDoorExitID = 52868;
	final int[] foodID = { 1895, 1893, 1891, 4293, 2142, 291, 2140, 3228, 9980,
			7223, 6297, 6293, 6295, 6299, 7521, 9988, 7228, 2878, 7568, 2343,
			1861, 13433, 315, 325, 319, 3144, 347, 355, 333, 339, 351, 329,
			3381, 361, 10136, 5003, 379, 365, 373, 7946, 385, 397, 391, 3369,
			3371, 3373, 2309, 2325, 2333, 2327, 2331, 2323, 2335, 7178, 7180,
			7188, 7190, 7198, 7200, 7208, 7210, 7218, 7220, 2003, 2011, 2289,
			2291, 2293, 2295, 2297, 2299, 2301, 2303, 1891, 1893, 1895, 1897,
			1899, 1901, 7072, 7062, 7078, 7064, 7084, 7082, 7066, 7068, 1942,
			6701, 6703, 7054, 6705, 7056, 7060, 2130, 1985, 1993, 1989, 1978,
			5763, 5765, 1913, 5747, 1905, 5739, 1909, 5743, 1907, 1911, 5745,
			2955, 5749, 5751, 5753, 5755, 5757, 5759, 5761, 2084, 2034, 2048,
			2036, 2217, 2213, 2205, 2209, 2054, 2040, 2080, 2277, 2225, 2255,
			2221, 2253, 2219, 2281, 2227, 2223, 2191, 2233, 2092, 2032, 2074,
			2030, 2281, 2235, 2064, 2028, 2187, 2185, 2229, 6883, 1971, 4608,
			1883, 1885, 15272 };
	private RSTile[] bankToHouse = { new RSTile(3182, 3444),
			new RSTile(3186, 3446), new RSTile(3180, 3447),
			new RSTile(3171, 3450), new RSTile(3165, 3451),
			new RSTile(3160, 3450), new RSTile(3151, 3448),
			new RSTile(3148, 3446), new RSTile(3146, 3443),
			new RSTile(3138, 3443), new RSTile(3131, 3445),
			new RSTile(3126, 3446), new RSTile(3119, 3446),
			new RSTile(3115, 3449) };
	private RSTile[] ladderToEntrance = { new RSTile(3115, 9852),
			new RSTile(3113, 9848), new RSTile(3112, 9843),
			new RSTile(3111, 9836), new RSTile(3108, 9832),
			new RSTile(3104, 9826) };
	private RSTile houseTileOut = new RSTile(3115, 3449);
	private RSTile ladderTile = new RSTile(3116, 9851);
	private RSTile bankTile = new RSTile(3182, 3444);
	private RSTile dungExitTile = new RSTile(1134, 4589);
	private boolean waitForDeath = false;
	boolean waitedLost = false;
	ArrayList<Integer> lootItems;
	RSNPC npc;
	RSArea bankArea;
	RSArea houseArea;
	RSArea giantArea;
	RSArea resDungArea;
	States state;

	// ITEMS
	GEItem bone;
	GEItem fire;
	GEItem nat;
	GEItem limp;
	int bonesID = 532;
	int fireID = 554;
	int natID = 561;
	int limpID = 225;
	int bonePrice;
	int firePrice;
	int natPrice;
	int limpPrice;
	int currentBones;
	int currentFire;
	int currentNat;
	int currentLimp;
	int totalBones;
	int totalFire;
	int totalNat;
	int totalLimp;

	// Paint
	private long startTime = System.currentTimeMillis();
	private int startXPAttack = 0, startXPDefense = 0, startXPStrength = 0,
	startXPHP = 0, startXPRange = 0;

	public boolean onStart() {
		log("Loading up Variables, please wait");
		bankArea = new RSArea(new RSTile(3182, 3433), new RSTile(3189, 3446));
		houseArea = new RSArea(new RSTile(3113, 3450), new RSTile(3117, 3453));
		giantArea = new RSArea(new RSTile(3129, 9859), new RSTile(3080, 9815));
		resDungArea = new RSArea(new RSTile(1141, 4594), new RSTile(1098, 4558));
		if (!inventory.contains(keyID)) {
			log.severe("Didn't start with Brass Key, Shutting Down");
			game.logout(true);
			stopScript();
		}
		if (game.isLoggedIn()) {
			startXPAttack = skills.getCurrentExp(0);
			startXPDefense = skills.getCurrentExp(1);
			startXPStrength = skills.getCurrentExp(2);
			startXPHP = skills.getCurrentExp(3);
			startXPRange = skills.getCurrentExp(4);
		}
		log("Loading up Prices, please wait, this may take a while");
		bone = grandExchange.lookup(532);
		log("Loaded 1/4");
		fire = grandExchange.lookup(554);
		log("Loaded 2/4");
		nat = grandExchange.lookup(561);
		log("Loaded 3/4");
		limp = grandExchange.lookup(225);
		log("Loaded 4/4");
		bonePrice = bone.getGuidePrice();
		firePrice = fire.getGuidePrice();
		natPrice = nat.getGuidePrice();
		limpPrice = limp.getGuidePrice();
		log("Complete");
		startBones = inventory.getCount(532);
		startFire = inventory.getCount(554);
		startNat = inventory.getCount(561);
		startLimp = inventory.getCount(225);
		log("Loaded, loading Images, Please Wait");
		img1 = getImage("http://img28.imageshack.us/img28/7425/hillgiants.png");
		img2 = getImage("http://img20.imageshack.us/img20/9528/clicky.png");
		img3 = getImage("http://img10.imageshack.us/img10/6854/clickmen.png");
		img4 = getImage("http://img202.imageshack.us/img202/9029/foodgui.png");
		img5 = getImage("http://img248.imageshack.us/img248/7950/blankbox.png");
		log("Loaded images");
		safeTileSpot = 0;
		if (random(1, 50) < 25) {
			safey = new RSTile(3098, 9837);
		} else {
			safey = new RSTile(3115, 9829);
		}
		mouse.setSpeed(random(3, 5));
		return true;
	}

	int gamble;
	RSTile safey;

	@Override
	public int loop() {
		if (start) {
			state = getState();
			if(interfaces.canContinue()) {
				interfaces.clickContinue();
				sleep(500, 700);
			}
			switch (state) {
			case IN_BANK:
				inBankYeah();
				waitedLost = false;
				break;
			case IN_GIANT:
				inGiantYeah();
				waitedLost = false;
				break;
			case IN_DUNG:
				inDungYeah();
				waitedLost = false;
				break;
			case IN_WORLD:
				inWorldYeah();
				waitedLost = false;
				break;
			case IN_HOUSE:
				inHouseYeah();
				randTile();
				waitedLost = false;
				break;
			case LOST:

				break;
			}
		}
		return 0;
	}

	// Enums :)
	public enum States {
		IN_BANK, IN_GIANT, IN_DUNG, IN_WORLD, FIGHT, LOOT, IN_HOUSE, LOST;
	}

	public States getState() {
		npc = newNPC();
		setRun();
		getCurrentCount();
		if (wereFighting()) {
			waitForDeath = true;
		} else if (!wereFighting() && waitForDeath) {
			sleep(2500, 3000);
			waitForDeath = false;
		}

		if (inventory.getCount(foodID) > 0 && yesFood || !inventory.isFull()
				&& !yesFood) {
			if (bankArea.contains(playerPos())) {
				return States.IN_BANK;
			} else if (houseArea.contains(playerPos())) {
				return States.IN_HOUSE;
			} else if (giantArea.contains(playerPos()) || playerPos().equals(new RSTile(3098, 9837))) {
				return States.IN_GIANT;
			} else if (resDungArea.contains(playerPos())) {
				return States.IN_DUNG;
			} else {
				return States.IN_WORLD;
			}
		} else if (inventory.getCount(foodID) == 0 && yesFood
				|| inventory.isFull() && !yesFood) {
			if (bankArea.contains(playerPos())) {
				return States.IN_BANK;
			} else if (houseArea.contains(playerPos())) {
				return States.IN_HOUSE;
			} else if (giantArea.contains(playerPos())) {
				return States.IN_GIANT;
			} else if (resDungArea.contains(playerPos()) || playerPos().equals(new RSTile(3098, 9837))) {
				return States.IN_DUNG;
			} else {
				return States.IN_WORLD;
			}
		}
		return States.LOST;
	}

	private void doBank() {
		if (!bank.isOpen()) {
			bank.open();
			sleep(1000, 1200);
		} else {
			addRefresh();
			bank.depositAllExcept(keyID);
			sleep(750, 1000);
			if (yesFood) {
				if (bank.getCount(foodWithdraw) < withdrawAmount) {
					log("Out of Food");
					stopScript();
				}
				if (inventory.getCount(foodWithdraw) == 0
						&& bank.getCount(foodWithdraw) >= withdrawAmount) {
					bank.withdraw(foodWithdraw, withdrawAmount);
					sleep(1000, 1200);
					bank.close();
				}
			} else {

			}
		}
	}

	private void randTile() {
		if (random(1, 50) < 25) {
			safey = new RSTile(3098, 9837);
		} else {
			safey = new RSTile(3115, 9829);
		}
	}

	public void inWorldYeah() {
		RSObject door = objects.getNearest(doorID);
		if (inventory.getCount(foodID) > 0 && yesFood || !inventory.isFull()
				&& !yesFood) {
			if (calc.distanceTo(houseTileOut) > 4) {
				walkPath(bankToHouse);
			} else if (calc.distanceTo(houseTileOut) < 4 && door != null
					&& !door.isOnScreen()) {
				walking.walkTileMM(houseTileOut, 2, 2);
				sleep(1000, 1500);
			} else if (calc.distanceTo(houseTileOut) < 4 && door != null
					&& door.isOnScreen() && door != null) {
				door.doAction("Open");
				sleep(1000, 1500);
			}
		} else if (inventory.getCount(foodID) == 0 && yesFood
				|| inventory.isFull() && !yesFood) {
			if (calc.distanceTo(bankTile) > 4) {
				walkPathReverse(bankToHouse);
				sleep(1000, 1200);
			} else if (calc.distanceTo(bankTile) < 4 && bankTile != null) {
				walking.walkTileMM(bankTile);
				sleep(1000, 1200);
			}
		}
	}

	public void inBankYeah() {
		if (inventory.getCount(foodID) > 0 && yesFood || !inventory.isFull()
				&& !yesFood) {
			walkPath(bankToHouse);
		} else if (inventory.getCount(foodID) == 0 && yesFood
				|| inventory.isFull() && !yesFood) {
			doBank();
		}
	}

	public void inHouseYeah() {
		RSObject door = objects.getNearest(doorID);
		RSObject ladderDown = objects.getNearest(ladderDownID);
		if (inventory.getCount(foodID) > 0 && yesFood || !inventory.isFull()
				&& !yesFood) {
			if (ladderDown != null) {
				ladderDown.doAction("Climb");
				sleep(1000, 1500);
			}
		} else if (inventory.getCount(foodID) == 0 && yesFood
				|| inventory.isFull() && !yesFood) {
			if (door != null && door.isOnScreen()) {
				door.doAction("Open");
				sleep(1000, 1200);
			}
		}
	}

	public void inGiantYeah() {
		RSObject ladderUp = objects.getNearest(29355);
		if (resDung) {
			if (inventory.getCount(foodID) > 0 && yesFood
					|| !inventory.isFull() && !yesFood) {
				RSObject resDoor = objects.getNearest(resDoorID);
				if (resDoor != null && resDoor.isOnScreen()) {
					resDoor.doAction("Enter");
					sleep(1000, 1200);
				} else if (resDoor != null && !resDoor.isOnScreen()) {
					walkPath(ladderToEntrance);
					sleep(1000, 1200);
				} else {
					walkPath(ladderToEntrance);
					sleep(1000, 1200);
				}
			} else if (inventory.getCount(foodID) == 0 && yesFood
					|| inventory.isFull() && !yesFood) {
				if (ladderUp != null && calc.distanceTo(ladderTile) > 4
						&& !ladderUp.isOnScreen()) {
					RSPath toLadder = walking.getPath(ladderTile);
					toLadder.traverse();
					sleep(1000, 1500);
				} else if (ladderUp != null && ladderUp.isOnScreen()) {
					ladderUp.doAction("Climb");
					sleep(1000, 1200);
				} else if (calc.distanceTo(ladderTile) < 4 && ladderUp != null) {
					ladderUp.doAction("Climb");
					sleep(1000, 1200);
				}
			}
		} else {
			if (inventory.getCount(foodID) > 0 && yesFood
					|| !inventory.isFull() && !yesFood) {
				if (needHeal()) {
					RSItem food = inventory.getItem(foodID);
					if (food != null) {
						food.doAction("Eat");
						sleep(750, 1000);
					}
					if (!yesFood) {
						log.severe("Were not using food but we need to heal");
						log.severe("Script stopping");
						stopScript();
					}
				} else {
					if (!lootThere()) {
						dominate();
					} else {
						if (!wereFighting()) {
							loot();
						}
					}
				}
			}  else if(inventory.getCount(foodID) == 0 && yesFood && lootThere()) {
				loot();
			} else if (inventory.getCount(foodID) == 0 && yesFood && !lootThere()
					|| inventory.isFull() && !yesFood) {
				if (ladderUp != null && calc.distanceTo(ladderTile) > 4
						&& !ladderUp.isOnScreen()) {
					RSPath toLadder = walking.getPath(ladderTile);
					toLadder.traverse();
					sleep(1000, 1500);
				} else if (ladderUp != null && ladderUp.isOnScreen()) {
					ladderUp.doAction("Climb");
					sleep(1000, 1200);
				} else if (calc.distanceTo(ladderTile) < 4 && ladderUp != null) {
					ladderUp.doAction("Climb");
					sleep(1000, 1200);
				}
			}
		}
	}

	public void inDungYeah() {
		if (resDung) {
			if (inventory.getCount(foodID) > 0 && yesFood
					|| !inventory.isFull() && !yesFood) {
				if (needHeal()) {
					RSItem food = inventory.getItem(foodID);
					if (food != null) {
						food.doAction("Eat");
						sleep(750, 1000);
					}
					if (!yesFood) {
						log.severe("Were not using food but we need to heal");
						log.severe("Script stopping");
						stopScript();
					}
				} else {
					if (!lootThere()) {
						dominate();
					} else {
						if (!wereFighting()) {
							loot();
						}
					}
				}
			} else if (inventory.getCount(foodID) == 0 && yesFood
					|| inventory.isFull() && !yesFood) {
				RSObject exitDoor = objects.getNearest(resDoorExitID);
				if (calc.distanceTo(dungExitTile) > 4 && exitDoor != null) {
					walking.getPath(dungExitTile).traverse();
					sleep(1000, 1200);
				} else if (calc.distanceTo(dungExitTile) < 4
						&& exitDoor != null && exitDoor.isOnScreen()) {
					exitDoor.doAction("Exit");
					sleep(1000, 1200);
				} else if (exitDoor != null && exitDoor.isOnScreen()) {
					exitDoor.doAction("Exit");
					sleep(1000, 1200);
				} else {
					walking.getPath(dungExitTile).traverse();
					sleep(1000, 1200);
				}
			}
		} else {
			if (inventory.getCount(foodID) > 0 && yesFood
					|| !inventory.isFull() && !yesFood) {
				if (inventory.getCount(foodID) > 0 && yesFood
						|| !inventory.isFull() && !yesFood) {
					if (needHeal()) {
						RSItem food = inventory.getItem(foodID);
						if (food != null) {
							food.doAction("Eat");
							sleep(750, 1000);
						}
						if (!yesFood) {
							log
							.severe("Were not using food but we need to heal");
							log.severe("Script stopping");
							stopScript();
						}
						if (!lootThere()) {
							dominate();
						} else {
							if (!wereFighting()) {
								loot();
							}
						}
					}
				}

			} else if (inventory.getCount(foodID) == 0 && yesFood
					|| inventory.isFull() && !yesFood) {
				RSObject exitDoor = objects.getNearest(resDoorExitID);
				if (calc.distanceTo(dungExitTile) > 4 && exitDoor != null) {
					walking.getPath(dungExitTile).traverse();
					sleep(1000, 1200);
				} else if (calc.distanceTo(dungExitTile) < 4
						&& exitDoor != null && exitDoor.isOnScreen()) {
					exitDoor.doAction("Exit");
					sleep(1000, 1200);
				} else if (exitDoor != null && exitDoor.isOnScreen()) {
					exitDoor.doAction("Exit");
					sleep(1000, 1200);
				} else {
					walking.getPath(dungExitTile).traverse();
					sleep(1000, 1200);
				}
			}
		}
	}

	boolean safeSpot = false;
	RSTile[] safeSpotTiles = { new RSTile(3098, 9837), new RSTile(3115, 9829) };

	public void dominate() {
		if (!safeSpot || resDung) {
			if (npc != null && npc.isOnScreen() && !wereFighting()) {
				fight();
				antiBan();
			} else if (npc != null && !npc.isOnScreen() && !wereFighting()
					&& npc.getInteracting() == null
					&& !getMyPlayer().isMoving()) {
				walking.walkTileMM(npc.getLocation());
				sleep(1000, 1500);
			} else if (npc != null && !npc.isOnScreen()
					&& npcs.getNearest(npcID) != null
					&& !npcs.getNearest(npcID).isOnScreen()) {
				if (!wereFighting()) {
					if (inventory.contains(bigBonesID) && yesBury) {
						if (getMyPlayer().getAnimation() == -1) {
							inventory.getItem(bigBonesID).doAction("Bury");
							sleep(500, 750);
						}
					}
					RSPath toMiddle = walking.getPath(npcs.getNearest(npcID)
							.getLocation());
					toMiddle.traverse();
					sleep(1000, 1200);
					antiBan();
				}
			}
		} else {
			if (playerPos().equals(safey)) {
				if (npc != null && npc.isOnScreen() && !wereFighting()) {
					fight();
					antiBan();
				} else if (npc != null && !npc.isOnScreen()
						&& npcs.getNearest(npcID) != null
						&& !npcs.getNearest(npcID).isOnScreen()) {
					if (!wereFighting()) {
						if (inventory.contains(bigBonesID) && yesBury) {
							if (getMyPlayer().getAnimation() == -1) {
								inventory.getItem(bigBonesID).doAction("Bury");
								sleep(500, 750);
							}
						}
					}
				} else {
					antiBan();
				}
			} else {
				walkSafeTile();
			}
		}
	}

	private void walkSafeTile() {
		if (!playerPos().equals(safey)) {
			if (calc.distanceTo(safey) > 3) {
				walking.getPath(safey).traverse();
				sleep(1000, 1200);
			} else {
				walking.walkTileOnScreen(safey);
				sleep(1000, 1200);
			}
		}
	}

	private RSTile playerPos() {
		return getMyPlayer().getLocation();
	}

	private void walkPath(RSTile[] path) {
		RSTilePath dest = walking.newTilePath(path);
		dest.traverse();
		sleep(1000, 1500);
	}

	private void walkPathReverse(RSTile[] path) {
		RSTilePath dest = walking.newTilePath(path);
		dest.reverse();
		dest.traverse();
		sleep(1000, 1500);
		dest.reverse();
	}

	private void fight() {
		npc = newNPC();
		if (npc != null && (getMyPlayer().getInteracting() == null)) {
			npc.doAction("Attack");
			sleep(750, 1000);
		}
	}

	private boolean needHeal() {
		if (combat.getLifePoints() < (skills.getRealLevel(Skills.CONSTITUTION) * 10) / 2) {
			return true;
		}
		return false;
	}

	RSArea lootArea;

	public int loot() {
		lootID = convertIntegers(lootItems);
		RSGroundItem loot = groundItems.getNearest(lootID);
		if (resDung) {
			lootArea = resDungArea;
		} else {
			lootArea = giantArea;
		}
		if (!safeSpot) {
			if (loot != null && lootArea.contains(loot.getLocation())
					&& !waitForDeath) {
				if (players.getMyPlayer().isMoving()) {
					return random(400, 600);
				}
				if (!inventory.isFull()) {
					if (!loot.isOnScreen()) {
						camera.turnToTile(loot.getLocation(), 15);
						if (!loot.isOnScreen()) {
							walking.walkTileMM(walking.getClosestTileOnMap(loot
									.getLocation()));
							return random(900, 1200);
						}
					}
					loot.doAction("Take " + loot.getItem().getName());
					sleep(random(1000, 1500));
					return random(900, 1100);
				} else {
					if (!yesBury) {
						RSItem food = inventory.getItem(foodID);
						if(food != null) {
							food.doAction("Eat");
							sleep(750, 1000);
						}
					} else {
						if (inventory.contains(bigBonesID)) {
							if (getMyPlayer().getAnimation() == -1) {
								inventory.getItem(bigBonesID).doAction("Bury");
								sleep(500, 750);
							}
						}
					}
				}
			}
		} else {
			if (loot != null && lootArea.contains(loot.getLocation())
					&& !waitForDeath) {
				if (players.getMyPlayer().isMoving()) {
					return random(400, 600);
				}
				if (!inventory.isFull()) {
					if (loot.isOnScreen()) {
						loot.doAction("Take " + loot.getItem().getName());
						sleep(random(1000, 1500));
					}
					return random(900, 1100);
				} else {
					RSItem food = inventory.getItem(foodID);
					if (!yesBury && food != null) {
						food.doAction("Eat");
						sleep(750, 1000);
					} else {
						if (inventory.contains(bigBonesID)) {
							if (getMyPlayer().getAnimation() == -1) {
								inventory.getItem(bigBonesID).doAction("Bury");
								sleep(500, 750);
							}
						}
					}
				}
			}
		}
		return random(500, 600);
	}

	public void getCurrentCount() {
		int bonesCount = inventory.getCount(bonesID);
		currentBones = bonesCount - startBones;
		int fireCount = inventory.getCount(fireID);
		currentFire = fireCount - startFire;
		int natCount = inventory.getCount(natID);
		currentNat = natCount - startNat;
		int limpCount = inventory.getCount(limpID);
		currentLimp = limpCount - startLimp;
	}

	public void addRefresh() {
		totalBones += currentBones;
		currentBones = 0;
		startBones = 0;
		totalFire += currentFire;
		currentFire = 0;
		startFire = 0;
		totalNat += currentNat;
		currentNat = 0;
		startNat = 0;
		totalLimp += currentLimp;
		currentLimp = 0;
		startLimp = 0;
	}

	public boolean lootThere() {
		lootID = convertIntegers(lootItems);
		RSGroundItem loot = groundItems.getNearest(lootID);
		if (!safeSpot) {
			if (loot != null) {
				return true;
			}
		} else {
			if (loot != null && loot.isOnScreen()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the nearest NPC, specified by ID, that is not in combat.
	 * 
	 * @param npcID
	 *            ID of NPC
	 * @return A RSNPC object with the given ID that is not in combat, else
	 *         null.
	 */
	// CREDITS TO GRAPES
	public RSNPC getNearestFree(final int npcID) {
		return npcs.getNearest(new Filter<RSNPC>() {
			public boolean accept(RSNPC npc) {
				return npc.getID() == npcID && !npc.isInCombat()
				&& npc.getHPPercent() > 0;
			}
		});
	}

	public static int[] convertIntegers(List<Integer> integers) {
		int[] ret = new int[integers.size()];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = integers.get(i).intValue();
		}
		return ret;
	}

	private boolean wereFighting() {
		if (getMyPlayer().getInteracting() != null) {
			return true;
		}
		return false;
	}

	private RSNPC newNPC() {
		RSNPC interacting = interactingNPC();
		return interacting != null ? interacting : npcs
				.getNearest(new Filter<RSNPC>() {
					public boolean accept(RSNPC n) {
						if (npcID == null) {
							return false;
						}
						for (int i : npcID) {
							if (n.getID() != i || n.getHPPercent() == 0
									|| n.isInCombat()) {
								continue;
							}
							return true;
						}
						return false;
					}
				});
	}

	private RSNPC interactingNPC() {
		return npcs.getNearest(new Filter<RSNPC>() {
			public boolean accept(RSNPC n) {
				if (n.getInteracting() == null) {
					return false;
				}
				String[] acts = n.getActions();
				if (acts == null) {
					return false;
				}
				for (String a : acts) {
					if (a == null || !a.contains("Attack")) {
						continue;
					}
					return n != null && n.getInteracting().equals(players.getMyPlayer());
				}
				return false;
			}
		});
	}

	public void setRun() {
		if (walking.getEnergy() > random(60, 100)) {
			walking.setRun(true);
		}
	}

	public void antiBan() {
		if (random(0, 15) == 0) {
			final char[] LR = new char[] { KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT };
			final char[] UD = new char[] { KeyEvent.VK_DOWN, KeyEvent.VK_UP };
			final char[] LRUD = new char[] { KeyEvent.VK_LEFT,
					KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_UP };
			final int random2 = random(0, 2);
			final int random1 = random(0, 2);
			final int random4 = random(0, 4);

			if (random(0, 3) == 0) {
				mouse.moveSlightly();
				keyboard.pressKey(LR[random1]);
				sleep(random(100, 400));
				keyboard.pressKey(UD[random2]);
				sleep(random(300, 600));
				keyboard.releaseKey(UD[random2]);
				sleep(random(100, 400));
				keyboard.releaseKey(LR[random1]);

				if (random(0, 8) == 0) {
					if (game.getCurrentTab() != 1) {
						game.openTab(1);
						mouse.move(new Point(583, 252), 29, 11);
						sleep(random(2000, 3000));
					}
					if (interfaces.getComponent(744, 9).isValid()) {
						interfaces.getComponent(744, 9).doClick();
					}
					if (interfaces.getComponent(449, 29).isValid()) {
						interfaces.getComponent(449, 29).doClick();
					}
				}

				if (random(0, 10) == 0) {
					game.openTab(random(0, 14));
				}
			} else {
				keyboard.pressKey(LRUD[random4]);
				if (random4 > 1) {
					sleep(random(300, 600));
				} else {
					sleep(random(500, 900));
				}
				keyboard.releaseKey(LRUD[random4]);
			}
		} else {
			sleep(random(200, 2000));
		}
	}

	private Image getImage(String url) {
		try {
			return ImageIO.read(new URL(url));
		} catch (IOException e) {
			return null;
		}
	}

	public double getVersion() {
		return 5.28;
	}

	public static String formatTime(long time) {
		if (time <= 0)
			return "00:00:00";
		long sec = time / 1000;
		long min = 0;
		long hour = 0;
		long day = 0;
		while (sec >= 60) {
			sec -= 60;
			min += 1;
		}
		while (min >= 60) {
			min -= 60;
			hour += 1;
		}

		while (hour >= 24) {
			hour -= 24;
			day += 1;
		}
		return (day == 0 ? "" : "" + day + ":")
		+ (hour < 10 ? "0" + hour : "" + hour) + ":"
		+ (min < 10 ? "0" + min : "" + min) + ":"
		+ (sec < 10 ? "0" + sec : "" + sec);
	}

	private final Color color1 = new Color(0, 0, 0);
	private final Color color2 = new Color(209, 185, 119);
	private final Color color3 = new Color(1, 1, 0);
	private final Color color8 = new Color(207, 186, 159);
	private final BasicStroke stroke1 = new BasicStroke(1);
	private final Font font1 = new Font("Arial", 1, 11);
	private Image img1;
	private Image img2;
	private Image img3;
	String showCurrent = "Show XP";

	public void onRepaint(Graphics g1) {
		long millis = System.currentTimeMillis() - startTime;
		long runTime = System.currentTimeMillis() - startTime;
		long hours = millis / (1000 * 60 * 60);
		millis -= hours * 1000 * 60 * 60;
		long minutes = millis / (1000 * 60);
		millis -= minutes * 1000 * 60;
		int XPChangeAttack = skills.getCurrentExp(0) - startXPAttack;
		int attackPerHour = 0;
		int attackTL = skills.getExpToNextLevel(0);
		int XPChangeDefense = skills.getCurrentExp(1) - startXPDefense;
		int defensePerHour = 0;
		int defTL = skills.getExpToNextLevel(1);
		int XPChangeStrength = skills.getCurrentExp(2) - startXPStrength;
		int strengthPerHour = 0;
		int strengthTL = skills.getExpToNextLevel(2);
		int XPChangeHP = skills.getCurrentExp(3) - startXPHP;
		int hpPerHour = 0;
		int hpTL = skills.getExpToNextLevel(3);
		int XPChangeRange = skills.getCurrentExp(4) - startXPRange;
		int rangePerHour = 0;
		int rangeTL = skills.getExpToNextLevel(4);
		int bones = currentBones + totalBones;
		int bonesPerHour = 0;
		int bonesBuriedPerHour = 0;
		int profit = ((currentBones + totalBones) * bonePrice)
		+ ((currentFire + totalFire) * firePrice)
		+ ((currentNat + totalNat) * natPrice)
		+ ((currentLimp + totalLimp) * limpPrice);
		int profitPerHour = 0;
		if (runTime / 1000 > 0) {
			attackPerHour = (int) (3600000.0 / runTime * XPChangeAttack);
			strengthPerHour = (int) (3600000.0 / runTime * XPChangeStrength);
			defensePerHour = (int) (3600000.0 / runTime * XPChangeDefense);
			hpPerHour = (int) (3600000.0 / runTime * XPChangeHP);
			rangePerHour = (int) (3600000.0 / runTime * XPChangeRange);
			bonesPerHour = (int) (3600000.0 / runTime * bones);
			profitPerHour = (int) (3600000.0 / runTime * profit);
			bonesBuriedPerHour = (int) (3600000.0 / runTime * bonesBuried);
		}
		final int attackTTL = (int) (((double) attackTL / (double) attackPerHour) * 3600000);
		final int strengthTTL = (int) (((double) strengthTL / (double) strengthPerHour) * 3600000);
		final int defTTL = (int) (((double) defTL / (double) defensePerHour) * 3600000);
		final int hpTTL = (int) (((double) hpTL / (double) hpPerHour) * 3600000);
		final int rangeTTL = (int) (((double) rangeTL / (double) rangePerHour) * 3600000);

		Graphics2D g = (Graphics2D) g1;

		int yPos = 370;

		if (start) {
			if (!hide) {
				g.drawImage(img1, 7, 221, null);
				g.setFont(font1);
				g.setColor(color1);
				g.setColor(color2);
				g.fillRect(16, 446, 220, 19);
				g.setColor(color1);
				g.setStroke(stroke1);
				g.drawRect(16, 446, 220, 19);
				g.setColor(color3);
				g.drawString("" + showCurrent, 90, 461);

				if (xp) {
					if (XPChangeAttack > 0) {
						g.drawString("Attack: " + XPChangeAttack + " || "
								+ attackPerHour + "/Hour" + " || " + "TTL: "
								+ formatTime(attackTTL), 23, yPos);
						yPos += 20;
					}
					if (XPChangeStrength > 0) {
						g.drawString("Strength: " + XPChangeStrength + " || "
								+ strengthPerHour + "/Hour" + " || " + "TTL: "
								+ formatTime(strengthTTL), 23, yPos);
						yPos += 20;
					}
					if (XPChangeDefense > 0) {
						g.drawString("Defense: " + XPChangeDefense + " || "
								+ defensePerHour + "/Hour" + " || " + "TTL: "
								+ formatTime(defTTL), 23, yPos);
						yPos += 20;
					}
					if (XPChangeRange > 0) {
						g.drawString("Range: " + XPChangeRange + " || "
								+ rangePerHour + "/Hour" + " || " + "TTL: "
								+ formatTime(rangeTTL), 23, yPos);
						yPos += 20;
					}
					if (XPChangeHP > 0) {
						g.drawString("Hp: " + XPChangeHP + " || " + hpPerHour
								+ "/Hour" + " || " + "TTL: "
								+ formatTime(hpTTL), 23, yPos);
						yPos += 20;
					}
				}
				if (main) {
					g.setFont(font1);
					g.setColor(color1);
					g.drawString("Runtime: " + formatTime(runTime), 25, 365);
					g.drawString("Profit: " + profit + " || " + profitPerHour
							+ "/Hour", 25, 385);
					if (yesBury) {
						g.drawString("Bones Buried: " + bonesBuried + " || "
								+ bonesBuriedPerHour + "/Hour", 25, 405);
					} else {
						g.drawString("Bones Collected: " + bones + " || "
								+ bonesPerHour + "/Hour", 25, 405);
					}
				}
				g.drawImage(img2, 481, 345, null);
				g.drawImage(img3, 427, 286, null);
			}
			g.drawImage(img2, 481, 345, null);
			g.drawImage(img3, 427, 286, null);
		}
		/*********** Paint GUI ********/
		if (!started && !start) {
			g.drawImage(img4, 8, 346, null); // Fish nDat

			// Red for YES - Food
			if (yesFood) {
				g.setColor(color6);// GREEN
			} else {
				g.setColor(color4);// RED
			}
			g.fillRect(88, 365 - 10, 12, 11); // SMALL BOX
			g.setColor(color5); // BLACK
			g.setStroke(stroke2); // BLACK STROKE
			g.drawRect(88, 365 - 10, 12, 11); // BOX OUTLINE

			// Box for Withdraw Amount - 1 (GREEN)
			if (one) {
				g.setColor(color6);// GREEN
			} else {
				g.setColor(color4);// RED
			}
			g.fillRect(194, 378, 12, 11); // SMALL BOX
			g.setColor(color5); // BLACK
			g.setStroke(stroke2); // BLACK STROKE
			g.drawRect(194, 378, 12, 11); // BOX OUTLINE

			// Box for Withdraw Amount - 5 (Red)
			if (five) {
				g.setColor(color6);// GREEN
			} else {
				g.setColor(color4);// RED
			}
			g.fillRect(194, 396, 12, 11); // SMALL BOX
			g.setColor(color5); // BLACK
			g.setStroke(stroke2); // BLACK STROKE
			g.drawRect(194, 396, 12, 11); // BOX OUTLINE

			// Box for Withdraw Amount - 10 (Red)
			if (ten) {
				g.setColor(color6);// GREEN
			} else {
				g.setColor(color4);// RED
			}
			g.fillRect(194, 410, 12, 11); // SMALL BOX
			g.setColor(color5); // BLACK
			g.setStroke(stroke2); // BLACK STROKE
			g.drawRect(194, 410, 12, 11); // BOX OUTLINE

			// Box for Withdraw Amount - 20 (Red)
			if (twenty) {
				g.setColor(color6);// GREEN
			} else {
				g.setColor(color4);// RED
			}
			g.fillRect(194, 426, 12, 11); // SMALL BOX
			g.setColor(color5); // BLACK
			g.setStroke(stroke2); // BLACK STROKE
			g.drawRect(194, 426, 12, 11); // BOX OUTLINE

			/*
			 * // Box for Withdraw Amount - All (Red) if (all) {
			 * g.setColor(color6);// GREEN } else { g.setColor(color4);// RED }
			 * g.fillRect(194, 441, 12, 11); // SMALL BOX g.setColor(color5); //
			 * BLACK g.setStroke(stroke2); // BLACK STROKE g.drawRect(194, 441,
			 * 12, 11); // BOX OUTLINE
			 */
			createBox(g1, all, 164, 451);

			// BOX FOR NO FOR BANKING
			if (!resDung) {
				g.setColor(color6);// GREEN
			} else {
				g.setColor(color4);// RED
				safeSpot = false;
			}
			g.fillRect(280, 436, 12, 11); // SMALL BOX
			g.setColor(color5); // BLACK
			g.setStroke(stroke2); // BLACK STROKE
			g.drawRect(280, 436, 12, 11); // BOX OUTLINE

			if (safeSpot) {
				g.setColor(color6);// GREEN
				resDung = false;
			} else {
				g.setColor(color4);// RED
			}
			g.fillRect(88, 390 - 10, 12, 11); // SMALL BOX
			g.setColor(color5); // BLACK
			g.setStroke(stroke2); // BLACK STROKE
			g.drawRect(88, 390 - 10, 12, 11); // BOX OUTLINE


			// BOX FOR YES FOR BANKING
			if (resDung) {
				g.setColor(color6);// GREEN
			} else {
				g.setColor(color4);// RED
			}
			g.fillRect(280, 416, 12, 11); // SMALL BOX
			g.setColor(color5); // BLACK
			g.setStroke(stroke2); // BLACK STROKE
			g.drawRect(280, 416, 12, 11); // BOX OUTLINE

			g.setColor(color5); // BLACK
			g.setFont(font2); // NORMAL FONT
			g.drawString("Use Food?", 15, 365);
			g.drawString("Safe Spot", 15, 390);
			g.setColor(color6); // GREEN
			g.setColor(color5); // BLACK
			g.drawString("Food Type :", 368, 373);
			g.drawString("Withdrawal Amount :", 149, 372);
			g.drawString("1", 164, 388);
			g.drawString("All", 164, 451);
			g.drawString("5", 164, 406);
			g.drawString("10", 164, 420);
			g.drawString("20", 164, 436);
			g.drawString("Res Dung :", 250, 406);
			g.drawString("Yes", 250, 426);
			g.drawString("No", 250, 446);
			g.setColor(color5);
			g.setFont(font3);
			g.drawString("Food Used : " + foodUsed, 326, 468);
			if (!yesFood) {
				g.setColor(color7);
				g.fillRect(140, 352, 363, 120);// COVER UP THINGS
			}

			g.setFont(font3);
			g.setColor(color8);
			g.fillRect(15, 447, 130, 22);
			g.setColor(color5);
			g.drawRect(15, 447, 130, 22);
			g.drawString("Next Options", 47, 463);
		}
		/********* Paint Loot GUI *************/
		if (started && !start) {
			g.drawImage(img5, 8, 346, null);

			g.setFont(font1);
			g.setColor(color1);
			g.drawString("Loot Options :", 21, 362);
			g.setFont(font3);
			g.drawString("Charms :", 156, 375);
			g.drawString("Big bones :", 156, 390);
			g.drawString("Limpwurt :", 156, 405);

			g.drawString("Runes", 450, 406);
			g.setFont(font3);
			g.drawString("Water :", 328, 360);
			g.drawString("Fire :", 328, 375);
			g.drawString("Cosmic :", 328, 390);
			g.drawString("Law :", 328, 405);
			g.drawString("Mind :", 328, 420);
			g.drawString("Chaos :", 328, 435);
			g.drawString("Nature :", 328, 450);
			g.drawString("Death :", 328, 465);
			g.drawString("Gems :", 156, 420);
			g.drawString("Steel Arrow :", 156, 435);
			g.drawString("Iron Arrow :", 156, 450);
			g.setFont(font1);
			g.drawString("Bury Bones :", 32, 381);
			g.setFont(font3);
			g.drawString("Yes:", 32, 400);
			g.drawString("No :", 32, 417);

			createBox(g1, lootBones, 156 + 84, 390);
			createBox(g1, lootCharms, 156 + 84, 375);
			createBox(g1, lootLimpwurt, 156 + 84, 405);
			createBox(g1, lootWater, 400, 360);
			createBox(g1, lootFire, 400, 375);
			createBox(g1, lootCosmic, 400, 390);
			createBox(g1, lootLaw, 400, 405);
			createBox(g1, lootMind, 400, 420);
			createBox(g1, lootChaos, 400, 435);
			createBox(g1, lootNature, 400, 450);
			createBox(g1, lootDeath, 400, 465);
			createBox(g1, lootSteelA, 156 + 84, 435);
			createBox(g1, lootIronA, 156 + 84, 450);
			createBox(g1, lootGems, 156 + 84, 420);
			createBox(g1, yesBury, 32, 400);
			createBox(g1, noBury, 32, 417);

			g.setColor(color8);
			g.fillRect(15, 447, 130, 22);
			g.setColor(color5);
			g.drawRect(15, 447, 130, 22);
			g.drawString("Start Script!", 47, 463);
		}
	}

	public void createBox(Graphics g1, boolean option, int x, int y) {
		Graphics2D g = (Graphics2D) g1;
		if (option) {
			g.setColor(color6);// GREEN
		} else {
			g.setColor(color4);// RED
		}
		g.fillRect(x + 30, y - 10, 12, 11); // SMALL BOX
		g.setColor(color5); // BLACK
		g.setStroke(stroke2); // BLACK STROKE
		g.drawRect(x + 30, y - 10, 12, 11); // BOX OUTLINE
	}

	private final Color color4 = new Color(255, 0, 51);// RED
	private final Color color5 = new Color(0, 0, 0); // BLACK
	private final Color color6 = new Color(51, 255, 0);// GREEN
	private final Color color7 = new Color(216, 197, 162); // BORDER COLOUR

	private final BasicStroke stroke2 = new BasicStroke(1);

	private final Font font2 = new Font("Arial", 1, 12);
	private final Font font3 = new Font("Arial", 1, 10);
	private Image img4;
	private Image img5;

	boolean main = true;
	boolean xp = false;
	boolean hide = false;
	boolean yesFood = true;
	boolean noFood = false;
	boolean one = true, five = false, ten = false, twenty = false, all = false;
	String foodUsed = "Not Chosen";
	boolean started = false, start = false;
	boolean lootCharms = false, lootBones = true, lootLimpwurt = true,
	lootWater = true, lootFire = true, lootCosmic = false,
	lootLaw = false, lootMind = false, lootChaos = false,
	lootNature = true, lootDeath = false, lootGems = true,
	lootSteelA = false, lootIronA = false, yesBury = false,
	noBury = true;
	boolean resDung = false;

	@Override
	public void mouseClicked(MouseEvent e) {
		Rectangle toggleBox = new Rectangle(16, 446, 220, 19);
		Rectangle hideButton = new Rectangle(481, 345, 30, 30);
		if (start) {
			if (toggleBox.contains(e.getPoint()) && !hide && main) {
				showCurrent = "Show Main";
				main = false;
				xp = true;
				hide = false;
			} else if (toggleBox.contains(e.getPoint()) && !hide && xp) {
				showCurrent = "Show XP";
				main = true;
				xp = false;
				hide = false;
			} else if (hideButton.contains(e.getPoint()) && !hide) {
				main = false;
				xp = false;
				hide = true;
			} else if (hideButton.contains(e.getPoint()) && hide) {
				main = true;
				xp = false;
				hide = false;
			}
		} else if (!started && !start) {

			Rectangle startButton = new Rectangle(15, 447, 130, 22);
			if (startButton.contains(e.getPoint())) {
				started = true;
			}
			Rectangle yesButton = new Rectangle(88, 365 - 10, 12, 11); // yesButton
			if (yesButton.contains(e.getPoint())) {
				yesFood = booleanToggle(yesFood);
			}
			Rectangle oneButton = new Rectangle(194, 378, 12, 11); // One Button
			Rectangle fiveButton = new Rectangle(194, 396, 12, 11); // Five
			// Button
			Rectangle tenButton = new Rectangle(194, 410, 12, 11); // Ten Button
			Rectangle twentyButton = new Rectangle(194, 426, 12, 11); // 20
			// Button
			Rectangle allButton = new Rectangle(194, 441, 12, 11); // All Button
			if (oneButton.contains(e.getPoint())) {
				one = true;
				five = false;
				ten = false;
				twenty = false;
				all = false;
				withdrawAmount = 1;
			} else if (fiveButton.contains(e.getPoint())) {
				one = false;
				five = true;
				ten = false;
				twenty = false;
				all = false;
				withdrawAmount = 5;
			} else if (tenButton.contains(e.getPoint())) {
				one = false;
				five = false;
				ten = true;
				twenty = false;
				all = false;
				withdrawAmount = 10;
			} else if (twentyButton.contains(e.getPoint())) {
				one = false;
				five = false;
				ten = false;
				twenty = true;
				all = false;
				withdrawAmount = 20;
			} else if (allButton.contains(e.getPoint())) {
				one = false;
				five = false;
				ten = false;
				twenty = false;
				all = true;
				withdrawAmount = 0;
			}
			Rectangle troutButton = new Rectangle(325, 386, 37, 32);
			Rectangle salmonButton = new Rectangle(368, 386, 37, 34);
			Rectangle tunaButton = new Rectangle(409, 385, 35, 35);
			Rectangle lobsterButton = new Rectangle(449, 387, 38, 33);
			Rectangle swordfishButton = new Rectangle(322, 423, 39, 33);
			Rectangle monkfishButton = new Rectangle(364, 425, 37, 31);
			Rectangle sharkButton = new Rectangle(406, 425, 38, 30);
			if (troutButton.contains(e.getPoint())) {
				foodWithdraw = 333;
				foodUsed = "Trout";
			} else if (salmonButton.contains(e.getPoint())) {
				foodWithdraw = 329;
				foodUsed = "Salmon";
			} else if (tunaButton.contains(e.getPoint())) {
				foodWithdraw = 361;
				foodUsed = "Tuna";
			} else if (lobsterButton.contains(e.getPoint())) {
				foodWithdraw = 379;
				foodUsed = "Lobster";
			} else if (swordfishButton.contains(e.getPoint())) {
				foodWithdraw = 373;
				foodUsed = "Swordfish";
			} else if (monkfishButton.contains(e.getPoint())) {
				foodWithdraw = 7946;
				foodUsed = "Monkfish";
			} else if (sharkButton.contains(e.getPoint())) {
				foodWithdraw = 385;
				foodUsed = "Shark";
			}
			Rectangle yesDungButton = new Rectangle(280, 416, 12, 11);
			Rectangle noDungButton = new Rectangle(280, 436, 12, 11);
			if (yesDungButton.contains(e.getPoint())) {
				resDung = true;
			} else if (noDungButton.contains(e.getPoint())) {
				resDung = false;
			}

			Rectangle safeSpotButton = new Rectangle(88, 390-10, 12, 11);
			if(safeSpotButton.contains(e.getPoint())) {
				safeSpot = booleanToggle(safeSpot);
			}
		} else if (!start && started) {
			Rectangle bonesButton = createRectangle(156 + 84, 390);
			Rectangle charmsButton = createRectangle(156 + 84, 375);
			Rectangle limpwurtButton = createRectangle(156 + 84, 405);
			Rectangle waterButton = createRectangle(400, 360);
			Rectangle fireButton = createRectangle(400, 375);
			Rectangle cosmicButton = createRectangle(400, 390);
			Rectangle lawButton = createRectangle(400, 405);
			Rectangle mindButton = createRectangle(400, 420);
			Rectangle chaosButton = createRectangle(400, 435);
			Rectangle natureButton = createRectangle(400, 450);
			Rectangle deathButton = createRectangle(400, 465);
			Rectangle gemButton = createRectangle(156 + 84, 420);
			Rectangle steelAButton = createRectangle(156 + 84, 435);
			Rectangle ironAButton = createRectangle(156 + 84, 450);
			Rectangle yesBuryButton = createRectangle(32, 400);
			Rectangle noBuryButton = createRectangle(32, 417);

			if (bonesButton.contains(e.getPoint())) {
				lootBones = booleanToggle(lootBones);
			}
			if (charmsButton.contains(e.getPoint())) {
				lootCharms = booleanToggle(lootCharms);
			}
			if (limpwurtButton.contains(e.getPoint())) {
				lootLimpwurt = booleanToggle(lootLimpwurt);
			}
			if (waterButton.contains(e.getPoint())) {
				lootWater = booleanToggle(lootWater);
			}
			if (fireButton.contains(e.getPoint())) {
				lootFire = booleanToggle(lootFire);
			}
			if (cosmicButton.contains(e.getPoint())) {
				lootCosmic = booleanToggle(lootCosmic);
			}
			if (lawButton.contains(e.getPoint())) {
				lootLaw = booleanToggle(lootLaw);
			}
			if (mindButton.contains(e.getPoint())) {
				lootMind = booleanToggle(lootMind);
			}
			if (chaosButton.contains(e.getPoint())) {
				lootChaos = booleanToggle(lootChaos);
			}
			if (natureButton.contains(e.getPoint())) {
				lootNature = booleanToggle(lootNature);
			}
			if (deathButton.contains(e.getPoint())) {
				lootDeath = booleanToggle(lootDeath);
			}
			if (gemButton.contains(e.getPoint())) {
				lootGems = booleanToggle(lootGems);
			}
			if (steelAButton.contains(e.getPoint())) {
				lootSteelA = booleanToggle(lootSteelA);
			}
			if (ironAButton.contains(e.getPoint())) {
				lootIronA = booleanToggle(lootIronA);
			}
			if (yesBuryButton.contains(e.getPoint())) {
				yesBury = true;
				noBury = false;
			} else if (noBuryButton.contains(e.getPoint())) {
				noBury = true;
				yesBury = false;
			}

			Rectangle startButton = new Rectangle(15, 447, 130, 22);
			if (startButton.contains(e.getPoint())) {
				start = true;
				lootItems = new ArrayList<Integer>();
				addLoot(lootBones, 532);
				if (lootCharms) {
					lootItems.add(12158);
					lootItems.add(12160);
					lootItems.add(12163);
					lootItems.add(12159);
				}
				addLoot(lootLimpwurt, 225);
				addLoot(lootWater, 555);
				addLoot(lootFire, 554);
				addLoot(lootCosmic, 564);
				addLoot(lootLaw, 563);
				addLoot(lootMind, 558);
				addLoot(lootChaos, 562);
				addLoot(lootNature, 561);
				addLoot(lootDeath, 560);
				if (lootGems) {
					lootItems.add(1617);
					lootItems.add(1619);
					lootItems.add(1621);
					lootItems.add(1623);
				}
				addLoot(lootSteelA, 886);
				addLoot(lootIronA, 884);
			}
		}
	}

	public void addLoot(boolean option, int itemID) {
		if (option) {
			lootItems.add(itemID);
		}
	}

	public boolean booleanToggle(boolean option) {
		if (option) {
			return false;
		} else if (!option) {
			return true;
		}
		return false;
	}

	private Rectangle createRectangle(int x, int y) {
		return new Rectangle(x + 30, y - 10, 12, 11);
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
	public void messageReceived(MessageEvent e) {
		if (e.getMessage().contains("You bury the bones"))
			bonesBuried++;
		if (e.getMessage().contains("Dungeoneering level of 20") && resDung) {
			log("Using Resource dungeon and your level isn't high enough");
			game.logout(true);
			stopScript();
		}
	}
}

