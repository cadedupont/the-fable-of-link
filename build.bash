#! /bin/bash

set -ue
javac Game.java View.java Controller.java Model.java Tile.java Json.java Link.java
java Game 700 525
