// Author: Cade DuPont
// Date: 03.07.23
// Description: Class for Link character on screen

import java.awt.Image;
import java.awt.Graphics;

public class Link {
    // Integers for storing Link's coordinates / previous coordinates for collision fixing
    // Hard-coded Link's initial position on screen
    int x = 173, y = 102;
    int prev_x, prev_y;

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

    // Integer for current image of Link's still animation
    int currStill;

    // Image arrays to store Link images
    // Image[][] linkStill;
    Image[] linkStill, linkUp, linkDown, linkLeft, linkRight;
    public static int MAX_IMAGES = 10;
    static Image image = null;

    // public enum Direction {
    //     UP,
    //     DOWN,
    //     LEFT,
    //     RIGHT;

    //     // Convert enums to lowercase for reading image files
    //     @Override
    //     public String toString() {
    //         return name().toLowerCase();
    //     }
    // }

    public Link() {
        // Load link animation images into corresponding arrays
        if (image == null) {
            // Potential 2D array implementation for animated still images

            // linkStill = new Image[4][3];
            // for (int i = 0; i < linkStill.length; i++)
            //     for (int j = 0; j < linkStill[i].length; j++)
            //         linkStill[i][j] = View.loadImage("img/link/still/" + Direction.values()[i].toString() + "/" + j + ".png");

            linkStill = new Image[4];
            for (int i = 0; i < linkStill.length; i++)
                linkStill[i] = View.loadImage("img/link/still/" + i + ".png");

            linkUp = new Image[MAX_IMAGES];
            for (int i = 0; i < linkUp.length; i++)
                linkUp[i] = View.loadImage("img/link/up/" + i + ".png");

            linkDown = new Image[MAX_IMAGES];
            for (int i = 0; i < linkDown.length; i++)
                linkDown[i] = View.loadImage("img/link/down/" + i + ".png");

            linkLeft = new Image[MAX_IMAGES];
            for (int i = 0; i < linkLeft.length; i++)
                linkLeft[i] = View.loadImage("img/link/left/" + i + ".png");

            linkRight = new Image[MAX_IMAGES];
            for (int i = 0; i < linkRight.length; i++)
                linkRight[i] = View.loadImage("img/link/right/" + i + ".png");
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

        // If link is moving, draw images for animation depending on direction of movement
        if (isMoving) {
            switch (direction) {
                case 0: g.drawImage(linkUp[currImage], x, y, null); break;
                case 1: g.drawImage(linkDown[currImage], x, y, null); break;
                case 2: g.drawImage(linkLeft[currImage], x, y, null); break;
                case 3: g.drawImage(linkRight[currImage], x, y, null); break;
            }
        } else {
            // If link is not moving, draw still animation for Link's current direction
            g.drawImage(linkStill[direction], x, y, null);

            // Working implementation for animating still images- however, cycle happens too quickly

            // g.drawImage(linkStill[direction][currStill], x, y, null);

            // Don't animate still images if edit mode is toggled
            // if (!Controller.editOn)
            //     currStill++;
            // if (currStill >= 3)
            //     currStill = 0;
        }
    }

    // Update current Link image frame and direction
    public void updateImage(int direction) {
        this.direction = direction;
        isMoving = true;
        currImage++;

        // Checking if current image frame has exceeded max # of images used for animation
        if (currImage >= MAX_IMAGES)
            currImage = 0;
    }

    // Store Link's previous position before movement for collision fixing
    public void savePrev() {
        prev_x = x;
        prev_y = y;
    }

    // Function called when Link is colliding with a tile
    // Based on which side of tile Link collided with, move Link back to previous position
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
