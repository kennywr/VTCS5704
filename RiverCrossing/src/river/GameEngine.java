package river;

/*
 * (1) Create a private method called getGameObject that returns a game object
 *     associated with an id. Use the new method to simplify the getName,
 *     getLocation, getSound, and loadBoat methods.
 *     Done
 *
 * (2) Create two private methods: boatHasPassenger and getBoatPassenger, that
 *     check if the boat has a passenger (Wolf, Goose, or Beans - does not apply
 *     to Farmer) and returns the passenger on the boat, respectively. Use them
 *     to simplify loadBoat and unloadBoat. Note that getBoatPassenger should be
 *     an *accessor* method - in other words, it should simply report who the boat's
 *     passenger is; it should NOT remove that passenger. getBoatPassenger should
 *     return a gameObject.
 *     Done
 *
 * (3) Implement a method called oppositeLocation that returns the START if the
 *     current location is FINSH and returns FINISH if the current location is
 *     START. Use it to simplify rowBoat.
 *     Done
 *
 * (4) Rename the method getCurrentLocation to getBoatLocation
 * 		Done
 *
 * (5) It turns out we do not actually need the field currentLocation as it is
 *     always the same as the player's location. Remove this field.
 *     Done
 *
 * (6) The two enum types are currently inner classes. Make them regular classes
 *     in package river. Change the constants in Item from TOP, MID, BOTTOM, and
 *     PLAYER to WOLF, GOOSE, BEANS, and FARMER. Change the constants START and
 *     FINISH in Location to LEFT_BANK and RIGHT_BANK.
 *
 * (7) Make all GameObject fields final. Change the names top, mid, bottom, and
 *     player to wolf, goose, beans, and farmer.
 *     done
 */

public class GameEngine {

    private final GameObject wolf;
    private final GameObject goose;
    private final GameObject beans;
    private final GameObject farmer;
    private Item chars;
    private Location locs;


    public GameEngine() {
        wolf = new Wolf();
        goose = new Goose();
        beans = new Beans();
        farmer = new Farmer();
    }

    public String getName(Item id) {
        return getGameObject(id).getName();

//		Old Version
//    	switch (id) {
//        case TOP:
//            return top.getName();
//        case MID:
//            return mid.getName();
//        case BOTTOM:
//            return bottom.getName();
//        default:
//            return player.getName();
//        }
    }

    public Location getLocation(Item id) {
    	return getGameObject(id).getLocation();

//		Old Version
//        switch (id) {
//        case TOP:
//            return top.getLocation();
//        case MID:
//            return mid.getLocation();
//        case BOTTOM:
//            return bottom.getLocation();
//        default:
//            return player.getLocation();
//        }
    }

    public String getSound(Item id) {
    	return getGameObject(id).getSound();

//	Old Version
//        switch (id) {
//        case TOP:
//            return top.getSound();
//        case MID:
//            return mid.getSound();
//        case BOTTOM:
//            return bottom.getSound();
//        default:
//            return player.getSound();
//        }
    }

    public Location getBoatLocation() {
        return farmer.getLocation();
    }

    public void loadBoat(Item id) {

        //check to see if the boat is empty and nearby and if so load the boat
    	GameObject tempLocation = getGameObject(id);

    	if(!boatHasPassenger() && tempLocation.getLocation() == farmer.getLocation()) {
    		getGameObject(id).setLocation(Location.BOAT);
    	}

//    	switch (id) {
//        case TOP:
//            if (top.getLocation() == currentLocation
//                    && mid.getLocation() != Location.BOAT
//                    && bottom.getLocation() != Location.BOAT) {
//                top.setLocation(Location.BOAT);
//            }
//            break;
//        case MID:
//            if (mid.getLocation() == currentLocation
//                    && top.getLocation() != Location.BOAT
//                    && bottom.getLocation() != Location.BOAT) {
//                mid.setLocation(Location.BOAT);
//            }
//            break;
//        case BOTTOM:
//            if (bottom.getLocation() == currentLocation
//                    && top.getLocation() != Location.BOAT
//                    && mid.getLocation() != Location.BOAT) {
//                bottom.setLocation(Location.BOAT);
//            }
//            break;
//        default: // do nothing
//        }
    }

    public void unloadBoat() {

    	if(boatHasPassenger()) {
    		getBoatPassenger().setLocation(farmer.getLocation());
    	}

    	//Old Code
//        if (top.getLocation() == Location.BOAT) {
//            top.setLocation(currentLocation);
//        } else if (mid.getLocation() == Location.BOAT) {
//            mid.setLocation(currentLocation);
//        } else if (bottom.getLocation() == Location.BOAT) {
//            bottom.setLocation(currentLocation);
//        }
    }

    public void rowBoat() {
        assert (farmer.getLocation() != Location.BOAT);
        farmer.setLocation(farmer.getLocation());
//        if (currentLocation == Location.START) {
//            currentLocation = Location.FINISH;
//            player.setLocation(Location.FINISH);
//        } else {
//            currentLocation = Location.START;
//            player.setLocation(Location.START);
//        }
    }

    public boolean gameIsWon() {
        return wolf.getLocation() == Location.RIGHT_BANK
                && goose.getLocation() == Location.RIGHT_BANK
                && beans.getLocation() == Location.RIGHT_BANK;
    }

    public boolean gameIsLost() {
        if (goose.getLocation() == Location.BOAT) {
            return false;
        }
        if (goose.getLocation() != farmer.getLocation()
                && goose.getLocation() == wolf.getLocation()) {
            return true;
        }
        if (goose.getLocation() != farmer.getLocation()
                && goose.getLocation() == beans.getLocation()) {
            return true;
        }
        return false;
    }

    private GameObject getGameObject(Item id) {
//    	if(id.Wolf.getName() == "WOLF") {
//    	    return wolf;
//    	}
//    	else if(id.Goose.getName() == "GOOSE") {
//    	    return goose;
//    	}
//    	else if(id.Beans.getName() == "BEANS") {
//    	    return beans;
//    	}
//    	else
//        {
//            return farmer;
//        }

        switch (id) {
         case WOLF:
             return wolf;
         case GOOSE:
             return goose;
         case BEANS:
             return beans;
         default:
             return farmer;
         }
    }

    private boolean boatHasPassenger() {
    	if(wolf.getLocation() == Location.BOAT || goose.getLocation() == Location.BOAT || beans.getLocation() == Location.BOAT) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }

    private GameObject getBoatPassenger() {
    	if(wolf.getLocation() == Location.BOAT) {
    		return wolf;
    	}
    	else if(goose.getLocation() == Location.BOAT){
    		return goose;
    	}
    	else if(beans.getLocation() == Location.BOAT) {
    		return beans;
    	}

    	return farmer;
    }

    private Location oppositeLocation() {
    	if(farmer.getLocation() == Location.LEFT_BANK) {
    		return Location.RIGHT_BANK;
    	}
    	else {
    		return Location.LEFT_BANK;
    	}

    }

    // ----------------------------------------------------------
    /**
     * A temporary helper for me to figure out what's going on
     * @param name the name of the char you are looking for
     * @return a gameobject with that char
     */
    public GameObject getCharacter(String name) {
        if(name == "WOLF") {
            return wolf;
        }
        else if(name == "GOOSE") {
            return goose;
        }
        else if(name == "BEANS") {
            return beans;
        }
        else
        {
            return farmer;
        }
    }
}
