package com.rezdron.chris.agame;

/**
 * Created by Chris on 1/14/2016.
 * Impliments vertical movement only physics as com.rezdron.chris.agame.Player token would use
 */
public class PhysFloat extends TokenPhysics {
    double phase1 = 19.0f;
    double phase2 = 24.0f;
    double amp1 = (phase2/(phase1 + phase2) * 10);
    double amp2 = (phase1/(phase1 + phase2) * 10);
    double norm = (1.0/Math.sqrt(phase1 * phase1 + phase2 * phase2));
    int sealevel = 50;

    public PhysFloat(int input_x, int input_y, float input_dvx, float input_dvy)
    {
        super(input_x,input_y,input_dvx,input_dvy);
    }

    public Boolean tick()
    {
        float timecount = ((float) ContentGen.getInstance().tickCount) / 25;
        //timecount += 1;
        // Update internal variables for x,y and dvx dvy
        //dvy = dvy + (grav * grav);
        //y = Math.round(dvy) + y;
        //y = WaterLine.getInstance().
        y = (int) Math.round(amp2*Math.sin((x*phase1 + timecount)*phase1*norm)
                + amp1*Math.sin((x*phase2 - timecount)*phase2*norm)
                - 0.035*Math.sin(timecount*2.5 + x))
                + sealevel;
        // Return values never being used currently anyway
        //return (x < -100 || x > 1000) ? false : true;
        return true;
    }
}
