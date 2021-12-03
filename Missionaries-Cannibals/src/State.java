import java.util.ArrayList;

public class State implements Comparable<State>{
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
        this.g = 0;
        this.sideOfBoat = BoatSide.LEFT;
    }

    public State (int missionariesLeft, int cannibalsLeft, int missionariesRight, int cannibalsRight, int boatCapacity, int g,BoatSide sideOfBoat){
        this.cannibalsLeft = cannibalsLeft;
        this.cannibalsRight = cannibalsRight;
        this.missionariesLeft = missionariesLeft;
        this.missionariesRight = missionariesRight;
        this.boatCapacity = boatCapacity;
        this.sideOfBoat = sideOfBoat;
        this.g = g + 1;
    }

    public void print(){
        if (sideOfBoat.equals(BoatSide.LEFT)) {
            System.out.println(" ***************************************");
            System.out.println("|\t\t\t$\t\t\t\t$\t\t\t|");
            System.out.println("| M: " + missionariesLeft + "\t\t$  <| \t\t\t$ M: " + missionariesRight + "\t\t|");
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

    public ArrayList<State> goRight(){
        ArrayList<State> children = new ArrayList<>();

        for (int i = 0; i <= missionariesLeft; i++) {
            for (int j = 0; j <= cannibalsLeft; j++) {
                if ((i + j) != 0 && ((i + j) <= boatCapacity) && (i == 0 || i >= j)) {
                    State child = new State(missionariesLeft - i, cannibalsLeft - j, missionariesRight + i,
                            cannibalsRight + j, boatCapacity, g, BoatSide.RIGHT);
                    if (child.isValid()) {
                        child.heuristic();
                        child.setFather(this);
                        children.add(child);
                    }
                }
            }
        }
        return children;
    }

    public ArrayList<State> goLeft(){
        ArrayList<State> children = new ArrayList<>();

        for (int i = 0; i <= missionariesRight; i++) {
            for (int j = 0; j <= cannibalsRight; j++) {
                if ((i + j) != 0 && ((i + j) <= boatCapacity)) {
                    State child = new State(missionariesLeft + i, cannibalsLeft + j, missionariesRight - i,
                            cannibalsRight - j, boatCapacity, g, BoatSide.LEFT);
                    if (child.isValid()) {
                        child.heuristic();
                        child.setFather(this);
                        children.add(child);
                    }
                }
            }
        }
        return children;
    }


    ArrayList<State> getChildren(){
        if (sideOfBoat.equals(BoatSide.LEFT)) return this.goRight();
        return this.goLeft(); // if the boat is in the right side
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

    private void heuristic(){
        if (boatCapacity > 2) {
            if ((missionariesLeft + cannibalsLeft) <= boatCapacity) {
                this.score = g + 1; // one crossing if persons on the left side are leq than boat capacity
            }
            else {
                this.score = g + Math.round(2 * (missionariesLeft + cannibalsLeft) / (float) (boatCapacity - 1) - 1);
            }
        }
        if (boatCapacity == 2) this.score = g + 2 * (missionariesLeft + cannibalsLeft) - 3;
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
        return this.missionariesLeft^2 + this.cannibalsLeft^2 ;
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

    public int getBoatCapacity() {
        return this.boatCapacity;
    }
}
