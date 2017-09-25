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
    private GameObject passenger;
    private Location currentBoatLocation = Location.LEFT_BANK;

    public GameEngine() {
        wolf = new Wolf();
        goose = new Goose();
        beans = new Beans();
        farmer = new Farmer();
        passenger = null;
    }


    public String getName(Item id) {
        return getGameObject(id).getName();
    }

    public Location getLocation(Item id) {
    	return getGameObject(id).getLocation();
    }

    public String getSound(Item id) {
    	return getGameObject(id).getSound();
    }

    public Location getBoatLocation() {
        return currentBoatLocation;
    }


    //load driver, unload driver
    //load passenger(item), unload passenger

    public void loadDriver() {
        farmer.setLocation(Location.BOAT);
        System.out.println("Farmer says i'm in the boat at " + currentBoatLocation.toString());
    }

    public void unloadDriver() {
        farmer.setLocation(currentBoatLocation);
        System.out.println("Farmer says i'm in the boat at " + currentBoatLocation.toString());
    }

    public void loadPassenger(Item id) {
        if(!hasPassenger())
        {
            getGameObject(id).setLocation(Location.BOAT);
            passenger = getGameObject(id);
        }
    }

    public void unloadPassenger() {
        passenger.setLocation(currentBoatLocation);
        passenger = null;
    }

    public void rowBoat() {
    //if driver is empty, no move
    //if passenger is empty, nomove - NVM on this
    //otherwise move boat to other side
        if(farmer.getLocation() == Location.BOAT) {
            currentBoatLocation = oppositeLocation();
        }

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

    public void resetGame() {

    }
    // ----------------------------------------------------------
    /**
     * Get the current value of passenger.
     * @return The value of passenger for this object.
     */
    public GameObject getPassenger()
    {
        return passenger;
    }

    // ----------------------------------------------------------
    /**
     * Set the current value of passenger.
     */
    public void setPassenger(GameObject inPass)
    {
        passenger = inPass;
    }

    // ----------------------------------------------------------
    /**
     * is there anything in the passenger slot
     * @return  bool that is true if there is false if there isn't
     */
    public boolean hasPassenger()
    {
        if(passenger == null) {
            return false;
        }
        return true;
    }

    private GameObject getGameObject(Item id) {
//      if(id.Wolf.getName() == "WOLF") {
//          return wolf;
//      }
//      else if(id.Goose.getName() == "GOOSE") {
//          return goose;
//      }
//      else if(id.Beans.getName() == "BEANS") {
//          return beans;
//      }
//      else
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


    private Location oppositeLocation() {
        if(currentBoatLocation == Location.LEFT_BANK) {
            return Location.RIGHT_BANK;
        }
        return Location.LEFT_BANK;

    }
}

//old methods
//public void loadBoat(Item id) {
//
//  if(!boatHasPassenger()) {
//      getGameObject(id).location = farmer.getLocation();
//      System.out.println("loaded" + getGameObject(id).getName() + " onto boat at " + farmer.getLocation().toString());
//  }
//
//
////  switch (id) {
////  case TOP:
////      if (top.getLocation() == currentLocation
////              && mid.getLocation() != Location.BOAT
////              && bottom.getLocation() != Location.BOAT) {
////          top.setLocation(Location.BOAT);
////      }
////      break;
////  case MID:
////      if (mid.getLocation() == currentLocation
////              && top.getLocation() != Location.BOAT
////              && bottom.getLocation() != Location.BOAT) {
////          mid.setLocation(Location.BOAT);
////      }
////      break;
////  case BOTTOM:
////      if (bottom.getLocation() == currentLocation
////              && top.getLocation() != Location.BOAT
////              && mid.getLocation() != Location.BOAT) {
////          bottom.setLocation(Location.BOAT);
////      }
////      break;
////  default: // do nothing
////  }
//}
//
//public void unloadBoat() {
//
//if(boatHasPassenger()) {
//    getBoatPassenger().setLocation(farmer.getLocation());
//}
//
////Old Code
////  if (top.getLocation() == Location.BOAT) {
////      top.setLocation(currentLocation);
////  } else if (mid.getLocation() == Location.BOAT) {
////      mid.setLocation(currentLocation);
////  } else if (bottom.getLocation() == Location.BOAT) {
////      bottom.setLocation(currentLocation);
////  }
//}
//private boolean boatHasPassenger() {
//if(wolf.getLocation() == Location.BOAT || goose.getLocation() == Location.BOAT || beans.getLocation() == Location.BOAT) {
//  return true;
//}
//else {
//  return false;
//}
//}

//private GameObject getBoatPassenger() {
//if(wolf.getLocation() == Location.BOAT) {
//  return wolf;
//}
//else if(goose.getLocation() == Location.BOAT){
//  return goose;
//}
//else if(beans.getLocation() == Location.BOAT) {
//  return beans;
//}
//
//return farmer;
//}