package com.rezdron.chris.agame;
import android.util.Pair;
import java.util.Vector;

/**
 * Created by Chris on 1/4/2016.
 */

public class GameMode {
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
        // Configure sMap for valid state transitions
        sMap = new Vector<Pair<MODE,MODE>>();
        sMap.add(new Pair<MODE,MODE>(MODE.TITLE,MODE.LOADING));

        sMap.add(new Pair<MODE,MODE>(MODE.TITLE, MODE.EXIT));
        sMap.add(new Pair<MODE,MODE>(MODE.LOADING, MODE.GAMEPLAY));
        sMap.add(new Pair<MODE,MODE>(MODE.LOADING, MODE.PAUSE));
        sMap.add(new Pair<MODE,MODE>(MODE.PAUSE, MODE.GAMEPLAY));
        sMap.add(new Pair<MODE,MODE>(MODE.PAUSE, MODE.EXIT));
        sMap.add(new Pair<MODE,MODE>(MODE.PAUSE, MODE.GAMEOVER));
        sMap.add(new Pair<MODE,MODE>(MODE.PAUSE,MODE.TITLE));
        sMap.add(new Pair<MODE,MODE>(MODE.GAMEOVER,MODE.TITLE));
        sMap.add(new Pair<MODE,MODE>(MODE.GAMEOVER,MODE.LOADING));
        sMap.add(new Pair<MODE,MODE>(MODE.GAMEOVER,MODE.EXIT));

        // This last one is implied not having any mapping to leave the exit state
        // Is accurate to exiting without prompt
        // With a do you want to quit prompt additional options:
        // EXIT -> TITLE
        // EXIT -> PAUSE
        // Would definitely be valid and possibly other transitions
        //sMap.add(new Pair<MODE,MODE>(MODE.EXIT,MODE.EXIT));
    }

    public MODE getMode(){return gameState;}

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
};