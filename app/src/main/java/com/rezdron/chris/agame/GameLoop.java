package com.rezdron.chris.agame;
import com.rezdron.chris.agame.GameMode;

/**
 * Created by Chris on 1/7/2016.
 *
 * This can be done in a few ways. It can be done as an event listener where each module
 * Adds itself to the notify on tick()
 * Would need to be especially careful to pay attention to the tick order and
 * offload some state checking into each module
 *
 * Or it can be hard coded to call individual modules.
 * Quicker easier less flexible in long long run
 *
 * tokenhandler class
 * is event-listener each object created by the map gen adds itself to this class
 * as each token adds itself to tokenhandler it can register for various events
 */
public class GameLoop {

    GameMode gameState;
    Integer tickCount;
    //Some kind of object to define the level map unless all obstables are procedurally generated elsewhere?
    //Level progress;

    //TokenHandler mobs = new TokenHandler();
    //Token player = new Token();
    // Tokens is anything on the screen that isn't background or ui
    // tokens includes the player, any obstacles any physics objects

    public void loop()
    {
        while (gameState.getMode() != GameMode.MODE.EXIT)
        {
            // Gather inputs (not written yet)
            // Process inputs which may change game mode or other elements
            // inputHandle();

            // Sound always happens options to disable sound volume = 0 ?
            //sound()

            // Event Handling
            // Event from input gathering needs to be available to this method
            // Several inputs may switch modes
            // Others apply to gameplay and will update the Player token
            // Player.Input();

            // Only process tick() while game is still in gameplay
            if (gameState.getMode() == GameMode.MODE.GAMEPLAY) {
                tick();
                //if (Player.dead() == true) {
                //    gameState.setMode(GameMode.MODE.GAMEOVER);
                //}
            }
            else if (gameState.getMode() == GameMode.MODE.GAMEOVER)
            {
                // Scoring is handled by mobs tracking point values of each object
                // Render game over screen
                // renderGameOver(mobs.getScore());
            }
            else if (gameState.getMode() == GameMode.MODE.LOADING)
            {
                // Reset tickCount to 0
                tickCount = 0;
                //mobs.zeroScore();
            }
        }




    }

    public void tick() {
        // Run each engine module one tick forward

        // Everything has moved, physics will check collisions and cull out of frame objects
        // May handle some water wave physics as well
        // Update physics
        // Physics.Tick(mobs);

        // Scoring and ui updates in general
        // UI.Tick();

        // Update gfx
        // Wipe surface to prep for new render
        // Render(progress,mobs);
        // If anything is needed to flip the buffer add here

        // Check game conditions (did player just lose? did any things bounce off each other? etc)
        // Physics.Collision();
    }
}
