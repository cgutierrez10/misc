package com.rezdron.chris.agame;

import com.rezdron.chris.agame.PhysVert;

/**
 * Created by Chris on 1/15/2016.
 * Physics vertical class for com.rezdron.chris.agame.Player, will provide some extra options for user input
 */
public class PlayerPhys extends PhysVert {

    public PlayerPhys(int init_x, int init_y, float init_dvx, float init_dvy) {
        super(init_x, init_y, init_dvx, init_dvy);
    }

    public void addDvx(float new_dvx)
    {
        dvx = dvx + new_dvx;
    }

    public void addDvy(float new_dvy)
    {
        dvy = dvy + new_dvy;
    }
}
