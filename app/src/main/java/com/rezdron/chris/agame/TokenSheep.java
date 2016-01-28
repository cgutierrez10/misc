package com.rezdron.chris.agame;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

/**
 * Created by Chris on 1/22/2016.
 * First try at creating a fully functional mobile, not intended for release
 */
public class TokenSheep extends Token {
    public TokenSheep(int score) {
        super(score, new PhysVert(96,96,(float) 0.0, (float) 0.0));
    }

    @Override
    public void tick() {
        active = phys.tick();
    }

    @Override
    public void onDraw()
    {
        GfxResourceHandler.getInstance().blitAt("sheep",phys.getX(),phys.getY());
        // Possibly want to do gfxresource handler blit @string at <x,y>
    }
}
