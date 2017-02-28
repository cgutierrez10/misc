package com.rezdron.chris.agame;

import android.util.Pair;

import com.rezdron.chris.agame.PhysVert;

import java.util.Vector;

/**
 * Created by Chris on 1/15/2016.
 * Physics vertical class for com.rezdron.chris.agame.Player, will provide some extra options for user input
 */
class PlayerPhys extends TokenPhysics {
    private Vector<Pair<Float,Float>> waveTerms;
    private float phase1 = 19.0f;
    private float phase2 = 24.0f;
    private float amp1   = (phase2/(phase1 + phase2) * 10);
    private float amp2   = (phase1/(phase1 + phase2) * 10);
    private float norm   = (float) (1.0/Math.sqrt(phase1 * phase1 + phase2 * phase2));
    private boolean jump = false;
    private boolean fly = false;

    // Used for short circuiting wave physics check
    // Adding flat 400 as sea level will need to correct this for screen dimensions later
    // So it can exactly match up to rendered water
    private double sealevel = 400.0;

    int tick = 0;


    PlayerPhys(int init_x, int init_y, float init_dvx, float init_dvy) {
        super(init_x, init_y, init_dvx, init_dvy);
        grav = grav * 3;
        //waveTerms.add(new Pair<>(phase1,amp1));
        //waveTerms.add(new Pair<>(phase2,amp2));
        //waveTerms.add(new Pair<>(2.0f,0.35f));
    }

    public Boolean tick()
    {
        // Considering allowing wave jump to modify dvx, and then bringing it back short later to keep
        // Player aligned on a given x on average but allow to flex with jump activity?
        tick++;
        // Update internal variables for x,y and dvx dvy
        dvy = dvy - (grav * grav);
        // Jump check may add to dvy with a bonus, above or below the gravity may impact the effect
        //jumpCheck(tick);
        y = Math.round(dvy) + y;

        return !(y < -100 || y > 1000);
    }

    public void addDvx(float new_dvx)
    {
        dvx = dvx + new_dvx;
    }

    // Gravity directly sets dvy internal, adddvy is only called by player interactions, this could cause pain later be aware
    void addDvy(float new_dvy)
    {
        if (!fly) { dvy = dvy + new_dvy; }
    }

    // Not called yet, still minor debugging to do with the bobbers. Placing this to proto out what it will look like and some hooked up variables
    public void jumpCheck() {
        /*float timecount = ((float) ContentGen.getInstance().tickCount) / 25;
        float x = (this.getX() / (mglRender.getInstance().mScreenWidth)) + (32 / mglRender.getInstance().mScreenWidth); // all sprites are 64px wide before scaling, should be -64 post scaled?
        double wave = (Math.round(amp2*Math.sin((x*phase1 + timecount)*phase1*norm)
                + amp1*Math.sin((x*phase2 - timecount)*phase2*norm)
                - 0.035*Math.sin(timecount*2.5 + x)));
        double scale = (512/mglRender.getInstance().mScreenHeight);
        jump = ((wave * scale * 4.0) + (sealevel / 2)) > this.y;*/


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
        jump = (int) ((wave * scale * 4.0) + (sealevel / 2)) > this.y;

        // Start from sea level instead of adding it in everywhere else
        // Positive transition out of water, dvy increases by a bonus, may need tuning to ensure clearing is instant or does not retrigger again until dvy flips
        if (jump)
        {
            // This should be the tangent of the above waveheight aligned with current dvy
            // Added or removed as a bonus, up to 50% +/- currently can be scaled more

            // Using cosines instead of sines here as derivative for tangent
            addDvy((1.5f - Math.abs((this.x/this.y) - (Math.round(amp2*Math.cos((x*phase1 + timecount)*phase1*norm)
                    + amp1*Math.cos((x*phase2 - timecount)*phase2*norm)
                    - 0.035*Math.cos(timecount*2.5 + x))))) * this.dvy);
            fly = dvy > 0;
        }
    }
}
