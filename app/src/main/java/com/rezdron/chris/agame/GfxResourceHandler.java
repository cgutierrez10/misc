package com.rezdron.chris.agame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Chris on 1/12/2016.
 * This file handles loading all the game graphics and storing them internally
 * It will also provide calls to draw them onto a provided canvas
 * With specified location
 *
 * Like TokenHandler this is also a singleton should not waste effort re-loading graphics ever
 */
public class GfxResourceHandler {
    Map<String,Bitmap> gfxResource = new HashMap<>();
    Context state;
    private static GfxResourceHandler instance = new GfxResourceHandler();
    private Context appContext;
    private Canvas rendered;

    /*
    public GfxResourceHandler(Context c)
    {
        state = c;
        // What gfx size to pull? may not be needed best practices unclear
        /*
        DisplayMetrics metrics = new DisplayMetrics();
        if (metrics.densityDpi <= 160)
        {
            // Medium density minimum to support
        }
        else if (metrics.densityDpi == 240)
        {
            // High density
        }
        else if (metrics.densityDpi >= 360)
        {
            // XHigh density, highest that will be supported
        }* /
    }
    */

    public static GfxResourceHandler getInstance() { return instance; }

    public void resetContext(Context c)
    {
        state = c;
    }

    public Bitmap getRsx(String request)
    {
        if (gfxResource.containsKey(request)) {
            return gfxResource.get(request);
        }
        else
        {
            // Load the resource on demand
            // If things get choppy or need to be pre cached this function
            // Could be called in the load area to load the necessary graphics ahead of time
            Resources rsx = appContext.getResources();
            Bitmap loadRsx = BitmapFactory.decodeResource(rsx, rsx.getIdentifier("drawable",request,appContext.getPackageName()));
            gfxResource.put(request, loadRsx);
            return gfxResource.get(request);
        }
    }

    public void setCanvas(Canvas layer)
    {
        rendered = layer;
    }

    public void blitAt(String request, int x, int y)
    {
        // Implement a draw to the current context canvas of resource at location
        Bitmap blit = getRsx(request);
        Rect clipBounds = new Rect();

        clipBounds.set(x, y, x + blit.getHeight(), y + blit.getWidth());
        //clipBounds.offset(x, y);
        //Drawable tokenImg = getRsx(request);
        //tokenImg.setBounds(clipBounds);
        //tokenImg.draw(rendered);
        Paint background = new Paint();
        background.setColor(Color.TRANSPARENT);
        rendered.drawBitmap(blit,x,y,background);

    }
}
