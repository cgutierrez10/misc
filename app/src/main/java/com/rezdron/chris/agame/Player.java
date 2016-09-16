package com.rezdron.chris.agame;

import android.util.Log;

import com.rezdron.chris.agame.PhysVert;
import com.rezdron.chris.agame.Token;

/**
 * Created by Chris on 1/14/2016.
 * Carries player token specific data including special physics functions
 *
 * Player is a special type of token, it is not part of the tokenhandling main logic
 * The player will remain static wrt the leftmost edge and free to move vertically
 *
 * Player token is managed by gamerun rather than tokenhandler
 */
public class Player extends Token {
    String gfx_type = "player";
    static Player instance = new Player();
    Player()
    {
        super(0, new PlayerPhys(8,128,(float)0.0,(float)-15.0));
        this.gfx_type = "player";
    }

    public void reset() {
        phys.x = 1000;
        phys.y = 128;
        phys.x = 16;
        phys.y = 128;
        phys.dvy = -15.0f;
        phys.dvy = 15.0f;
        this.active = true;
        //instance = new Player();
    }

    public void setAccel(Integer y)
    {
        ((PlayerPhys) phys).addDvy(y);
    }

    public void tick()
    {
        phys.tick();
    }

    public static Player getInstance() {return instance;}

    @Override
    public void deactivate() {
        // Player ded. Cleanup and start transition to gameover and scoring
        this.active = false;
    }

    public void collideWith(Token element) {
        // Not sure if this should be 32 or 64
        if (element.quickCollision(this.getX(),this.getY(),32,32))
        {
            this.deactivate();
        }

    }
    public int getWidth() { return 0;}

    public void onDraw() {
        //Log.d("player","Player drawn at" + String.valueOf(phys.getX()) + "," + String.valueOf(phys.getY()));
        GfxResourceHandler.getInstance().blitAt(this.gfx_type, phys.getX(), phys.getY(), 0);
        //Log.d("token",this.gfx_type + " at " + phys.getX().toString() + " : " + phys.getY().toString());
    }
}
