package puzzles.common.solver;

import java.io.IOException;
import java.util.*;

public class Solver {
    public static int totalconfigs = 0;
    public static int uniqueconfigs=0;

    /**
     * get the shortest path
     *
     * @param start starting string
     * @return ordered configurations from start to finish
     */

    public static Collection<Configuration> getShortestPath(Configuration start) throws IOException {
        List<Configuration> queue = new ArrayList<>();
        // every time you loop through the neighbor, you add one to the total config.
        queue.add(start); // populate the queue with the starting node

        Map<Configuration, Configuration> predecessors = new HashMap<>(); // construct the predecessors data structure
        predecessors.put(start, null); // put the starting node in, and assign itself as predecessor
        totalconfigs = 1;
        uniqueconfigs = 1;

        while (!queue.isEmpty()) { // loop until either the finish node is found, or the queue is empty
            Configuration current = queue.remove(0); // dequeue fron current configuration
            if (current.isSolution()) {
                return constructPath(predecessors, current);
            }
            Collection<Configuration> neighbors = current.getNeighbors();
            for (Configuration neighbor : neighbors) { // loop over all neighbors of current
                totalconfigs++;
                if (!predecessors.containsKey(neighbor)) { // process unvisited neighbors
                    predecessors.put(neighbor, current);
                    queue.add(neighbor);
                    uniqueconfigs++;
                }
            }
        }
        return new ArrayList<>();
    }

    /**
     * Method to return a path from the starting to finishing node.
     *
     * @param predecessors Map used to reconstruct the path
     * @return a list containing the sequence of nodes comprising the path.
     * An empty list if no path exists.
     */
    private static List<Configuration> constructPath(Map<Configuration, Configuration> predecessors, Configuration current) {

        List<Configuration> path = new ArrayList<>();
        if (predecessors.containsKey(current)) {
            Configuration currConfig = current;
            while (currConfig != null) {
                path.add(0, currConfig); // gets results from individual configs (similar to getname)
                currConfig = predecessors.get(currConfig);
            }
        }
        return path;
    }
}
