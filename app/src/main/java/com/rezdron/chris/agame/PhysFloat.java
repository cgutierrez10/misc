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
    int sealevel = 0;

    public PhysFloat(int input_x, int input_y, float input_dvx, float input_dvy)
    {
        super(input_x,input_y,input_dvx,input_dvy);
        sealevel = y;
    }

    public Boolean tick()
    {
        // Not needed, just need to convert
        // world x to pixel x
        // then pixel y back to world y

        // World max is ?? mapped to
        // OpenGL is -1,1 mapped to
        // Screen pixels

        float xdim = mglRender.getInstance().mScreenWidth;
        float ydim = mglRender.getInstance().mScreenHeight;
        float xscale, yscale; // Comment for commit

        // Are these scaling to or from csreen pixel?
        xscale = 2/xdim;       // Pixels per gl coordinate unit

        yscale = 2/ydim;       // Pixels per gl coordinate unit

        this.x = (int) (this.x * xscale);

        float timecount = ((float) ContentGen.getInstance().tickCount) / 25;
        //timecount = 50;
        //timecount += 1;
        // Update internal variables for x,y and dvx dvy
        //dvy = dvy + (grav * grav);
        //y = Math.round(dvy) + y;
        //y = WaterLine.getInstance().
        // This needs a scaling factor and a way to sync sea level to align to graphics height and range of motion
        // Looks to be vaguely accurate right now but might be out of time sync?
        y = (int) (sealevel + Math.round(amp2*Math.sin((x*phase1 + timecount)*phase1*norm)
                + amp1*Math.sin((x*phase2 - timecount)*phase2*norm)
                - 0.035*Math.sin(timecount*2.5 + x)));
        // Equation is returning pixel coords, object <x,y> are in world coords need to convert to pixel coords before the function and back afterwards
        this.x = (int) (this.x / xscale);
        this.y = (int) (this.y / yscale);

        // Return values never being used currently anyway
        //return (x < -100 || x > 1000) ? false : true;
        return true;
    }

    /*
    private Pair<Integer,Integer> toWorldCoords(int x, int y)
    {

        return Pair.create(0,0);
    }

    private Pair<Integer,Integer> toPixelCoords(int x, int y)
    {
        return Pair.create(0,0);
    }
    */
}
