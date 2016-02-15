package com.mygdx.game.model;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.thetask.MapVars;
import com.mygdx.game.thetask.Point;
import com.mygdx.game.thetask.Robot;

public class RobotTile extends MapTile{
    public Robot robot;

    /**
     * Robot texture
     *
     * @param robot
     */
    public RobotTile(Robot robot) {
        //create texture by robot type
        super(robot.getType()== Robot.TYPE.RED?MapVars.RED_ROB:MapVars.BLUE_ROB,robot.getPosition().getX(),robot.getPosition().getY());
        this.robot = robot;
    }

    public Array<MapTile> getVisibleTiles() {
        Array<MapTile> mapTiles = new Array<MapTile>();
        for (Point p : robot.getVisionField()) {
            mapTiles.add(new MapTile(MapVars.VIS_FLOOR, p.getX() * 30, p.getY() * 30));
        }
        return mapTiles;
    }

}
