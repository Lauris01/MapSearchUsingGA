package com.mygdx.game.thetask;

import java.util.ArrayList;

public class Robot {


    private enum Direction {NORTH, EAST, SOUTH, WEST}

    public enum TYPE {RED, BLUE}

    private boolean reachedGoal = false;
    public int xPosition;
    public int yPosition;
    private Direction heading;
    int maxMoves;
    int moves;
    private Map maze;
    private Point goal;
    private TYPE type;
    private int[] chromosome;
    boolean[][] knownMap = new boolean[10][10];
    private ArrayList<Point> route = new ArrayList<>();

    /**
     * @param chromosome The string to map the sensor value to actions
     * @param maze       The maze the robot will use
     * @param maxMoves   The maximum number of moves the robot can make
     * @param type
     */
    public Robot(int[] chromosome, Map maze, int maxMoves, TYPE type) {
        this.chromosome = chromosome;
        this.maze = maze;
        //differend starting points for differend robots
        Point startPos = this.maze.getStartPosition(type);
        this.xPosition = startPos.getX();
        this.yPosition = startPos.getY();
        this.heading = Direction.EAST;
        this.maxMoves = maxMoves;
        this.moves = 0;
        this.type = type;
        //different goals for different type robots
        this.goal = maze.getPointOfGoal(type);
    }

    /**
     * run method for evaluation
     */
    public void run() {
        for (int move : chromosome) {
            moves++;
            //if goal reached
            if (goal.x == xPosition && goal.y == yPosition) {
                reachedGoal = true;
                return;
            }
            //if target in sight move forward
            if (knownMap[goal.x][goal.y]) {
                move();
                continue;
            }
            //if no move
            if (move == 0) {
                move();
            }
            if (move == 8) {//up
                this.heading = Direction.NORTH;
                move();
            }
            if (move == 6) {//right
                this.heading = Direction.EAST;
                move();
            }
            if (move == 2) {//down
                this.heading = Direction.SOUTH;
                move();
            }
            if (move == 4) {//left
                this.heading = Direction.WEST;
                move();
            }
        }
    }

    /**
     * method for game rendering
     */
    public void stepMove() {

        if (maxMoves < moves) {
            return;
        }
        int move = chromosome[moves];
        moves++;
        if (goal.x == xPosition && goal.y == yPosition) {
            reachedGoal = true;
            return;
        }
        if (knownMap[goal.x][goal.y]) {

            move();
//            continue;
            return;
        }

        //if no move
        if (move == 0) {
            move();
        }
        if (move == 8) {
            this.heading = Direction.NORTH;
            move();
        }
        if (move == 6) {
            this.heading = Direction.EAST;
            move();
        }
        if (move == 2) {
            this.heading = Direction.SOUTH;
            move();
        }
        if (move == 4) {
            this.heading = Direction.WEST;
            move();
        }
    }

    private void move() {
        int currentX = this.xPosition;
        int currentY = this.yPosition;
        switch (this.heading) {
            case NORTH:
                this.yPosition -= 1;
                break;
            case EAST:
                this.xPosition += 1;
                break;
            case SOUTH:
                this.yPosition += 1;
                break;
            case WEST:
                this.xPosition -= 1;
                break;
        }


        // We can't move here
        if (this.maze.isWall(this.xPosition, this.yPosition) == true) {
            this.xPosition = currentX;
            this.yPosition = currentY;
        } else if (currentX != this.xPosition || currentY != this.yPosition) {
            route.add(new Point(currentX, currentY));
            addVisibleMap();
        }
//        }
    }

    private void addVisibleMap() {
        StringBuilder sb = new StringBuilder();
        sb.append("Visible: \n");
        switch (getHeading()) {
            case NORTH:
                for (int d = 1; d <= 3; d++) {
                    if (0 < (yPosition - d)) {
                        knownMap[xPosition][yPosition - d] = true;
                        sb.append((xPosition) + ":" + (yPosition - d) + "\n");
                    }
                }
                break;
            case EAST:
                for (int d = 1; d <= 3; d++) {
                    if (maze.getMaxX() > xPosition + d) {
                        knownMap[xPosition + d][yPosition] = true;
                        sb.append((xPosition + d) + ":" + (yPosition) + "\n");
                    }
                }
                break;
            case SOUTH:

                for (int d = 1; d <= 3; d++) {
                    if (maze.getMaxY() > (yPosition + d)) {
                        knownMap[xPosition][yPosition + d] = true;
                        sb.append((xPosition) + ":" + (yPosition + d) + "\n");
                    }

                }
                break;
            case WEST:
                for (int d = 1; d <= 3; d++) {
                    if (0 < xPosition - d) {
                        knownMap[xPosition - d][yPosition] = true;
                        sb.append((xPosition - d) + ":" + (yPosition) + "\n");
                    }
                }
                break;
        }
//        System.out.println(sb.toString());


    }

    public void pm() {

        for (int i = 0; i < maze.getMaxY(); i++) {
            for (int j = 0; j < maze.getMaxX(); j++) {
                System.out.print(knownMap[i][j] ? 1 : ".");
            }
            System.out.println();
        }
    }

    public ArrayList<Point> getVisionField() {
        ArrayList<Point> result = new ArrayList<>();
        switch (getHeading()) {
            case NORTH:
                for (int d = 1; d <= 3; d++) {
                    if (0 < (yPosition - d))
                        result.add(new Point(xPosition, yPosition - d));
                }
                break;
            case EAST:
                for (int d = 1; d <= 3; d++) {
                    if (maze.getMaxX() > xPosition + d)
                        result.add(new Point(xPosition + d, yPosition));

                }
                break;
            case SOUTH:

                for (int d = 1; d <= 3; d++) {
                    if (maze.getMaxY() > (yPosition + d))
                        result.add(new Point(xPosition, yPosition + d));

                }
                break;
            case WEST:
                for (int d = 1; d <= 3; d++) {
                    if (0 < xPosition - d)
                        result.add(new Point(xPosition - d, yPosition));

                }
                break;
        }
        return result;

    }

    public Point getPosition() {
        return new Point(this.xPosition, this.yPosition);
    }

    private Direction getHeading() {
        return this.heading;
    }

    public TYPE getType() {
        return type;
    }

    public boolean reachedGoal() {
        return reachedGoal;
    }


    public boolean[][] getMapKnown() {
        return knownMap;
    }

    public ArrayList<Point> getRoute() {
        return route;
    }

    public Point getGoal() {
        return goal;
    }


}
