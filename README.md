# The Fable of Link
A labyrinth-style game written in Java. Below are instructions on how to install and run the game on your local machine using Java. If you don't have Java installed on your machine, you can play an alternate version of the game written in JavaScript [here](http://csce.uark.edu/~cldupont/the-fable-of-link/).

## To run
Clone this repository to your local machine by running the following command in your computer's terminal:<br>
`git clone https://github.com/cadedupont/the-fable-of-link.git`

The following files are included in the `scripts` folder to run the game:<br>
`build.sh` <b>for MacOS/Linux</b><br>
`build.bat` <b>for Windows</b><br>

Different build scripts are included based on OS to ensure that the tile images fit perfectly within the game window.<br>

`build.sh` uses the `bash` shell and `build.bat` uses the `cmd` shell.<br>

Before executing one of the scripts, you'll have to add executable permissions to the file for the user. For MacOS, you can run the following command in the terminal:

`chmod u+x path/to/build.sh`<br>

For Windows, `.bat` files should be executable by default. If it's not, you can right-click on the `.bat` file, click `Properties`, and add the corresponding permissions from there.

Then, run the following command in the terminal at the root directory of the game's folder:<br>

`./scripts/build.sh` or `scripts/build.bat`<br>

and begin playing the game. Compiled class files will be placed in the `bin` folder.<br>

If neither script is used to run the game, a default window size will be provided automatically.<br>

## Controls
While in regular mode:<br>
`<Arrow Keys>` - Move Link<br>
`<Ctrl>` or `<B>` - Throw boomerang<br>
`<S>` - Save current tile/pot map<br>
`<L>` - Load tile/pot map saved in map.json file<br>
`<E>` - Toggle edit mode

While in edit mode:<br>
`<Click>` - Add/remove tiles<br>
    &emsp;- Click on empty space to add a tile<br>
    &emsp;- Click on tile to remove tile<br>
`<P>` - Toggle placing either tiles or pots

Run into pots to slide them across the room into a wall.<br>
Throw a boomerang at a pot to break the pot in its place.<br>
Slide a pot into another pot to break both pots.<br>
Broken pots will be removed from the screen after a short period of time.

## Screenshots
<p align='center'>
    <img width="812" alt="green_play" src="https://github.com/cadedupont/zelda-game/assets/98860495/b4b39bac-3730-4428-824f-160a5794f0e2">
    <img width="812" alt="blue_play" src="https://github.com/cadedupont/zelda-game/assets/98860495/730a4bb4-0d20-4173-8fae-756af70e765d">
    <img width="812" alt="red_play" src="https://github.com/cadedupont/zelda-game/assets/98860495/89b07983-d1e5-4593-9ba6-d4272c87a593">
    <img width="812" alt="green_edit" src="https://github.com/cadedupont/zelda-game/assets/98860495/d03416f8-a82c-4273-b890-5c9f5eb86d50">
</p>
