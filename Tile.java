// Name: Cade DuPont
// Date: 02.15.23
// Description: Class for single tile placed on JFrame window

public class Tile {
    // Static integers for storing tile images' widths and heights
    static final int width = 50, height = 50;

    // Integers for storing Tile's coordinates
    int x, y;

    // TODO: #4 Add BufferedImage attribute to store current tile's image

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Print tile information
    @Override
    public String toString() {
        return ("Tile (x, y) = (" + this.x + ", " + this.y + ")");
    }

    // Load data from Json file, set attributes of current tile
    public Tile(Json ob) {
        this.x = (int) ob.getLong("x");
        this.y = (int) ob.getLong("y");
    }

    // Check if parameter coordinates match a tile's coordinates
    public boolean isOnTile(int x, int y) {
        return (this.x == x && this.y == y);
    }

    // Marshal a tile's data to Json object
    public Json marshal() {
        Json ob = Json.newObject();
        ob.add("x", x);
        ob.add("y", y);
        return ob;
    }
}
