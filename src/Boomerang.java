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
    final double speed = 7.5;
    Direction dirThrown;

    // Constructor used when user presses either control or 'b' to create new
    // boomerang object
    public Boomerang(int x, int y, Direction dir) {
        this.x = x;
        this.y = y;
        this.dirThrown = dir;
        this.width = 10;
        this.height = 10;

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
        switch (dirThrown) {
            case UP -> y -= speed;
            case DOWN -> y += speed;
            case LEFT -> x -= speed;
            case RIGHT -> x += speed;
        }

        currImage = (currImage + 1) % MAX_IMAGES;
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
