package com.mygdx.game.thetask;

public class Map {
    public int maze[][];

    public Map(int maze[][]) {
        this.maze = maze;
    }

    /**
     * @param type robot type
     * @return robot starting point
     */
    public Point getStartPosition(Robot.TYPE type) {
        int base = type == Robot.TYPE.BLUE ? MapVars.BLUE_BASE.value : MapVars.RED_BASE.value;
        return getPointOfValue(base);
    }

    private Point getPointOfValue(int type) {
        for (int rowIndex = 0; rowIndex < this.maze.length; rowIndex++) {
            for (int colIndex = 0; colIndex < this.maze[rowIndex].length; colIndex++) {
                // 2 is the type for start position
                if (this.maze[rowIndex][colIndex] == type) {
                    return new Point(rowIndex, colIndex);//new int[]{colIndex, rowIndex}
                }
            }
        }
        return null;
    }

    /**
     * @param type robot type
     * @return robot goal point
     */
    public Point getPointOfGoal(Robot.TYPE type) {
        return getPointOfValue(type == Robot.TYPE.BLUE ? MapVars.RED_BASE.value : MapVars.BLUE_BASE.value);
    }


    public int getPositionValue(int x, int y) {
        if (x < 0 || y < 0 || x >= this.maze.length || y >= this.maze[0].length) {
            return 1;
        }
        return this.maze[y][x];
    }

    public boolean isWall(int x, int y) {
        return (this.getPositionValue(x, y) == 1);
    }

    public int getMaxX() {
        return this.maze[0].length - 1;
    }

    public int getMaxY() {
        return this.maze.length - 1;
    }

    public double scoreRoute(Robot robot) {
        double score = 0;
        boolean visited[][] = new boolean[this.getMaxY() + 1]
                [this.getMaxX() + 1];
        double d = 100;
        for (Point routeStep : robot.getRoute()) {
            double distanceToGoal = distance(routeStep, robot.getGoal());
            if (distanceToGoal < d&&!visited[routeStep.x][routeStep.y]) {
                d = distanceToGoal;
                score += 1;
            }

            visited[routeStep.x][routeStep.y] = true;
        }
        //lower fitness for moves count
        score = score - robot.moves*0.3;
        return score;
    }

    public double distance(Point a, Point b) {
        double dx = a.x - b.x;
        double dy = a.y - b.y;

        return Math.sqrt(dx * dx + dy * dy);
    }

}
