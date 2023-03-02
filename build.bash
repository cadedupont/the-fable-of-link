#! /bin/bash

set -ue
javac Game.java View.java Controller.java Model.java Tile.java Json.java
java Game
