package de.mrapp.apriori;

import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * Tests the functionality of the class {@link ItemSet}.
 *
 * @author Michael Rapp
 */
public class ItemSetTest {

    /**
     * Tests, if all class members are set correctly by the default constructor.
     */
    @Test
    public final void testDefaultConstructor() {
        ItemSet<NamedItem> itemSet = new ItemSet<>();
        assertEquals(0, itemSet.getSupport(), 0);
        assertTrue(itemSet.isEmpty());
    }

    /**
     * Tests, if all class members are set correctly by the constructor, which expects another item
     * set as an argument.
     */
    @Test
    public final void testConstructorWithItemSetArgument() {
        NamedItem item = new NamedItem("a");
        ItemSet<NamedItem> itemSet1 = new ItemSet<>();
        itemSet1.setSupport(0.5);
        itemSet1.add(item);
        ItemSet<NamedItem> itemSet2 = new ItemSet<>(itemSet1);
        assertEquals(itemSet1.getSupport(), itemSet2.getSupport(), 0);
        assertEquals(itemSet1.size(), itemSet2.size());
        assertEquals(item, itemSet2.first());
    }

    /**
     * Tests the functionality of the method, which allows to set the support of an item set.
     */
    @Test
    public final void testSetSupport() {
        double support = 0.5;
        ItemSet<NamedItem> itemSet = new ItemSet<>();
        itemSet.setSupport(support);
        assertEquals(support, itemSet.getSupport(), 0);
    }

    /**
     * Tests the functionality of the method, which allows to add new items to an item set.
     */
    @Test
    public final void testAdd() {
        NamedItem item1 = new NamedItem("c");
        NamedItem item2 = new NamedItem("a");
        NamedItem item3 = new NamedItem("b");
        ItemSet<NamedItem> itemSet = new ItemSet<>();
        assertTrue(itemSet.isEmpty());
        itemSet.add(item1);
        itemSet.add(item2);
        itemSet.add(item3);
        assertEquals(3, itemSet.size());
        Iterator<NamedItem> iterator = itemSet.iterator();
        assertEquals(item2, iterator.next());
        assertEquals(item3, iterator.next());
        assertEquals(item1, iterator.next());
        assertFalse(iterator.hasNext());
    }

    /**
     * Tests the functionality of the method, which allows to remove items from an item set.
     */
    @Test
    public final void testRemove() {
        NamedItem item1 = new NamedItem("c");
        NamedItem item2 = new NamedItem("a");
        NamedItem item3 = new NamedItem("b");
        ItemSet<NamedItem> itemSet = new ItemSet<>();
        itemSet.add(item1);
        itemSet.add(item2);
        itemSet.add(item3);
        assertEquals(3, itemSet.size());
        itemSet.remove(item3);
        assertEquals(2, itemSet.size());
        Iterator<NamedItem> iterator = itemSet.iterator();
        assertEquals(item2, iterator.next());
        assertEquals(item1, iterator.next());
        assertFalse(iterator.hasNext());
    }

    /**
     * Tests the functionality of the method, which allows to check, whether an item is contained by
     * an item set, or not.
     */
    @Test
    public final void testContains() {
        NamedItem item1 = new NamedItem("a");
        NamedItem item2 = new NamedItem("b");
        ItemSet<NamedItem> itemSet = new ItemSet<>();
        itemSet.add(item1);
        assertEquals(1, itemSet.size());
        assertTrue(itemSet.contains(item1));
        assertFalse(itemSet.contains(item2));
        itemSet.clear();
        assertTrue(itemSet.isEmpty());
        assertFalse(itemSet.contains(item1));
        assertFalse(itemSet.contains(item2));
    }

    /**
     * Tests the functionality of the toString-method.
     */
    @Test
    public final void testToString() {
        NamedItem item1 = new NamedItem("a");
        NamedItem item2 = new NamedItem("b");
        ItemSet<NamedItem> itemSet = new ItemSet<>();
        itemSet.add(item1);
        itemSet.add(item2);
        assertEquals("[" + item1.getName() + ", " + item2.getName() + "]", itemSet.toString());
    }

    /**
     * Tests the functionality of the hashCode-method.
     */
    @Test
    public final void testHashCode() {
        ItemSet<NamedItem> itemSet1 = new ItemSet<>();
        ItemSet<NamedItem> itemSet2 = new ItemSet<>();
        assertEquals(itemSet1.hashCode(), itemSet1.hashCode());
        assertEquals(itemSet1.hashCode(), itemSet2.hashCode());
        itemSet1.add(new NamedItem("a"));
        assertNotEquals(itemSet1.hashCode(), itemSet2.hashCode());
        itemSet2.add(new NamedItem("a"));
        assertEquals(itemSet1.hashCode(), itemSet2.hashCode());
        itemSet1.add(new NamedItem("b"));
        assertNotEquals(itemSet1.hashCode(), itemSet2.hashCode());
    }

    /**
     * Tests the functionality of the equals-method.
     */
    @Test
    public final void testEquals() {
        ItemSet<NamedItem> itemSet1 = new ItemSet<>();
        ItemSet<NamedItem> itemSet2 = new ItemSet<>();
        assertFalse(itemSet1.equals(null));
        assertFalse(itemSet1.equals(new Object()));
        assertTrue(itemSet1.equals(itemSet1));
        assertTrue(itemSet1.equals(itemSet2));
        itemSet1.add(new NamedItem("a"));
        assertFalse(itemSet1.equals(itemSet2));
        itemSet2.add(new NamedItem("a"));
        assertTrue(itemSet1.equals(itemSet2));
        itemSet1.add(new NamedItem("b"));
        assertFalse(itemSet1.equals(itemSet2));
    }

}
