// Author: Cade DuPont
// Date: 02.15.23
// Description: Class for ArrayList of sprites, marshaling / unmarshaling map.json file of tile and pot locations

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
		// If currently in edit mode, don't update any sprites
		if (Controller.editOn) return;

		// Using iterator to allow for removing elements of the ArrayList while iterating
		Iterator<Sprite> it = sprites.iterator();
		while (it.hasNext()) {
			Sprite sprite1 = it.next();

			// Update sprites
			if (!sprite1.update()) {
				it.remove();
				continue;
			}

			// Check each sprite for collision against the first sprite
			// If colliding, perform action and break out of inner loop
			for (Sprite sprite2 : sprites) {
				if (isColliding(sprite1, sprite2)) {
					// System.out.println(sprite2.toString());
					// If Link is colliding with a tile, move Link out of the tile
					if (sprite1.isLink() && sprite2.isTile())
						((Link)sprite1).stopColliding(sprite2);

					else if (sprite1.isTile() && sprite2.isLink())
						((Link)sprite2).stopColliding(sprite1);

					// If Link is colliding with a pot, begin sliding the pot in the direction Link
					// is facing
					else if ((sprite1.isLink() && sprite2.isPot()) && !((Pot)sprite2).isBroken)
						((Pot)sprite2).sliding = ((Link)sprite1).facing;

					else if ((sprite1.isPot() && sprite2.isLink()) && !((Pot)sprite1).isBroken)
						((Pot)sprite1).sliding = ((Link)sprite2).facing;

					// If a boomerang is colliding with a tile, remove the boomerang from the Sprite
					// ArrayList
					else if (sprite1.isBoomerang() && sprite2.isTile())
						it.remove();

					// If a boomerang is colliding with a pot, display broken pot image and begin
					// the countdown
					else if (sprite1.isBoomerang() && sprite2.isPot()) {
						if (!((Pot)sprite2).isBroken)
							it.remove();
						((Pot)sprite2).isBroken = true;
					}

					// If a pot is colliding with a tile, stop pot movement, display broken pot
					// image, begin countdown
					else if (sprite1.isPot() && sprite2.isTile()) {
						((Pot)sprite1).isBroken = true;
						((Pot)sprite1).sliding = null;
					}

					// If two pots collide, break both pots and stop movement
					else if ((sprite1.isPot() && sprite2.isPot()) && sprite1 != sprite2) {
						((Pot)sprite1).isBroken = true;
						((Pot)sprite2).isBroken = true;
						((Pot)sprite1).sliding = null;
						((Pot)sprite2).sliding = null;
					}

					// If a boomerang is colliding with Link, remove the boomerang from the list of sprites
					if (sprite1.isBoomerang() && sprite2.isLink() && ((Boomerang)sprite1).updateCount >= 15)
						it.remove();

					// Collision has been detected, break out of inner loop
					break;
				}
			}
		}
	}

	// Check if two sprites are colliding
	public boolean isColliding(Sprite sprite1, Sprite sprite2) {
		return !(sprite1.x + sprite1.width < sprite2.x
				|| sprite1.x > sprite2.width + sprite2.x
				|| sprite1.y + sprite1.height < sprite2.y
				|| sprite1.y + (sprite1.height / 2) > sprite2.height + sprite2.y);
	}

	// Instantiate new Boomerang object, add to ArrayList of sprites
	public void throwBoomerang() {
		sprites.add(new Boomerang((link.x + link.width / 2), (link.y + link.height / 2), link.facing));
	}

	// Marshal object into Json node, save tile locations to map.json
	public Json marshal() {
		Json ob = Json.newObject();
		Json tileList = Json.newList();
		Json potList = Json.newList();
		ob.add("tiles", tileList);
		ob.add("pots", potList);

		for (Sprite sprite : sprites)
			if (sprite.isTile())
				tileList.add(sprite.marshal());
			else if (sprite.isPot())
				potList.add(sprite.marshal());

		return ob;
	}

	// Unmarshal tile and pot location data from map.json, add to ArrayList of tile
	// and pot objects
	public void unmarshal(Json ob) {
		sprites = new ArrayList<Sprite>();
		Json tileList = ob.get("tiles");
		Json potList = ob.get("pots");

		for (int i = 0; i < tileList.size(); i++)
			sprites.add(new Tile(tileList.get(i)));

		for (int i = 0; i < potList.size(); i++)
			sprites.add(new Pot(potList.get(i)));

		sprites.add(link);
	}
}