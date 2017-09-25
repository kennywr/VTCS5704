package river;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/*
 * (1) Most test methods start off by creating a local variable named engine.
 *     Instead of doing this, declare a (private) field named engine and
 *     initialize it in the setUp method.
 *
 * (2) Create a private method called transport, that loads the boat with an
 *     item, rows the boat, and unloads the boat. Use this method wherever you
 *     can to replace code that loads, rows, then unloads the boat.
 */

public class GameEngineTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testObjects() {
        GameObject farmer = new Farmer();
        GameObject wolf = new Wolf();
        GameObject goose = new Goose();
        GameObject beans = new Beans();
        Assert.assertEquals("Farmer", farmer.getName());
        Assert.assertEquals(Location.LEFT_BANK, farmer.getLocation());
        Assert.assertEquals("", farmer.getSound());
        Assert.assertEquals("Wolf", wolf.getName());
        Assert.assertEquals(Location.LEFT_BANK, wolf.getLocation());
        Assert.assertEquals("Howl", wolf.getSound());
        Assert.assertEquals("Goose", goose.getName());
        Assert.assertEquals(Location.LEFT_BANK, goose.getLocation());
        Assert.assertEquals("Honk", goose.getSound());
        Assert.assertEquals("Beans", beans.getName());
        Assert.assertEquals(Location.LEFT_BANK, beans.getLocation());
        Assert.assertEquals("", beans.getSound());
    }

    @Test
    public void testGooseTransport() {
        GameEngine engine = new GameEngine();
        Assert.assertEquals(Location.LEFT_BANK, engine.getLocation(Item.GOOSE));
        engine.loadBoat(Item.GOOSE);
        Assert.assertEquals(Location.BOAT, engine.getLocation(Item.GOOSE));
        engine.rowBoat();
        Assert.assertEquals(Location.BOAT, engine.getLocation(Item.GOOSE));
        engine.unloadBoat();
        Assert.assertEquals(Location.RIGHT_BANK, engine.getLocation(Item.GOOSE));
    }

    @Test
    public void testWinningGame() {

        GameEngine engine = new GameEngine();

        // transport the goose
        engine.loadBoat(Item.GOOSE);
        engine.rowBoat();
        engine.unloadBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // transport the wolf
        engine.loadBoat(Item.WOLF);
        engine.rowBoat();
        engine.unloadBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // transport the goose
        engine.loadBoat(Item.GOOSE);
        engine.rowBoat();
        engine.unloadBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // transport the beans
        engine.loadBoat(Item.BEANS);
        engine.rowBoat();
        engine.unloadBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // transport the goose
        engine.loadBoat(Item.GOOSE);
        engine.rowBoat();
        engine.unloadBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertTrue(engine.gameIsWon());
    }

    @Test
    public void testLosingGame() {

        GameEngine engine = new GameEngine();

        // transport the goose
        engine.loadBoat(Item.GOOSE);
        engine.rowBoat();
        engine.unloadBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // transport the wolf
        engine.loadBoat(Item.WOLF);
        engine.rowBoat();
        engine.unloadBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone
        engine.rowBoat();
        Assert.assertTrue(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());
    }

    @Test
    public void testError() {

        GameEngine engine = new GameEngine();

        // transport the goose
        engine.loadBoat(Item.GOOSE);
        engine.rowBoat();
        engine.unloadBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // save the state
        Location topLoc = engine.getLocation(Item.WOLF);
        Location midLoc = engine.getLocation(Item.GOOSE);
        Location bottomLoc = engine.getLocation(Item.BEANS);
        Location playerLoc = engine.getLocation(Item.FARMER);

        engine.loadBoat(Item.WOLF);

        // check that the state has not changed
        //Assert.assertEquals(topLoc, engine.getLocation(Item.WOLF));
        Assert.assertEquals(midLoc, engine.getLocation(Item.GOOSE));
        Assert.assertEquals(bottomLoc, engine.getLocation(Item.BEANS));
        Assert.assertEquals(playerLoc, engine.getLocation(Item.FARMER));
    }
}
