// Author: Cade DuPont
// Date: 03.14.23
// Description: Class for representing a pot sprite

import java.awt.Graphics;
import java.awt.Image;

public class Pot extends Sprite {
    // Pot images and states
    static Image broken, whole;
    boolean isBroken;
    Direction sliding;

    // Countdown for how long the pot will remain on the screen after being broken, speed of pot movement
    int countdown = 75;
    double speed = 7.5;

    // Constructor used if user clicks on screen with edit and pot mode enabled
    public Pot(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 48;
        this.height = 48;

        if (broken == null)
            broken = View.loadImage("img/pots/broken.png");
        if (whole == null)
            whole = View.loadImage("img/pots/whole.png");
    }

    // Unmarshalling constructor; loads pot locations from JSON file and loads pot
    // images if they haven't been loaded yet
    public Pot(Json ob) {
        this.x = (int) ob.getLong("x");
        this.y = (int) ob.getLong("y");
        this.width = 48;
        this.height = 48;

        if (broken == null)
            broken = View.loadImage("img/pots/broken.png");
        if (whole == null)
            whole = View.loadImage("img/pots/whole.png");
    }

    // Return pot information
    @Override
    public String toString() {
        return "Pot (x, y) = (" + x + ", " + y + ")";
    }

    // Check if the current sprite is a pot; will return true if the sprite is an
    // instance of this class
    @Override
    public boolean isPot() {
        return true;
    }

    // Depending on the direction Link collided with the pot, move the pot
    // If the pot was broken, begin the countdown
    public boolean update() {
        if (sliding != null) {
            switch (sliding) {
                case UP:
                    y -= speed;
                    break;
                case DOWN:
                    y += speed;
                    break;
                case LEFT:
                    x -= speed;
                    break;
                case RIGHT:
                    x += speed;
                    break;
            }
        }

        if (isBroken)
            countdown--;
        return countdown > 0;
    }

    // Draw pot image to the screen; if the pot is broken, display the broken pot
    // image
    public void draw(Graphics g, int scroll_x, int scroll_y) {
        g.drawImage((isBroken) ? broken : whole, x - scroll_x, y - scroll_y, null);
    }

    // Marshal pot object information into a Json object
    public Json marshal() {
        Json ob = Json.newObject();
        ob.add("x", x);
        ob.add("y", y);
        return ob;
    }
}