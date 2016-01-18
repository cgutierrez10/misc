package com.rezdron.chris.agame;

import com.rezdron.chris.agame.PhysVert;
import com.rezdron.chris.agame.Token;

/**
 * Created by Chris on 1/14/2016.
 * Carries player token specific data including special physics functions
 */
public class Player extends Token {
    Player()
    {
        super(0, new PlayerPhys(32,48,(float)0.0,(float)0.0));
    }

    public void setAccel(Integer y)
    {
        ((PlayerPhys) phys).addDvy(y);
    }

    public void tick()
    {
        phys.tick();
    }

}
