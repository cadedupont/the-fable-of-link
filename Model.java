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

	public boolean isColliding() {
		// if Link isn't not colliding with a tile, return true, otherwise return false
		for (Tile tile : tiles)
			if (!(link.x + link.width < tile.x || link.x > Tile.width + tile.x
				|| link.y + link.height < tile.y || link.y + (link.height / 2) > Tile.height + tile.y))
				return true;
		return false;
	}

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