import java.awt.Image;

import java.awt.Graphics;

public class Link {
    // Integers for storing Link's coordinates
    int x, y;

    // TODO: Set Link's width and height to be width and height of current image?
    // Integers for Link's width/height
    int width = 78, height = 85;

    // Double for storing Link's movement speed
    double speed = 10;

    // Boolean to check whether Link is currently moving
    boolean isMoving;

    // Image arrays to store Link movement images
    Image linkStill;
    Image[] linkUp;
    Image[] linkDown;
    Image[] linkLeft;
    Image[] linkRight;
    static Image image = null;

    public Link() {
        if (image == null) {
            linkStill = View.loadImage("res/link/still/0.png");

            linkUp = new Image[11];
            for (int i = 0; i < linkUp.length; i++)
                linkUp[i] = View.loadImage("res/link/up/" + i + ".png");

            linkDown = new Image[13];
            for (int i = 0; i < linkDown.length; i++)
                linkDown[i] = View.loadImage("res/link/down/" + i + ".png");

            linkLeft = new Image[13];
            for (int i = 0; i < linkLeft.length; i++)
                linkLeft[i] = View.loadImage("res/link/left/" + i + ".png");

            linkRight = new Image[13];
            for (int i = 0; i < linkRight.length; i++)
                linkRight[i] = View.loadImage("res/link/right/" + i + ".png");
        }
    }

    @Override
    public String toString() {
        return ("Link (x, y) = (" + this.x + ", " + this.y + ")");
    }

    public void update() {
        
    }

    public void draw(Graphics g) {
        if (!isMoving) {
            g.drawImage(linkStill, x - View.scroll_x, y - View.scroll_y, null);
            return;
        }

        g.drawImage(linkUp[3], x - View.scroll_x, y - View.scroll_y, null);
        
    }

    // TODO: #2 Fix Link's position if colliding with tile
    public void stopColliding() {

    }
}
