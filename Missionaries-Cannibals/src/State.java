import java.util.ArrayList;

public class State implements Comparable<State>{
    public static int uniqueCode = 1;
    private int identifier;
    private int cannibalsLeft;
    private int cannibalsRight;
    private int missionariesRight;
    private int missionariesLeft;
    private int crossings;
    private int boatCapacity;

    enum BoatSide {
        RIGHT,
        LEFT
    }

    private BoatSide sideOfBoat;

    // A star score
    private int score;
    private int g;

    private State father = null;

    public State(int numberOfCannibalsAndMissionaries, int boatCapacity, int crossings ){
        this.cannibalsLeft = numberOfCannibalsAndMissionaries;
        this.missionariesLeft = numberOfCannibalsAndMissionaries;
        this.boatCapacity = boatCapacity;
        this.crossings = crossings;
        this.cannibalsRight = 0;
        this.missionariesRight = 0;
        this.identifier = uniqueCode++;
        this.sideOfBoat = BoatSide.LEFT;
    }

    public State (int missionariesLeft, int cannibalsLeft, int missionariesRight, int cannibalsRight, int boatCapacity, BoatSide sideOfBoat){
        this.cannibalsLeft = cannibalsLeft;
        this.cannibalsRight = cannibalsRight;
        this.missionariesLeft = missionariesLeft;
        this.missionariesRight = missionariesRight;
        this.boatCapacity = boatCapacity;
        this.sideOfBoat = sideOfBoat;
        this.identifier = uniqueCode++;
    }

    public void print(){
        if (sideOfBoat.equals(BoatSide.LEFT)) {
            System.out.println(" ***************************************");
            System.out.println("|\t\t\t$\t\t\t\t$\t\t\t|");
            System.out.println("| M: " + missionariesLeft + "\t\t$   |>\t\t\t$ M: " + missionariesRight + "\t\t|");
            System.out.println("|\t\t\t$ \\_|_/\t\t\t$\t\t\t|");
            System.out.println("| C: " + cannibalsLeft + "\t\t$\t\t\t\t$ C: " + cannibalsRight + "\t\t|");
            System.out.println("|\t\t\t$\t\t\t\t$\t\t\t|");
            System.out.println(" ***************************************");
        }
        else {
            System.out.println(" ***************************************");
            System.out.println("|\t\t\t$\t\t\t\t$\t\t\t|");
            System.out.println("| M: " + missionariesLeft + "\t\t$\t\t    |>  $ M: " + missionariesRight + "\t\t|");
            System.out.println("|\t\t\t$\t\t  \\_|_/ $\t\t\t|");
            System.out.println("| C: " + cannibalsLeft + "\t\t$\t\t\t\t$ C: " + cannibalsRight + "\t\t|");
            System.out.println("|\t\t\t$\t\t\t\t$\t\t\t|");
            System.out.println(" ***************************************");
        }
    }

    ArrayList<State> getChildren(){
        ArrayList<State> children = new ArrayList<>();

        if (sideOfBoat == BoatSide.LEFT) {
            for (int i = 0; i <= missionariesLeft; i++) {
                for (int j = 0; j <= cannibalsLeft; j++) {
                    // (i == 0 || i >= j) ---> if i is 0, no check is needed. otherwise, i >= j is a must
                    if ((i + j) != 0 && ((i + j) <= boatCapacity) && (i == 0 || i >= j)) {
                        State child = new State(missionariesLeft - i, cannibalsLeft - j, missionariesRight + i,
                                cannibalsRight + j, boatCapacity, BoatSide.RIGHT);
                        if (child.isValid()) {
                            child.heuristic();
                            child.setFather(this);
                            children.add(child);
//                            System.out.println(child);
                        }
                    }
                }
            }
        } else if (sideOfBoat == BoatSide.RIGHT) {
            for (int i = 0; i <= missionariesRight; i++) {
                for (int j = 0; j <= cannibalsRight; j++) {

                    if ((i + j) != 0 && ((i + j) <= boatCapacity)) {
                        State child = new State(missionariesLeft + i, cannibalsLeft + j, missionariesRight - i,
                                cannibalsRight - j, boatCapacity, BoatSide.LEFT);

                        if (child.isValid()) {
                            child.heuristic();
                            child.setFather(this);
                            children.add(child);
//                            System.out.println(child);
                        }
                    }
                }
            }
        }

        return children;
    }

    // True or False if the the goal achieved
    public boolean isFinal() {
        return this.cannibalsLeft == 0 && this.missionariesLeft == 0;
    }

    // True or false if each position of cannibals and missionaries is right
    public boolean isValid() {
        return this.missionariesLeft >= 0 && this.missionariesRight >= 0 && this.cannibalsLeft >= 0 && this.cannibalsRight >= 0
                && (this.missionariesLeft == 0 || this.missionariesLeft >= this.cannibalsLeft)
                && (this.missionariesRight == 0 || this.missionariesRight >= this.cannibalsRight);
    }

//    public boolean goLeft(){
//        return !sideOfBoat.equals(BoatSide.LEFT) && isValid(); // return true if both statements are true
//    }
//
//    public boolean goRight(){
//        return !sideOfBoat.equals(BoatSide.RIGHT) && isValid();
//    }

    private void heuristic(){

    }

    // Override them for proper comparisons
    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof State)) {
            return false;
        }
        State s = (State) obj;
        return (s.cannibalsLeft == this.cannibalsLeft && s.missionariesLeft == this.missionariesLeft
                && s.sideOfBoat == this.sideOfBoat && s.cannibalsRight == this.cannibalsRight
                && s.missionariesRight == this.missionariesRight);
    }

    @Override
    public int hashCode() {
        return this.missionariesLeft + this.cannibalsLeft + this.missionariesRight + this.cannibalsRight + this.identifier;
    }

    @Override
    public int compareTo(State state) {
        return Double.compare(this.score, state.score); // compare based on heuristic score
    }

    // Initialize Getters - Setters
    public void setCannibalsLeft(int cannibalsLeft) {
        this.cannibalsLeft = cannibalsLeft;
    }

    public void setCannibalsRight(int cannibalsRight) {
        this.cannibalsRight = cannibalsRight;
    }

    public void setMissionariesRight(int missionariesRight) {
        this.missionariesRight = missionariesRight;
    }

    public void setMissionariesLeft(int missionariesLeft) {
        this.missionariesLeft = missionariesLeft;
    }

    public void setSideOfBoat(BoatSide sideOfBoat) {
        this.sideOfBoat = sideOfBoat;
    }

    public void setFather(State father) {
        this.father = father;
    }

    public void setCrossings(int crossings) {
        this.crossings = crossings;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setG(int g) {
        this.g = g;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public void setBoatCapacity(int boatCapacity) {
        this.boatCapacity = boatCapacity;
    }

    public int getCannibalsLeft() {
        return this.cannibalsLeft;
    }

    public int getCannibalsRight() {
        return this.cannibalsRight;
    }

    public int getMissionariesRight() {
        return this.missionariesRight;
    }

    public int getMissionariesLeft() {
        return this.missionariesLeft;
    }

    public BoatSide getBoatSide() {
        return this.sideOfBoat;
    }

    public int getCrossings() {
        return this.crossings;
    }

    public State getFather() { return this.father; }

    public int getScore() {
        return this.score;
    }

    public int getG() {
        return this.g;
    }

    public int getIdentifier() {
        return this.identifier;
    }

    public int getBoatCapacity() {
        return this.boatCapacity;
    }
}
