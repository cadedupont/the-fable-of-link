// Author: Cade DuPont
// Date: 03.14.23
// Description: Class representing a boomerang sprite

import java.awt.Graphics;
import java.awt.Image;

public class Boomerang extends Sprite {

    @Override
    public String toString() {
        return ("Boomerang (x, y) = (" + x + ", " + y + ")");
    }

    public void update() {

    }

    public void draw(Graphics g, int scroll_x, int scroll_y) {

    }

    public Json marshal() {
        Json ob = Json.newObject();
        ob.add("x", x);
        ob.add("y", y);
        return ob;
    }
}
