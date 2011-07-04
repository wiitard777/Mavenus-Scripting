package slayer.util;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;

import org.rsbuddy.tabs.Inventory;
import org.rsbuddy.widgets.Bank;

import com.rsbuddy.script.methods.Calculations;
import com.rsbuddy.script.methods.Camera;
import com.rsbuddy.script.methods.Game;
import com.rsbuddy.script.methods.Mouse;
import com.rsbuddy.script.methods.Npcs;
import com.rsbuddy.script.methods.Objects;
import com.rsbuddy.script.methods.Players;
import com.rsbuddy.script.methods.Walking;
import com.rsbuddy.script.util.Random;
import com.rsbuddy.script.wrappers.Area;
import com.rsbuddy.script.wrappers.GameObject;
import com.rsbuddy.script.wrappers.Item;
import com.rsbuddy.script.wrappers.Npc;
import com.rsbuddy.script.wrappers.Tile;

/**
 * Created by IntelliJ IDEA.
 * User: Sakura
 * Date: 7/2/11
 * Time: 6:35 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * AIBanking, a wonderful class, which automatically thinks what items to
 * withdraw, deposits and even sorts the items at the end. The only thing that
 * you have to do to use this all in one banker. Make a list of banking parts
 * and use them as parameter in the contructer. After you've constructed the
 * instance invoke the method run and it'll do a banking loop. This'll also open
 * the bank, if it's currently not open. Please be aware, that this was a hell
 * lot of work to create, so credits must be given. And you MUST inform me, when
 * you're using this for a paid script.
 *
 * @author Joris
 *
 */
public class SakBank {
    private final BankingPart[] parts;
    private CorrespondingInventory inventory;

    /**
     * Construct a AIBanking instance. The only thing that you have to do to use
     * this all in one banker. Make a list of banking parts and use them in the
     * contructer. After you've constructed the instance invoke the method run
     * and it'll do a banking loop.
     *
     * @param bankingParts
     *            the bankingparts
     *
     *
     * @throws IllegalArgumentException
     *             will throw an exception if bankingParts == null ||
     *             bankingParts is empty.
     */
    public SakBank(BankingPart... bankingParts) throws IllegalArgumentException {
        if (bankingParts == null || bankingParts.length == 0) {
            throw new IllegalArgumentException();
        }
        this.parts = bankingParts;
    }

    /**
     * Run the banking loop.
     */
    public void run() {
        for (BankingPart[] parts = getParts(); parts != null; parts = getParts()) {
            for (BankingPart part : parts) {
                if (!Bank.isOpen()) {
                    if (!openBank()) {
                        return;
                    } else {
                        break;
                    }
                }
                switch (part.getEvent()) {
                    case DEPOSIT_ALL:
                        depositAll();
                        break;
                    case DEPOSIT:
                        deposit(part);
                        break;
                    case WITHDRAW:
                        withdraw(part);
                        break;
                }
            }
        }

        if (getParts() == null) {
            sort();
            sleep(400, 600);
        }

        Bank.close();
        for (int i = 0; i < 13 && Bank.isOpen(); i++) {
            sleep(85, 115);
        }
    }

    /**
     * Return the banking actions, the script stil has to do.
     *
     * @return the banking parts
     */
    private BankingPart[] getParts() {
        inventory = new CorrespondingInventory();
        return inventory.getParts();
    }

    /**
     * Sort the inventory.
     */
    private void sort() {
        inventory = new CorrespondingInventory();
        if (inventory.getParts() == null) {
            Top: while (true) {
                for (int i = 0; i < 28; i++) {

                    if (inventory.items[i] == null) {
                        if (inventory.currInv[i] == null) {
                            continue;
                        }
                        for (int a = i + 1; a < 28; a++) {
                            if (inventory.currInv[a] == null && inventory.items[a] != null
                                    && inventory.items[a].getId() == inventory.currInv[i].getId()) {
                                dragItem(i, a);
                                inventory = new CorrespondingInventory();
                                continue Top;
                            }
                        }
                        for (int a = i + 1; a < 28; a++) {
                            if (inventory.currInv[a] == null) {
                                dragItem(i, a);
                                inventory = new CorrespondingInventory();
                                continue Top;
                            }
                        }
                    }

                    else if (inventory.currInv[i] == null) {
                        if (inventory.items[i] == null) {
                            continue;
                        }
                        for (int a = i + 1; a < 28; a++) {
                            if (inventory.currInv[a] != null
                                    && inventory.currInv[a].getId() == inventory.items[i].getId()
                                    && (inventory.items[a] == null || (inventory.items[a].getId() != inventory.currInv[a].getId()))) {
                                dragItem(a, i);
                                inventory = new CorrespondingInventory();
                                continue Top;
                            }
                        }
                    }

                    else if (inventory.currInv[i].getId() != inventory.items[i].getId()) {
                        for (int a = i + 1; a < 28; a++) {
                            if (inventory.currInv[a] != null
                                    && inventory.currInv[a].getId() == inventory.items[i].getId()
                                    && inventory.items[a] != null
                                    && inventory.items[a].getId() == inventory.currInv[i].getId()) {
                                dragItem(i, a);
                                inventory = new CorrespondingInventory();
                                continue Top;
                            }
                        }
                        for (int a = i + 1; a < 28; a++) {
                            if (inventory.currInv[a] != null
                                    && inventory.currInv[a].getId() == inventory.items[i].getId()) {
                                dragItem(i, a);
                                inventory = new CorrespondingInventory();
                                continue Top;
                            }
                        }
                    }

                }
                break;
            }
        }
    }

    /**
     * Drag items from one index to another.
     *
     * @param firstIndex
     *            the index to start the drag.
     * @param secIndex
     *            the index to end the drag
     */
    private void dragItem(final int firstIndex, final int secIndex) {
        final Point first = Inventory.getItemAt(firstIndex).getComponent().getAbsLocation();
        final Point second = Inventory.getItemAt(secIndex).getComponent().getAbsLocation();
        if (first != null && second != null) {
            Mouse.move(first);
            sleep(50, 75);
            Mouse.drag(second);
            sleep(350, 450);
        }
    }

    /**
     * This will open the bank.
     *
     * @return
     */
    private boolean openBank() {
        ReachableArea area = new ReachableArea();
        GameObject bankBooth = Objects.getNearest(Bank.BANK_BOOTHS);
        Npc banker = Npcs.getNearest(Bank.BANKERS);
        GameObject bankChest = Objects.getNearest(Bank.BANK_CHESTS);
        if ((bankBooth != null) && area.isReachable(bankBooth)
                && Calculations.isTileOnScreen(bankBooth.getLocation())) {
            if (bankBooth.interact("Use-Quickly")) {
                for (int count = 0; count < 10 && !Bank.isOpen(); count++) {
                    sleep(200, 400);
                    if (Players.getLocal().isMoving()) {
                        count = 0;
                    }
                }
            } else {
                Camera.turnTo(bankBooth);
            }
            return true;
        } else if ((banker != null) && area.isReachable(banker)
                && Calculations.isTileOnScreen(banker.getLocation())) {
            if (banker.interact("Bank ")) {
                int count = 0;
                while (!Bank.isOpen() && ++count < 10) {
                    sleep(200, 400);
                    if (Players.getLocal().isMoving()) {
                        count = 0;
                    }
                }
            } else {
                Camera.turnTo(banker, 20);
            }
            return true;
        } else if ((bankChest != null) && area.isReachable(bankChest)
                && Calculations.isTileOnScreen(bankChest.getLocation())) {
            if (bankChest.interact("Bank Chest")) {
                int count = 0;
                while (!Bank.isOpen() && ++count < 10) {
                    sleep(200, 400);
                    if (Players.getLocal().isMoving()) {
                        count = 0;
                    }
                }
            } else {
                Camera.turnTo(bankChest);
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Deposits an item.
     *
     * @param part
     *            the bankingpart, with the required information.
     */
    private void deposit(BankingPart part) {
        if (part.getArray().length < 2) {
            return;
        }

        int deposit = part.getArray()[1];
        if (deposit == 0) {
            Bank.deposit(part.getArray()[0], 0);
            sleep(1000, 1200);
            deposit = 0;
        }
        while (deposit != 0) {
            if (deposit >= Inventory.getCount(true, part.getArray()[0])) {
                Bank.deposit(part.getArray()[0], 0);
                sleep(1000, 1200);
                deposit = 0;
            } else if (deposit >= 51) {
                Bank.deposit(part.getArray()[0], part.getArray()[1]);
                sleep(1000, 1200);
                deposit = 0;
            } else if (deposit >= 10) {
                Bank.deposit(part.getArray()[0], 10);
                sleep(1000, 1200);
                deposit -= 10;
            } else if (deposit >= 5) {
                Bank.deposit(part.getArray()[0], 5);
                sleep(1000, 1200);
                deposit -= 5;
            } else if (deposit >= 1) {
                Bank.deposit(part.getArray()[0], 1);
                sleep(1000, 1200);
                deposit -= 1;
            }
        }
    }

    /**
     * Clicks the deposit button.
     */
    private void depositAll() {
        Bank.depositAll();
        sleep(1200, 1300);
    }

    /**
     * Withdraws an item.
     *
     * @param part
     *            the bankingpart, with the given information.
     */
    private void withdraw(BankingPart part) {
        if (part.getArray().length < 2) {
            return;
        }

        int withdraw = part.getArray()[1];
        while (withdraw != 0) {
            if (Inventory.getItem(part.getArray()[0]) == null) {
                return;
            }

            if (withdraw >= Bank.getCount(part.getArray()[0])) {
                Bank.withdraw(part.getArray()[0], 0);
                sleep(1000, 1200);
                withdraw = 0;
            } else if (withdraw >= 51) {
                Bank.withdraw(part.getArray()[0], part.getArray()[1]);
                sleep(1000, 1200);
                withdraw = 0;
            } else if (withdraw >= 10) {
                Bank.withdraw(part.getArray()[0], 10);
                sleep(1000, 1200);
                withdraw -= 10;
            } else if (withdraw >= 5) {
                Bank.withdraw(part.getArray()[0], 5);
                sleep(1000, 1200);
                withdraw -= 5;
            } else if (withdraw >= 1) {
                Bank.withdraw(part.getArray()[0], 1);
                sleep(1000, 1200);
                withdraw -= 1;
            }
        }
    }

    private void sleep(final int min, final int max) {
        sleep(Random.nextInt(min, max));
    }

    private void sleep(final int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
        }
    }

    /**
     * A class, which represents a part to perform.
     *
     * @author Joris
     *
     */
    public final static class BankingPart {
        private final BankingEvent event;
        private final int[] array;

        /**
         * Construct an banking part needed to activate this special piece of
         * code.
         *
         * @param event
         *            the Banking event.
         * @param array
         *            the array of integers needed to peform the action.
         * @throws IllegalArgumentException
         *             will throw exception if event == null || array == null ||
         *             the required amount of integers in the array is not
         *             enough.
         */
        public BankingPart(final BankingEvent event, final int... array)
                throws IllegalArgumentException {

            if (event == null || array == null || array.length < event.getNeeded()) {
                throw new IllegalArgumentException();
            }
            this.event = event;
            this.array = array;
        }

        /**
         * Get the banking event.
         *
         * @return the banking event
         */
        private BankingEvent getEvent() {
            return event;
        }

        /**
         * Gets the array of integers.
         *
         * @return array of integers.
         */
        private int[] getArray() {
            return array;
        }

    }

    public static enum BankingEvent {
        /**
         * Will deposit all items in your inventory.
         */
        DEPOSIT_ALL(0),
        /**
         * Will deposit all items in your inventory, except the items listed in
         * that array.
         */
        DEPOSIT_ALL_EXCEPT(1),
        /**
         * Deposit an item. Requires the id of the item.
         */
        DEPOSIT(1),

        /**
         * Will get the amount of items requested. Nothing more and nothing
         * less. The first number in the array will be the id of the item, and
         * the second number will be the amount to withdraw.
         */
        WITHDRAW(2),

        /**
         * Withdraw the item, if the inventory doesn't contain that item. The
         * first number should be the id of the item to withdraw, and the rest
         * of the ids should be the check. This could come in handy, if you're
         * using teleporting jewelery.
         */
        WITHDRAW_IF_INVENTORY_DOESNT_CONTAIN(2);

        final private int needed;

        BankingEvent(final int needed) {
            this.needed = needed;
        }

        /**
         * The required amount of integers necessary to perform the event.
         *
         * @return amount of integers necessary.
         */
        private int getNeeded() {
            return needed;
        }
    }

    private class CorrespondingInventory {

        private Item[] items = new Item [28];
        private final Item[] currInv;

        private CorrespondingInventory() {
            currInv = new Item [28];
            for (int i = 0; i < 28; i++) {
                if (Inventory.getItemAt(i).getId() > -1) {
                    items[i] = Inventory.getItemAt(i);
                    currInv[i] = Inventory.getItemAt(i);
                }
            }
            initialize();
        }

        private void initialize() {
            for (BankingPart part : parts) {
                switch (part.getEvent()) {
                    case WITHDRAW:
                        boolean stack = isStackable(part.getArray()[0]);
                        for (int i = 0; i < 28; i++) {
                            if (getItemAt(i) != null && getItemAt(i).getId() == part.getArray()[0]) {
                                setItem(i, null);
                            }
                        }
                        if (part.getArray()[1] == 0) {
                            for (int i = 0; i < 28; i++) {
                                if (getItemAt(i) == null) {
                                    setItem(i, new Item(part.getArray()[0], 1));
                                }
                            }
                            break;
                        }
                        for (int i = 0; i < part.getArray()[1] && (i == 0 || !stack); i++) {
                            addItem(new Item(part.getArray()[0], stack ? part.getArray()[1] : 1));
                        }
                        break;
                    case DEPOSIT:
                        for (int i = 0; i < 28; i++) {
                            if (getItemAt(i) != null && getItemAt(i).getId() == part.getArray()[0]) {
                                setItem(i, null);
                            }
                        }
                        break;
                    case DEPOSIT_ALL:
                        for (int i = 0; i < 28; i++) {
                            setItem(i, null);
                        }
                        break;
                    case DEPOSIT_ALL_EXCEPT:
                        for (int i = 0; i < 28; i++) {
                            Item item = null;
                            for (int id : part.getArray()) {

                                if (getItemAt(i) != null && getItemAt(i).getId() == id) {
                                    item = getItemAt(i);
                                    break;
                                }
                            }
                            setItem(i, item);

                        }
                        break;
                    case WITHDRAW_IF_INVENTORY_DOESNT_CONTAIN:
                        int[] check = new int [part.getArray().length - 1];
                        System.arraycopy(part.getArray(), 1, check, 0, part.getArray().length - 1);
                        if (getCount(true, true, check) <= 0) {
                            addItem(new Item(part.getArray()[0], 1));
                        }
                        break;

                }
            }
        }

        private BankingPart[] getParts() {
            ArrayList<Integer> array = new ArrayList<Integer>();
            ArrayList<BankingPart> parts = new ArrayList<BankingPart>();
            for (Item itemInv : currInv) {
                if (itemInv == null || array.contains(itemInv.getId())) {
                    continue;
                }

                final int id = itemInv.getId();
                final int countInv = getCount(true, false, id);
                final int countSug = getCount(true, true, id);
                final boolean stack = isStackable(id);
                if ((!stack && countInv - countSug > 0) || (stack && countSug == 0)) {
                    final int amount = stack || countSug == 0 ? 0 : countInv - countSug;
                    parts.add(new BankingPart(BankingEvent.DEPOSIT, id, amount));
                }

                array.add(id);
            }
            array.clear();
            for (Item itemSug : items) {
                if (itemSug == null || array.contains(itemSug.getId())) {
                    continue;
                }

                final int id = itemSug.getId();
                final int countInv = getCount(true, false, id);
                final int countSug = getCount(true, true, id);
                if (countSug - countInv > 0) {
                    parts.add(new BankingPart(BankingEvent.WITHDRAW, id, countSug - countInv));
                }

                array.add(id);
            }
            return parts.isEmpty() ? null : parts.toArray(new BankingPart [parts.size()]);
        }

        private Item getItemAt(final int index) {
            return index > -1 && index < 28 ? items[index] : null;
        }

        private void setItem(final int index, final Item item) {
            if (index > -1 && index < 28) {
                items[index] = item;
            }
        }

        private void addItem(final Item item) {
            for (int i = 0; i < items.length; i++) {
                if (items[i] == null) {
                    items[i] = item;
                    return;
                }
            }
        }

        private int getCount(final boolean includeStacks, final boolean suggestedInventory, final int... ids) {
            int amount = 0;
            for (int i = 0; i < 28; i++) {
                for (int id : ids) {
                    if (suggestedInventory) {
                        if (items[i] != null && items[i].getId() == id) {
                            amount += includeStacks ? items[i].getStackSize() : 1;
                            break;
                        }
                    } else {
                        if (currInv[i] != null && currInv[i].getId() == id) {
                            amount += includeStacks ? currInv[i].getStackSize() : 1;
                            break;
                        }
                    }

                }
            }
            return amount;
        }

        private boolean isStackable(final int id) {
            for (int i = 0; i < 28; i++) {
                if (currInv[i] != null && currInv[i].getId() == id && currInv[i].getStackSize() > 1) {
                    return true;
                }
            }
            return false;
        }

    }

    /**
     * ReachableArea, originally made to see what tiles the player is able to
     * access. There are special methods to see if certain npcs/objects, the
     * player is able to access. This class is ripped version of the original. I
     * added it to this class, so that people using AIBanking could use it. I'll
     * create a new thread for this class though.
     *
     * @author Joris
     *
     */
    private class ReachableArea {
        private Tile base;
        private Tile max;
        private Tile[] tiles;
        private boolean[][] booleans;

        final static int BLOCK_0 = 0x100;
        final static int BLOCK_1 = 0x40000;
        final static int BLOCK_2 = 0x200000;
        final static int BLOCK_3 = 0x40000000;

        final static int BLOCK_0_NORTH = 0x2;
        final static int BLOCK_0_EAST = 0x8;
        final static int BLOCK_0_SOUTH = 0x20;
        final static int BLOCK_0_WEST = 0x80;
        final static int BLOCK_1_NORTH = 0x400;
        final static int BLOCK_1_EAST = 0x1000;
        final static int BLOCK_1_SOUTH = 0x4000;
        final static int BLOCK_1_WEST = 0x10000;
        final static int BLOCK_2_NORTH = 0x800000;
        final static int BLOCK_2_EAST = 0x2000000;
        final static int BLOCK_2_SOUTH = 0x8000000;
        final static int BLOCK_2_WEST = 0x20000000;
        final static int FULL_BLOCK = BLOCK_0 | BLOCK_1 | BLOCK_2 | BLOCK_3;

        final static int BLOCK_NORTH = FULL_BLOCK | BLOCK_0_NORTH | BLOCK_1_NORTH | BLOCK_2_NORTH;

        final static int BLOCK_SOUTH = FULL_BLOCK | BLOCK_0_SOUTH | BLOCK_1_SOUTH | BLOCK_2_SOUTH;

        final static int BLOCK_WEST = FULL_BLOCK | BLOCK_0_WEST | BLOCK_1_WEST | BLOCK_2_WEST;

        final static int BLOCK_EAST = FULL_BLOCK | BLOCK_0_EAST | BLOCK_1_EAST | BLOCK_2_EAST;

        /**
         * Constructs an area of tiles, which is currently able to access.
         */
        private ReachableArea() {
            initArea(null, -1);
            initBase();
        }

        /**
         * Checks if current area, includes one of given tiles.
         *
         * @param tiles
         *            an array of tiles,
         * @return true if one was in the area; otherwise false.
         */
        private boolean contains(final Tile... tiles) {
            if (booleans != null && base != null && max != null && tiles != null) {
                for (Tile tile : tiles) {
                    if (tile.getX() - base.getX() >= 0 && max.getX() - tile.getX() >= 0) {
                        if (tile.getY() - base.getY() >= 0 && max.getY() - tile.getY() >= 0) {
                            if (booleans[tile.getX() - base.getX()][tile.getY() - base.getY()]) {
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        }

        /**
         * Checks if gameobject is accessible
         *
         * @param object
         *            object to check.
         * @return true; if you're able to access current object.
         */
        private boolean isReachable(final GameObject object) {
            if (object != null && this.tiles != null) {
                return contains(getSurrounding(object.getArea()));
            }
            return false;
        }

        /**
         * Checks if npc is reachable
         *
         * @param npc
         *            npc to check
         * @return true if npc is reachable
         */
        private boolean isReachable(final Npc npc) {
            if (npc != null && this.tiles != null) {
                return contains(getSurrounding(new Area(new Tile [] {npc.getLocation()})));
            }
            return false;
        }

        /**
         * Initializes a area based upon tiles able to reach.
         *
         * @param centreTile
         *            if area should end in a circle; otherwise null.
         * @param radius
         *            radius of circle.
         */
        private void initArea(final Tile centreTile, final int radius) {
            final Tile curr = Players.getLocal().getLocation();
            final boolean[][] contains = new boolean [104] [104];
            final LinkedList<Tile> reachable = new LinkedList<Tile>();
            LinkedList<Tile> check = new LinkedList<Tile>();
            reachable.add(curr);
            check.add(curr);
            final int[][] blocks = Walking.getCollisionFlags(Game.getFloorLevel());
            final boolean area = centreTile != null;
            final int baseX = Game.getMapBase().getX() - 1;
            final int baseY = Game.getMapBase().getY() - 1;
            while (!check.isEmpty()) {
                final LinkedList<Tile> next = new LinkedList<Tile>();
                for (Tile t : check) {
                    for (Tile tile : new Tile [] {new Tile(t.getX() - 1, t.getY()),
                            new Tile(t.getX() + 1, t.getY()), new Tile(t.getX(), t.getY() - 1),
                            new Tile(t.getX(), t.getY() + 1)}) {
                        int i = tile.getX() - baseX, j = tile.getY() - baseY;
                        if (i < 1 || i > 102 || j < 1 || j > 102 || contains[i][j]) {
                            continue;
                        }
                        if (area && Calculations.distanceBetween(tile, centreTile) > radius) {
                            continue;
                        }

                        final int curBlock = blocks[i][j];

                        if ((curBlock & FULL_BLOCK) != 0) {
                            continue;
                        }
                        if (t.getY() < tile.getY()) {
                            if (((blocks[i][j - 1] & BLOCK_NORTH) != 0)
                                    || ((curBlock & BLOCK_SOUTH) != 0)) {
                                continue;
                            }
                        } else if (t.getY() > tile.getY()) {
                            if (((blocks[i][j + 1] & BLOCK_SOUTH) != 0)
                                    || ((curBlock & BLOCK_NORTH) != 0)) {
                                continue;
                            }
                        } else if (t.getX() < tile.getX()) {
                            if (((blocks[i - 1][j] & BLOCK_EAST) != 0)
                                    || ((curBlock & BLOCK_WEST) != 0)) {
                                continue;
                            }
                        } else if (t.getX() > tile.getX()) {
                            if (((blocks[i + 1][j] & BLOCK_WEST) != 0)
                                    || ((curBlock & BLOCK_EAST) != 0)) {
                                continue;
                            }
                        }
                        reachable.add(tile);
                        next.add(tile);
                        contains[i][j] = true;

                    }
                }
                check = next;
            }
            this.tiles = reachable.toArray(new Tile [reachable.size()]);
        }

        /**
         * Initializes an array of booleans to cut the time to check for tiles.
         */
        private void initBase() {
            int minX = 9999999;
            int minY = 9999999;
            int maxX = 0;
            int maxY = 0;
            for (Tile tile : this.tiles) {
                minX = tile.getX() < minX ? tile.getX() : minX;
                minY = tile.getY() < minY ? tile.getY() : minY;
                maxX = tile.getX() > maxX ? tile.getX() : maxX;
                maxY = tile.getY() > maxY ? tile.getY() : maxY;
            }
            if (minX > maxX || minY > maxY) {
                return;
            }
            this.base = new Tile(minX, minY);
            this.max = new Tile(maxX, maxY);
            this.booleans = new boolean [maxX - minX + 1] [maxY - minY + 1];
            for (Tile tile : this.tiles) {
                this.booleans[tile.getX() - base.getX()][tile.getY() - base.getY()] = true;
            }
        }

        /**
         * Returns the tiles where you are able to interact with tiles within
         * the area.
         *
         * @param area
         *            area to interact with.
         * @return tiles where you can interact with area.
         */
        private Tile[] getSurrounding(Area area) {
            ArrayList<Tile> array = new ArrayList<Tile>();
            final int[][] blocks = Walking.getCollisionFlags(Game.getFloorLevel());
            int minX = area.getX() - 1, maxX = area.getX() + area.getWidth(), minY = area.getY() - 1, maxY = area.getY()
                    + area.getHeight();

            for (int x = minX; x <= maxX; x++) {
                for (int y = minY; y <= maxY; y++) {
                    if (((x == minX ? 1 : 0) + (x == maxX ? 1 : 0) + (y == minY ? 1 : 0) + (y == maxY ? 1 : 0)) == 1) {
                        int i = x - Game.getMapBase().getX() + 1, j = y - Game.getMapBase().getY()
                                + 1;
                        if (i < 1 || i > 100 || j < 1 || j > 100) {
                            continue;
                        }

                        final int curBlock = blocks[i][j];

                        if ((curBlock & FULL_BLOCK) != 0) {
                            continue;
                        }

                        if (Objects.getAllAt(new Tile(x, y)).length > 0) {

                            if (y == maxY) {
                                if ((curBlock & BLOCK_SOUTH) != 0) {
                                    continue;
                                }
                            } else if (y == minY) {
                                if ((curBlock & BLOCK_NORTH) != 0) {
                                    continue;
                                }
                            } else if (x == maxX) {
                                if ((curBlock & BLOCK_WEST) != 0) {
                                    continue;
                                }
                            } else if (x == minX) {
                                if ((curBlock & BLOCK_EAST) != 0) {
                                    continue;
                                }
                            }

                        }

                        array.add(new Tile(x, y));
                    }
                }
            }
            return array.toArray(new Tile [array.size()]);
        }
    }

}
