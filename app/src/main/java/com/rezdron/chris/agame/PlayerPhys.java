package com.rezdron.chris.agame;

import android.util.Pair;

import com.rezdron.chris.agame.PhysVert;

import java.util.Vector;

/**
 * Created by Chris on 1/15/2016.
 * Physics vertical class for com.rezdron.chris.agame.Player, will provide some extra options for user input
 */
public class PlayerPhys extends TokenPhysics {
    private Vector<Pair<Float,Float>> waveTerms;
    private float phase1 = 19.0f;
    private float phase2 = 24.0f;
    private float amp1   = (phase2/(phase1 + phase2) * 10);
    private float amp2   = (phase1/(phase1 + phase2) * 10);
    private float norm   = (float) (1.0/Math.sqrt(phase1 * phase1 + phase2 * phase2));
    private boolean jump = false;
    // Used for short circuiting wave physics check
    // Adding flat 400 as sea level will need to correct this for screen dimensions later
    // So it can exactly match up to rendered water
    private double seaLvl = 400.0;
    // TODO: find way to align sea level with rendered sea level for any screen dimensions
    private double maxWave =  ((amp1 + amp2 + 0.35) / 2) + seaLvl;
    private double minWave = (((amp1 + amp2 + 0.35) / 2) * -1) + seaLvl;
    int tick = 0;


    public PlayerPhys(int init_x, int init_y, float init_dvx, float init_dvy) {
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
        //jumpCheck(tick);
        dvy = dvy + (grav * grav);
        y = Math.round(dvy) + y;
        return (y < -100 || y > 1000) ? false : true;
    }

    public void addDvx(float new_dvx)
    {
        dvx = dvx + new_dvx;
    }

    public void addDvy(float new_dvy)
    {
        dvy = dvy + new_dvy;
    }

    public void jumpCheck(int time) {
        // Short circuit and save the processing if not possibly in jump transition
        if (y > (maxWave + seaLvl) || y < (minWave + seaLvl))
        {
            return;
        }
        int i = 0;
        // Start from sea level instead of adding it in everywhere else
        Double waterline = seaLvl;
        for (Pair<Float,Float> wave : waveTerms) {
            if (i == 0) {
                waterline = waterline + (wave.second * Math.sin((x * wave.first + time) * wave.first * norm));
                //                       amp1        *      sin((position.x * phase2 - time) * phase2     * norm)
            } else if (i == 1) {
                waterline = waterline + (wave.second * Math.sin((x * wave.first + time) * wave.first * norm));
            } else {
                // Third wave not going to fit this code very well, 0.35amp fine, time*2.5 + pos.x is a problem to represent as above
                waterline = waterline + (wave.second * Math.sin((time * 2.5 + x )));
            }
            i++;
        }
        if (y > waterline && !jump)
        {
            jump = true;
            i = 0;
            // Waterline now represents the tangent of the wave, not adding sea level
            waterline = 0.0;
            // This should be the tangent of the above waveheight aligned with current dvy
            for (Pair<Float,Float> wave : waveTerms) {
                if (i == 0) {
                    waterline = waterline + (wave.second * Math.cos((x * wave.first + time) * wave.first * norm));
                    //                       amp1        *      sin((position.x * phase2 - time) * phase2     * norm)
                } else if (i == 1) {
                    waterline = waterline + (wave.second * Math.cos((x * wave.first + time) * wave.first * norm));
                } else {
                    // Third wave not going to fit this code very well, 0.35amp fine, time*2.5 + pos.x is a problem to represent as above
                    waterline = waterline + (wave.second * Math.cos((time * 2.5 + x )));
                }
                i++;
            }
            addDvy(1.0f);
        }
        if (y < waterline && jump)
        {
            jump = false;
            i = 0;
            // Waterline now represents the tangent of the wave, not adding sea level
            waterline = 0.0;
            // This should be the tangent of the above waveheight aligned with current dvy
            for (Pair<Float,Float> wave : waveTerms) {
                if (i == 0) {
                    waterline = waterline + (wave.second * Math.cos((x * wave.first + time) * wave.first * norm));
                    //                       amp1        *      sin((position.x * phase2 - time) * phase2     * norm)
                } else if (i == 1) {
                    waterline = waterline + (wave.second * Math.cos((x * wave.first + time) * wave.first * norm));
                } else {
                    // Third wave not going to fit this code very well, 0.35amp fine, time*2.5 + pos.x is a problem to represent as above
                    waterline = waterline + (wave.second * Math.cos((time * 2.5 + x)));
                }
                i++;
            }
            // Waterline is now tangent  of wave at that point
            // TODO: Find unit vector formula for the tangent found above and find formula for alignment of the tangent with the players motion vector
            addDvy(-1.0f);
        }
    }
}
