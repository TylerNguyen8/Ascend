// Tyler Nguyen and Andy Lai, 2024/06/15
// Class: Balloon/Gem
// Description: Class which creates a new balloon (gem) object that calls the abstract class' constructor

public class Balloon extends Item{

    // Description: constructor for balloon/gem class
	// Parameters: takes in item type, map, positionX, positionY, and regenTime all as integers
	// Return: nothing, but creates a new balloon/gem object by calling abstract class constructor
    public Balloon(int type, int map, int positionX, int positionY, int regenTime) {
        super(type, map, positionX, positionY, regenTime);
    }
}
