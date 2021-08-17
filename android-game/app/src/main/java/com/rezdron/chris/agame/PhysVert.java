package com.rezdron.chris.agame;

import android.util.Log;

/**
 * Created by Chris on 1/14/2016.
 * Impliments vertical movement only physics as com.rezdron.chris.agame.Player token would use
 */
class PhysVert extends TokenPhysics {

    PhysVert(int input_x, int input_y, float input_dvx, float input_dvy)
    {
        super(input_x,input_y,input_dvx,input_dvy);
    }

    public Boolean tick()
    {
        // Update internal variables for x,y and dvx dvy
        dvy = dvy + (grav * grav);
        y = Math.round(dvy) + y;
        return !(y < -100 || y > 1000);
    }
}
