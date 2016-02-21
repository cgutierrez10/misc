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
    // Difficulty should scale from very easy to very hard/impossible over 0-18,000 ticks
    int difficulty = 0; // Higher initial difficulty softer difficulty curve

    public static ContentGen getInstance() { return instance; }

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

    public void addToken()
    {
        // rng nextint range should be up to difficulty to allow scaled new critters
        int type = rng.nextInt(2);
        Token addtoken;
        if (type == 0) {
            // Sheep!
            addtoken = new TokenSheep(10);
            // What physics?
        } else {
            // Spider!
            addtoken = new TokenSpider(10);
        }
        nextadd = 50;
        TokenHandler.getInstance().addToken(addtoken);
    }

}
