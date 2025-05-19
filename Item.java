// Tyler Nguyen and Andy Lai, 2024/06/15
// Class: Item
// Description: Class which handles all the logic for items such as the gem and disappearing tiles. Ensures the items are able to be regenerated based on time.

import java.awt.Rectangle;
import java.util.HashSet;

public abstract class Item {
    // Instance variables
    private int itemType;
    private int map;
    private int positionX;
    private int positionY;
    private int regenTime;
    public static HashSet<Item> itemList = new HashSet<>();

    // Description: constructor for item class
	// Parameters: takes in item type, map, positionX, positionY, and regenTime all as integers
	// Return: nothing, but creates a new item object
    public Item(int itemtype, int map, int positionX, int positionY, int regenTime) {
        this.itemType = itemtype;
        this.map = map;
        this.positionX = positionX;
        this.positionY = positionY;
        this.regenTime = regenTime;
    }

    // Description: updates items (respawn them into the map)
	// Parameters: the numbered and tiled 2d arrays of the specified map
	// Return: nothing (updates whether item regenerated or not)
    public static void update(int[][]map, Rectangle[][] mapRectangles) {
        for (Item item : Item.itemList) { // checks for every item in the list
			if(GamePanel.frameCount % 60 == 0) { // for every second, amount of time it takes to regen decreases
				item.setRegenTime(item.getRegenTime() - 1);
            }
            if(item.getRegenTime() <= 0 && GamePanel.currentMap == item.getMap()) { // if the regen time is 0, respawns item back into the map
                map[item.getPositionX()][item.getPositionY()] = item.getItemType();
                mapRectangles[item.getPositionX()][item.getPositionY()] = new Rectangle(item.getPositionY()*GamePanel.tileSize, item.getPositionX()*GamePanel.tileSize, GamePanel.tileSize, GamePanel.tileSize);
                Item.itemList.remove(item);
                break;
            }
        }
    }

    // Description: getter method for the map number item is on
	// Parameters: nothing
	// Return: the map number of the specified item
    public int getMap() {
        return map;
    }

    // Description: getter method for the item type
	// Parameters: nothing
	// Return: returns the item type of the specified item
    public int getItemType() {
        return itemType;
    }

    // Description: getter method for the item regen time 
	// Parameters: nothing
	// Return: returns the specified item's regen time
    public int getRegenTime() {
        return regenTime;
    }

    // Description: setter method for the item regen time
	// Parameters: the amount of time it takes to regen the item
	// Return: nothing (sets the specified item's regen time to the parameter)
    public void setRegenTime(int regenTime) {
        this.regenTime = regenTime;
    }

    // Description: getter method for the x position of the item
	// Parameters: nothing
	// Return: returns the specified item's x position
    public int getPositionX(){
        return positionX;
    }

    // Description: getter method for the y position of the item
	// Parameters: nothing
	// Return: returns the specified item's y position
    public int getPositionY(){
        return positionY;
    }

    // Description: equals method to ensure hashset works properly
	// Parameters: boolean
	// Return: returns true or false depending on if the item is the same item the player collided with
    public boolean equals(Object o) {
        Item i = (Item) o;
        return (this.positionX + "" + this.positionY).equals(i.getPositionX() + "" + i.getPositionY());
    }

    // Description: hashcode method to ensure hashset works properly
	// Parameters: int
	// Return: returns an integer for hashcode created based of item type and positions
    public int hashCode() {
        return ("" + itemType + positionX + positionY).hashCode();
    }
}