package com.rezdron.chris.agame;

/**
 * Created by Chris on 1/14/2016.
 * Impliments vertical movement only physics as com.rezdron.chris.agame.Player token would use
 */
public class PhysVertBounce extends TokenPhysics {
    int range;
    int traveled = 0;

    public PhysVertBounce(int input_x, int input_y, float input_dvx, float input_dvy, int input_range)
    {
        super(input_x,input_y,input_dvx,input_dvy);
        this.range = input_range;
    }

    public Boolean tick()
    {
        // Update internal variables for x,y and dvx dvy
        dvy = dvy + (grav * grav);
        y = Math.round(dvy) + y;
        if (traveled > range) {
            dvy = dvy * -1;
        }
        if (traveled <= 0)
        {
            dvy = dvy * -1;
        }

        return (y < -100 || y > 1000) ? false : true;
    }
}
