// Author: Cade DuPont
// Date: 02.15.23
// Description: Class for single tile placed on JFrame window

import java.awt.Graphics;
import java.awt.Image;

public class Tile extends Sprite {
    // Store possible tile images
    static Image greenTile, redTile, purpleTile, cyanTile;

    // Set single tile's coordinates
    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 50;
        this.height = 50;

        // Lazy loading for tile images
        if (greenTile == null)
            greenTile = View.loadImage("img/tiles/green.jpg");
        if (redTile == null)
            redTile = View.loadImage("img/tiles/red.jpg");
        if (purpleTile == null)
            purpleTile = View.loadImage("img/tiles/purple.jpg");
        if (cyanTile == null)
            cyanTile = View.loadImage("img/tiles/cyan.jpg");
    }

    // Load data from Json file, set attributes of current tile
    public Tile(Json ob) {
        this.x = (int) ob.getLong("x");
        this.y = (int) ob.getLong("y");
        this.width = 50;
        this.height = 50;

        // Lazy loading for tile images
        if (greenTile == null)
            greenTile = View.loadImage("img/tiles/green.jpg");
        if (redTile == null)
            redTile = View.loadImage("img/tiles/red.jpg");
        if (purpleTile == null)
            purpleTile = View.loadImage("img/tiles/purple.jpg");
        if (cyanTile == null)
            cyanTile = View.loadImage("img/tiles/cyan.jpg");
    }

    // Print tile information
    @Override
    public String toString() {
        return "Tile (x, y) = (" + x + ", " + y + ")";
    }

    // Identify current Sprite as a tile
    @Override
    public boolean isTile() {
        return true;
    }

    public boolean update() {
        return true;
    }

    // Marshal a tile's data to Json object
    public Json marshal() {
        Json ob = Json.newObject();
        ob.add("x", x);
        ob.add("y", y);
        return ob;
    }

    // Draw tile image onto screen; different color based on tile position
    public void draw(Graphics g, int scroll_x, int scroll_y) {
        if (x < View.maxWidth && y < View.maxHeight)
            g.drawImage(greenTile, x - scroll_x, y - scroll_y, null);
        if (x >= View.maxWidth && y < View.maxHeight)
            g.drawImage(redTile, x - scroll_x, y - scroll_y, null);
        if (x < View.maxWidth && y >= View.maxHeight)
            g.drawImage(purpleTile, x - scroll_x, y - scroll_y, null);
        if (x >= View.maxWidth && y >= View.maxHeight)
            g.drawImage(cyanTile, x - scroll_x, y - scroll_y, null);
    }
}
