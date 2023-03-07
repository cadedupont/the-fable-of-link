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
	
	// Link movement directions
	public static boolean keyUp, keyDown, keyLeft, keyRight;
	
	// Boolean indicating whether edit mode is currently triggered
	public static boolean editOn;
	
	public Controller(Model m) {
		this.model = m;
	}

	public void setView(View v) {
		this.view = v;
	}

	public void update() {
		if (editOn) return;

		// Save Link's previous position before updating
		model.link.savePrev();

		// If movement key is being pressed, then update Link's position
		if (keyUp) {
			model.link.y -= model.link.speed;
			model.link.updateImage(0);
		} else if (keyDown) {
			model.link.y += model.link.speed;
			model.link.updateImage(1);
		} else if (keyLeft) {
			model.link.x -= model.link.speed;
			model.link.updateImage(2);
		} else if (keyRight) {
			model.link.x += model.link.speed;
			model.link.updateImage(3);
		}

		// If Link has moved to a new room, jump to room / update window
		if (model.link.y < View.maxHeight && view.scroll_y > View.minHeight) view.scroll_y -= View.maxHeight;
		if (model.link.y > View.maxHeight && view.scroll_y < View.maxHeight) view.scroll_y += View.maxHeight;
		if (model.link.x < View.maxWidth && view.scroll_x > View.minWidth) view.scroll_x -= View.maxWidth;
		if (model.link.x > View.maxWidth && view.scroll_x < View.maxWidth) view.scroll_x += View.maxWidth;
	}

	public void keyPressed(KeyEvent e) {
		// Because function currently only takes Link movement into consideration, if edit is on, then arrow key movements
		// don't need to be read, so break out of function
		if (editOn) return;

		switch(e.getKeyCode()) {
			// If movement keys have been pressed, update corresponding boolean variables
			// and set other key booleans to false to prevent diagonal movement. Calling function
			// inside Link class to update direction Link is facing and animating movement
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
		switch(e.getKeyCode()) {
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

			// If currently touching opposite border, update scroll position to desired border
			case KeyEvent.VK_W: if (editOn && view.scroll_y == View.maxHeight) view.scroll_y -= View.maxHeight; break;
			case KeyEvent.VK_X: if (editOn && view.scroll_y == View.minHeight) view.scroll_y += View.maxHeight; break;
			case KeyEvent.VK_A: if (editOn && view.scroll_x == View.maxWidth) view.scroll_x -= View.maxWidth; break;
			case KeyEvent.VK_D: if (editOn && view.scroll_x == View.minWidth) view.scroll_x += View.maxWidth; break;

			// If editing mode is currently on, save current ArrayList of tiles to map.json
			case KeyEvent.VK_S: if (editOn) model.marshal().save("map.json"); break;

			// Trigger edit mode
			case KeyEvent.VK_E: editOn = !editOn; break;

			// Program exit cases
			case KeyEvent.VK_Q:
            case KeyEvent.VK_ESCAPE: System.exit(0); break;
		}
	}
	public void keyTyped(KeyEvent e) {}

	public void mousePressed(MouseEvent e) {
		// If editing mode is not turned on, leave function
		if (!editOn) return;

		// Calculate position clicked - take into account scroll position, snapping tile to grid
		int x = (e.getX() + view.scroll_x)  - ((e.getX() + view.scroll_x) % Tile.width);
		int y = (e.getY() + view.scroll_y) - ((e.getY() + view.scroll_y) % Tile.height);

		// Check each tile in ArrayList, if clicked on tile that already exists, remove tile and return
		for (Tile tile : model.tiles) {
			if (tile.isOnTile(x, y)) {
				model.tiles.remove(tile);
				return;
			}
		}

		// If clicked on empty space, add tile to position of empty space
		Tile tile = new Tile(x, y);
		model.tiles.add(tile);
	}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
}
