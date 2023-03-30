// Author: Cade DuPont
// Date: 03.14.23
// Description: Class for representing a pot sprite

import java.awt.Graphics;
import java.awt.Image;

public class Pot extends Sprite {
    // Store pot images
    public static Image broken, whole;

    // Boolean to determine whether the pot's been collided with
    public boolean isBroken;

    // Direction pot will slide if Link collided with it
    public Direction sliding;

    // Countdown for displaying broken pot image before removing it
    public int countdown = 75;

    // Speed of pot's movement
    public double speed = 7.5;

    // Constructor used if user clicks on screen with edit and pot mode enabled
    public Pot(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 48;
        this.height = 48;
    }

    // Unmarshalling constructor; loads pot locations from JSON file and loads pot
    // images if they haven't been loaded yet
    public Pot(Json ob) {
        this.x = (int) ob.getLong("pot_x");
        this.y = (int) ob.getLong("pot_y");
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
        if (countdown <= 0)
            return false;
        return true;
    }

    // Draw pot image to the screen; if the pot is broken, display the broken pot
    // image
    public void draw(Graphics g, int scroll_x, int scroll_y) {
        if (isBroken)
            g.drawImage(broken, x - scroll_x, y - scroll_y, null);
        else
            g.drawImage(whole, x - scroll_x, y - scroll_y, null);
    }

    // Marshal pot object information into a Json object
    public Json marshal() {
        Json ob = Json.newObject();
        ob.add("pot_x", x);
        ob.add("pot_y", y);
        return ob;
    }
}