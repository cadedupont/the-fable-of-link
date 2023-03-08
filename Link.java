// Author: Cade DuPont
// Date: 03.07.23
// Description: Class for Link character on screen

import java.awt.Image;
import java.awt.Graphics;

public class Link {
    // Integers for storing Link's coordinates / previous coordinates for collision fixing
    int x = 173, y = 102;
    int prev_x, prev_y;

    // TODO: Set Link's width and height to be width and height of current image?
    // Integers for Link's width/height
    // Currently set to be the max width/height of given sprites
    int width = 78, height = 85;

    // Double for storing Link's movement speed
    double speed = 7.5;

    // Boolean to check whether Link is currently moving
    boolean isMoving;

    // Integer to store Link's direction:
    // 0 = up
    // 1 = down
    // 2 = left
    // 3 = right
    int direction = 1;

    // Integer for current image of Link's movement animation
    int currImage;

    // Image arrays to store Link movement images
    Image[] linkStill, linkUp, linkDown, linkLeft, linkRight;
    static Image image = null;
    public static int MAX_IMAGES = 10;

    // TODO: #6 Change to enum for greater readability
    // enum Direction {
    //     UP,
    //     DOWN,
    //     LEFT,
    //     RIGHT
    // }

    public Link() {
        // Load link animation images into corresponding arrays
        if (image == null) {
            linkStill = new Image[4];
            for (int i = 0; i < linkStill.length; i++)
                linkStill[i] = View.loadImage("res/link/still/" + i + ".png");

            linkUp = new Image[MAX_IMAGES];
            for (int i = 0; i < linkUp.length; i++)
                linkUp[i] = View.loadImage("res/link/up/" + i + ".png");

            linkDown = new Image[MAX_IMAGES];
            for (int i = 0; i < linkDown.length; i++)
                linkDown[i] = View.loadImage("res/link/down/" + i + ".png");

            linkLeft = new Image[MAX_IMAGES];
            for (int i = 0; i < linkLeft.length; i++)
                linkLeft[i] = View.loadImage("res/link/left/" + i + ".png");

            linkRight = new Image[MAX_IMAGES];
            for (int i = 0; i < linkRight.length; i++)
                linkRight[i] = View.loadImage("res/link/right/" + i + ".png");
        }
    }

    // Print Link information
    @Override
    public String toString() {
        return ("Link (x, y) = (" + x + ", " + y + ")");
    }

    public void update() {}

    // Draw Link onto screen
    public void draw(Graphics g, int scroll_x, int scroll_y) {
        // Calculate Link's current position on screen
        int x = this.x - scroll_x;
        int y = this.y - scroll_y;

        // If link is not currently moving, draw still image of Link's current direction and leave function
        if (!isMoving) {
            g.drawImage(linkStill[direction], x, y, null);
            return;
        }

        // If link is moving, draw images for animation depending on direction of movement
        switch (direction) {
            case 0: g.drawImage(linkUp[currImage], x, y, null); break;
            case 1: g.drawImage(linkDown[currImage], x, y, null); break;
            case 2: g.drawImage(linkLeft[currImage], x, y, null); break;
            case 3: g.drawImage(linkRight[currImage], x, y, null); break;
        }
    }

    // Update current Link image frame and direction
    public void updateImage(int direction) {
        this.direction = direction;
        isMoving = true;
        currImage++;

        // Need to check if currImage has reached max frame for each direction, reset to 0 if so
        if (currImage >= MAX_IMAGES) currImage = 0;
    }

    public void savePrev() {
        prev_x = x;
        prev_y = y;
    }

    // TODO: #2 Fix Link's position if colliding with tile
    public void stopColliding(Tile tile) {
        if (y + height >= tile.y
                && prev_y + height <= tile.y)
            y = prev_y;

        else if ((y + height / 2) <= tile.y + Tile.height
                && (prev_y + height / 2) >= tile.y + Tile.height)
            y = prev_y;

        else if (x + width > tile.x
                && prev_x + width <= tile.x)
            x = prev_x;

        else if (x <= tile.x + Tile.width
                && prev_x >= tile.x + Tile.width)
            x = prev_x;
    }
}
