// Author: Cade DuPont
// Date: 02.15.23
// Description: Class for ArrayList of tiles, marshaling / unmarshaling map.json file of tile locations

import java.util.ArrayList;
import java.util.Iterator;

public class Model {
	// Store list of tiles, reference to Link
	ArrayList<Tile> tiles;
	Link link;

	public Model() {
		tiles = new ArrayList<Tile>();
		link = new Link();
	}

	public void update() {
		// If Link is colliding with a tile, snap Link to adjacent side of tile
		for (Tile tile : tiles)
			if (isColliding(tile))
				link.stopColliding(tile);
	}

	// Marshal object into Json node, save tile locations to map.json
	public Json marshal() {
		Json ob = Json.newObject();
		Json list = Json.newList();
		ob.add("tiles", list);

		// Using iterator for marshaling each tile; A4 requirement
		Iterator<Tile> it = tiles.iterator();
		while (it.hasNext()) {
			Tile tile = it.next();
			list.add(tile.marshal());
		}

		return ob;
	}

	// Unmarshal tile location data from map.json, add to ArrayList of tile objects
	public void unmarshal(Json ob) {
		tiles = new ArrayList<Tile>();
		Json list = ob.get("tiles");
		for (int i = 0; i < list.size(); i++)
			tiles.add(new Tile(list.get(i)));
	}

	// Check if Link character isn't not currently colliding with a tile
	public boolean isColliding(Tile tile) {
		return (!(link.x + link.width < tile.x
				|| link.x > Tile.width + tile.x
				|| link.y + link.height < tile.y
				|| link.y + (link.height / 2) > Tile.height + tile.y));
	}
}