package com.rezdron.chris.agame;

/**
 * Created by Chris on 1/14/2016.
 * Abstract class to handle most of the physics basics
 * And allow to implement a movement function in tick()
 */
public abstract class TokenPhysics {
    protected Integer x = 64; // Starting pixel
    protected Integer y = 32; // Starting pixel
    protected float dvx = (float) 0.0;
    protected float dvy = (float) 0.0;
    // Gravity will be -5.4 per second, tick is presuming 1/60th second per physics step
    // After 60 intervals should have come to -5.4m/s of velocity
    // Will be tuned with playtesting, desired sqrt(accel / 60) = 1/60th second accel
    static float grav = (float) -0.3; // This may be populated elsewhere as well

    public TokenPhysics(int input_x, int input_y, float input_dvx, float input_dvy)
    {
        x = input_x;
        y = input_y;
        dvx = input_dvx;
        dvy = input_dvy;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    abstract public Boolean tick();
}