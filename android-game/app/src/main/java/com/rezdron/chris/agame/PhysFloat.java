package com.rezdron.chris.agame;

/**
 * Created by Chris on 1/14/2016.
 * Impliments vertical movement only physics as com.rezdron.chris.agame.Player token would use
 */
class PhysFloat extends TokenPhysics {
    private double phase1 = 19.0f;
    private double phase2 = 24.0f;
    private double amp1 = (phase2/(phase1 + phase2) * 10);
    private double amp2 = (phase1/(phase1 + phase2) * 10);
    private double norm = (1.0/Math.sqrt(phase1 * phase1 + phase2 * phase2));
    private int sealevel = 0;

    PhysFloat(int input_x, int input_y, float input_dvx, float input_dvy)
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
        float timecount = ((float) ContentGen.getInstance().tickCount) / 25;
        // Update internal variables for x,y and dvx dvy
        //float x = (this.getX() / (mglRender.getInstance().mScreenWidth)) - 64; // all sprites are 64px wide before scaling, should be -64 post scaled?
        float x = (this.getX() / (mglRender.getInstance().mScreenWidth)) + (32 / mglRender.getInstance().mScreenWidth); // all sprites are 64px wide before scaling, should be -64 post scaled?
        // This needs a scaling factor and a way to sync sea level to align to graphics height and range of motion
        // Looks to be vaguely accurate right now but might be out of time sync?
         double wave = (Math.round(amp2*Math.sin((x*phase1 + timecount)*phase1*norm)
                + amp1*Math.sin((x*phase2 - timecount)*phase2*norm)
                - 0.035*Math.sin(timecount*2.5 + x)));
        double scale = (512/mglRender.getInstance().mScreenHeight);
        y = (int) ((wave * scale * 4.0) + (sealevel / 2));
        // Equation is returning pixel coords, object <x,y> are in world coords need to convert to pixel coords before the function and back afterwards
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
