package com.mygdx.game.thetask;

public enum MapVars {
    INVIS_FLOOR(0),
    VIS_FLOOR(2),
    RED_ROB(5),
    BLUE_ROB(6),
    RED_BASE(8),
    BLUE_BASE(9),
    WALL(1);

    public int value;

    MapVars(int value) {
        this.value = value;
    }

}
