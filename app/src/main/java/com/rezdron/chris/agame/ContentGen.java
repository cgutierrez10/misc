package com.rezdron.chris.agame;

import java.util.Random;

/**
 * Created by Chris on 2/9/2016.
 * Will call rng/mixrng and generate tokens based on this.
 * Will provide functions for game thread to call for possible new tokens
 */
public class ContentGen {
    private static ContentGen instance = new ContentGen();
    Random rng = new Random(14641);
    int nextadd;
    int x = 50;
    // Difficulty should scale from very easy to very hard/impossible over 0-18,000 ticks
    int difficulty = 0; // Higher initial difficulty softer difficulty curve

    public static ContentGen getInstance() { return instance; }

    public void reset() {
        // Return this to an initialize state
        x = 50;
        difficulty = 0;
        nextadd = 0;

        // Eventually this will pick a new seed at all occasions
        rng = new Random(14641);
    }

    public void tick(int tick)
    {
        nextadd--;
        // Check if a token can be added again yet
        if (nextadd < difficulty) {
            // If eligable then add now?
            if (rng.nextFloat()*difficulty > nextadd ) {
                addToken();
                // After add reset nextadd
                difficulty++;
                //nextadd = addtoken.getWidth() + difficulty;

            }
        }
    }

    public void setWidth(int width) { this.x = width; }

    public void addToken()
    {
        // rng nextint range should be up to difficulty to allow scaled new critters
        int type = rng.nextInt(2);
        Token addtoken;
        if (type == 0) {
            // Sheep!
            addtoken = new TokenSheep(10,0,0);
            // What physics?
        } else {
            // Spider!
            addtoken = new TokenSpider(10,0,0);
        }
        nextadd = 50;
        TokenHandler.getInstance().addToken(addtoken);
    }
    public float getAmplitude(float max)
    {
        // Should return a range limited value?
        return rng.nextFloat()*max;

    }
}
