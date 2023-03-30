// Author: Cade DuPont
// Date: 03.14.23
// Description: Class representing a sprite for polymorphism

import java.awt.Graphics;

public abstract class Sprite {
    // Store sprite's coordinates, width and height
    int x, y;
    int width, height;

    // All classes inheriting Sprite must define these methods
    public abstract boolean update();
    public abstract void draw(Graphics g, int scroll_x, int scroll_y);
    public abstract Json marshal();

    // Check if parameter coordinates match a tile's coordinates
    public boolean clickedOn(int x, int y) {
        return (this.x == x && this.y == y);
    }

    // Check if a sprite is an instance of a certain class; will return false for
    // all classes it's not an instance of
    public boolean isTile() {
        return false;
    }

    public boolean isLink() {
        return false;
    }

    public boolean isPot() {
        return false;
    }

    public boolean isBoomerang() {
        return false;
    }
}
