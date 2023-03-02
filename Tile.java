// Name: Cade DuPont
// Date: 02.15.23
// Description: Class for single tile placed on JFrame window

public class Tile {
    static final int width = 50, height = 50;
    int x, y;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Tile(Json ob) {
        this.x = (int) ob.getLong("x");
        this.y = (int) ob.getLong("y");
    }

    public boolean isOnTile(int x, int y) {
        return (this.x == x && this.y == y);
    }

    public Json marshal() {
        Json ob = Json.newObject();
        ob.add("x", x);
        ob.add("y", y);
        return ob;
    }
}
