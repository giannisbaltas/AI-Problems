import java.util.Scanner;

public class Main {
    private static int missionariesAndCannibalsLeft;
    private static int boatCapacity;
    private static int crossings;

    public static void main(String [] args){
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nGive the number of Missionaries and Cannibals on the left side: ");
        missionariesAndCannibalsLeft = scanner.nextInt();
        System.out.println("\nGive the capacity of the boat: ");
        boatCapacity = scanner.nextInt();
        System.out.println("\nGive the number of crossings: ");
        crossings = scanner.nextInt();

        State initialState = new State(missionariesAndCannibalsLeft, boatCapacity, crossings);
    }
}
