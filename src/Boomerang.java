// Author: Cade DuPont
// Date: 03.14.23
// Description: Class representing a boomerang sprite

import java.awt.Graphics;
import java.awt.Image;

public class Boomerang extends Sprite {
    // Store boomerang images
    public static Image[] images;
    final int MAX_IMAGES = 4;
    int currImage = 0;

    // Store boomerang movement speed, direction boomerang is moving
    double speed = 7.5;
    int updateCount = 0;
    boolean returning = false;
    Direction dirThrown;

    // Constructor used when user presses either control or 'b' to create new
    // boomerang object
    public Boomerang(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.width = 10;
        this.height = 10;
        this.dirThrown = direction;

        if (images == null) {
            images = new Image[MAX_IMAGES];
            for (int i = 0; i < MAX_IMAGES; i++)
                images[i] = View.loadImage("img/boomerangs/" + i + ".png");
        }
    }

    // Return boomerang information
    @Override
    public String toString() {
        return "Boomerang (x, y) = (" + x + ", " + y + ")";
    }

    // Check whether the current sprite is a boomerang; if it's an instance of this
    // class, it will return true
    @Override
    public boolean isBoomerang() {
        return true;
    }

    // Move boomerang in the direction it's been thrown, cycle through images for
    // animating boomerang movement
    public boolean update() {
        // Calculate a curve factor using a sine function
        double curveFactor = Math.sin(updateCount / 30.0 * Math.PI * 2);
        
        // Move boomerang in the direction it's been thrown, add curvature to movement
        switch (dirThrown) {
            case UP:
                y -= speed;
                x += curveFactor * speed;
                break;
            case DOWN:
                y += speed;
                x += curveFactor * speed;
                break;
            case LEFT:
                x -= speed;
                y += curveFactor * speed;
                break;
            case RIGHT:
                x += speed;
                y += curveFactor * speed;
                break;
        }

        // Increment current image index and update count
        currImage = (currImage + 1) % MAX_IMAGES;
        updateCount++;

        // Cut speed in half when boomerang is returning
        speed = (updateCount >= 25 && !returning || updateCount <= 5 && returning) ? 3.75 : 7.5;

        if (updateCount >= 30 && !returning) {
            updateCount = 0;
            returning = true;
            switch (dirThrown) {
                case UP:
                    dirThrown = Direction.DOWN;
                    break;
                case DOWN:
                    dirThrown = Direction.UP;
                    break;
                case LEFT:
                    dirThrown = Direction.RIGHT;
                    break;
                case RIGHT:
                    dirThrown = Direction.LEFT;
                    break;
            }
        }
        return true;
    }

    // Draw current boomerang image to screen
    public void draw(Graphics g, int scroll_x, int scroll_y) {
        g.drawImage(images[currImage], this.x - scroll_x, this.y - scroll_y, null);
    }

    // Marshal boomerang cldupont information into Json object; not used
    public Json marshal() {
        Json ob = Json.newObject();
        ob.add("boomerang_x", x);
        ob.add("boomerang_y", y);
        return ob;
    }
}
