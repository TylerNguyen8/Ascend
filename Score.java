// Tyler Nguyen and Andy Lai, 2024/06/15
// Class: Score
// Description: Class which creates a score object to ensure the leaderboard works

public class Score implements Comparable<Score>{
    // Instance variables
    private String name;
    private long score;

    // Description: constructor for score class
	// Parameters: takes in a string for the username and a long for the time
	// Return: nothing, but creates a new score object
    public Score(String name, long score) {
        this.name = name;
        this.score = score;
    }

    // Description: getter for username
	// Parameters: takes in nothing
	// Return: returns username as String
    public String getName() {
        return this.name;
    }

    // Description: getter for score/time to complete
	// Parameters: takes in nothing
	// Return: returns time as long
    public long getScore() {
        return this.score;
    }

    // Description: compareTo method to ensure treemap sort order works
	// Parameters: takes in a score object
	// Return: returns an integer to represent which score object is greater
    public int compareTo(Score s) {
        if(this.score == s.score) {
            return this.name.compareTo(s.name);
        }
        return (int)(this.score - s.score);
    }

}
