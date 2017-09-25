package river;


import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Console;
import java.util.stream.Collector.Characteristics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import sun.java2d.windows.WindowsFlags;

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
        System.out.println("made wolf");
        fillActualRect(gooseRect, g, Color.CYAN, "G");
        System.out.println("made goose");
        fillActualRect(beansRect, g, Color.CYAN, "B");
        System.out.println("made beans");

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
        if(engine.getLocation(Item.FARMER)==Location.LEFT_BANK) {
            farmerRect = leftFarmerRect;
        }
        else if(engine.getLocation(Item.FARMER)==Location.RIGHT_BANK) {
            farmerRect = rightFarmerRect;
        }
        else if(engine.getLocation(Item.FARMER)==Location.BOAT && engine.getBoatLocation() == Location.LEFT_BANK) {
            farmerRect = leftBoatDriverRect;
        }
        else if(engine.getLocation(Item.FARMER)==Location.BOAT && engine.getBoatLocation() == Location.RIGHT_BANK) {
            farmerRect = rightBoatDriverRect;
        }

        //Wolf Assignment
        if(engine.getLocation(Item.WOLF)==Location.LEFT_BANK) {
            wolfRect = leftWolfRect;
        }
        else if(engine.getLocation(Item.WOLF)==Location.RIGHT_BANK) {
            wolfRect = rightWolfRect;
        }
        else if(engine.getLocation(Item.WOLF)==Location.BOAT && engine.getBoatLocation() == Location.LEFT_BANK) {
            wolfRect = leftBoatPassengerRect;
        }
        else if(engine.getLocation(Item.WOLF)==Location.BOAT && engine.getBoatLocation() == Location.RIGHT_BANK) {
            wolfRect = rightBoatPassengerRect;
        }

        //Goose Assignment
        if(engine.getLocation(Item.GOOSE)==Location.LEFT_BANK) {
            gooseRect = leftGooseRect;
        }
        else if(engine.getLocation(Item.GOOSE)==Location.RIGHT_BANK) {
            gooseRect = rightGooseRect;
        }
        else if(engine.getLocation(Item.GOOSE)==Location.BOAT && engine.getBoatLocation() == Location.LEFT_BANK) {
            gooseRect = leftBoatPassengerRect;
        }
        else if(engine.getLocation(Item.GOOSE)==Location.BOAT && engine.getBoatLocation() == Location.RIGHT_BANK) {
            gooseRect = rightBoatPassengerRect;
        }

        //Beans assignment
        if(engine.getLocation(Item.BEANS)==Location.LEFT_BANK) {
            beansRect = leftBeansRect;
        }
        else if(engine.getLocation(Item.BEANS)==Location.RIGHT_BANK) {
            beansRect = rightBeansRect;
        }
        else if(engine.getLocation(Item.BEANS)==Location.BOAT && engine.getBoatLocation() == Location.LEFT_BANK) {
            beansRect = leftBoatPassengerRect;
        }
        else if(engine.getLocation(Item.BEANS)==Location.BOAT && engine.getBoatLocation() == Location.RIGHT_BANK) {
            beansRect = rightBoatPassengerRect;
        }
        //Boat Assignment
        if(engine.getBoatLocation() == Location.LEFT_BANK) {
            boatRect = leftBoatRect;
        }
        else if(engine.getBoatLocation() == Location.RIGHT_BANK) {
            boatRect = rightBoatRect;
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

        if (leftFarmerRect.contains(e.getPoint())) {
            if (engine.getLocation(Item.FARMER) == Location.LEFT_BANK) {
                engine.loadDriver();;
            }

        } else if (leftWolfRect.contains(e.getPoint())) {
            if (engine.getLocation(Item.WOLF) == Location.LEFT_BANK) {
                engine.loadPassenger(Item.WOLF);
            }
        } else if (leftGooseRect.contains(e.getPoint())) {
            if (engine.getLocation(Item.GOOSE) == Location.LEFT_BANK) {
                engine.loadPassenger(Item.GOOSE);
            }
        } else if (leftBeansRect.contains(e.getPoint())) {
            if (engine.getLocation(Item.BEANS) == Location.LEFT_BANK) {
                engine.loadPassenger(Item.BEANS);
            }
        } else if (leftBoatDriverRect.contains(e.getPoint())) {
            if (engine.getBoatLocation() == Location.LEFT_BANK &&
                    engine.getLocation(Item.FARMER) == Location.BOAT) {
                engine.unloadDriver();
            }
        } else if (leftBoatPassengerRect.contains(e.getPoint())) {
            if (engine.getBoatLocation() == Location.LEFT_BANK &&
                    (engine.getLocation(Item.WOLF) == Location.BOAT ||
                    engine.getLocation(Item.GOOSE) == Location.BOAT ||
                    engine.getLocation(Item.BEANS) == Location.BOAT)) {
                engine.unloadPassenger();
            }
        } else if (leftBoatRect.contains(e.getPoint())) {
            if (engine.getBoatLocation() == Location.LEFT_BANK &&
                    engine.getLocation(Item.FARMER) == Location.BOAT) {
                engine.rowBoat();
            }
        } else if (rightFarmerRect.contains(e.getPoint())) {
            if (engine.getLocation(Item.FARMER) == Location.RIGHT_BANK) {
                engine.loadDriver();
            }
        } else if (rightWolfRect.contains(e.getPoint())) {
            if (engine.getLocation(Item.WOLF) == Location.RIGHT_BANK) {
                engine.loadPassenger(Item.WOLF);
            }
        } else if (rightGooseRect.contains(e.getPoint())) {
            if (engine.getLocation(Item.GOOSE) == Location.RIGHT_BANK) {
                engine.loadPassenger(Item.GOOSE);
            }
        } else if (rightBeansRect.contains(e.getPoint())) {
            if (engine.getLocation(Item.BEANS) == Location.RIGHT_BANK) {
                engine.loadPassenger(Item.BEANS);
            }
        } else if (rightBoatDriverRect.contains(e.getPoint())) {
            if (engine.getBoatLocation() == Location.RIGHT_BANK &&
                    engine.getLocation(Item.FARMER) == Location.BOAT) {
                engine.unloadDriver();
            }
        } else if (rightBoatPassengerRect.contains(e.getPoint())) {
            if (engine.getBoatLocation() == Location.RIGHT_BANK &&
                    (engine.getLocation(Item.WOLF) == Location.BOAT ||
                    engine.getLocation(Item.GOOSE) == Location.BOAT ||
                    engine.getLocation(Item.BEANS) == Location.BOAT)) {
                engine.unloadPassenger();
            }
        } else if (rightBoatRect.contains(e.getPoint())) {
            if (engine.getBoatLocation() == Location.RIGHT_BANK &&
                    engine.getLocation(Item.FARMER) == Location.BOAT) {
                engine.rowBoat();
            }
        } else {
            return;
        }
        repaint();
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