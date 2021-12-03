public class TestHeuristic {

    public static void main (String[] args) {
        int heuristicPrediction;
        for(int boatCapacity = 3; boatCapacity <= 10; boatCapacity = boatCapacity + 1){
            System.out.println("BoatCapacity(M) = " + boatCapacity + ": ");
            for(int totalLeftSide = 2; totalLeftSide <= 30; totalLeftSide = totalLeftSide + 1){
                if (totalLeftSide <= boatCapacity) {
                    heuristicPrediction = 1;
                }
                else {
                    heuristicPrediction = Math.round(2 * (totalLeftSide) / (float)(boatCapacity - 1) - 1);
                }
                System.out.println("\tTotalLeftSide(N) = " + totalLeftSide + " --> " + heuristicPrediction + " = Crossings");
            }
            System.out.println();
            System.out.println("--------------------------------------");
            System.out.println();
        }
    }
}
