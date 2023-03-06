// Name: Cade DuPont
// Date: 02.15.23
// Description: Class for ArrayList of tiles, marshaling / unmarshaling map.json file of tile locations

import java.util.ArrayList;

public class Model {
	ArrayList<Tile> tiles;
	Link link;

	public Model() {
		tiles = new ArrayList<Tile>();
		link = new Link();
	}

	public void update() {
		link.update();
	}

	// Marshal object into Json node, save tile locations to map.json
	public Json marshal() {
		Json ob = Json.newObject();
		Json list = Json.newList();
		ob.add("tiles", list);

		for (Tile tile : tiles)
			list.add(tile.marshal());

		return ob;
	}

	// Unmarshal tile location data from map.json, add to ArrayList of tile objects
	public void unmarshal(Json ob) {
		tiles = new ArrayList<Tile>();
		Json list = ob.get("tiles");
		for (int i = 0; i < list.size(); i++)
			tiles.add(new Tile(list.get(i)));
	}

	public boolean isColliding() {
		// If Link isn't not colliding with a tile, return true
		// If all tiles have been checked, return false
		for (Tile tile : tiles)
			if (!(link.x + link.width < tile.x || link.x > Tile.width + tile.x
				|| link.y + link.height < tile.y || link.y + (link.height / 2) > Tile.height + tile.y))
				return true;
		return false;
	}
}