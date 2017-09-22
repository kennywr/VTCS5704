package river;

import river.GameEngine.Location;

public class Goose extends GameObject {

    public Goose() {
        super();
        name = "Goose";
        location = Location.START;
        sound = "Honk";
    }

}
