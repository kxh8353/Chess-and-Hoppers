package puzzles.strings;

import puzzles.common.solver.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class StringsConfig {

    public String startstring;
    public String endstring;

    public StringsConfig(String startstring, String endstring){
        this.startstring = startstring;
        this.endstring = endstring;
    }

    /**
     * checks if it is solution
     * @return true if startstring equals endstring, false otherwise
     */
    @Override
    public boolean isSolution() {

        return startstring.equals(endstring);
    }

    /**
     * get neighboring letters
     * @return neighboring letters
     */
    @Override
    public Collection<Configuration> getNeighbors() {
        List<Configuration> neighbors = new ArrayList<>();
        for (int i = 0; i<startstring.length(); i++){ // loops over length of startstring
            char Char = this.startstring.charAt(i); // get a character from the startstring
            if (Char == 'A'){
                StringBuilder build = new StringBuilder();
                build.append(startstring); // adds the letters
                build.setCharAt(i, 'Z'); // if Z then moves to Z
                neighbors.add(new StringsConfig(build.toString(), endstring));
                build.setCharAt(i, 'B');// create the b
                neighbors.add(new StringsConfig(build.toString(), endstring));
                return neighbors;
            }
            if (Char == 'Z'){
                StringBuilder build = new StringBuilder();
                build.append(startstring); // adds the letters
                build.setCharAt(i, 'Y'); // if z then moves to A
                neighbors.add(new StringsConfig(build.toString(), endstring));
                build.setCharAt(i, 'A'); // if z then moves to A
                neighbors.add(new StringsConfig(build.toString(), endstring));
                return neighbors;
            }else {
                StringBuilder build = new StringBuilder(); // default letters for B-Y
                build.append(startstring);
                int minus = startstring.charAt(i) - 1; // typecasst to character
                int add = startstring.charAt(i) + 1;
                char c = (char) minus;
                char b = (char) add;
                build.setCharAt(i, c); // set left defaults
                neighbors.add(new StringsConfig(build.toString(), endstring));
                build.setCharAt(i, b); // set right defaults
                neighbors.add(new StringsConfig(build.toString(), endstring));
            }
        }
        return neighbors;
    }

    /**
     * compares if two objects are equal
     * @param other other instanceof object
     * @return true if objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof StringsConfig s){
            result = startstring.equals(s.startstring) && endstring.equals(s.endstring);
        }
        return result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startstring, endstring);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(this.startstring);
        return result + "";
    }
}
