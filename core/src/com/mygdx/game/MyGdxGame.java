package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.thetask.MapVars;
import com.mygdx.game.model.MapTile;
import com.mygdx.game.model.RobotTile;
import com.mygdx.game.thetask.Map;
import com.mygdx.game.thetask.Robot;


public class MyGdxGame extends Game {
    private float accumulator = 0f;
    SpriteBatch batch;
    private final Map map;
    private RobotTile robot;
    private RobotTile robot1;
    private Robot blueRobot;
    private Robot redTobot;
    private Array<MapTile> mapTiles;
    private float tileSize = 30;
    private final float timeStep = 0.5f;

    /**
     *
     * @param bestBlue - best Blue robot
     * @param bestRed - best red robot
     * @param map - map
     */
    public MyGdxGame(Robot bestBlue, Robot bestRed, Map map) {
        this.blueRobot = bestBlue;
        this.redTobot = bestRed;
        this.map = map;
    }


    @Override
    public void create() {
        robot = new RobotTile(blueRobot);
        robot1 = new RobotTile(redTobot);
        batch = new SpriteBatch();
        mapTiles = createMapTiles(map);
    }

    private Array<MapTile> createMapTiles(Map map) {
        Array<MapTile> mapTiles = new Array<MapTile>();
        for (int i = 0; i < map.getMaxY()+1; i++) {
            for (int j = 0; j < map.getMaxX()+1; j++) {
                int o = map.maze[i][j];
                if (o == MapVars.BLUE_BASE.value) {
                    mapTiles.add(new MapTile(MapVars.BLUE_BASE, i * tileSize, j * tileSize));
                }
                if (o == MapVars.RED_BASE.value) {
                    mapTiles.add(new MapTile(MapVars.RED_BASE, i * tileSize, j * tileSize));
                }
                if (o == MapVars.INVIS_FLOOR.value) {
                    mapTiles.add(new MapTile(MapVars.INVIS_FLOOR, i * tileSize, j * tileSize));
                }
                if (o == MapVars.INVIS_FLOOR.value) {
                    mapTiles.add(new MapTile(MapVars.INVIS_FLOOR, i * tileSize, j * tileSize));
                }
                if (o == MapVars.WALL.value) {
                    mapTiles.add(new MapTile(MapVars.WALL, i * tileSize, j * tileSize));
                }
            }

        }
        return mapTiles;
    }

    //main loop
    @Override
    public void render() {
        accumulator += Gdx.graphics.getDeltaTime();
        if (accumulator >= timeStep) {
            robot.robot.stepMove();
            robot1.robot.stepMove();
            accumulator -= timeStep;
        }
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        //draw map
        for (MapTile wall : mapTiles) {
            batch.draw(wall.getTexture(), wall.getY(), wall.getX());
        }
        //draw robots
        batch.draw(robot.getTexture(), robot.robot.yPosition * tileSize, robot.robot.xPosition * tileSize);
        batch.draw(robot1.getTexture(), robot1.robot.yPosition * tileSize, robot1.robot.xPosition * tileSize);
        //draw robot vision fields
        for (MapTile vision : robot.getVisibleTiles()) {
            batch.draw(new Texture("core/assets/vision_blue.png"), vision.getY(), vision.getX());
        }
        for (MapTile vision : robot1.getVisibleTiles()) {
            batch.draw(new Texture("core/assets/vision_red.png"), vision.getY(), vision.getX());
        }
        batch.end();

    }
}