// Author: Cade DuPont
// Date: 02.15.23
// Description: Class for implementing mouse / key listeners, operations to perform with user input

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

class Controller implements MouseListener, KeyListener {
	View view;
	Model model;

	// Booleans for Link movement, toggling edit mode
	boolean keyUp, keyDown, keyLeft, keyRight;
	public static boolean editOn, potOn;

	public Controller(Model m) {
		this.model = m;
	}

	public void setView(View v) {
		this.view = v;
	}

	public void update() {
		// If edit mode is currently active, leave function since function is only used
		// for updating Link position
		if (editOn) return;

		// Save Link's previous position before updating for collision fixing
		model.link.savePrev();

		// If movement key is being pressed, then update Link's position
		// Call function to update direction Link is facing, image being displayed for
		// animation
		if (keyUp) {
			model.link.y -= model.link.speed;
			model.link.updateImage(Direction.UP);
		} else if (keyDown) {
			model.link.y += model.link.speed;
			model.link.updateImage(Direction.DOWN);
		} else if (keyLeft) {
			model.link.x -= model.link.speed;
			model.link.updateImage(Direction.LEFT);
		} else if (keyRight) {
			model.link.x += model.link.speed;
			model.link.updateImage(Direction.RIGHT);
		}

		// If Link has moved to a new room, jump to room / update window
		if (model.link.y < View.maxHeight && view.scroll_y > View.minHeight) view.scroll_y -= View.maxHeight;
		if (model.link.y > View.maxHeight && view.scroll_y < View.maxHeight) view.scroll_y += View.maxHeight;
		if (model.link.x < View.maxWidth && view.scroll_x > View.minWidth) view.scroll_x -= View.maxWidth;
		if (model.link.x > View.maxWidth && view.scroll_x < View.maxWidth) view.scroll_x += View.maxWidth;
	}

	public void keyPressed(KeyEvent e) {
		// Because function currently only takes Link movement into consideration, if
		// edit is on, then arrow key movements
		// don't need to be read, so leave function
		if (editOn)
			return;

		switch (e.getKeyCode()) {
			// If movement keys have been pressed, update corresponding boolean variables
			// and set other key booleans to false to prevent diagonal movement
			case KeyEvent.VK_UP:
				keyUp = true;
				keyDown = false;
				keyLeft = false;
				keyRight = false;
				break;
			case KeyEvent.VK_DOWN:
				keyUp = false;
				keyDown = true;
				keyLeft = false;
				keyRight = false;
				break;
			case KeyEvent.VK_LEFT:
				keyUp = false;
				keyDown = false;
				keyLeft = true;
				keyRight = false;
				break;
			case KeyEvent.VK_RIGHT:
				keyUp = false;
				keyDown = false;
				keyLeft = false;
				keyRight = true;
				break;
		}
	}

	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			// If movement keys have been released, update corresponding boolean variables
			case KeyEvent.VK_UP:
				keyUp = false;
				model.link.isMoving = false;
				break;
			case KeyEvent.VK_DOWN:
				keyDown = false;
				model.link.isMoving = false;
				break;
			case KeyEvent.VK_LEFT:
				keyLeft = false;
				model.link.isMoving = false;
				break;
			case KeyEvent.VK_RIGHT:
				keyRight = false;
				model.link.isMoving = false;
				break;

			// If currently touching opposite border, update scroll position to desired
			// border
			case KeyEvent.VK_W:
				if (editOn && view.scroll_y == View.maxHeight)
					view.scroll_y -= View.maxHeight;
				break;
			case KeyEvent.VK_X:
				if (editOn && view.scroll_y == View.minHeight)
					view.scroll_y += View.maxHeight;
				break;
			case KeyEvent.VK_A:
				if (editOn && view.scroll_x == View.maxWidth)
					view.scroll_x -= View.maxWidth;
				break;
			case KeyEvent.VK_D:
				if (editOn && view.scroll_x == View.minWidth)
					view.scroll_x += View.maxWidth;
				break;

			// Save current ArrayList of tiles to map.json / load tile locations from Json
			// file into ArrayList
			case KeyEvent.VK_S:
				model.marshal().save("map.json");
				break;
			case KeyEvent.VK_L:
				model.unmarshal(Json.load("map.json"));
				break;

			// Toggle edit mode
			case KeyEvent.VK_E:
				editOn = !editOn;
				break;

			// Throw boomerang
			case KeyEvent.VK_B:
			case KeyEvent.VK_CONTROL:
				if (!editOn)
					model.throwBoomerang();
				break;

			// Toggle ability to place pots onto screen when in edit mode
			case KeyEvent.VK_P:
				if (editOn)
					potOn = !potOn;
				break;

			// Exit program
			case KeyEvent.VK_Q:
			case KeyEvent.VK_ESCAPE:
				System.exit(0);
		}
	}
	
	public void keyTyped(KeyEvent e) {}

	public void mousePressed(MouseEvent e) {
		// If editing mode is not turned on, leave function
		if (!editOn) return;

		// Calculate position clicked - take into account scroll position, snapping tile
		// to grid
		int x = (e.getX() + view.scroll_x) - ((e.getX() + view.scroll_x) % 50);
		int y = (e.getY() + view.scroll_y) - ((e.getY() + view.scroll_y) % 50);

		// Check if a sprite has been clicked on
		for (Sprite sprite : model.sprites) {
			// If the current sprite is not a Link, check if snapped position of click is not occupied
			// by a tile or pot. Don't remove tile if in pot mode and vice versa.
			if (!sprite.isLink())
				if (sprite.clickedOn(x, y)) {
					if ((sprite.isPot() && Controller.potOn) || (sprite.isTile() && !Controller.potOn))
						model.sprites.remove(sprite);
					return;
			// If current sprite is a Link, check if Link would be colliding with a tile if
			// it were placed. If so, do nothing
			} else {
				if (model.isColliding(model.link, new Tile(x, y)))
					return;
			}
		}

		// If clicked on empty space, add either pot or tile to position of empty space
		model.sprites.add(0, (potOn) ? new Pot(x, y) : new Tile(x, y));
	}

	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
}
