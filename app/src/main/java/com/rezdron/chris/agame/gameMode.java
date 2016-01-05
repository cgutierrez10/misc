package com.rezdron.chris.agame;

/**
 * Created by Chris on 1/4/2016.
 */
public interface GameMode {
    void OnTouch(View v);
    void update(GameMode mode);
};

// Additional game states

    public class TitleState implements GameMode {

        public void OnTouch (View v) {
        /* Pseudo code
        if input == (input L) { // Switch mode to L }
            else if input == (input E) { // Switch mode to E }
            }

            virtual void update(eMode& mode) {
                    // continue displaying screen and waiting for input
            }
            */
        }
        public void update(GameMode mode) {}
    };

    public class LoadStat implements GameMode {
        public void OnTouch(View v) {
            /* Pseudo code
            if input == (input GP) { // Switch mode to GP }
                else if input == (input P) { // Switch mode to P }
                }

                virtual void update(eMode& mode) {
                        // continue loading screen ticks
                        // Eventually handle as switch mode to GP
                }
                */

        }

        public void update(GameMode mode) {
        }
    }


    public class PlayState implements GameMode {
        public void OnTouch(View v) {
                /* Pseudo code
                if input == (input P) { // Switch mode to L }
                    else if input == (input GP) { // Switch mode to E }
                        else { // Handle all other input as gameplay inputs }
                        }

                        virtual void update(eMode& mode) {
                                // continue displaying screen and waiting for input
                        }
                    }*/
        }

        public void update(GameMode mode) {
            // Handle state change interactions from play mode
        }
    };

    public class PauseState implements GameMode {
        public void OnTouch(View v) {
            /* Pseudo code
            if input == (input GP) { // Switch mode to GP }
            else if input == (input E) { // Switch mode to E }
            else if input == (input GO) { // Switch mode to GO }
            else if input == (input T-I) { // Switch mode to T-I } */
        }

        public void update(GameMode mode) {
            // Continue displaying handling pause menu interactions
        }
    };