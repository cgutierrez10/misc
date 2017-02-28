package com.rezdron.chris.agame;

/**
 * Created by Chris on 1/14/2016.
 * Abstract class to handle most of the physics basics
 * And allow to implement a movement function in tick()
 */
abstract class TokenPhysics {
    protected Integer x = 64; // Starting pixel
    protected Integer y = 32; // Starting pixel
    float dvx = (float) 0.0;
    float dvy = (float) 0.0;
    float udvy = (float) 0.0; // This could maybe be used as a world travel rate
                              // If playerjump accelerates forward motion it could be tracked here?
    // Gravity will be -5.4 per second, tick is presuming 1/60th second per physics step
    // After 60 intervals should have come to -5.4m/s of velocity
    // Will be tuned with playtesting, desired sqrt(accel / 60) = 1/60th second accel
    static float grav = (float) -0.3; // This may be populated elsewhere as well
    // Gravity scale, approx 16px/meter 1g is approx -2.6, current scaling has player moving on scale of about 5px/meter, 64px sprite ~4meter dolphin?

    TokenPhysics(int input_x, int input_y, float input_dvx, float input_dvy)
    {
        x = input_x;
        y = input_y;
        dvx = input_dvx;
        dvy = input_dvy;
    }

    void shift(int offset) { this.x = this.x - offset; }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public void setudvy(float ddvy) { this.dvy = this.dvy + ddvy; }

    abstract public Boolean tick();
}
