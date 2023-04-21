package puzzles.common.solver;

import java.io.IOException;
import java.util.Collection;

public interface Configuration {
    boolean isSolution();
    Collection<Configuration> getNeighbors() throws IOException;
    boolean equals(Object other);
    int hashCode();
    String toString();
}
