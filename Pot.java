// Author: Cade DuPont
// Date: 03.14.23
// Description: Class for representing a pot sprite

import java.awt.Graphics;
import java.awt.Image;

public class Pot extends Sprite {
    
    @Override
    public String toString() {
        return ("Pot (x, y) = (" + x + ", " + y + ")");
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
