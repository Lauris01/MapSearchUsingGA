package com.mygdx.game.model;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.thetask.MapVars;

public class MapTile extends Entity {

    private Texture texture;
    private MapVars mapVar;

    public MapTile(MapVars v, float x, float y) {
        super(x, y);
        mapVar = v;
        setupTexture();
        setWidth(texture.getWidth());
        setHeight(texture.getHeight());
    }

    private void setupTexture() {
        switch (mapVar) {
            case INVIS_FLOOR:
                texture = new Texture("core/assets/invisible_floor.png");
                break;
            case VIS_FLOOR:
                texture = new Texture("core/assets/visible_floor.png");
                break;
            case RED_ROB:
                texture = new Texture("core/assets/red_robbot.png");
                break;
            case BLUE_ROB:
                texture = new Texture("core/assets/blue_robbot.png");
                break;
            case RED_BASE:
                texture = new Texture("core/assets/red_robbot_base.png");
                break;
            case BLUE_BASE:
                texture = new Texture("core/assets/blue_robbot_base.png");
                break;
            case WALL:
                texture = new Texture("core/assets/wall.png");
                break;
        }
    }

    public Texture getTexture() {
        return texture;
    }
}
