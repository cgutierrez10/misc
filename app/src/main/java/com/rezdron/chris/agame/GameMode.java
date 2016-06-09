package com.rezdron.chris.agame;
import android.util.Pair;
import java.util.Vector;

/**
 * Created by Chris on 1/4/2016.
 * Basic state machine for game operating modes.
 */

public class GameMode {
    private static GameMode instance = new GameMode();
    public static GameMode getInstance() { return instance; }
    public enum MODE {
        TITLE,
        LOADING,
        GAMEPLAY,
        PAUSE,
        GAMEOVER,
        EXIT
    }

    MODE gameState;
    Vector<Pair<MODE,MODE>> sMap; // = new EnumMap<MODE,MODE>(MODE.class);
    GameMode()
    {
        gameState = MODE.TITLE;
        // Configure sMap for valid state transitions
        sMap = new Vector<>();
        sMap.add(new Pair<>(MODE.TITLE,MODE.LOADING));

        sMap.add(new Pair<>(MODE.TITLE, MODE.EXIT));
        sMap.add(new Pair<>(MODE.LOADING, MODE.GAMEPLAY));
        sMap.add(new Pair<>(MODE.LOADING, MODE.PAUSE));
        sMap.add(new Pair<>(MODE.GAMEPLAY, MODE.PAUSE));
        sMap.add(new Pair<>(MODE.GAMEPLAY, MODE.EXIT));
        sMap.add(new Pair<>(MODE.GAMEPLAY, MODE.GAMEOVER));
        sMap.add(new Pair<>(MODE.PAUSE, MODE.GAMEPLAY));
        sMap.add(new Pair<>(MODE.PAUSE, MODE.GAMEOVER));
        sMap.add(new Pair<>(MODE.PAUSE,MODE.TITLE));
        sMap.add(new Pair<>(MODE.GAMEOVER,MODE.TITLE));
        sMap.add(new Pair<>(MODE.GAMEOVER,MODE.LOADING));
        sMap.add(new Pair<>(MODE.GAMEOVER,MODE.EXIT));

        // This one added for testing, may want to remove above gameover -> loading in favor of this one.
        sMap.add(new Pair<>(MODE.GAMEOVER,MODE.GAMEPLAY));
        // This last one is implied not having any mapping to leave the exit state
        // Is accurate to exiting without prompt
        // With a do you want to quit prompt additional options:
        // EXIT -> TITLE
        // EXIT -> PAUSE
        // Would definitely be valid and possibly other transitions
        //sMap.add(new Pair<MODE,MODE>(MODE.EXIT,MODE.EXIT));
    }

    public MODE getMode(){return gameState;}

    public String getText(){
        switch(gameState) {
            case TITLE:
                return "Title";
            case LOADING:
                return "Loading";
            case GAMEPLAY:
                return "Gameplay";
            case PAUSE:
                return "Pause";
            case GAMEOVER:
                return "Game Over";
            case EXIT:
                return "Exit";
            default:
                return "";
        }
    }

    public boolean changeMode(MODE newState)
    {
        for (Pair<MODE,MODE> transition : sMap)
        {
            if ((transition.first == gameState) && (transition.second == newState)) {
                gameState = newState;
                return(true);
            }
        }
            return false;
    }
}