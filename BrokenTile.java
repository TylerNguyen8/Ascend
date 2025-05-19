// Tyler Nguyen and Andy Lai, 2024/06/15
// Class: Broken Tile
// Description: Class which creates a new broken tile object that calls the abstract class' constructor

public class BrokenTile extends Item{
    // Instance variable
    private int breakTime;

    // Description: constructor for broken tile class
	// Parameters: takes in item type, map, positionX, positionY, regenTime, and break time all as integers
	// Return: nothing, but creates a new broken tile object by calling abstract class constructor
    public BrokenTile(int itemtype, int map, int positionX, int positionY, int regenTime, int breakTime) {
        super(itemtype, map, positionX, positionY, regenTime);
        this.breakTime = breakTime;
    }

    // Description: getter for break time
	// Parameters: takes in nothing
	// Return: returns break time as integer
    public int getBreakTime(){
        return this.breakTime;
    }

    // Description: setter for break time
	// Parameters: takes in integer to update break time
	// Return: returns nothing, but updates break time
    public void setBreakTime(int breakTime){
        this.breakTime = breakTime;
    }
}
