import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {

    public static void main(String [] args){
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nGive the number of Missionaries and Cannibals on the left side: ");
        int missionariesAndCannibalsLeft = scanner.nextInt();
        System.out.println("Give the capacity of the boat: ");
        int boatCapacity = scanner.nextInt();
        System.out.println("Give the maximum number of crossings allowed: ");
        int crossings = scanner.nextInt();
        System.out.println("************************* MISSIONARIES AND CANNIBALS **********************************");
        System.out.println("Missionaries: " + missionariesAndCannibalsLeft + ", Cannibals: " + missionariesAndCannibalsLeft + ", Boat capacity: " + boatCapacity + ", Maximum number of crossings allowed: " + crossings);
        System.out.println("************************************ START ********************************************");
        System.out.println();

        State initialState = new State(missionariesAndCannibalsLeft, boatCapacity, crossings);

        AStarSearcher searcher = new AStarSearcher();
        long start = System.currentTimeMillis();
        State terminalState = searcher.AStarClosedSet(initialState);
        long end = System.currentTimeMillis();

        int originalCrossingsScore = 0;
        if(terminalState == null) System.out.println("A* Searcher could not find a solution.");
        else {
            // print the path from terminalState to initialState.
            State temp = terminalState; // begin from the end.
            ArrayList<State> path = new ArrayList<>();
            path.add(terminalState);
            while(temp.getFather() != null) // if father is null, then we are at the root.
            {
                originalCrossingsScore++;
                path.add(temp.getFather());
                temp = temp.getFather();
            }
            // reverse the path and print.
            Collections.reverse(path);
            for(State item: path)
            {
                item.print();
            }
            System.out.println("\nSearch time: " + (double)(end - start) / 1000 + " seconds");  // total time of searching in seconds
            System.out.println("\nFinal Crossings: " + originalCrossingsScore);
            if (originalCrossingsScore >= crossings) System.out.println("\nFail to find a solution with less crossings than given ( " + crossings + " )");
        }
    }
}
