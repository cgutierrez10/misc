package com.rezdron.chris.agame;

/**
 * Created by Chris on 1/14/2016.
 * Impliments vertical movement only physics as Player token would use
 */
public class PhysVert extends TokenPhysics {

    public PhysVert(int input_x, int input_y, float input_dvx, float input_dvy)
    {
        super(input_x,input_y,input_dvx,input_dvy);
    }

    public void tick()
    {
        // Update internal variables for x,y and dvx dvy
        dvy = dvy + (grav * grav);
        y = Math.round(dvy) + y;
    }
}
