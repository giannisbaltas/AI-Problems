import java.util.ArrayList;

public class State implements Comparable<State>{
    private int cannibalsLeft;
    private int cannibalsRight;
    private int missionariesRight;
    private int missionariesLeft;
    private int boatSide; // 0 for right, 1 for left
    private int crossings;

    // A star score
    private int score;
    private int g;

    private State father = null;

    public State(int cannibalsRight, int missionariesRight, int crossings ){
        this.cannibalsRight = cannibalsRight;
        this.missionariesRight = missionariesRight;
        this.crossings = crossings;
    }

    public void print(){

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

    public void setBoatSide(int boatSide) {
        this.boatSide = boatSide;
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

    public int getBoatSide() {
        return this.boatSide;
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

    ArrayList<State> getChildren(int heuristic){
        ArrayList<State> children = new ArrayList<>();

        return children;
    }

    // True or False if the the goal achieved
    public boolean isFinal() {
        return this.cannibalsLeft == 0 && this.missionariesLeft == 0;
    }

    // True or false if each position of cannibals and missionaries is right
    public boolean isValid() {
        if (this.missionariesLeft >= 0 && this.missionariesRight >= 0 && this.cannibalsLeft >= 0 && this.cannibalsRight >= 0
                && (this.missionariesLeft == 0 || this.missionariesLeft >= this.cannibalsLeft)
                && (this.missionariesRight == 0 || this.missionariesRight >= this.cannibalsRight)) {
            return true;
        }
        return false;
    }

    public boolean goLeft(){

        return true;
    }

    public boolean goRight(){

        return true;
    }

    private void heuristic(){

    }

    // Override them for proper comparisons
    @Override
    public boolean equals(Object obj) {

        return true;
    }

    @Override
    public int hashCode() {

        return 0;
    }

    @Override
    public int compareTo(State o) {
        return 0;
    }
}
