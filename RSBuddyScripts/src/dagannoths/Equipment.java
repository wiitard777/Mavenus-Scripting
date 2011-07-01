package dagannoths;

import com.rsbuddy.script.methods.Players;

/**
 * Equipment class. This class obtains the items using the getAppearence()
 * method.
 * 
 * @author Joris
 * 
 */
public class Equipment {
    public final static int BODY = 4;
    public final static int HELMET = 0;
    public final static int SHIELD = 5;
    public final static int CAPE = 1;
    public final static int BOOTS = 10;
    public final static int GLOVES = 9;
    public final static int AMULET = 2;
    public final static int WEAPON = 3;
    public final static int LEGS = 7;

    private final static int[] ALL = {BODY, HELMET, SHIELD, CAPE, BOOTS, GLOVES, AMULET, WEAPON,
            LEGS};

    public boolean contains(final int... ids) {
        final int[] items = getItems();
        Top: for (final int id : ids) {
            for (final int item : items) {
                if (id == item) {
                    continue Top;
                }
            }
            return false;
        }
        return true;
    }

    public boolean containsOneOf(final int... ids) {
        final int[] items = getItems();
        for (final int id : ids) {
            for (final int item : items) {
                if (id == item) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Will return a array of ids of the items, the player is currently
     * wielding.
     * 
     * @return array of ids. It'll never return null;
     */
    public int[] getItems() {
        int[] ids = new int [0];
        final int[] array = Players.getLocal().getAppearance();
        for (int index : ALL) {
            if (array == null || array.length <= index) {
                continue;
            }
            final int id = array[index];
            if ((id & 0x40000000) != 0) {
                int[] temp = ids;
                ids = new int [temp.length + 1];
                System.arraycopy(temp, 0, ids, 0, temp.length);
                ids[ids.length - 1] = id - 0x40000000;
            }
        }
        return ids;
    }

    /**
     * Will return the id of the given slot. If the slot doesn't contain an item, it'll
     * return -1.
     * 
     * @param index
     *            the slot number.
     * @return the id of the item.
     */
    public int getItem(final int index) {
        final int[] array = Players.getLocal().getAppearance();
        if (array == null || array.length <= index) {
            return -1;
        }
        final int id = array[index];
        return (id & 0x40000000) != 0 ? id - 0x40000000 : -1;

    }

}