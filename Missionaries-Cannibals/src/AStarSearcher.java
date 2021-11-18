import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class AStarSearcher {

    private ArrayList<State> frontier;
    private HashSet<State> closedSet;

    AStarSearcher() {
        this.frontier = new ArrayList<>();
        this.closedSet = new HashSet<>();
    }

    State AStarClosedSet(State initialState){
        if(initialState.isFinal()) return initialState;
        // add initial state in the frontier
        this.frontier.add(initialState);
        // check for empty frontier
        while(this.frontier.size() > 0)
        {
            // get the first node out of the frontier.
            State currentState = this.frontier.remove(0);
            if(currentState.isFinal()) return currentState;
            // if the node is not in the closed set, put the children at the frontier.
            // else go to step 2.
            if(!this.closedSet.contains(currentState))
            {
                this.closedSet.add(currentState);
                this.frontier.addAll(currentState.getChildren());
                // sort the frontier based on the heuristic score to get best as first
                Collections.sort(this.frontier);
            }
        }
        return null;
    }
}
