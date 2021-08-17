package com.rezdron.chris.agame;

/**
 * Created by Chris on 1/14/2016.
 * Impliments vertical movement only physics as com.rezdron.chris.agame.Player token would use
 */
class PhysVertBounce extends TokenPhysics {
    private int range;
    private int offset;
    private int life = 425;

    PhysVertBounce(int input_x, int input_y, float input_dvx, float input_dvy, int input_range)
    {
        super(input_x,input_y,input_dvx,input_dvy);
        offset = input_y;
        this.range = input_range;
    }

    public Boolean tick()
    {
        life--;
        // Update internal variables for x,y and dvx dvy
        //dvy = dvy + (grav * grav);
        y = Math.round(dvy) + y;
        if ((y - offset) > range) {
            dvy = dvy * -1;
        }
        if ((y - offset) < 0 - dvy)
        {
            dvy = dvy * -1;
        }

        return life >= 0;
    }
}
