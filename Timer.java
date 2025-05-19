// Tyler Nguyen and Andy Lai, 2024/06/15
// Class: Timer
// Description: Class which allows for ease of use for displaying and holding time

public class Timer {

    long total = 0; // total amount of time in milliseconds
    int minutes = 0; // minutes part of total time
    int seconds = 0; // seconds part of total time
    int milliseconds = 0; // milliseconds part of total time
    
    // Description: initializes Timer object
	// Parameters: takes in time in milliseconds (long)
	// Return: nothing (initalizes object)
    public Timer(long time){
        total = time;
        minutes = (int)(time/60000);
        seconds = (int)(time/1000)%60;
        milliseconds = (int)(time)%1000/10;
    }

    // Description: resets timer object based on inputted time
	// Parameters: takes in time in milliseconds (long)
	// Return: nothing (resets time)
    public void refactor(long time){
        total = time;
        minutes = (int)(time/60000);
        seconds = (int)(time/1000)%60;
        milliseconds = (int)(time)%1000/10;
    }
    
    // Description: getter method for total time
	// Parameters: nothing
	// Return: total time in milliseconds
    public long getTotal() {
        return total;
    }

    // Description: getter method for minutes
	// Parameters: nothing
	// Return: amount of minutes in total time
    public int getMinutes() {
        return minutes;
    }

    // Description: getter method for seconds
	// Parameters: nothing
	// Return: amount of seconds in total time
    public int getSeconds() {
        return seconds;
    }

    // Description: getter method for milliseconds
	// Parameters: nothing
	// Return: amount of milliseconds in total time
    public int getMilliseconds() {
        return milliseconds;
    }

    // Description: toString method that allows to display time in minutes:seconds:milliseconds
	// Parameters: nothing
	// Return: a string of the time in format: minutes:seconds:milliseconds
    public String toString(){
        return String.format("%02d:%02d:%02d", minutes, seconds, milliseconds);
    }
}
