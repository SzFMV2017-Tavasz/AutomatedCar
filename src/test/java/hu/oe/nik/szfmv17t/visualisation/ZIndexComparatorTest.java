package hu.oe.nik.szfmv17t.visualisation;

import hu.oe.nik.szfmv17t.environment.domain.Road;
import hu.oe.nik.szfmv17t.environment.domain.Tree;
import hu.oe.nik.szfmv17t.environment.interfaces.IWorldObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by winifred on 2017.04.03..
 */
public class ZIndexComparatorTest {

    private IWorldObject road1;
    private IWorldObject road2;
    private IWorldObject tree;
    private ZIndexComparator zIndexComparator;

    @Before
    public void setUp() {
        zIndexComparator = new ZIndexComparator();
        road1 = new Road(100, 100, 350, 350, 0, 0, "road_2lane_straight.png", 0, 0, 0, 0);
        road2 = new Road(200, 200, 350, 350, 0, 0, "road_2lane_straight.png", 0, 0, 0, 0);
        tree = new Tree(0, 0, 144, 162, 0, 1, "tree.png", 0, 0, 0);
    }

    @Test
    public void ZIndexCompareZeroTest() {
        int result = zIndexComparator.compare(road1, road2);
        assertEquals(0, result);
    }

    @Test
    public void ZIndexCompareLessThanZeroTest() {
        int result = zIndexComparator.compare(road1, tree);
        assertEquals(-1, result);
    }

    @Test
    public void ZIndexCompareGreaterThanZero() {
        int result = zIndexComparator.compare(tree, road1);
        assertEquals(1, result);
    }
}
