package com.rezdron.chris.agame;

/**
 * Created by Chris on 1/14/2016.
 * Impliments horizontal only movement suitable for floating stationary or horizontal
 * forward/backward motions
 */
class PhysHoriz extends TokenPhysics {

    float jerky = 0.0f;

    PhysHoriz(int input_x, int input_y, float input_dvx, float input_dvy, float jerk)
    {
        super(input_x,input_y,input_dvx,input_dvy);
        jerky = jerk;
    }

    public Boolean tick()
    {
        // Update internal variables for x,y and dvx dvy
        // No gravity! Doesn't get much simpler
        this.dvy = this.dvy - jerky;
        y = Math.round(dvy) + y;
        return !(y < -100 || y > 1000);
    }
}
