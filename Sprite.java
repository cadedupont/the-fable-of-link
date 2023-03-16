// Author: Cade DuPont
// Date: 03.14.23
// Description: Class representing a sprite for polymorphism

import java.awt.Graphics;
import java.awt.Image;

public abstract class Sprite {
    int x, y;
    int width, height;
    Image image;

    public abstract void update();
    public abstract void draw(Graphics g, int scroll_x, int scroll_y);
    public abstract Json marshal();

    public boolean isTile() {
        return false;
    }

    public boolean isLink() {
        return false;
    }

    public boolean isPot() {
        return false;
    }
}
