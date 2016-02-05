package com.rezdron.chris.agame;

/**
 * Created by Chris on 1/22/2016.
 * First try at creating a fully functional mobile, not intended for release
 */
public class TokenSpider extends Token {
    public TokenSpider(int score) {
        super(score, new PhysVertBounce(10,10,(float) 0.0, (float) 5.0, 150));
        this.gfx_type = "spider";
    }

    public TokenSpider(int score, int x, int y) {
        super(score, new PhysVertBounce(x,y,(float) 0.0, (float) 5.0, 150));
        this.gfx_type = "spider";
    }

    public TokenSpider(int score, TokenPhysics phys)
    {
        super(score, phys);
        this.gfx_type = "spider";
    }

    public void deactivate() {
        active = false;
    }

    @Override
    public void tick() {
        active = phys.tick();
    }

    @Override
    public void onDraw()
    {
        GfxResourceHandler.getInstance().blitAt(this.gfx_type,phys.getX(),phys.getY());
        // Possibly want to do gfxresource handler blit @string at <x,y>
    }
}
