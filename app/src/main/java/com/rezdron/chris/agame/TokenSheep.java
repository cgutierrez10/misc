package com.rezdron.chris.agame;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * Created by Chris on 1/22/2016.
 * First try at creating a fully functional mobile, not intended for release
 */
public class TokenSheep extends Token {
    public TokenSheep(int score) {
        super(score, new PhysVert(10,10,(float) 0.0, (float) 5.0));
        this.gfx_type = "sheep";
    }

    @Override
    public void tick() {
        active = phys.tick();
    }

    @Override
    public void onDraw()
    {
        Log.d("TokenDraw", "Ondraw called");
        GfxResourceHandler.getInstance().blitAt(this.gfx_type,phys.getX(),phys.getY());
        // Possibly want to do gfxresource handler blit @string at <x,y>
    }
}
