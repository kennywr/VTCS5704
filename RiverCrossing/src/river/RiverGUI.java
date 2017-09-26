package river;


import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
//import java.io.Console;
//import java.util.stream.Collector.Characteristics;
import javax.swing.JFrame;
import javax.swing.JPanel;



/**
 * Graphical interface for the River application
 *
 * @author Gregory Kulczycki
 */
public class RiverGUI extends JPanel implements MouseListener {

    // ==========================================================
    // Fields (hotspots)
    // ==========================================================

    private final Rectangle leftFarmerRect = new Rectangle(80, 215, 50, 50);
    private final Rectangle leftWolfRect = new Rectangle(20, 215, 50, 50);
    private final Rectangle leftGooseRect = new Rectangle(20, 275, 50, 50);
    private final Rectangle leftBeansRect = new Rectangle(80, 275, 50, 50);
    private final Rectangle leftBoatRect = new Rectangle(140, 275, 110, 50);
    private final Rectangle leftBoatDriverRect = new Rectangle(140, 215, 50, 50);
    private final Rectangle leftBoatPassengerRect = new Rectangle(200, 215, 50, 50);

    private final Rectangle rightFarmerRect = new Rectangle(730, 215, 50, 50);
    private final Rectangle rightWolfRect = new Rectangle(670, 215, 50, 50);
    private final Rectangle rightGooseRect = new Rectangle(670, 275, 50, 50);
    private final Rectangle rightBeansRect = new Rectangle(730, 275, 50, 50);
    private final Rectangle rightBoatRect = new Rectangle(550, 275, 110, 50);
    private final Rectangle rightBoatDriverRect = new Rectangle(550, 215, 50, 50);
    private final Rectangle rightBoatPassengerRect = new Rectangle(610, 215, 50, 50);

    private final Rectangle resetButton = new Rectangle(300, 140, 200, 50);

    // ==========================================================
    // Private Fields
    // ==========================================================

    private GameEngine engine; // Model
    private Rectangle farmerRect;
    private Rectangle wolfRect;
    private Rectangle gooseRect;
    private Rectangle beansRect;
    private Rectangle boatRect;

    // ==========================================================
    // Constructor
    // ==========================================================

    public RiverGUI() {

        engine = new GameEngine();
        addMouseListener(this);
        setActiveRectangles();

    }

    // ==========================================================
    // Paint Methods (View)
    // ==========================================================

    @Override
    public void paintComponent(Graphics g) {

        setActiveRectangles();
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        //Fill Boat
        fillActualRect(boatRect, g, Color.ORANGE, "");

        //paint farmer
        fillActualRect(farmerRect, g, Color.MAGENTA, "");

        //Fill passengers
        fillActualRect(wolfRect, g, Color.CYAN, "W");
        fillActualRect(gooseRect, g, Color.CYAN, "G");
        fillActualRect(beansRect, g, Color.CYAN, "B");

        if(engine.gameIsLost()) {
    		paintMessage("You lost!", g);
    	}

    	if(checkWin()) {
    		paintMessage("You win!", g);
    	}

        if(engine.isResetNeeded())
        {
        	fillActualRect(resetButton, g, Color.WHITE, "RESET");

        }
        else {

        }

    }



    public void paintStringInRectangle(String str, int x, int y, int width, int height, Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Verdana", Font.BOLD, 36));
        FontMetrics fm = g.getFontMetrics();
        int strXCoord = x + width/2 - fm.stringWidth(str)/2;
        int strYCoord = y + height/2 + 36/2 - 4;
        g.drawString(str, strXCoord, strYCoord);
    }

    // ==========================================================
    // Startup Methods
    // ==========================================================

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {

        // Create and set up the window
        JFrame frame = new JFrame("RiverCrossing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create and set up the content pane
        RiverGUI newContentPane = new RiverGUI();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);

        // Display the window
        frame.setSize(800, 600);
        frame.setVisible(true);

    }

    private void setActiveRectangles() {

        //Farmer assignment

        //ispassenger(id)
        //left or right passenger

        //left or right square


        if(engine.getLocation(Item.FARMER)==Location.BOAT && engine.getBoatLocation() == Location.LEFT_BANK) {
            farmerRect = leftBoatDriverRect;
        }
        else if(engine.getLocation(Item.FARMER)==Location.BOAT && engine.getBoatLocation() == Location.RIGHT_BANK) {
            farmerRect = rightBoatDriverRect;
        }
        else if(engine.getLocation(Item.FARMER)==Location.LEFT_BANK) {
            farmerRect = leftFarmerRect;
        }
        else if(engine.getLocation(Item.FARMER)==Location.RIGHT_BANK) {
            farmerRect = rightFarmerRect;
        }

        //Wolf, Goose, Beans
        //helper checks for passenger via isPassenger
        //then goes to either assign to passenger slot (if passenger)
        //or left or right bank assignment
        rectHelper(Item.WOLF);
        rectHelper(Item.GOOSE);
        rectHelper(Item.BEANS);


        //Boat Assignment
        if(engine.getBoatLocation() == Location.LEFT_BANK) {
            boatRect = leftBoatRect;
        }
        else if(engine.getBoatLocation() == Location.RIGHT_BANK) {
            boatRect = rightBoatRect;
        }

        //Status Check
        debugCurrentLocationPrinter(Item.WOLF);
        debugCurrentLocationPrinter(Item.FARMER);
        debugCurrentLocationPrinter(Item.GOOSE);
        debugCurrentLocationPrinter(Item.BEANS);
        System.out.println("The boat is " + engine.getBoatLocation().toString());
    }

    private void debugCurrentLocationPrinter(Item id) {
        System.out.println(engine.getName(id) + " Current Location " + engine.getLocation(id));
    }

    private void rectHelper(Item id) {
        if(isPassenger(id)) {
            leftOrRightPassenger(id);
        }
        else {
            leftOrRightBank(id);
        }
    }

    private boolean isPassenger(Item id) {
        //without this if statement, checks to see name of null pointer
        if(engine.hasPassenger()) {
            if(engine.getPassenger().getName() == engine.getName(id)) {
                return true;
            }
        }

        return false;
    }

    //only called when id is confirmed passenger
    private void leftOrRightPassenger(Item id) {
        if(engine.getBoatLocation() == Location.RIGHT_BANK) {
            switch (id) {
                case WOLF:
                    wolfRect = rightBoatPassengerRect;
                case GOOSE:
                    gooseRect = rightBoatPassengerRect;
                case BEANS:
                    beansRect = rightBoatPassengerRect;
            }
        }
        else {
            switch (id) {
                case WOLF:
                    wolfRect = leftBoatPassengerRect;
                case GOOSE:
                    gooseRect = leftBoatPassengerRect;
                case BEANS:
                    beansRect = leftBoatPassengerRect;
            }
        }


    }

    private void leftOrRightBank(Item id) {
        if(engine.getLocation(id) == Location.RIGHT_BANK) {
            switch (id) {
                case WOLF:
                    wolfRect = rightWolfRect;
                case GOOSE:
                    gooseRect = rightGooseRect;
                case BEANS:
                    beansRect = rightBeansRect;
            }
        }
        else {
            switch (id) {
                case WOLF:
                    wolfRect = leftWolfRect;
                case GOOSE:
                    gooseRect = leftGooseRect;
                case BEANS:
                    beansRect = leftBeansRect;
            }
        }
    }

    private void fillActualRect(Rectangle rectToFill, Graphics g, Color color, String toWrite) {
        g.setColor(color);

        g.fillRect((int)rectToFill.getX(), (int)rectToFill.getY(),
            (int)rectToFill.getWidth(), (int)rectToFill.getHeight());

        paintStringInRectangle(toWrite, (int)rectToFill.getX(),
            (int)rectToFill.getY(), (int)rectToFill.getWidth(),
            (int)rectToFill.getHeight(), g);
    }


    public void paintMessage(String message, Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Verdana", Font.BOLD, 36));
        FontMetrics fm = g.getFontMetrics();
        int strXCoord = 400 - fm.stringWidth(message)/2;
        int strYCoord = 100;
        g.drawString(message, strXCoord, strYCoord);
    }

    public static void main(String[] args) {

        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(RiverGUI::createAndShowGUI);
    }

    // ==========================================================
    // MouseListener Methods (Controller)
    // ==========================================================

    @Override
    public void mouseClicked(MouseEvent e) {

    	//wrap the entire thing in an if to see if reset is needed
    	if(!engine.isResetNeeded()) {
	    	//check to see if the moust click was within any of the five rectangles
	    	//if so, which one
	    	//if farmer, was farmer driver, if so reverse, if not drive
	    	//if gooose, wolf or beans, check to see if on boat or if not and move to right space
	    	//if boat, see if it has a drive and if so move it


	    	//farmer
	    	if(farmerRect.contains(e.getPoint())){
	    		if(engine.getLocation(Item.FARMER) == Location.BOAT) {
	    			engine.unloadDriver();
	    		}
	    		else{
	    			engine.loadDriver();
	    		}
	    	}

	    	//goose, wolf or bean
	    	else if(wolfRect.contains(e.getPoint())) {
	    		if(!engine.hasPassenger()) {
	    			engine.loadPassenger(Item.WOLF);
	    		}
	    		else {
	    			if(engine.getPassenger().getName() =="Wolf") {
		    			engine.unloadPassenger();
		    		}
	    		}
	    	}
	    	else if(gooseRect.contains(e.getPoint())) {
	    		if(!engine.hasPassenger()) {
	    			engine.loadPassenger(Item.GOOSE);
	    		}
	    		else{
	    			if(engine.getPassenger().getName() =="Goose") {
	    				engine.unloadPassenger();
	    			}
	    		}
	    	}
	    	else if(beansRect.contains(e.getPoint())) {
	    		if(!engine.hasPassenger()) {
	    			engine.loadPassenger(Item.BEANS);
	    		}
	    		else {
		    		if(engine.getPassenger().getName() =="Beans") {
		    			engine.unloadPassenger();
		    		}
	    		}
	    	}

	    	//boat
	    	else if(boatRect.contains(e.getPoint())) {
	    		//if(engine.getLocation(Item.FARMER)==Location.BOAT) {
	    			engine.rowBoat();
	    		//}
	    	}
    	}
    	else {
    		if(resetButton.contains(e.getPoint())) {
    			engine.resetGame();
    		}
    	}
        repaint();
    }

    private boolean checkWin() {
    	if(!engine.gameIsLost() && engine.gameIsWon()) {
    		return true;
    	}
    	return false;
    }
    // ----------------------------------------------------------
    // None of these methods will be used
    // ----------------------------------------------------------

    @Override
    public void mousePressed(MouseEvent e) {
        //
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //
    }
}
//Old Methods
//public void paintObjectsOnLeft(Graphics g) {
//
//    if (engine.getLocation(Item.FARMER) == Location.LEFT_BANK) {
//        g.setColor(Color.MAGENTA);
//        g.fillRect(80, 215, 50, 50);
//    }
//    if (engine.getLocation(Item.WOLF) == Location.LEFT_BANK) {
//        g.setColor(Color.CYAN);
//        g.fillRect(20, 215, 50, 50);
//        paintStringInRectangle("W", 20, 215, 50, 50, g);
//    }
//    if (engine.getLocation(Item.GOOSE) == Location.LEFT_BANK) {
//        g.setColor(Color.CYAN);
//        g.fillRect(20, 275, 50, 50);
//        paintStringInRectangle("G", 20, 275, 50, 50, g);
//    }
//    if (engine.getLocation(Item.BEANS) == Location.LEFT_BANK) {
//        g.setColor(Color.CYAN);
//        g.fillRect(80, 275, 50, 50);
//        paintStringInRectangle("B", 80, 275, 50, 50, g);
//    }
//}
//
//public void paintObjectsOnRight(Graphics g) {
//
//    if (engine.getLocation(Item.FARMER) == Location.RIGHT_BANK) {
//        g.setColor(Color.MAGENTA);
//        g.fillRect(730, 215, 50, 50);
//    }
//    if (engine.getLocation(Item.WOLF) == Location.RIGHT_BANK) {
//        g.setColor(Color.CYAN);
//        g.fillRect(670, 215, 50, 50);
//        paintStringInRectangle("W", 670, 215, 50, 50, g);
//    }
//    if (engine.getLocation(Item.GOOSE) == Location.RIGHT_BANK) {
//        g.setColor(Color.CYAN);
//        g.fillRect(670, 275, 50, 50);
//        paintStringInRectangle("G", 670, 275, 50, 50, g);
//    }
//    if (engine.getLocation(Item.BEANS) == Location.RIGHT_BANK) {
//        g.setColor(Color.CYAN);
//        g.fillRect(730, 275, 50, 50);
//        paintStringInRectangle("B", 730, 275, 50, 50, g);
//    }
//}
//
//public void paintObjectsOnBoat(Graphics g) {
//    if (engine.getBoatLocation() == Location.LEFT_BANK) {
//        g.setColor(Color.ORANGE);
//        g.fillRect(140, 275, 110, 50);
//        if (engine.getLocation(Item.FARMER) == Location.BOAT) {
//            g.setColor(Color.MAGENTA);
//            g.fillRect(140, 215, 50, 50);
//        }
//        if (engine.getLocation(Item.WOLF) == Location.BOAT) {
//            g.setColor(Color.CYAN);
//            g.fillRect(200, 215, 50, 50);
//            paintStringInRectangle("W", 200, 215, 50, 50, g);
//        } else if (engine.getLocation(Item.GOOSE) == Location.BOAT) {
//            g.setColor(Color.CYAN);
//            g.fillRect(200, 215, 50, 50);
//            paintStringInRectangle("G", 200, 215, 50, 50, g);
//        } else if (engine.getLocation(Item.BEANS) == Location.BOAT) {
//            g.setColor(Color.CYAN);
//            g.fillRect(200, 215, 50, 50);
//            paintStringInRectangle("B", 200, 215, 50, 50, g);
//        }
//    }
//    if (engine.getBoatLocation() == Location.RIGHT_BANK) {
//        g.setColor(Color.ORANGE);
//        g.fillRect(550, 275, 110, 50);
//        if (engine.getLocation(Item.FARMER) == Location.BOAT) {
//            g.setColor(Color.MAGENTA);
//            g.fillRect(550, 215, 50, 50);
//        }
//        if (engine.getLocation(Item.WOLF) == Location.BOAT) {
//            g.setColor(Color.CYAN);
//            g.fillRect(610, 215, 50, 50);
//            paintStringInRectangle("W", 610, 215, 50, 50, g);
//        } else if (engine.getLocation(Item.GOOSE) == Location.BOAT) {
//            g.setColor(Color.CYAN);
//            g.fillRect(610, 215, 50, 50);
//            paintStringInRectangle("G", 610, 215, 50, 50, g);
//        } else if (engine.getLocation(Item.BEANS) == Location.BOAT) {
//            g.setColor(Color.CYAN);
//            g.fillRect(610, 215, 50, 50);
//            paintStringInRectangle("B", 610, 215, 50, 50, g);
//        }
//    }
//}