package river;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/*
 * (1) Most test methods start off by creating a local variable named engine.
 *     Instead of doing this, declare a (private) field named engine and
 *     initialize it in the setUp method.
 *     done
 *
 *
 * (2) Create a private method called transport, that loads the boat with an
 *     item, rows the boat, and unloads the boat. Use this method wherever you
 *     can to replace code that loads, rows, then unloads the boat.
 *     done
 */

public class GameEngineTest {
    private GameEngine engine;

    @Before
    public void setUp() throws Exception {
        engine = new GameEngine();
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
        Assert.assertEquals(Location.LEFT_BANK, engine.getLocation(Item.GOOSE));
        engine.loadPassenger(Item.GOOSE);
        Assert.assertEquals("Goose", engine.getPassenger().getName());
        engine.loadDriver();
        engine.rowBoat();
        Assert.assertEquals("Goose", engine.getPassenger().getName());
        engine.unloadPassenger();
        engine.unloadDriver();
        Assert.assertEquals(Location.RIGHT_BANK, engine.getLocation(Item.GOOSE));
    }
    
    @Test
    public void testMidTransport() {
        Assert.assertEquals(Location.LEFT_BANK, engine.getLocation(Item.GOOSE));
        engine.loadBoat(Item.GOOSE);
        Assert.assertEquals(Location.BOAT, engine.getLocation(Item.GOOSE));
        engine.loadBoat(Item.FARMER);
        engine.rowBoat();
        Assert.assertEquals(Location.BOAT, engine.getLocation(Item.GOOSE));
        engine.unLoadBoat();
        Assert.assertEquals(Location.RIGHT_BANK, engine.getLocation(Item.GOOSE));
    }

    @Test
    public void testWinningGame() {

        // transport the goose
        transportID(Item.GOOSE);
        Assert.assertEquals(Location.RIGHT_BANK, engine.getLocation(Item.GOOSE));
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone
        goBackAlone();
        Assert.assertEquals(Location.RIGHT_BANK, engine.getLocation(Item.GOOSE));
//        System.out.println("Step 2");
//        System.out.println("Goose is at " + engine.getLocation(Item.GOOSE));
//        System.out.println("Wolf is at " + engine.getLocation(Item.WOLF));
//        System.out.println("Farmer is at " + engine.getLocation(Item.FARMER));
//        System.out.println("Beans are at " + engine.getLocation(Item.BEANS));
//        System.out.println("Boat is at "+ engine.getBoatLocation().toString());
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // transport the wolf
        transportID(Item.WOLF);
        Assert.assertEquals(Location.RIGHT_BANK, engine.getLocation(Item.GOOSE));
        Assert.assertEquals(Location.RIGHT_BANK, engine.getLocation(Item.WOLF));
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // transport the goose
        transportID(Item.GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // transport the beans
        transportID(Item.BEANS);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone
        goBackAlone();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // transport the goose
        transportID(Item.GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertTrue(engine.gameIsWon());
        
        Assert.assertTrue(engine.isResetNeeded());
    }

    @Test
    public void testLosingGame() {


        // transport the goose
        transportID(Item.GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone
        goBackAlone();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // transport the wolf
        transportID(Item.WOLF);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone
        goBackAlone();
        Assert.assertTrue(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());
    }

    @Test
    public void testError() {


        // transport the goose
        transportID(Item.GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // save the state
        Location topLoc = engine.getLocation(Item.WOLF);
        Location midLoc = engine.getLocation(Item.GOOSE);
        Location bottomLoc = engine.getLocation(Item.BEANS);
        Location playerLoc = engine.getLocation(Item.FARMER);

        engine.loadPassenger(Item.WOLF);

        // check that the state has not changed
        Assert.assertEquals(topLoc, engine.getLocation(Item.WOLF));
        Assert.assertEquals(midLoc, engine.getLocation(Item.GOOSE));
        Assert.assertEquals(bottomLoc, engine.getLocation(Item.BEANS));
        Assert.assertEquals(playerLoc, engine.getLocation(Item.FARMER));
    }
    
    @Test
	public void testGameEngineGetters() {
	Assert.assertEquals("Wolf", engine.getName(Item.WOLF));
	Assert.assertEquals("Howl", engine.getSound(Item.WOLF));
	Assert.assertEquals(Location.LEFT_BANK, engine.getLocation(Item.WOLF));
	Assert.assertEquals(Location.LEFT_BANK, engine.getBoatLocation());
    }
    
    @Test
    public void testTwoPassengers() {
    	engine.loadPassenger(Item.GOOSE);
    	engine.loadPassenger(Item.BEANS);
    	Assert.assertEquals("Goose", engine.getPassenger().getName());
    }
    
    @Test
    public void testNoDriverBoat() {
    	engine.loadPassenger(Item.GOOSE);
    	engine.rowBoat();
    	Assert.assertEquals("Goose", engine.getPassenger().getName());
    	Assert.assertEquals(Location.LEFT_BANK, engine.getBoatLocation());
    }
    
    @Test
    public void resetGame() {
    	engine.resetGame();
    	Assert.assertEquals(Location.LEFT_BANK, engine.getLocation(Item.FARMER));
    }
    
    @Test
    public void loadAllPassengers() {
    	engine.loadPassenger(Item.GOOSE);
    	Assert.assertEquals("Goose", engine.getPassenger().getName());
    	engine.unloadPassenger();
    	engine.loadPassenger(Item.WOLF);
    	Assert.assertEquals("Wolf", engine.getPassenger().getName());
    	engine.unloadPassenger();
    	engine.loadPassenger(Item.BEANS);
    	Assert.assertEquals("Beans", engine.getPassenger().getName());
    	engine.unloadPassenger();
    	
    	Assert.assertEquals(Location.LEFT_BANK, engine.getLocation(Item.FARMER));
    	
    }
    
    @Test
    public void unloadEmptyBoat() {
    	engine.unLoadBoat();
    	Assert.assertEquals(Location.LEFT_BANK, engine.getLocation(Item.FARMER));
    }
    
    @Test
    public void gameIsLostTester() {
    	
    }

    private void transportID(Item id) {
        engine.loadBoat(Item.FARMER);
    	engine.loadPassenger(id);
        engine.loadDriver();
        engine.rowBoat();
        engine.unloadPassenger();
        engine.unloadDriver();
    }

    public void goBackAlone() {
    	engine.loadBoat(Item.FARMER);
    	engine.loadDriver();
        engine.rowBoat();
        engine.unloadDriver();
    }
}
