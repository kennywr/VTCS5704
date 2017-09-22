package river;

import river.GameEngine.Location;

public class Wolf extends GameObject {

    public Wolf() {
        super();
        name = "Wolf";
        location = Location.START;
        sound = "Howl";
    }

}
