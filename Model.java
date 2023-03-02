// Name: Cade DuPont
// Date: 02.15.23
// Description: Class for ArrayList of tiles, marshaling / unmarshaling map.json file of tile locations

import java.util.ArrayList;

public class Model {
	ArrayList<Tile> tiles;
	Link link;

	public Model() {
		tiles = new ArrayList<Tile>();
	}

	public void update() {
		link.update();
	}

	// public boolean isColliding() {
	// 	boolean colliding = true;
	// 	for (Tile tile : tiles) {
	// 		// if (link.right < tile.left) colliding = false;
	// 		// if (link.left > tile.right) colliding = false;
	// 		// if (link.bottom < tile.top) colliding = false;
	// 		// if (link.top > tile.bottom) colliding = false;
	// 	}
	// 	return colliding;
	// }

	public Json marshal() {
		Json ob = Json.newObject();
		Json list = Json.newList();
		ob.add("tiles", list);

		for (Tile tile : tiles)
			list.add(tile.marshal());

		return ob;
	}

	public void unmarshal(Json ob) {
		tiles = new ArrayList<Tile>();
		Json list = ob.get("tiles");
		for (int i = 0; i < list.size(); i++)
			tiles.add(new Tile(list.get(i)));
	}
}