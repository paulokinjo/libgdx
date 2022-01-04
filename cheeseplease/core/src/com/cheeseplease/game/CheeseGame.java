package com.cheeseplease.game;

import com.badlogic.gdx.Game;
import com.cheeseplease.menu.CheeseMenu;

public class CheeseGame extends Game {

    @Override
    public void create() {
        CheeseMenu gameMenu = new CheeseMenu(this);
        setScreen(gameMenu);
    }
}
