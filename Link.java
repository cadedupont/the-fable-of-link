// Author: Cade DuPont
// Date: 03.07.23
// Description: Class for Link character on screen

import java.awt.Image;
import java.awt.Graphics;

// Declaring enums for directions Link can be facing
enum Direction {
    UP(0),
    DOWN(1),
    LEFT(2),
    RIGHT(3);

    public final int direction;

    // Private constructor for assigning enums with integer values
    private Direction(int direction) {
        this.direction = direction;
    }
    
    // Convert enums to lowercase for reading image files
    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}

public class Link {
    // Integers for Link coordinates, character width/height, previous coordinates for collision fixing
    // Hard-coding starting position in window, width and height based on max image size in sprite sheet
    int x = 173, y = 102;
    int width = 78, height = 85;
    int prev_x, prev_y;

    // Store Link's movement speed, whether Link is moving and where he's facing
    // Link facing downward upon running game
    double speed = 7.5;
    boolean isMoving;
    Direction facing = Direction.DOWN;

    // Arrays for storing Link images
    Image[][] linkMove;
    Image[] linkStill;
    static Image image = null;
    
    public static final int MAX_MOVE_IMAGES = 10;
    public static final int MAX_STILL_IMAGES = 4;
    int currImage;

    public Link() {
        // Load link animation images into corresponding arrays
        if (image == null) {
            linkStill = new Image[MAX_STILL_IMAGES];
            for (int i = 0; i < linkStill.length; i++)
                linkStill[i] = View.loadImage("img/link/still/" + i + ".png");

            linkMove = new Image[Direction.values().length][MAX_MOVE_IMAGES];
            for (int i = 0; i < linkMove.length; i++)
                for (int j = 0; j < linkMove[i].length; j++)
                    linkMove[i][j] = View.loadImage("img/link/" + Direction.values()[i].toString() + "/" + j + ".png");
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
        // If link is moving, draw images for animation depending on direction of movement
        // Otherwise, draw still image in that direction
        if (isMoving)
            g.drawImage(linkMove[facing.direction][currImage], x - scroll_x, y - scroll_y, null);
        else
            g.drawImage(linkStill[facing.direction], x - scroll_x, y - scroll_y, null);
    }

    // Update current Link image frame and direction
    public void updateImage(Direction facing) {
        this.facing = facing;
        isMoving = true;
        currImage++;

        // Checking if current image frame has exceeded max # of images used for animation
        if (currImage >= MAX_MOVE_IMAGES)
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
