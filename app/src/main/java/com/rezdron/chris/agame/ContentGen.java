package com.rezdron.chris.agame;

import java.util.Random;

/**
 * Created by Chris on 2/9/2016.
 * Will call rng/mixrng and generate tokens based on this.
 * Will provide functions for game thread to call for possible new tokens
 */
public class ContentGen {
    Random rng = new Random(14641);
    int nextadd;
    // Difficulty should scale from very easy to very hard/impossible over 0-18,000 ticks
    int difficulty = 0; // Higher initial difficulty softer difficulty curve

    public void tick(int tick)
    {
        nextadd--;
        // Check if a token can be added again yet
        if (nextadd < difficulty) {
            // If eligable then add now?
            if (rng.nextFloat()*difficulty > nextadd ) {
                // If added then what type
                int type = rng.nextInt(2);
                Token addtoken;
                if (type == 0) {
                    // Sheep!
                } else {
                    // Spider!
                }

                // If added then anything special? (inputs to physics?)

                // After add reset nextadd
                difficulty++;
                //nextadd = addtoken.getWidth() + difficulty;

            }
        }

    }

}
