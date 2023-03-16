// Author: Cade DuPont
// Date: 02.15.23
// Description: Class for ArrayList of sprites, marshaling / unmarshaling map.json file of tile locations

import java.util.ArrayList;
import java.util.Iterator;

public class Model {
	// Store list of sprites, reference to Link
	ArrayList<Sprite> sprites;
	Link link;

	public Model() {
		sprites = new ArrayList<Sprite>();
		link = new Link();
	}

	public void update() {
		// If Link is colliding with a tile, move Link to adjacent side of tile
		for (Sprite sprite : sprites) {
			// sprite.update();
			if (isColliding(link, sprite))
				if (sprite.isTile())
					link.stopColliding(sprite);
		}
	}

	// Check if two sprites are colliding
	public boolean isColliding(Sprite a, Sprite b) {
		return !(a.x + a.width < b.x
				|| a.x > b.width + b.x
				|| a.y + a.height < b.y
				|| a.y + (a.height / 2) > b.height + b.y);
	}

	// Marshal object into Json node, save tile locations to map.json
	public Json marshal() {
		Json ob = Json.newObject();
		Json list = Json.newList();
		ob.add("sprites", list);

		// Using iterator for marshaling each tile; A4 requirement
		Iterator<Sprite> it = sprites.iterator();
		while (it.hasNext())
			list.add(it.next().marshal());

		return ob;
	}

	// Unmarshal tile location data from map.json, add to ArrayList of tile objects
	public void unmarshal(Json ob) {
		sprites = new ArrayList<Sprite>();
		Json list = ob.get("sprites");
		for (int i = 0; i < list.size(); i++)
			sprites.add(new Tile(list.get(i)));
		sprites.add(link);
	}	
}