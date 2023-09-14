# Name: Cade DuPont
# Date: 04.27.23
# Description: Porting Link Game to Python

import pygame
from pygame.locals import *
from time import sleep
import json

# Declare dictionary for directions
directions = {
    "UP": 0,
    "DOWN": 1,
    "LEFT": 2,
    "RIGHT": 3
}

class Sprite():
    # Set sprite's coordinates, width, and height
    def __init__(self, x, y, width, height):
        self.x = x
        self.y = y
        self.width  = width
        self.height = height

class Link(Sprite):
    # Declare static variables for Link's images
    walkImages = None
    stillImages = None

    def __init__(self):
        # Call super constructor for setting x, y, width, and height
        super().__init__(173, 102, 78, 85)

        # Declare variables for Link's previous position
        self.prev_x = 0
        self.prev_y = 0

        # Declare variables for Link's speed, initial direction, and movement status
        self.speed = 7.5
        self.isMoving = False
        self.direction = directions["DOWN"]

        # Declare variable for current Link image being displayed
        self.currImage = 0

        # Load Link's movement images if they haven't been loaded yet
        if (Link.walkImages == None):
            Link.walkImages = [4]
            for i in range(0, 4):
                Link.walkImages[i] = []
                for j in range(0, 10):
                    Link.walkImages[i].append(pygame.image.load("../../img/link/" + list(directions.keys())[i].lower() + "/" + str(j) + ".png"))
                Link.walkImages.append(Link.walkImages[i])

        # Load Link's still images if they haven't been loaded yet
        if (Link.stillImages == None):
            Link.stillImages = []
            for i in range(0, 4):
                Link.stillImages.append(pygame.image.load("../../img/link/still/" + str(i) + ".png"))

    # Return string representation of Link
    def __str__(self):
        return "Link (x, y) = (" + str(self.x) + ", " + str(self.y) + ")";

    def update(self):
        return True;

    # Draw Link image on screen (different based on whether Link is moving, current image frame, direction)
    def draw(self, screen, scroll_x, scroll_y):
        screen.blit(Link.walkImages[self.direction][self.currImage] if self.isMoving else Link.stillImages[self.direction], (self.x - scroll_x, self.y - scroll_y))

    # Cycle through Link's walking animation
    def cycleImage(self, direction):
        self.direction = direction
        self.isMoving = True
        self.currImage = (self.currImage + 1) % 10

    # Save Link's previous position for collision fixing
    def savePrev(self):
        self.prev_x = self.x
        self.prev_y = self.y

    def stopColliding(self, tile):
        # Bottom side of Link colliding with upper side of tile
        if (self.y + self.height >= tile.y
                and self.prev_y + self.height <= tile.y):
            self.y = self.prev_y;

        # Top side of Link colliding with bottom side of tile
        elif (self.y <= tile.y + tile.height
                and self.prev_y + self.height / 2 >= tile.y + tile.height):
            self.y = self.prev_y;
        
        # Right side of Link colliding with left side of tile
        elif (self.x + self.width >= tile.x
                and self.prev_x + self.width <= tile.x):
            self.x = self.prev_x;

        # Left side of Link colliding with right side of tile
        elif (self.x <= tile.x + tile.width
                and self.prev_x >= tile.x + tile.width):
            self.x = self.prev_x;

class Tile(Sprite):
    # Declare static variables for tile images
    greenTile = None
    redTile = None
    purpleTile = None
    cyanTile = None

    def __init__(self, x, y):
        # Call super constructor for setting x, y, width, and height
        super().__init__(x, y, 50, 50)

        # Load tile images if they haven't been loaded yet
        if (Tile.greenTile == None):
            Tile.greenTile = pygame.image.load("../../img/tiles/green.jpg")
        if (Tile.redTile == None):
            Tile.redTile = pygame.image.load("../../img/tiles/red.jpg")
        if (Tile.purpleTile == None):
            Tile.purpleTile = pygame.image.load("../../img/tiles/purple.jpg")
        if (Tile.cyanTile == None):
            Tile.cyanTile = pygame.image.load("../../img/tiles/cyan.jpg")

    # Return string representation of Tile
    def __str__(self):
        return "Tile (x, y) = (" + str(self.x) + ", " + str(self.y) + ")";

    def update(self):
        return True;

    # Draw different color Tile image on screen based on its position
    def draw(self, screen, scroll_x, scroll_y):
        if self.x < View.screenWidth and self.y < View.screenHeight:
            screen.blit(Tile.greenTile, (self.x - scroll_x, self.y - scroll_y))
        if self.x >= View.screenWidth and self.y < View.screenHeight:
            screen.blit(Tile.redTile, (self.x - scroll_x, self.y - scroll_y))
        if self.x < View.screenWidth and self.y >= View.screenHeight:
            screen.blit(Tile.purpleTile, (self.x - scroll_x, self.y - scroll_y))
        if self.x >= View.screenWidth and self.y >= View.screenHeight:
            screen.blit(Tile.cyanTile, (self.x - scroll_x, self.y - scroll_y))

class Boomerang(Sprite):
    # Declare static variable for boomerang images
    images = None

    def __init__(self, x, y, direction):
        # Call super constructor for setting x, y, width, and height
        super().__init__(x, y, 10, 10)

        # Set boomerang speed, direction, and current image
        self.speed = 7.5
        self.direction = direction
        self.currImage = 0

        # Load boomerang images if they haven't been loaded yet
        if (Boomerang.images == None):
            Boomerang.images = []
            for i in range(0, 4):
                Boomerang.images.append(pygame.image.load("../../img/boomerangs/" + str(i) + ".png"))

    # Return string representation of Boomerang
    def __str__(self):
        return "Boomerang (x, y) = (" + str(self.x) + ", " + str(self.y) + ")";

    def update(self):
        # Move boomerang in direction it was thrown
        if self.direction == directions["UP"]:
            self.y -= self.speed
        elif self.direction == directions["DOWN"]:
            self.y += self.speed
        elif self.direction == directions["LEFT"]:
            self.x -= self.speed
        elif self.direction == directions["RIGHT"]:
            self.x += self.speed
        
        # Cycle through boomerang images
        self.currImage = (self.currImage + 1) % 4
        return True;

    # Draw boomerang image on screen
    def draw(self, screen, scroll_x, scroll_y):
        screen.blit(Boomerang.images[self.currImage], (self.x - scroll_x, self.y - scroll_y))

class Pot(Sprite):
    # Declare static variables for pot images
    whole = None
    broken = None

    def __init__(self, x, y):
        # Call super constructor for setting x, y, width, and height
        super().__init__(x, y, 48, 48)

        # Declare speed, countdown, initial broken status, and direction
        self.isBroken = False
        self.direction = None
        self.speed = 7.5
        self.countdown = 75

        # Load pot images if they haven't been loaded yet
        if (Pot.whole == None):
            Pot.whole = pygame.image.load("../../img/pots/whole.png")
        if (Pot.broken == None):
            Pot.broken = pygame.image.load("../../img/pots/broken.png")

    # Return string representation of Pot
    def __str__(self):
        return "Pot (x, y) = (" + str(self.x) + ", " + str(self.y) + ")";

    def update(self):
        # Move pot in direction it was pushed
        if self.direction == directions["UP"]:
            self.y -= self.speed
        elif self.direction == directions["DOWN"]:
            self.y += self.speed
        elif self.direction == directions["LEFT"]:
            self.x -= self.speed
        elif self.direction == directions["RIGHT"]:
            self.x += self.speed

        # Decrement countdown if pot is broken
        if (self.isBroken):
            self.countdown -= 1
        return not self.countdown <= 0

    # Draw pot image on screen
    def draw(self, screen, scroll_x, scroll_y):
        screen.blit(Pot.broken if self.isBroken else Pot.whole, (self.x - scroll_x, self.y - scroll_y))

class Model():
    def __init__(self):
        # Declare sprites list
        self.sprites = []

        # Load tiles and pots from map.json into sprites array
        data = json.load(open("../../src/map.json", "r"))
        for tile in data["tiles"]:
            self.sprites.append(Tile(tile["tile_x"], tile["tile_y"]))
        for pot in data["pots"]:
            self.sprites.append(Pot(pot["pot_x"], pot["pot_y"]))

        # Declare Link object and add to sprites list
        self.link = Link()
        self.sprites.append(self.link)

    def update(self):
        for sprite1 in self.sprites:

            # If sprite's update method returns False, remove sprite from list
            if not sprite1.update():
                self.sprites.remove(sprite1)
                continue

            # If sprite is a Tile, continue to next sprite to reduce number of comparisons and improve performance
            if isinstance(sprite1, Tile):
                continue

            for sprite2 in self.sprites:
                # If the outer loop and inner loop sprite are the same, continue to next sprite to prevent collision detection with itself
                if sprite1 == sprite2:
                    continue

                # Declare boolean to determine if sprite should be removed from list
                remove = False

                # If two sprites are colliding, check which sprites and perform some behavior
                if self.isColliding(sprite1, sprite2):

                    # If Link collides with a Tile, stop Link's movement
                    if isinstance(sprite1, Link) and isinstance(sprite2, Tile):
                        sprite1.stopColliding(sprite2)

                    # If Link collides with a Pot, move the Pot in the direction of Link
                    if isinstance(sprite1, Link) and isinstance(sprite2, Pot) and not sprite2.isBroken:
                        sprite2.direction = sprite1.direction

                    # If a Boomerang collides with a Pot, remove the Boomerang and break the Pot
                    if isinstance(sprite1, Boomerang) and isinstance(sprite2, Pot) and not sprite2.isBroken:
                        remove = True
                        sprite2.isBroken = True

                    # If a Boomerang collides with a Tile, remove the Boomerang
                    if isinstance(sprite1, Boomerang) and isinstance(sprite2, Tile):
                        remove = True
                    
                    # If a Pot collides with a Tile, break the Pot and stop its movement
                    if isinstance(sprite1, Pot) and isinstance(sprite2, Tile) and not sprite1.isBroken:
                        sprite1.isBroken = True
                        sprite1.direction = None

                    # If two Pots collide, break both Pots and stop their movement
                    if isinstance(sprite1, Pot) and isinstance(sprite2, Pot):
                        sprite1.isBroken = True
                        sprite2.isBroken = True
                        sprite1.direction = None
                        sprite2.direction = None
                
                # If a sprite is marked for removal, remove it from the list and break out of the inner loop
                if (remove):
                    self.sprites.remove(sprite1)
                    break

    # Check if two sprites are colliding
    def isColliding(self, sprite1, sprite2):
        return not (sprite1.x + sprite1.width < sprite2.x
                or sprite1.x > sprite2.width + sprite2.x
                or sprite1.y + sprite1.height < sprite2.y
                or sprite1.y + (sprite1.height / 2) > sprite2.height + sprite2.y);

    # Throw boomerang from Link's position
    def throwBoomerang(self):
        self.sprites.append(Boomerang(self.link.x + self.link.width / 2, self.link.y + self.link.height / 2, self.link.direction))

class View():
    # Declare static variables for screen width and height
    screenWidth = 700
    screenHeight = 500

    def __init__(self, model):
        # Initialize pygame and set screen size
        self.model = model
        self.screen = pygame.display.set_mode((View.screenWidth, View.screenHeight))

        # Initialize scroll variables
        self.scroll_x = 0
        self.scroll_y = 0

    def update(self):
        # Depending on current room position, draw different background color
        position = self.scroll_x + self.scroll_y
        if position == 0:
            self.screen.fill([146, 220, 167])
        elif position == View.screenWidth:
            self.screen.fill([223, 135, 123])
        elif position == View.screenHeight:
            self.screen.fill([213, 140, 234])
        elif position == View.screenWidth + View.screenHeight:
            self.screen.fill([129, 227, 240])

        # Draw each sprite
        for sprite in self.model.sprites:
            sprite.draw(self.screen, self.scroll_x, self.scroll_y)

        # Update screen
        pygame.display.flip()

class Controller():
    def __init__(self, model, view):
        # Initialize pygame, set run boolean to True
        self.model = model
        self.view = view
        self.run = True

    def update(self):
        for event in pygame.event.get():
            # Check if user has quit, if so then set boolean to False
            if event.type == QUIT:
                self.run = False

            elif event.type == KEYUP:
                # Check if user has pressed escape or q, if so then set boolean to False
                if event.key == K_ESCAPE or event.key == K_q:
                    self.run = False
                
                # Check if user has released arrow keys, if so then stop Link's movement
                if event.key == K_UP or event.key == K_DOWN or event.key == K_LEFT or event.key == K_RIGHT:
                    self.model.link.isMoving = False
                
                # Check if user has released b or ctrl, if so then throw boomerang
                if event.key == K_b or event.key == K_LCTRL or event.key == K_RCTRL:
                    self.model.throwBoomerang()

        # Get keys pressed
        keys = pygame.key.get_pressed()

        # Save Link's previous position
        self.model.link.savePrev()

        # Check if user has pressed arrow keys, if so then move Link
        if keys[K_UP]:
            self.model.link.y -= self.model.link.speed
            self.model.link.cycleImage(directions["UP"])
        elif keys[K_DOWN]:
            self.model.link.y += self.model.link.speed
            self.model.link.cycleImage(directions["DOWN"])
        elif keys[K_LEFT]:
            self.model.link.x -= self.model.link.speed
            self.model.link.cycleImage(directions["LEFT"])
        elif keys[K_RIGHT]:
            self.model.link.x += self.model.link.speed
            self.model.link.cycleImage(directions["RIGHT"])

        # If Link has moved to a new room, update scroll position
        if self.model.link.y + self.model.link.height / 2 < View.screenHeight and self.view.scroll_y > 0:
            self.view.scroll_y -= View.screenHeight
        if self.model.link.y + self.model.link.height / 2 > View.screenHeight and self.view.scroll_y < View.screenHeight:
            self.view.scroll_y += View.screenHeight
        if self.model.link.x + self.model.link.width / 2 < View.screenWidth and self.view.scroll_x > 0:
            self.view.scroll_x -= View.screenWidth
        if self.model.link.x + self.model.link.width / 2 > View.screenWidth and self.view.scroll_x < View.screenWidth:
            self.view.scroll_x += View.screenWidth

pygame.init()
pygame.display.set_caption("A8 - Porting Link Game to Python")
pygame.display.set_icon(pygame.image.load("../../img/pots/whole.png"))

model = Model()
view = View(model)
controller = Controller(model, view)

while controller.run:
    controller.update()
    model.update()
    view.update()
    sleep(0.035)