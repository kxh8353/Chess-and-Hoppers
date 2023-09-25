package puzzles.clock;

import puzzles.common.solver.Configuration;

import java.util.*;

/** Project 2 part 1 - ClockConfig
 * Author: Kevin Huang
 */
public class ClockConfig implements Configuration {
    public int hours; // total hrs
    public  int starthour; //at index 1 , specific to class
    public int endhour; // at last index, specific to class

    public ClockConfig(int hours, int starthour, int endhour){
        this.hours = hours;
        this.starthour = starthour;
        this.endhour = endhour;
    }

    /**
     * checks if it reaches a solution (similar to isgoal())
     * @return if solution or not
     */

    @Override
    public boolean isSolution() {
        return starthour == endhour; // signifies clock has gone in a cycle
    }

    /**
     * get neighbors
     *
     * @return neighbors
     */
    @Override
    public Collection<Configuration> getNeighbors() {// implement collection

        List<Configuration> neighbors = new ArrayList<>();

        int firstneighbor = this.starthour-1;
        if (firstneighbor<1) {
            firstneighbor = hours;
        }
        ClockConfig neighborOne = new ClockConfig(hours, firstneighbor, endhour);
        neighbors.add(neighborOne);

        int secondneighbor = this.starthour+1;
        if (secondneighbor>hours) {
            secondneighbor = 1;
        }
        ClockConfig neighborTwo = new ClockConfig(hours, secondneighbor, endhour);
        neighbors.add(neighborTwo);
        return neighbors;
    }

    /**
     * checks if two objects are equal
     * @param other otherinstanceof
     * @return if two objects are equal
     */
    @Override
    public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof ClockConfig o){ // type comparison
            result = starthour == o.starthour && endhour == o.endhour && hours == o.hours;
        }
        return result;
    }

    /**
     * number representing an object
     * @return  hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(hours,starthour,endhour);
    }
    /**
     * represents neighbors
     * @return a string representation
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(); // set as empty string as default
        result.append(this.starthour); // add name to result
        return result + "";
    }
}

