package com.rezdron.chris.agame;

import android.util.Log;

/**
 * Created by Chris on 1/22/2016.
 * First try at creating a fully functional mobile, not intended for release
 */
public class TokenBobber extends Token {
    //String gfx_type = "sheep";

    public TokenBobber(int score) {
        super(score, new PhysFloat(10,10,(float) 0.0, (float) 5.0));
    }

    public TokenBobber(int score, int x, int y) {
        super(score, new PhysFloat(x,y,(float) 0.0, (float) 0.0));
        Log.d("token","Adding bobber");
        this.gfx_type = "player";
        this.collide = false;
    }
    /*
    public TokenSheep(int score, TokenPhysics phys)
    {
        super(score, phys);
        this.gfx_type = "sheep";
    }
    */

    @Override
    public void tick() {
        active = phys.tick();
    }

    // Non-collidable.
    public int getWidth() { return 0; }

    // Bobbers won't scroll horizontally, they're handy debuggy objects for now
    // Later if bouy's are needed this could be modified to suit
    // and this shift function override could be removed
    @Override
    public void shift(int offset) {}

    @Override
    public String name() { return "bobber"; }

    @Override
    public void onDraw()
    {
        GfxResourceHandler.getInstance().blitAt(this.gfx_type,phys.getX(),phys.getY(),0);
        //Log.d("token",this.name() + " at " + phys.getX().toString() + " : " + phys.getY().toString());
        // Possibly want to do gfxresource handler blit @string at <x,y>
    }
}
