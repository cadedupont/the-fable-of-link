# The Fable of Link
A labyrinth-style game written in Java.

---

### To run<br>
The following files are included in the `scripts` folder to run the game:<br>
`build.sh` <b>for MacOS/Linux</b><br>
`build.bat` <b>for Windows</b><br>

Different build scripts are included based on OS to ensure that the tile images fit perfectly within the game window.<br>
`build.sh` uses the `bash` shell and `build.bat` uses the `cmd` shell.<br>

Before executing one of the scripts, you'll have to add executable permissions to the file for the user.<br>
For MacOS, you can run the following command in the terminal:
`chmod u+x path/to/build.sh`<br>
For Windows, `.bat` files should be executable by default. If it's not, you can right-click on the `.bat` file, click `Properties`, and add the corresponding permissions from there.

Then, run the following command in the terminal at the root directory of the game's folder:<br>
`./scripts/build.sh` or `scripts/build.bat`<br>
To begin playing the game. Compiled class files will be placed in the `bin` folder.<br>

If neither script is used to run the game, a default window size will be provided automatically.<br>

---

### Controls<br>
While in regular mode:<br>
* [Arrow Keys]: Move Link
* [Ctrl] or [B]: Throw boomerang
* [S]: Save current tile/pot map
* [L]: Load tile/pot map saved in map.json file
* [E]: Toggle edit mode

While in edit mode:
* [Click]: Add/remove tiles
    - click on empty space to add a tile
    - click on tile to remove tile
* [P]: Toggle placing either tiles or pots

Run into pots to slide them across the room into a wall.<br>
Throw a boomerang at a pot to break the pot in its place.<br>
Slide a pot into another pot to break both pots.<br>
Broken pots will be removed from the screen after a short period of time.

---

### The `extra` folder
The `extra` folder contains ports of the Zelda game written in both JavaScript and Python.

The `js` folder contains both a `Game.html` file and a `json-to-txt` folder. To run the game in JavaScript, simply open the `Game.html` file in your favorite browser. The `json-to-txt` folder contains a C++ program written to parse the `map.json` file into a 2D array of sprites. This was the preffered method as opposed parsing the `map.json` file directly.

The `python` folder contains a `game.py` file and a `requirements.txt` file. Before running Python script, you'll need to install necessary dependencies using `pip`:

`pip install -r requirements.txt`

Then, run the Python script in your terminal:

`python game.py`

---

### Images:
<p align='center'>
    <img width="812" alt="Screenshot 2023-05-15 at 3 42 27 PM" src="https://github.com/cadedupont/zelda-game/assets/98860495/b4b39bac-3730-4428-824f-160a5794f0e2">
    <img width="812" alt="Screenshot 2023-05-15 at 3 43 02 PM" src="https://github.com/cadedupont/zelda-game/assets/98860495/730a4bb4-0d20-4173-8fae-756af70e765d">
    <img width="812" alt="Screenshot 2023-05-15 at 3 44 46 PM" src="https://github.com/cadedupont/zelda-game/assets/98860495/89b07983-d1e5-4593-9ba6-d4272c87a593">
    <img width="812" alt="Screenshot 2023-05-15 at 3 46 15 PM" src="https://github.com/cadedupont/zelda-game/assets/98860495/d03416f8-a82c-4273-b890-5c9f5eb86d50">
</p>
