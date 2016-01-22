package com.rezdron.chris.agame;

import android.content.Context;
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
    Map<String,Drawable> gfxResource = new HashMap<>();
    Context state;
    private static GfxResourceHandler instance = new GfxResourceHandler();
    private Context appContext;

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

    public Drawable getRsx(String request)
    {
        if (gfxResource.containsKey(request)) {
            return gfxResource.get(request);
        }
        else
        {
            // Load the resource on demand
            // If things get choppy or need to be pre cached this function
            // Could be called in the load area to load the necessary graphics ahead of time
            gfxResource.put(request, ResourcesCompat.getDrawable(appContext.getResources(),0,null));
            return gfxResource.get(request);
        }
    }

    public void blitAt(String request, int x, int y)
    {
        // Implement a draw to the current context canvas of resource at location
    }
}
