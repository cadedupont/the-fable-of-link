// Author: Cade DuPont
// Date: 02.15.23
// Description: Main class for Zelda game

import javax.swing.JFrame;
import java.awt.Toolkit;

public class Game extends JFrame {
	Controller controller;
	Model model;
	View view;

	public Game(int width, int height) {
		model = new Model();
		controller = new Controller(model);
		view = new View(controller, model);

		view.addMouseListener(controller);
		this.addKeyListener(controller);
		this.setSize(width, height);
		this.setTitle("A5 - Adding Sprites, Polymorphism");
		this.setFocusable(true);
		this.getContentPane().add(view);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
	}

	public void run() {
		while (true) {
			// Only update Link movement if currently not in editing mode
			controller.update();
			model.update();
			view.repaint(); 

			// Update screen
			Toolkit.getDefaultToolkit().sync();

			// Sleep for 40 milliseconds (25 FPS)
			try {
				Thread.sleep(40);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}

	public static void main(String[] args) {
		// Size of window to perfectly fit tiles seems to differ based on OS- so,
		// when running build.bat on Windows or build.bash on Mac/Linux, window
		// width/height are passed as arguments when running respective scripts.
		// If no arguments are passed when running the program, then default 700x500
		// window size is used instead
		try {
			new Game(Integer.parseInt(args[0]), Integer.parseInt(args[1])).run();
		} catch (Exception e) {
			new Game(700, 500).run();
		}
	}
}
